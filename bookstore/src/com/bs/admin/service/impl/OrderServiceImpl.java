package com.bs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.admin.dao.OrderDao;
import com.bs.admin.pojo.CategoryOrStatus;
import com.bs.admin.pojo.Order;
import com.bs.admin.pojo.OrderModel;
import com.bs.admin.pojo.WindowInfo;
import com.bs.admin.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderDao od;

	@Override
	public List<OrderModel> queryOrder(String orderId, String orderStatus, Double topPrice, Double bottomPrice,
			Integer pageNumber, Integer pageSize) {
		Integer start = (pageNumber - 1) * pageSize;
		List<OrderModel> model = new ArrayList<OrderModel>();
		List<Order> order = od.getOrderByOrderInfo(orderId, orderStatus, topPrice, bottomPrice, start, pageSize);
		for (Order o : order) {
			model.add(new OrderModel(o.getOrderId(), o.getExpress().getExpressName(),
					o.getReceiveAddress().getAddressConsignee(), o.getReceiveAddress().getAddressMobile(), o.getReceiveAddress().getAddressProvince() + ",  " + 
					o.getReceiveAddress().getAddressCity() + ", " + o.getReceiveAddress().getAddressCounty() + ", " + 
					o.getReceiveAddress().getAddressDetail(), o.getUser().getUserName(), o.getOrderTime(), 
					o.getOrderStatus(), o.getTotalPrice()));
		}
		return model;
	}

	@Override
	public Integer daleteOrder(String orderId) {
		Integer rows = od.deleteOrder(orderId);
		Integer rows1 = od.deleteBookOrder(orderId);
		return rows + rows1;
	}

	@Override
	public Integer updateOrder(String orderId, String orderStatus, Double totalPrice, String consignee, String mobile,
			String province, String city, String county, String detail) {
		String detail1 = "".equals(detail) ? "自取" : detail;
		
		Integer rows = od.updateOrder(orderId, orderStatus, totalPrice, consignee, mobile, province, city, county, detail1);
		return rows;
	}

	@Override
	public List<CategoryOrStatus> getOrderStatus() {
		List<CategoryOrStatus> status = new ArrayList<CategoryOrStatus>();
		List<String> orderStatus = od.getOrderStatus();
		for(int i = 0;i < orderStatus.size();i++){
			status.add(new CategoryOrStatus(i+2, orderStatus.get(i), false));
		}
		return status;
	}

	@Override
	public List<WindowInfo> getOrderWinInfo(String orderId) {
		List<WindowInfo> wi = new ArrayList<WindowInfo>();
		List<Order> order = od.getOrderWinInfo(orderId);
		if  (order.size() > 0) {
			for (int i = 0; i < order.size(); i++) {
				WindowInfo windowInfo = new WindowInfo(order.get(i).getBooks().get(0).getBookName(),
						order.get(i).getBooks().get(0).getBookSalesPrice(), order.get(i).getBookNum());
				wi.add(windowInfo);
			}
			return wi;
		} 
		return null;
	}

	@Override
	public Integer updateStatus(String afterStatus, String orderStatus) {
		return od.updateStatus(afterStatus, orderStatus);
	}

	@Override
	public Integer getTotal(String orderId, String orderStatus, Double topPrice, Double bottomPrice) {
		return od.getTotal(orderId, orderStatus, topPrice, bottomPrice);
	}

	@Override
	public Integer updateAchieve(Boolean achieve, String orderId) {
		return od.updataAchieve(achieve, orderId);
	}

	@Override
	public List<OrderModel> getAchieveOrder(Integer start, Integer end) {
		List<OrderModel> orderModel = new ArrayList<OrderModel>();
		List<Order> achieveOrder = od.getAchieveOrder(start, end);
		for (Order o : achieveOrder) {
			orderModel.add(new OrderModel(o.getOrderId(), null, null, null, null, o.getUser().getUserName(), o.getOrderTime(), o.getOrderStatus(), o.getTotalPrice()));
		}
		return orderModel;
	}

	@Override
	public Integer getAchieveTotal() {
		return od.getAchieveTotal();
	}



}
