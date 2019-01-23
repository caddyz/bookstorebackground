package com.bs.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.CategoryOrStatus;
import com.bs.admin.pojo.OrderModel;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.WindowInfo;
import com.bs.admin.service.OrderService;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping("orderController")
public class OrderController {

	@Autowired
	private OrderService os;
	
	@RequestMapping("sendOrderPage")
	public String sendOrderPage(){
		return "orderPage";
	}
	
	/**
	 * 查询订单：有分页，因为订单会有很多，然它分页显示
	 */
	@RequestMapping(value="getOrder/{pageNumber}/{pageSize}", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData getOrder(@PathVariable("pageNumber")Integer pageNumber, @PathVariable("pageSize")Integer pageSize,
			@RequestParam("orderId")String orderId, @RequestParam("orderStatus")String orderStatus, 
			@RequestParam("topPrice")Double topPrice, @RequestParam("bottomPrice")Double bottomPrice){
		
		List<OrderModel> order = os.queryOrder(orderId, orderStatus, topPrice, bottomPrice, pageNumber, pageSize);
		Integer total = os.getTotal(orderId, orderStatus, topPrice, bottomPrice);
		PageData<OrderModel> pageData = new PageData<>(order, total);
		Boolean state = null != pageData ? true : false;
		String msg = state ? "查询成功！" : "查询失败！刷新后重试...";
		JsonData jsonData = new JsonData("pageData", pageData, msg, state);
		return jsonData; 
	}
	
	/**
	 * 删除订单
	 */
	@SystemControllerLog(type=2, description="删除了订单")
	@RequestMapping(value="removeOrder", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData removeOrder(@RequestParam("orderId")String orderId){
		Integer rows = os.daleteOrder(orderId);
		Boolean state = rows > 0 ? true : false;
		String msg = state ? "订单删除成功！" : "订单删除失败！刷新后重试...";
		JsonData jsonData = new JsonData("rows", rows, msg, state);
		return jsonData;
	}
	
	/**
	 * 修改订单
	 */
	@SystemControllerLog(type=2, description="修改了订单")
	@RequestMapping(value = "updateOrder", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData updateOrder(@RequestParam("orderId") String orderId,
			@RequestParam("orderStatus1") String orderStatus, @RequestParam("totalPrice") Double totalPrice,
			@RequestParam("addressConsignee")String consignee, @RequestParam("mobile")String mobile,
			@RequestParam("province") String province, @RequestParam("citys") String city,
			@RequestParam("county") String county, @RequestParam("detail") String detail) {
		
		Integer rows = os.updateOrder(orderId, orderStatus, totalPrice, consignee, mobile, province, city, county, detail);
		Boolean state = rows > 0 ? true : false;
		String msg = state ? "订单修改成功！请刷新后查看..." : "订单修改失败！请刷新后重试...";
		JsonData jsonData = new JsonData("rows", rows, msg, state);
		return jsonData;
	}
	
	/**
	 * 修改退货的订单状态
	 */
	@SystemControllerLog(type=2, description="修改退货的订单状态")
	@RequestMapping(value="updataStatus", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData updataStatus(@RequestParam("orderStatus") String orderStatus){
		String status = orderStatus.equals("退货中") ? "4" : orderStatus;
		Integer updateStatus = os.updateStatus("7", status);
		Boolean state = updateStatus > 0 ? true : false;
		String msg = state ? "退货订单已确认，待财务退款给买家..." : "退货订单确认失败！请刷新后重试...";
		JsonData jsonData = new JsonData("updateStatus", updateStatus, msg, state);
		return jsonData;
	}
	
	/**
	 * 获取全部状态
	 */
	@RequestMapping(value="getOrderStatus", produces="text/html;charset=utf-8")
	public @ResponseBody String getOrderStatus(){
		List<CategoryOrStatus> orderStatus = os.getOrderStatus();
		String jsonString = JSON.toJSONString(orderStatus);
		return jsonString;
	}
	
	/**
	 * 
	 * <p>Title: getWindowInfo</p>  
	 * <p>Description: 获取窗口显示的信息</p>  
	 * @param orderId
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	@RequestMapping(value="getOrderWinInfo", produces="text/html;charset=utf-8")
	public @ResponseBody String getWindowInfo(@RequestParam("orderId") String orderId){
		List<WindowInfo> orderWinInfo = os.getOrderWinInfo(orderId);
		String jsonString = JSON.toJSONString(orderWinInfo);
		return jsonString;
	}
	
	
	
}
