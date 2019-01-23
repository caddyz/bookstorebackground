package com.bs.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.PurchaseItem;
import com.bs.admin.service.PurchaseItemService;
import com.bs.admin.service.PurchaseService;
import com.bs.admin.service.impl.PurchaseServiceImpl;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping("purchaseItem")
public class PurchaseItemController {
	
	@Autowired
	private PurchaseItemService pis;
	@Autowired
	private PurchaseService ps;
	@SystemControllerLog(type=2, description="批量添加采购项目")
	@RequestMapping(value = "postPI", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData postPI(@RequestParam("purchaseItemList") String PIList) {
		Integer row = pis.postPurchaseItems(PIList);
		
		return new JsonData("postPI", row, "插入" + row + "行", true);
	}
	@SystemControllerLog(type=2, description="批量删除一些采购项目")
	@RequestMapping(value = "delPIById", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData delPIById(@RequestParam("idList") String str) {
		
		System.out.println(str);
		Integer row = pis.delPurchaseItemByIdBatch(str);

		return new JsonData("delPIById", row, "删除了" + row + "行", true);

	}
	@SystemControllerLog(type=2, description="通过采购信息删除采购项目")
	@RequestMapping(value = "delPIByPurchase", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData delPIByPurchase(@RequestParam("purchaseId") Integer purchaseId) {

		Integer row = pis.delPurchaseItemByPurchaseId(purchaseId);
		return new JsonData("delPIByPurchase", row, "删除了" + row + "行", true);

	}
	@SystemControllerLog(type=2, description="修改采购采购项目")
	@RequestMapping(value = "putPI", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData putPI(@RequestParam("purchaseItemId") Integer purchaseItemId,
			@RequestParam("purchaseId") Integer purchaseId, @RequestParam("bookId") Integer bookId,
			@RequestParam("purchaseNum") Integer purchaseNum, @RequestParam("purchasePrice") double purchasePrice

	) {

		Integer row = pis.putPurchaseItem(purchaseItemId, purchaseId, bookId, purchaseNum, purchasePrice);
		return new JsonData("delPIById", row, "修改了" + row + "行", true);

	}
	
	@RequestMapping(value = "getByPurchaseId", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getByPurchaseId(@RequestParam("purchaseId") Integer purchaseId,
			@RequestParam("currentPage") Integer currentPage,
			@RequestParam("pageSize") Integer pageSize
	) {
		System.out.println(currentPage);
		System.out.println(pageSize);
		System.out.println(purchaseId);
		Double pay = ps.getPurchasePay(purchaseId);
		String totalPay = pay==null?null:(pay+"");
	
		
		PageData<PurchaseItem> row = pis.getByPurchaseId(purchaseId, currentPage, pageSize);
	
		return new JsonData(totalPay, row, "查询到" + row.getTotal() + "条数据", true);

	}
	@RequestMapping(value = "getByBookId", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getByBookId(@RequestParam("bookId") Integer bookId,
			@RequestParam("currentPage") Integer currentPage,
			@RequestParam("pageSize") Integer pageSize

	) {
		PageData<PurchaseItem> row = pis.getByBookId(bookId, currentPage, pageSize);
		return new JsonData("getByBookId", row, "查询到" + 	row.getTotal() + "条数据", true);

	}
	
	@RequestMapping(value = "getPIById", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getPIById(@RequestParam("purchaseItemId") Long purchaseItemId

	) {
		PurchaseItem purchaseItem = pis.getPurchaseItemById(purchaseItemId);
		return new JsonData("getPIById", purchaseItem, "查询数据", true);

	}
	
	
	@RequestMapping(value = "dynamicGetPI3", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getByBookId(@RequestParam("purchaseBatch") String purchaseBatch,
			@RequestParam("bookName") String bookName,
			@RequestParam("currentPage") Integer currentPage,
			@RequestParam("pageSize") Integer pageSize

	) {
		PageData<PurchaseItem> row = pis.dynamicGetPI3(purchaseBatch, bookName, currentPage, pageSize);
		return new JsonData("getByBookId", row, "查询到" + 	row.getTotal() + "条数据", true);

	}
	
	
	
	

}
