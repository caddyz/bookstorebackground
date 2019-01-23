package com.bs.admin.dao.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.ActivityDao;
import com.bs.admin.mapper.ActivityMapper;
import com.bs.admin.mapper.ActivityPageDataMapper;
import com.bs.admin.mapper.BookMapper;
import com.bs.admin.pojo.Activity;
import com.bs.admin.pojo.ActivityPageData;
import com.bs.admin.pojo.Book;

@Repository
public class ActivityDaoImpl implements ActivityDao {

	@Autowired
	private ActivityMapper am;
	@Autowired
	private ActivityPageDataMapper pm;
	@Autowired
	private BookMapper bm;

	@Override
	public List<Activity> getActivityName() {
		return am.getActivityNameByActivity();
	}

	@Override
	public Integer addActivity(Activity activity) {
		return am.insertActivity(activity);
	}
	
	@Override
	public List<Activity> getActivityData() {
		return am.getActivityData();
	}

	@Override
	public Activity getActivityIdByActivity(Activity activity) {
		return am.getActivityIdByActivity(activity);
	}

	@Override
	public Integer addActivityBook(Integer activityId, Integer bookId) {
		return am.insertActivityBook(activityId, bookId);
	}

	@Override
	public Integer getIdCountByBookName(ActivityPageData activityPageData) {
		return pm.getIdCountByBookName(activityPageData);
	}

	@Override
	public Integer updateActivity(Activity activity) {
		return am.updateActivityByActivityId(activity);
	}
	
	@Override
	public Integer getActivityIdTotal(Integer activityId) {
		return pm.getActivityIdTotal(activityId);
	}
	
	@Override
	public Integer delActivity(Integer activityId) {
		return am.deleteActivity(activityId);
	}

	@Override
	public Integer addHistoryActivity(ActivityPageData activityPageData) {
		return pm.insertHistoryActivity(activityPageData);
	}

	@Override
	public Integer delActivityBook(Integer activityId, Integer bookId) {
		return am.delActivityBook(activityId, bookId);
	}
	
	@Override
	public Integer getHistoryTotal() {
		return pm.getHistoryTotal();
	}

	@Override
	public List<ActivityPageData> getHistoryActivity(Integer start, Integer end) {
		return pm.getHistoryActivity(start, end);
	}
	
	@Override
	public Integer delHistoryActivity(Integer activityId) {
		return pm.delHistoryActivity(activityId);
	}

	@Override
	public Integer getTotal(String bookName, String activityName, String activityStart, String activityEnd) {
		return pm.getTotal(bookName, activityName, activityStart, activityEnd);
	}
	
	@Override
	public List<ActivityPageData> getAllActivity(Integer start, Integer end, String bookName, String activityName, String activityStart, String activityEnd) {
		return pm.getAllActivity(start, end, bookName, activityName, activityStart, activityEnd);
	}

	@Override
	public Integer getAllTotal(String bookName, String bookCategory, String bookStatus, Double bottomPrice, Double topPrice) {
		return bm.getAllTotal(bookName, bookCategory, bookStatus, bottomPrice, topPrice);
	}
	
	@Override
	public List<Book> getAllBook(String bookName, String bookCategory, String bookStatus, Double bottomPrice, Double topPrice, Integer start, Integer end) {
		return bm.getAllBook(bookName, bookCategory, bookStatus, bottomPrice, topPrice, start, end);
	}
}
