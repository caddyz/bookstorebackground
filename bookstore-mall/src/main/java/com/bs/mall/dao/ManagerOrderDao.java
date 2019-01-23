package com.bs.mall.dao;

import java.util.List;

import com.bs.mall.pojo.Book;
import com.bs.mall.pojo.Order;
import com.bs.mall.pojo.OrderDetail;

public interface ManagerOrderDao {

	List<Order> getOrderUserId(Integer userId);

	Integer updateOrders(String orderId, String orderStatus);

	OrderDetail getOrderDetails(String orderId, Integer userId);
	
	List<Book> getAllOrderBooks(String orderId, Integer userId);
	
	List<Order> getOrderNoComment(Integer userId);
}
