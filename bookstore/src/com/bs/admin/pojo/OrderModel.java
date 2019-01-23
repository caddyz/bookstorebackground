package com.bs.admin.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
* <p>Title: Order</p>  
* <p>Description: 订单</p>  
* @author 胡杰  
* <p> @date 2018年12月6日</p>
 */
public class OrderModel implements Serializable {
	
	/**
	 serialVersionUID
	*/ 
	private static final long serialVersionUID = -931878944437811978L;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 快递公司
	 */
	private String expressName;
	/**
	 * 收货人
	 */
	private String addressConsignee;
	/**
	 * 收货人电话
	 */
	private String mobile;
	/**
	 * 收货地址
	 */
	private String address;
	/**
	 * 用户
	 */
	private String userName;
	/**
	 * 订单生成时间
	 */
	private String orderTime; 
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 总价
	 */
	private Double totalPrice;
	
	public OrderModel(String orderId, String expressName, String addressConsignee, String mobile, String address, String userName, String orderTime,
			String orderStatus, Double totalPrice) {
		this.orderId = orderId;
		this.expressName = expressName;
		this.addressConsignee = addressConsignee;
		this.mobile = mobile;
		this.address = address;
		this.userName = userName;
		this.orderTime = orderTime;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
	}
	public OrderModel() {
	}
	@Override
	public String toString() {
		return "OrderModel [orderId=" + orderId + ", expressName=" + expressName + ", address=" + address
				+ ", userName=" + userName + ", orderTime=" + orderTime + ", orderStatus=" + orderStatus
				+ ", totalPrice=" + totalPrice + ", addressConsignee"+ addressConsignee + "]";
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getAddressConsignee() {
		return addressConsignee;
	}
	public void setAddressConsignee(String addressConsignee) {
		this.addressConsignee = addressConsignee;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
