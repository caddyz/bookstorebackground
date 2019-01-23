package com.bs.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.StockDao;
import com.bs.admin.mapper.StockMapper;
import com.bs.admin.pojo.Stock;
@Repository
public class StockDaoImpl implements StockDao {
@Autowired
private StockMapper sm;

@Override
public List<Stock> selectBookStock(Integer bookId, Integer min, Integer max) {
	// TODO Auto-generated method stub
	return sm.selectBookStock(bookId, min, max);
}

@Override
public Integer selectBookStockNum(Integer bookId) {
	// TODO Auto-generated method stub
	return sm.selectBookStockNum(bookId);
}

@Override
public List<Stock> selectStockNumRange(Integer min, Integer max) {
	// TODO Auto-generated method stub
	return sm.selectStockNumRange(min, max);
}

@Override
public List<Stock> dynamicSelect(String purchaseBatch, String bookName, String startTime, String endTime,
		Integer storeHouseId, Integer min, Integer max) {
	return sm.dynamicSelect(purchaseBatch, bookName, startTime, endTime, storeHouseId, min, max);
}

@Override
public List<Stock> selectVStock(String bookName ,Integer min,Integer max) {
	
	
	return sm.selectVStock(bookName,min,max);
}

}
