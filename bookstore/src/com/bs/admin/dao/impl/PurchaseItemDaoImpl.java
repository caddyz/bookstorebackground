package com.bs.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.PurchaseItemDao;
import com.bs.admin.mapper.PurchaseItemMapper;
import com.bs.admin.pojo.PurchaseItem;

@Repository
public class PurchaseItemDaoImpl implements PurchaseItemDao {
	@Autowired
	private PurchaseItemMapper pim;

	@Override
	public Integer insertPurchaseItems(List<PurchaseItem> list) {
		
		return pim.insertPurchaseItems(list);
	}

	@Override
	public Integer deletePurchaseItemByIdBatch(List<Long> list) {
		// TODO Auto-generated method stub
		return pim.deletePurchaseItemByIdBatch(list);
	}

	@Override
	public Integer deletePurchaseItemByPurchaseId(Integer purchaseId) {
		// TODO Auto-generated method stub
		return pim.deletePurchaseItemByPurchaseId(purchaseId);
	}

	@Override
	public Integer updatePurchaseItem(PurchaseItem purchaseItem) {
		// TODO Auto-generated method stub
		return pim.updatePurchaseItem(purchaseItem);
	}

	@Override
	public List<PurchaseItem> selectByPurchaseId(Integer purchaseId) {
		return pim.selectByPurchaseId(purchaseId);
	}

	@Override
	public List<PurchaseItem> selectByBookId(Integer bookId) {
		return pim.selectByBookId(bookId);
	}

	@Override
	public PurchaseItem selectPurchaseItemById(Long purchaseItemId) {
		return pim.selectPurchaseItemById(purchaseItemId);
	}

	@Override
	public List<PurchaseItem> dynamicSelectPI3(String purchaseBatch, String bookName) {
		// TODO Auto-generated method stub
		return pim.dynamicSelectPI3(purchaseBatch, bookName);
	}

	

}
