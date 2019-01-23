package com.bs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.crypto.interfaces.PBEKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.admin.dao.PurchaseDao;
import com.bs.admin.pojo.Employee;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Purchase;
import com.bs.admin.pojo.QueryBoxData;
import com.bs.admin.pojo.Supplier;
import com.bs.admin.service.FinancialService;
import com.bs.admin.service.PurchaseService;
import com.bs.admin.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	@Autowired
	private PurchaseDao pd;
	@Autowired
	private FinancialService fs;

	/**
	 * 
	 * <p>
	 * Title: transferStatus
	 * </p>
	 * 
	 * <p>
	 * Description: 将字符串转换为订单状态的方法
	 * </p>
	 * 
	 * @param purchase
	 * @param status
	 * @return
	 *         <p>
	 * @date 2018年12月2日
	 *       </p>
	 */
	public Purchase transferStatus(Purchase purchase, String purchaseStatus) {
		switch (purchaseStatus) {
		case "计划中":
			purchase.setPurchaseStatus(1);
			break;
		case "进行中":
			purchase.setPurchaseStatus(2);

			break;
		case "待入库":
			purchase.setPurchaseStatus(3);
			break;
		case "已完成":
			purchase.setPurchaseStatus(4);
			break;
		case "已取消":
			purchase.setPurchaseStatus(0);
			break;

		default:
		
			return purchase;
		}

		return purchase;
	}

	/**
	 * 
	 * <p>
	 * Title: loadObj
	 * </p>
	 * 
	 * <p>
	 * Description: 将两个对象封装后加载入purchase中
	 * </p>
	 * 
	 * @param purchase
	 * @param employee
	 * @param supplier
	 * @return
	 *         <p>
	 * @date 2018年12月2日
	 *       </p>
	 */
	public Purchase loadObj(Purchase purchase, Integer employee, Integer supplier) {
		if (employee != null) {
			Employee emp = new Employee();
			emp.setEmpId(employee.longValue());
			purchase.setEmployee(emp);
		}
		if (supplier != null) {
			Supplier sup = new Supplier();
			sup.setSupplierId(supplier);
			purchase.setSupplier(sup);
		}

		return purchase;
	}

	@Override
	public Integer postPurchase(String purchaseBatch, String purchaseDate, Integer employee, Integer supplier
		, String purchaseLog) {

		// 判断下面字段是否为空，为空则不能插入数据，数据库要求
		if (purchaseBatch == null || purchaseBatch.length() == 0) {
			return 0;
		}
		if (purchaseDate == null || purchaseDate.length() == 0) {
			return 0;
		}

		Purchase purchase = new Purchase();
		purchase.setPurchaseDate(DateUtil.changeDateString(purchaseDate));
	
		purchase.setPurchaseStatus(1);
		if (purchaseLog != null && purchaseLog.length() != 0) {
			purchase.setPurchaseLog(purchaseLog);
		}
		purchase = this.loadObj(purchase, employee, supplier);
		purchase.setPurchaseBatch(purchaseBatch);
	
		
		
		System.out.println(purchase);
		
		
		return pd.insertPurchase(purchase);

	}

	@Override
	public Integer putPurchase(Integer purchaseId, String purchaseBatch, String purchaseDate, Integer employee,
			Integer supplier, String purchaseStatus, String purchaseLog) {
		if (purchaseId == null) {
			System.out.println("id为空");
			return 0;
		}
		Purchase purchase = new Purchase();
		purchase.setPurchaseId(purchaseId);
		purchase.setPurchaseBatch(purchaseBatch);
		purchase.setPurchaseDate(DateUtil.changeDateString(purchaseDate));
		purchase = this.transferStatus(purchase, purchaseStatus);
		purchase = this.loadObj(purchase, employee, supplier);
		System.out.println("22"+purchase);
		return pd.updatePurchase(purchase);
	}

	@Override
	public Integer delPurchaseById(Integer purchaseId) {

		return pd.deletePurchaseById(purchaseId);
	}

	@Override
	public Purchase getPurchaseById(Integer purchaseId) {

		return pd.selectPurchaseById(purchaseId);
	}

	@Override
	public PageData<Purchase> dynamicGet(String purchaseBatch, String startDate, String endDate, Integer employee,
			Integer supplier, String purchaseStatus, Integer pageNum, Integer pageSize) {
		

		Purchase purchase = new Purchase();
		if(!purchaseBatch.equals("全部"))
		purchase.setPurchaseBatch(purchaseBatch);
		purchase = this.transferStatus(purchase, purchaseStatus);
		purchase = this.loadObj(purchase, employee, supplier);
			PageHelper.startPage(pageNum, pageSize);
		 List<Purchase> list = pd.dynamicSelect(purchase, startDate, endDate);
		 Long total=new PageInfo<>(list).getTotal();
		 PageData<Purchase>pageData=new PageData<Purchase>(list, total.intValue());
		 
		return pageData;
		
		
		
	}

	@Override
	public List<QueryBoxData> getPurchaseBatch() {
		// set 集用于去重

		List<String> str = pd.selectPurchaseBatch();

		Integer i=1;
		ArrayList<QueryBoxData> list = new  ArrayList<QueryBoxData>();
		
		QueryBoxData data =null;
		list.add(new QueryBoxData(0,"全部",false));
		for( String item:str){
			data = new QueryBoxData(i++, item, false);
			list.add(data);
		}
		
		return  list;
	}

	@Override
	public Double getPurchasePay(Integer purchaseId) {
		return pd.selectPurchasePay(purchaseId);
	}

}
