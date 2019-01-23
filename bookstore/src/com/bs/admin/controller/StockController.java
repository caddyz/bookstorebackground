package com.bs.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Stock;
import com.bs.admin.service.StockService;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping("stock")
public class StockController {
	@Autowired
	private StockService ss;

	@RequestMapping("index")
	public String index() {
		System.out.println("访问库存页面");
		return "stockQuery";
	}
	
	@RequestMapping("bookStockQuery")
	public String bookStockQuery() {
		System.out.println("访问库存页面");
		return "bookStockQuery";
	}


	@RequestMapping(value = "dynamicGet", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData dynamicGet(@RequestParam("purchaseBatch") String purchaseBatch,
			@RequestParam("bookName") String bookName, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime, @RequestParam("storeHouseId") Integer storeHouseId,
			@RequestParam("min") Integer min, @RequestParam("max") Integer max,
			@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {

		PageData<Stock> list = ss.dynamicGet(purchaseBatch, bookName, startTime, endTime, storeHouseId, min, max,
				currentPage, pageSize);

		JsonData data = new JsonData("dynamicGet", list, "动态查询库存详情", true);

		return data;
	}

	
	@RequestMapping(value = "getStockNumRange", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getStockNumRange(@RequestParam("min") Integer min, @RequestParam("max") Integer max,
			@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {

		PageData<Stock> list = ss.getStockNumRange(min, max, currentPage, pageSize);

		JsonData data = new JsonData("getStockNumRange", list, "动态查询库存详情", true);

		return data;
	}
	
	
	@RequestMapping(value = "getStockNumByBookId", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getStockNumByBookId(
			@RequestParam("bookId") Integer bookId) {


		JsonData data = new JsonData("getStockNumByBookId", ss.getStockNumByBookId(bookId), "查询书的库存详情", true);

		return data;
	}
	
	@RequestMapping(value = "getStockByBookId", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getStockByBookId( @RequestParam("bookId") Integer bookId,
			@RequestParam("min") Integer min, @RequestParam("max") Integer max,@RequestParam("order") String order,
			@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {

		PageData<Stock> list = ss.getStockByBookId(bookId, min, max,order, currentPage, pageSize);
		JsonData data = new JsonData("getStockByBookId", list, "查询书的id", true);
		

		return data;
	}
	@RequestMapping(value = "getVStock", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getVStock( @RequestParam("bookName") String bookName,
			@RequestParam("min") Integer min, @RequestParam("max") Integer max,@RequestParam("order") String order,
			@RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {
		
		PageData<Stock> list = ss.getVStock(bookName, min, max, order, currentPage, pageSize);
		JsonData data = new JsonData("getStockByBookId", list, "查询库存情况概览", true);
		
		
		return data;
	}


}
