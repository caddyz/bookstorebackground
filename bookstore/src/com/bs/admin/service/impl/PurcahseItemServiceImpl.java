package com.bs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bs.admin.dao.PurchaseDao;
import com.bs.admin.dao.PurchaseItemDao;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.PurchaseItem;
import com.bs.admin.pojo.purchaseInsert;
import com.bs.admin.service.PurchaseItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class PurcahseItemServiceImpl implements PurchaseItemService {
	@Autowired
	private PurchaseItemDao pid;
	@Autowired
	private PurchaseDao pd;

	@Override
	public Integer postPurchaseItems(String PIList) {
		// 
		List<purchaseInsert> list  =JSON.parseArray(PIList,purchaseInsert.class);
		
		ArrayList<PurchaseItem> pItems = new ArrayList<PurchaseItem>();
		for(purchaseInsert pi:list){
			System.out.println(pi);
			pItems.add(pi.createPurchaseItem());
		}
		
		
		return pid.insertPurchaseItems(pItems);
	}

	@Override
	public Integer delPurchaseItemByIdBatch(String idList) {

		String substring = idList.substring(1, idList.length()-1);
		String[] split = substring.split(",");
		ArrayList<Long> list = new ArrayList<Long>();
		for(String i:split){
			list.add(Long.parseLong(i));
		}
		

		return pid.deletePurchaseItemByIdBatch(list);
	}

	@Override
	public Integer delPurchaseItemByPurchaseId(Integer purchaseId) {

		// 该方法仅仅可以在采购单状态为取消的时候执行

		return pid.deletePurchaseItemByPurchaseId(purchaseId);
	}

	@Override
	public Integer putPurchaseItem(Integer purchaseItemId, Integer purchaseId, Integer bookId, Integer purchaseNum,
			double purchasePrice) {
		//当订单状态为已完成就不可以修改了，且订单字段不能修改
		PurchaseItem purchaseItem = new PurchaseItem();
		return pid.updatePurchaseItem(purchaseItem);
	}

	
	@Override
	public PurchaseItem getPurchaseItemById(Long purchaseItemId) {
		
		
		
		return pid.selectPurchaseItemById(purchaseItemId);
	}

	@Override
	public PageData<PurchaseItem> getByPurchaseId(Integer purchaseId, Integer currentPage, Integer pageSize) {

		PageHelper.startPage(currentPage, pageSize);
		List<PurchaseItem> list = pid.selectByPurchaseId(purchaseId);
		PageInfo<PurchaseItem>pageInfo=new PageInfo<>(list);
		Long total = pageInfo.getTotal();
		PageData<PurchaseItem>data=new PageData<>(list,total.intValue());
		
		
		return data;
	}

	@Override
	public PageData<PurchaseItem> getByBookId(Integer bookId, Integer currentPage, Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<PurchaseItem> list = pid.selectByBookId(bookId);
		PageInfo<PurchaseItem>pageInfo=new PageInfo<>(list);
		Long total = pageInfo.getTotal();
		PageData<PurchaseItem>data=new PageData<>(list,total.intValue());
		
		return data;
	}

	@Override
	public PageData<PurchaseItem> dynamicGetPI3(String purchaseBatch, String bookName, Integer currentPage,
			Integer pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<PurchaseItem> list = pid.dynamicSelectPI3(purchaseBatch, bookName);
		PageInfo<PurchaseItem>pageInfo=new PageInfo<>(list);
		Long total = pageInfo.getTotal();
		PageData<PurchaseItem>data=new PageData<>(list,total.intValue());
		return data;
	}

}
