package com.bs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.ActivityDao;
import com.bs.admin.pojo.Activity;
import com.bs.admin.pojo.ActivityPageData;
import com.bs.admin.pojo.Book;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Salary;
import com.bs.admin.service.ActivityService;
import com.bs.admin.util.JsonData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	private ActivityDao ad;

	@Override
	public List<String> getActivityNameByActivity() {
		List<Activity> allActivity = ad.getActivityName();
		List<String> allActivityName = new ArrayList<String>();
		for (Activity activity : allActivity) {
			allActivityName.add(activity.getActivityName());
		}
		return allActivityName;
	}

	@Override
	public PageData<ActivityPageData> getHistoryActivity(Integer pageNum, Integer pageSize) {
		// 获取总条数
		Integer total = ad.getHistoryTotal();
		// 获取开始位置
		Integer start = (pageNum - 1) * pageSize;
		// 获取结束位置
		Integer end = (pageNum <= (total / pageSize)) ? pageSize : total;
		return new PageData<ActivityPageData>(ad.getHistoryActivity(start, end), total);
	}
	
	@Override
	public JsonData delHistoryActivity(List<ActivityPageData> activityPageDatas) {
		Integer index = 0;
		for (ActivityPageData activity : activityPageDatas) {
			if(ad.delHistoryActivity(activity.getActivityId())>0) {
				index++;
			}
		}
		if (index > 0) {
			return new JsonData(null, null, "删除" + index + "条数据成功", true);
		} else {
			return new JsonData(null, null, "删除失败", false);
		}	
	}

	@Override
	public PageData<ActivityPageData> getAllActivity(Integer pageNum, Integer pageSize, String bookName, String activityName, String activityStart, String activityEnd) {
		// 获取总条数
		Integer total = ad.getTotal(bookName, activityName, activityStart, activityEnd);
		// 获取开始位置
		Integer start = (pageNum - 1) * pageSize;
		// 获取结束位置
		Integer end = (pageNum <= (total / pageSize)) ? pageSize : total;
		return new PageData<ActivityPageData>(ad.getAllActivity(start, end, bookName, activityName, activityStart, activityEnd), total);
	}

	@Override
	public PageData<Book> getAllBook(String bookName, String bookCategory, String bookStatus, Double bottomPrice, Double topPrice, Integer pageNum, Integer pageSize) {
		Integer total = ad.getAllTotal(bookName, bookCategory, bookStatus, bottomPrice, topPrice);
		Integer start = (pageNum - 1) * pageSize;
		Integer end = (pageNum <= (total / pageSize)) ? pageSize : total;
		return new PageData<Book>(ad.getAllBook(bookName, bookCategory, bookStatus, bottomPrice, topPrice, start, end), total);
	}

	@Override
	public JsonData addActivity(List<ActivityPageData> activityDatas) {
		Integer index = 0;
		// 从历史活动启用,删除历史活动记录
		for (ActivityPageData activityData : activityDatas) {
			if(activityData.getActivityId()!=null) {
				ad.delHistoryActivity(activityData.getActivityId());
			}
		}
		satrt:for(int i=0;i<activityDatas.size();i++) {
			// 获取活动对象
			Activity activity = new Activity(activityDatas.get(i).getActivityName(), activityDatas.get(i).getActivityDiscount(), activityDatas.get(i).getActivityStart(), activityDatas.get(i).getActivityEnd());
			// 判断活动对象在表中是否存在
			Activity activityId = ad.getActivityIdByActivity(activity);
			// 活动信息不存在
			if (activityId == null) {
				// 添加活动
				Integer addActivity = ad.addActivity(activity);
				// 获取活动id
				Activity addActivityId = ad.getActivityIdByActivity(activity);
				// 循环中间表
				if (addActivity > 0) {
					// 添加数据
					Integer bookActivity = ad.addActivityBook(addActivityId.getActivityId(),activityDatas.get(i).getBookId());
					// 判断是否添加成功
					if (bookActivity > 0) {
						index++;
					}
				} else {
					return new JsonData(null, addActivity, "添加活动时失败", false);
				}
			} else {
				// 判断书的活动数据是否存在
				Integer count = ad.getIdCountByBookName(activityDatas.get(i));
				// 大于0存在书的活动信息
				if (count > 0) {
					i++;
					continue satrt;
					// 存在活动信息————>不存书的活动信息————>添加中间表
				} else {
					// 添加中间表
					Integer bookActivity = ad.addActivityBook(activityId.getActivityId(), activityDatas.get(i).getBookId());
					if (bookActivity > 0) {
						index++;
					}
				}
			}
		}
		if (index > 0) {
			return new JsonData(null, null, "添加" + index + "条数据成功", true);
		} else {
			return new JsonData(null, null, "添加失败", false);
		}	
	}

	@Override
	public JsonData delActivity(List<ActivityPageData> activityPageData) {
		Integer index = 0;
		for (ActivityPageData ap : activityPageData) {
			// 删除中间表
			Integer delActivityBook = ad.delActivityBook(ap.getActivityId(), ap.getBookId());
			// 添加到历史活动
			ad.addHistoryActivity(ap);
			// 获取中间表中是否还存在相关数据
			Integer count = ad.getActivityIdTotal(ap.getActivityId());
			// 不存在
			if (count == 0) {
				// 删除活动信息
				ad.delActivity(ap.getActivityId());
			}
			if (delActivityBook > 0) {
				index++;
			}
		}
		if (index > 0) {
			return new JsonData(null, null, "删除" + index + "条数据成功", true);
		} else {
			return new JsonData(null, null, "删除失败", false);
		}
	}

	@Override
	public JsonData updateActivity(List<ActivityPageData> activityPageDatas) {
		Integer index = 0;
		for (ActivityPageData ap : activityPageDatas) {
			Activity activity = new Activity(ap.getActivityId(), ap.getActivityName(), ap.getActivityDiscount(), ap.getActivityStart(), ap.getActivityEnd());
			Integer update = ad.updateActivity(activity);
			if(update>0) {
				index++;
			}
		}
		if (index > 0) {
			return new JsonData(null, null, "修改" + index + "条数据成功", true);
		} else {
			return new JsonData(null, null, "修改失败", false);
		}
	}

	@Override
	public PageData<Activity> getActivityData(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Activity> rows = ad.getActivityData();
		Long total = new PageInfo<Activity>(rows).getTotal();
		int total1 = total.intValue();		
		return new PageData<>(rows, total1);
	}

	@Override
	public JsonData insertActivity(List<Activity> ac) {
		Integer index = 0;
		Activity add = null;
		ss:for(int i=0;i<ac.size();i++) {
			Activity activity = ac.get(i);
			Activity activityId = ad.getActivityIdByActivity(activity);
			if (activityId == null) {
				// 添加活动
				Integer addActivity = ad.addActivity(activity);
				if(addActivity>0) {
					index++;
				}
				// 获取活动id
				add = ad.getActivityIdByActivity(activity);
			}else {
				i++;
				continue ss;
			}
		}
		if (index > 0) {
			return new JsonData(null, null, "添加成功", true);
		} else {
			return new JsonData(null, null, "修改失败", false);
		}
	}
}
