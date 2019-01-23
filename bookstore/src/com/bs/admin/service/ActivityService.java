package com.bs.admin.service;

import java.util.List;

import com.bs.admin.pojo.Activity;
import com.bs.admin.pojo.ActivityPageData;
import com.bs.admin.pojo.Book;
import com.bs.admin.pojo.PageData;
import com.bs.admin.util.JsonData;

public interface ActivityService {

	/**
	 * 查询所有活动名
	 * 
	 * @return 所有活动名的集合
	 */
	List<String> getActivityNameByActivity();

	/**  
	 * <p>Title: getAllBook</p>  
	 * <p>Description: 获取书的数据</p>  
	 * @param bookName		书名
	 * @param bookCategory	类型
	 * @param bookStatus	状态
	 * @param bottomPrice	最低价
	 * @param topPrice		最高价
	 * @param pageNum		页数
	 * @param pageSize		每页条数
	 * @return  
	 * <p> @date 2018年12月13日  </p> 
	 */
	PageData<Book> getAllBook(String bookName, String bookCategory, String bookStatus, Double bottomPrice, Double topPrice, Integer pageNum, Integer pageSize);

	/**  
	 * <p>Title: getHistoryActivity</p>  
	 * <p>Description: 分页查询历史活动</p>  
	 * @param pageNum
	 * @param pageSize
	 * @return  
	 * <p> @date 2018年12月17日  </p> 
	 */
	PageData<ActivityPageData> getHistoryActivity(Integer pageNum,Integer pageSize);
	
	/**  
	 * <p>Title: delHistoryActivity</p>  
	 * <p>Description: 批量删除历史活动</p>  
	 * @param activityId
	 * @return  
	 * <p> @date 2018年12月17日  </p> 
	 */
	JsonData delHistoryActivity(List<ActivityPageData> activityId);
	
	/**  
	 * <p>Title: getAllActivity</p>  
	 * <p>Description: 分页查询活动数据</p>  
	 * @param pageNum
	 * @param pageSize
	 * @param bookName
	 * @param activityName
	 * @param activityStart
	 * @param activityEnd
	 * @return  
	 * <p> @date 2018年12月17日  </p> 
	 */
	PageData<ActivityPageData> getAllActivity(Integer pageNum, Integer pageSize,String bookName, String activityName,String activityStart ,String activityEnd);
	
	/**  
	 * <p>Title: insertActivity</p>  
	 * <p>Description: 批量添加活动</p>  
	 * @param bookData 活动与书的数据集合
	 * @return  
	 * <p> @date 2018年12月14日  </p> 
	 */
	JsonData addActivity(List<ActivityPageData> datas);
	
	/**  
	 * <p>Title: delleteActivity</p>  
	 * <p>Description: 批量删除</p>  
	 * @param activityPageDatas 数据集合
	 * @return  
	 * <p> @date 2018年12月14日  </p> 
	 */
	JsonData delActivity(List<ActivityPageData> activityPageDatas);
	
	/**  
	 * <p>Title: getActivityData</p>  
	 * <p>Description: 获取所有的活动数据</p>  
	 * @param pageNum
	 * @param pageSize
	 * @return  
	 * <p> @date 2018年12月21日  </p> 
	 */
	PageData<Activity> getActivityData(Integer pageNum, Integer pageSize);
	
	/**  
	 * <p>Title: insertActivity</p>  
	 * <p>Description: 添加活动数据</p>  
	 * @param activities
	 * @return  
	 * <p> @date 2018年12月21日  </p> 
	 */
	JsonData insertActivity(List<Activity> activities);
	
	// 批量修改
	JsonData updateActivity(List<ActivityPageData> activityPageDatas);

}
