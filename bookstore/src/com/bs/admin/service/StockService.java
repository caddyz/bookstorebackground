package com.bs.admin.service;



import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Stock;

public interface StockService {

	PageData<Stock> getStockByBookId( Integer bookId ,Integer min, Integer max, String order,Integer currentPage,	Integer pageSize);
	
	
	
	Integer getStockNumByBookId(Integer bookId);
	
	
	
	/**  
	
	 * <p>Title: getStockNumRange</p>  
	
	 * <p>Description: 查询视图</p>  
	
	 * @param min
	 * @param max
	 * @return  
	 * <p> @date 2018年12月13日  </p> 
	 */
	PageData<Stock>getStockNumRange( Integer min, Integer max,Integer currentPage,	Integer pageSize);
	

	/**  
	
	 * <p>Title: dynamicSelect</p>  
	
	 * <p>Description:  动态查询库存情况获取详细信息</p>  
	
	 * @param purchaseBatch
	 * @param bookName
	 * @param startTime
	 * @param endTime
	 * @param storeHouseId
	 * @return  
	 * <p> @date 2018年12月13日  </p> 
	 */
	PageData<Stock> dynamicGet(String purchaseBatch, String bookName,String  startTime, String  endTime , Integer storeHouseId ,Integer min, Integer max,Integer currentPage,	Integer pageSize);

	
	PageData<Stock> getVStock(String bookName,Integer min,Integer max, String order,Integer currentPage,Integer pageSize);
	
}
