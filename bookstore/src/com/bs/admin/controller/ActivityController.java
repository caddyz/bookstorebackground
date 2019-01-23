package com.bs.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.Activity;
import com.bs.admin.pojo.ActivityPageData;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Purchase;
import com.bs.admin.pojo.QueryBoxData;
import com.bs.admin.service.ActivityService;
import com.bs.admin.service.BookService;
import com.bs.admin.util.DateUtil;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping(value = "activity")
public class ActivityController {
	@Autowired
	private ActivityService as;
	@Autowired
	private BookService bs;

	/**
	 *    转到页面
	 * 
	 * @return 转到活动管理页面
	 */
	@RequestMapping("pagination")
	public String toEmpPage() {
		return "ActivityManagement";
	}

	/**
	 *   获取活动名
	 * 
	 * @return 活动名的json数据
	 */
	@RequestMapping(value = "allActivityName", produces = "text/html;charset=UTF-8")
	public @ResponseBody String allActivityName() {
		List<String> allActivityName = as.getActivityNameByActivity();
		List<QueryBoxData> allActivityNameData = new ArrayList<QueryBoxData>();
		for (int i = 0; i < allActivityName.size(); i++) {
			allActivityNameData.add(new QueryBoxData(i, allActivityName.get(i), false));
		}
		return JSON.toJSONString(allActivityNameData);
	}

	/**
	 *   获取书名
	 * 
	 * @return 书名的json数据
	 */
	@RequestMapping(value = "allBookName", produces = "text/html;charset=UTF-8")
	public @ResponseBody String allBookName() {
		List<String> allBookName = bs.getBookName();
		List<QueryBoxData> allBookNameData = new ArrayList<QueryBoxData>();
		for (int i = 0; i < allBookName.size(); i++) {
			allBookNameData.add(new QueryBoxData(i, allBookName.get(i), false));
		}
		return JSON.toJSONString(allBookNameData);
	}
	
	/**
	 * 查询活动
	 * 
	 * @return 页面json数据
	 */
	@RequestMapping(value = "allActivityBook/{pageNum}/{pageSize}", produces = "text/html;charset=UTF-8")
	public @ResponseBody String allActivity(
			@RequestParam("bookName") String bookName,
			@RequestParam("activityName") String activityName,
			@RequestParam("activityStart") String activityStart,
			@RequestParam("activityEnd") String activityEnd,
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize) {
		return JSON.toJSONString(as.getAllActivity(pageNum, pageSize, bookName, activityName, activityStart, activityEnd));
	}	
			
	/**  
	 * @Title: createActivity  
	 * @Description: 进入到创建活动页面
	 * @return 活动页面jsp
	 * @date 2018年12月13日
	 */
	@RequestMapping(value = "create")
	public String createActivity() {
		return "CreateActivity";	
	}
	
	/**  
	 * <p>Title: getAllBook</p>  
	 * <p>Description: 添加活动时获取书的页面数据</p>  
	 * @param bookName 		书名
	 * @param bookCategory 	类型
	 * @param bookStatus 	状态
	 * @param bottomPrice 	最低价
	 * @param topPrice 		最高价
	 * @param pageNum 		页数
	 * @param pageSize		每页数据
	 * @return  			json数据
	 * <p> @date 2018年12月13日  </p> 
	 */
	@RequestMapping(value = "getAllBook/{pageNum}/{pageSize}", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getAllBook(
			@RequestParam("bookName") String bookName,
			@RequestParam("bookCategory") String bookCategory,
			@RequestParam("bookStatus") String bookStatus,
			@RequestParam("bottomPrice") Double bottomPrice,
			@RequestParam("topPrice") Double topPrice,
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize) {
		return JSON.toJSONString(as.getAllBook(bookName, bookCategory, bookStatus, bottomPrice, topPrice, pageNum, pageSize));
	}
	
	/**  
	 * <p>Title: insertActivity</p>  
	 * <p>Description: 批量添加活动</p>  
	 * @param activityPageDatas 添加的集合
	 * @return JsonData结果集
	 * <p> @date 2018年12月14日  </p> 
	 */
	@SystemControllerLog(type=2, description="批量添加活动信息")
	@RequestMapping(value = "addActivity",produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonData addActivity(
			@RequestBody List<ActivityPageData> activityPageDatas) {	
		return as.addActivity(activityPageDatas);	
	}
	
	/**  
	 * <p>Title: deleteActivity</p>  
	 * <p>Description: 批量删除活动</p>  
	 * @param activityPageDatas 删除的集合
	 * @return  JsonData结果集
	 * <p> @date 2018年12月14日  </p> 
	 */
	@SystemControllerLog(type=2, description="批量删除活动信息")
	@RequestMapping(value = "delActivity",produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonData delActivity(
			@RequestBody List<ActivityPageData> activityPageDatas) {	
		return as.delActivity(activityPageDatas);
	}
	
	/**
	 * <p>Title: updateActivity</p>  
	 * <p>Description: 批量修改活动</p>  
	 * @param updatedData 修改的集合
	 * @return JsonData结果集
	 * <p> @date 2018年12月13日  </p> 
	 */
	@SystemControllerLog(type=2, description="批量编辑活动信息")
	@RequestMapping(value = "updateActivity",produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonData updateActivity(
			@RequestBody List<ActivityPageData> updatedData) {
		for (ActivityPageData ap : updatedData) {
			ap.setActivityStart(DateUtil.changeDateString(ap.getActivityStart()));
			ap.setActivityEnd(DateUtil.changeDateString(ap.getActivityEnd()));
		}
		return as.updateActivity(updatedData);	
	}
	
	/**  
	 * @Title: historyActivity  
	 * @Description: 进入到历史活动页面
	 * @return 历史活动页面jsp
	 * @date 2018年12月17日
	 */
	@RequestMapping(value = "historyActivity")
	public String historyActivity() {
		return "HistoryActivity";	
	}
	
	/**  
	 * @Title: getHistory
	 * @Description: 查询历史活动
	 * @param pageNum
	 * @param pageSize
	 * @return  取消的活动的集合
	 * @date 2018年12月17日 
	 */
	@RequestMapping(value = "getHistoryActivity/{pageNum}/{pageSize}", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getHistoryActivity(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize){	
	return JSON.toJSONString(as.getHistoryActivity(pageNum, pageSize));	
	}
	
	/**  
	 * <p>Title: deleteHistory</p>  
	 * <p>Description: 批量删除历史活动</p>  
	 * @param activityPageDatas
	 * @return  
	 * <p> @date 2018年12月17日  </p> 
	 */
	@SystemControllerLog(type=2, description="批量删除历史活动信息")
	@RequestMapping(value = "delHistory",produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonData deleteHistory(
			@RequestBody List<ActivityPageData> activityPageDatas) {	
		return as.delHistoryActivity(activityPageDatas);
	}
	
	@RequestMapping(value = "allActivity/{pageNum}/{pageSize}", produces = "application/json;charset=UTF-8")
	public @ResponseBody PageData<Activity> getActivityData(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize) {
		return as.getActivityData(pageNum, pageSize);
	}
	
	@SystemControllerLog(type=2, description="添加活动信息")
	@RequestMapping(value = "insertActivity",produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonData insertActivity(
			@RequestBody List<Activity> insert) {
		for (Activity ac : insert) {
			if(ac.getActivityStart()!=null || !"".equals(ac.getActivityStart()) && ac.getActivityEnd()!=null || !"".equals(ac.getActivityEnd())) {
			ac.setActivityStart(DateUtil.changeDateString(ac.getActivityStart()));
			ac.setActivityEnd(DateUtil.changeDateString(ac.getActivityEnd()));
			}else {
				return new JsonData(null, null, null, false);
			}
		}
		return as.insertActivity(insert);
	}
}
