package com.bs.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.Admin;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Purchase;
import com.bs.admin.pojo.QueryBoxData;
import com.bs.admin.service.PurchaseService;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping("purchase")
public class PurchaseController {
	@Autowired
	private PurchaseService ps;

	@RequestMapping("index")
	public String index() {
		System.out.println("访问采购查询页面");
		return "purchaseQuery";
	}
	@RequestMapping("myIndex")
	public String myIndex() {
		System.out.println("访问我的采购页面");
		return "purchaseManagement";
	}
	@RequestMapping("purchaseDetail")
	public String purchaseDetail() {
		System.out.println("访问我的采购页面");
		return "purchaseDetail";
	}
	@SystemControllerLog(type=2, description="添加一则新的采购信息")
	@RequestMapping(value = "postPurchase", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData postPurchase(@RequestParam("purchaseBatch") String purchaseBatch,
			@RequestParam("purchaseDate") String purchaseDate, HttpServletRequest req,
			@RequestParam("supplier") Integer supplier,
			@RequestParam("purchaseLog") String purchaseLog

	) {
		Admin admin=(Admin) req.getSession().getAttribute("admin");
		Integer empId = admin.getEmp().getEmpId().intValue();

		
		
		return new JsonData("postPurchase",
				ps.postPurchase(purchaseBatch, purchaseDate, empId, supplier, purchaseLog), "插入数据",
				true);
	}
	@SystemControllerLog(type=2, description="删除一则采购信息")
	@RequestMapping(value = "delPurchase", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData delPurchase(@RequestParam("purchaseId") Integer purchaseId) {
		return new JsonData("delPurchase", ps.delPurchaseById(purchaseId), "已删除数据", true);
	}
	@SystemControllerLog(type=2, description="通过id修改一则采购信息")
	@RequestMapping("putPurchase")
	public @ResponseBody JsonData putPurchase(@RequestParam("purchaseId") Integer purchaseId,
			@RequestParam("purchaseBatch") String purchaseBatch, @RequestParam("purchaseDate") String purchaseDate,
			HttpServletRequest req, @RequestParam("supplier") Integer supplier,
			@RequestParam("purchaseStatus") String purchaseStatus, @RequestParam("purchaseLog") String purchaseLog) {
		Admin admin=(Admin) req.getSession().getAttribute("admin");
		Integer empId = admin.getEmp().getEmpId().intValue();
		Integer rows = ps.putPurchase(purchaseId, purchaseBatch, purchaseDate, empId, supplier, purchaseStatus,
				purchaseLog);
		return new JsonData("putPurchase", rows, "采购信息已更新", true);
	}

	@RequestMapping(value = "getBatch", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getBatch() {
		
		return JSON.toJSONString(ps.getPurchaseBatch());
	}

	@RequestMapping(value = "dynamicGet", produces = "application/json;charset=utf-8")

	public @ResponseBody JsonData dynamicGet(@RequestParam("purchaseId") Integer purchaseId,
			@RequestParam("purchaseBatch") String purchaseBatch, @RequestParam("employee") Integer employee,
			@RequestParam("supplier") Integer supplier, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("purchaseStatus") String purchaseStatus,
			 @RequestParam("currentPage") Integer currentPage,
			@RequestParam("pageSize") Integer pageSize) {
		
		System.out.println("purchaseBatch : "+purchaseBatch);
		System.out.println("sd  :"+startDate);
		System.out.println("ed "+endDate);
		PageData<Purchase> pagedata = ps.dynamicGet(purchaseBatch, startDate, endDate, employee, supplier,
				purchaseStatus,  currentPage, pageSize);

		return new JsonData("dynamicGet", pagedata, "成功", true);
	}
	@RequestMapping(value = "myPurchaseGet", produces = "application/json;charset=utf-8")
	
	public @ResponseBody JsonData myPurchaseGet(@RequestParam("purchaseId") Integer purchaseId,
			@RequestParam("purchaseBatch") String purchaseBatch,HttpServletRequest req,
			@RequestParam("supplier") Integer supplier, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, @RequestParam("purchaseStatus") String purchaseStatus,
			@RequestParam("currentPage") Integer currentPage,
			@RequestParam("pageSize") Integer pageSize) {
		Admin admin=(Admin) req.getSession().getAttribute("admin");
		Integer empId = admin.getEmp().getEmpId().intValue();
	
		PageData<Purchase> pagedata = ps.dynamicGet(purchaseBatch, startDate, endDate, empId, supplier,
				purchaseStatus,  currentPage, pageSize);
		
		return new JsonData("dynamicGet", pagedata, "成功", true);
	}
	@RequestMapping(value = "getPurchasePay", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getPurchasePay(@RequestParam("purchaseId") Integer purchaseId
			) {

		Double totalExpend=ps.getPurchasePay(purchaseId);
	
		return new JsonData("dynamicGet", totalExpend, "查询成功", true);
	}
	

}
