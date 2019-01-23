package com.bs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.admin.dao.SupplierDao;
import com.bs.admin.pojo.CategoryOrStatus;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.QueryBoxData;
import com.bs.admin.pojo.Supplier;
import com.bs.admin.service.SupplierService;
import com.bs.admin.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierDao sd;

	/**
	 * 
	 * <p>
	 * Title: transferSupplier
	 * </p>
	 * 
	 * <p>
	 * Description: 将string 状态转化为 integer 状态
	 * </p>
	 * 
	 * @param supplier
	 * @param status
	 * @return
	 *         <p>
	 * @date 2018年12月2日
	 *       </p>
	 */
	public Supplier transferSupplier(Supplier supplier, String status) {
		switch (status) {
		case "未合作":
			supplier.setCooperateStatus(0);
			break;
		case "合作中":
			supplier.setCooperateStatus(1);
			break;
		case "合作过":
			supplier.setCooperateStatus(2);
			break;
		default:
			return supplier;
		}
		return supplier;
	}



	@Override
	public Integer postSupplier(String supplierName, String supplierPhone, String supplierAddress,
			String supplierContact, String supplierContactPhone, String cooperateDate, Integer cooperateStatus) {
		
		Supplier supplier = new Supplier(supplierName, supplierPhone, supplierAddress, supplierContact,
				supplierContactPhone, DateUtil.changeDateString(cooperateDate), cooperateStatus);
	System.out.println(supplier);
		
		return sd.insertSupplier(supplier);
	}

	@Override
	public Integer delSupplierById(Integer supplierId) {
		Integer count = this.getCooperateCountById(supplierId);
		
		
		if (count == 0) {
			System.out.println(count);
			return sd.deleteSupplierById(supplierId);
		}

		return -1;
	}

	@Override
	public Integer putSupplierById(Integer supplierId, String supplierName, String supplierPhone,
			String supplierAddress, String supplierContact, String supplierContactPhone, String cooperateDate,
			Integer cooperateStatus) {
		
		cooperateDate=DateUtil.changeDateString(cooperateDate);
		
		Supplier supplier = new Supplier(supplierId, supplierName, supplierPhone, supplierAddress, supplierContact, supplierContactPhone, cooperateDate,cooperateStatus);
	
		System.out.println(supplier);
	
		return sd.updateSupplierById(supplier);
	}
	

	@Override
	public Supplier getBySupplierId(Integer supplierId) {
		return sd.selectBySupplierId(supplierId);
	}

	@Override
	public Integer getCooperateCountById(Integer supplierId) {

		return sd.selectCooperateCountById(supplierId);
	}

	@Override
	public PageData<Supplier> dynamicGet(String supplierName, String cooperateStatus, String startDate, String endDate,
			int currentPage, int pageSize) {

		Supplier supplier = new Supplier();
		supplier.setSupplierName(supplierName);
		
		
		
		
		if(cooperateStatus!=null)
		{supplier = this.transferSupplier(supplier, cooperateStatus);
		}
	
	
		PageHelper.startPage(currentPage, pageSize);
		List<Supplier> list = sd.dynamicSelect(supplier, startDate, endDate);
		Long total2 = new PageInfo<Supplier>(list).getTotal();
		int total = total2.intValue();
	
		PageData<Supplier> pagedata = new PageData<>(list, total);
		return pagedata;
	}



	@Override
	public List<QueryBoxData> getSupplierOnLine() {
		Supplier supplier = new Supplier();
		supplier.setCooperateStatus(1);
		
		List<Supplier> list = sd.dynamicSelect(supplier, null, null);
		List<QueryBoxData> rows = new ArrayList<QueryBoxData>();
		int size = list.size();
		QueryBoxData cos=null;
		for(int i=0;i<size;i++){
			cos=new QueryBoxData(list.get(i).getSupplierId(), list.get(i).getSupplierName(), true);
			rows.add(cos);
		}
			
		return rows;
	}






}
