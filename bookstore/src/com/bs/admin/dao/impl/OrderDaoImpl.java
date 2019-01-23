package com.bs.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.OrderDao;
import com.bs.admin.mapper.OrderMapper;
import com.bs.admin.pojo.Order;

/**
 * 
* <p>Title: OrderDaoImpl</p>  
* <p>Description: 订单dao包实现类</p>  
* @author 胡杰  
* <p> @date 2018年12月10日</p>
 */
@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private OrderMapper om;

	/**
	 * 获取订单信息
	 */
	@Override
	public List<Order> getOrderByOrderInfo(String orderId, String orderStatus, Double topPrice, Double bottomPrice,
			Integer start, Integer count) {
		List<Order> order = om.getOrderByOrderInfo(orderId, orderStatus, topPrice, bottomPrice, start, count);
		return order;
	}

	/**
	 * 删除订单
	 */
	@Override
	public Integer deleteOrder(String orderId) {
		Integer rows = om.deleteOrder(orderId);
		return rows;
	}

	/**
	 * 修改订单
	 */
	@Override
	public Integer updateOrder(String orderId, String orderStatus, Double totalPrice, String consignee, String mobile,
			String province, String city, String county, String detail) {
		Integer rows = om.updateOrder(orderId, orderStatus, totalPrice, consignee, mobile, province, city, county, detail);
		return rows;
	}

	/**
	 * 获取订单的全部状态
	 */
	@Override
	public List<String> getOrderStatus() {
		List<Order> orderStatus = om.getOrderStatus();
		List<String> status = new ArrayList<String>();
		for(Order o : orderStatus){
			status.add(o.getOrderStatus());
		}
		return status;
	}

	@Override
	public List<Order> getOrderWinInfo(String orderId) {
		List<Order> orderWinInfo = om.getOrderWinInfo(orderId);
		return orderWinInfo;
	}

	@Override
	public Integer deleteBookOrder(String orderId) {
		return om.deleteBookOrder(orderId);
	}

	@Override
	public Integer updateStatus(String afterStatus, String orderStatus) {
		return om.updateStatus(afterStatus, orderStatus);
	}

	@Override
	public Integer getTotal(String orderId, String orderStatus, Double topPrice, Double bottomPrice) {
		return om.getTotal(orderId, orderStatus, topPrice, bottomPrice);
	}

	@Override
	public Integer updataAchieve(Boolean achieve, String orderId) {
		return om.updateAchieve(achieve, orderId);
	}

	@Override
	public List<Order> getAchieveOrder(Integer start, Integer end) {
		return om.getAchieveOrder(start, end);
	}

	@Override
	public Integer getAchieveTotal() {
		return om.getAchieveTotal();
	}

	


	
	
}
