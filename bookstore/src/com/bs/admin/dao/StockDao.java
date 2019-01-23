package com.bs.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.admin.pojo.Stock;

public interface StockDao {
	
	/**  
	
	 * <p>Title: selectBookStock</p>  
	
	 * <p>Description:查询指定Id的书的库存信息 </p>  
	
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月12日  </p> 
	 */
	List<Stock> selectBookStock( Integer bookId,Integer min, Integer max);
	/**  
	
	 * <p>Title: selectBookStockNum</p>  
	
	 * <p>Description: </p>  
	
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月12日  </p> 
	 */
	Integer selectBookStockNum(Integer bookId);
	
	
	
	/**  
	
	 * <p>Title: selectStockNumRange</p>  
	
	 * <p>Description: 查询一定数量的库存</p>  
	
	 * @param min
	 * @param max
	 * @return  
	 * <p> @date 2018年12月13日  </p> 
	 */
	List<Stock>selectStockNumRange( Integer min, Integer max);
	

	/**  
	
	 * <p>Title: dynamicSelect</p>  
	
	 * <p>Description: </p>  
	
	 * @param purchaseBatch
	 * @param bookName
	 * @param startTime
	 * @param endTime
	 * @param storeHouse
	 * @return  
	 * <p> @date 2018年12月13日  </p> 
	 */
	List<Stock> dynamicSelect(String purchaseBatch, String bookName,String  startTime, String  endTime , Integer storeHouseId ,Integer min, Integer max );

	
	List<Stock> selectVStock( String bookName,Integer min,Integer max);
	
	
}

