package com.bs.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.admin.pojo.PurchaseItem;

public interface PurchaseItemDao {

	/**  
	
	 * <p>Title: insertPurchaseItems</p>  
	
	 * <p>Description: 批量插入数据</p>  
	
	 * @param list
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	Integer insertPurchaseItems(List<PurchaseItem> list);
	/**  
	
	 * <p>Title: deletPurchaseItemByIdBatch</p>  
	
	 * <p>Description: 批量删除指定数据</p>  
	
	 * @param list
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	Integer deletePurchaseItemByIdBatch(List <Long>list);
	
	/**  
	
	 * <p>Title: deletePurchaseItemByPurchaseId</p>  
	
	 * <p>Description:删除指定的purchase的数据 </p>  
	
	 * @param purchaseId
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	Integer deletePurchaseItemByPurchaseId(Integer purchaseId );
	
	
	

	/**  
	
	 * <p>Title: updatePurchaseItem</p>  
	
	 * <p>Description: 修改一条信息</p>  
	
	 * @param purchaseItem
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	Integer updatePurchaseItem(PurchaseItem purchaseItem);
	
	
	/**  
	
	 * <p>Title: selectByPurchase</p>  
	
	 * <p>Description: 通过purchase id查询 </p>  
	
	 * @param purchaseId
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	List<PurchaseItem>selectByPurchaseId(Integer purchaseId);
	/**  
	
	 * <p>Title: selectByBookId</p>  
	
	 * <p>Description:通过book id查询 </p>  
	
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	List<PurchaseItem>selectByBookId( Integer bookId);
/**  
	
	 * <p>Title: selectPurchaseItemById</p>  
	
	 * <p>Description: 通过id获取项目</p>  
	
	 * @param purchaseItemId
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	PurchaseItem selectPurchaseItemById(Long purchaseItemId);
	
	
	/**  
	
	 * <p>Title: dynamicSelectPI3</p>  
	
	 * <p>Description: 跟据批次或者书名查询待入库的书目 </p>  
	
	 * @param purchaseBatch
	 * @param bookName
	 * @return  
	 * <p> @date 2018年12月17日  </p> 
	 */
	List<PurchaseItem> dynamicSelectPI3( String purchaseBatch, String bookName);
}

