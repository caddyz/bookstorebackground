package com.bs.admin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.PurchaseItem;

public interface PurchaseItemService {

	/**  
	
	 * <p>Title: postPurchaseItems</p>  
	
	 * <p>Description:插入一条数据 </p>  
	
	 * @param PIlist json  数据
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	Integer postPurchaseItems(String PIlist);

	/**  
	
	 * <p>Title: delPurchaseItemByIdBatch</p>  
	
	 * <p>Description: 批量删除指定id的数据 </p>  
	
	 * @param list
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	Integer delPurchaseItemByIdBatch(String idList);

	/**  
	
	 * <p>Title: delPurchaseItemByPurchaseId</p>  
	
	 * <p>Description:删除指定采购id的数据 </p>  
	
	 * @param purchaseId
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	Integer delPurchaseItemByPurchaseId(Integer purchaseId);

	/**  
	
	 * <p>Title: putPurchaseItem</p>  
	
	 * <p>Description: 修改指定数据 </p>  
	
	 * @param purchaseItemId 定位符
	 * @param purchaseId  操作权限控制
	 * @param bookId 修改的内容
	 * @param purchaseNum 
	 * @param purchasePrice
	 * @return  
	 * <p> @date 2018年12月3日  </p> 
	 */
	Integer putPurchaseItem(Integer purchaseItemId, Integer purchaseId, Integer bookId, Integer purchaseNum,
			double purchasePrice);

	PageData<PurchaseItem> getByPurchaseId(Integer purchaseId,Integer currentPage,	Integer pageSize);

	PageData<PurchaseItem> getByBookId(Integer bookId,Integer currentPage,	Integer pageSize);
	
	/**  
	
	 * <p>Title: dynamicSelectPI3</p>  
	
	 * <p>Description: 跟据批次或者书名查询待入库的书目 </p>  
	
	 * @param purchaseBatch
	 * @param bookName
	 * @return  
	 * <p> @date 2018年12月17日  </p> 
	 */
	PageData<PurchaseItem> dynamicGetPI3(String purchaseBatch,String bookName,Integer currentPage,	Integer pageSize);

	PurchaseItem getPurchaseItemById(Long purchaseItemId);

}
