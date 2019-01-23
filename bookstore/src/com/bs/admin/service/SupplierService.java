package com.bs.admin.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.QueryBoxData;
import com.bs.admin.pojo.Supplier;

/**
 * 
 * <p>
 * Title: SupplierService
 * </p>
 * 
 * <p>
 * Description: cud+3R
 * </p>
 * 
 * @author 王顺坤
 * 
 *         <p>
 * @date 2018年12月2日
 *       </p>
 * 
 */
public interface SupplierService {

	/**
	 * 
	 * <p>
	 * Title: postSupplier
	 * </p>
	 * 
	 * <p>
	 * Description: 输入一个supplier 的一些参数来添加一个supplier
	 * </p>
	 * 
	 * @param supplierName
	 * @param supplierPhone
	 * @param supplierAddress
	 * @param supplierContact
	 * @param supplierContactPhone
	 * @param cooperateDate
	 * @param cooperateStatus
	 * @return
	 *         <p>
	 * 		@date 2018年12月5日
	 *         </p>
	 */
	Integer postSupplier(String supplierName, String supplierPhone, String supplierAddress, String supplierContact,
			String supplierContactPhone, String cooperateDate, Integer cooperateStatus);

	/**
	 * 
	 * <p>
	 * Title: delSupplierById
	 * </p>
	 * 
	 * <p>
	 * Description:删除一个供应商记录，前提是没有查询到这个供应商的合作记录
	 * </p>
	 * 
	 * @param supplierId
	 * @return
	 *         <p>
	 * @date 2018年12月2日
	 *       </p>
	 */
	Integer delSupplierById(Integer supplierId);


	/**  
	
	 * <p>Title: putSupplierById</p>  
	
	 * <p>Description: 修改</p>  
	
	 * @param supplierId
	 * @param supplierName
	 * @param supplierPhone
	 * @param supplierAddress
	 * @param supplierContact
	 * @param supplierContactPhone
	 * @param cooperateDate
	 * @param cooperateStatus
	 * @return  
	 * <p> @date 2018年12月5日  </p> 
	 */
	Integer putSupplierById(Integer supplierId, String supplierName, String supplierPhone, String supplierAddress,
			String supplierContact, String supplierContactPhone, String cooperateDate, Integer cooperateStatus);

	/**
	 * 
	 * <p>
	 * Title: getCooperateCountById
	 * </p>
	 * 
	 * <p>
	 * Description: 获取某个id的供应商的合作次数
	 * </p>
	 * 
	 * @param supplierId
	 * @return
	 *         <p>
	 * @date 2018年12月2日
	 *       </p>
	 */
	Integer getCooperateCountById(Integer supplierId);

	/**
	 * 
	 * <p>
	 * Title: getBySupplierId
	 * </p>
	 * 
	 * <p>
	 * Description: 通过id值获取某个供应商的信息
	 * </p>
	 * 
	 * @param supplierId
	 * @return
	 *         <p>
	 * @date 2018年12月2日
	 *       </p>
	 */

	/**
	 * 
	 * <p>
	 * Title: dynamicGet
	 * </p>
	 * 
	 * <p>
	 * Description: 动态获取一些供应商信息
	 * </p>
	 * 
	 * @param supplierName
	 *            模糊查询供应商的名字
	 * @param cooperateStatus
	 *            供应商状态的字符串
	 * @param startDate
	 *            合作时间的起始查询条件
	 * @param endDate
	 *            合作时间的结束查询条件
	 * @param currentPage
	 *            目前的页面
	 * @param pageSize
	 *            每个页面显示的数据量
	 * @return
	 *         <p>
	 * @date 2018年12月2日
	 *       </p>
	 */
	PageData<Supplier> dynamicGet(String supplierName, String cooperateStatus, String startDate, String endDate,
			int currentPage, int pageSize);

	/**
	 * 
	 * <p>
	 * Title: getBySupplierId
	 * </p>
	 * 
	 * <p>
	 * Description: 通过id值获取对象
	 * </p>
	 * 
	 * @param supplierId
	 * @return supplier对象
	 *         <p>
	 * @date 2018年12月2日
	 *       </p>
	 */
	Supplier getBySupplierId(Integer supplierId);
	
	List<QueryBoxData> getSupplierOnLine();

}
