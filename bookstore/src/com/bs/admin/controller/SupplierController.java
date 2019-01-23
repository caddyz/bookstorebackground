package com.bs.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Supplier;
import com.bs.admin.service.SupplierService;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping("supplier")
public class SupplierController {

	@Autowired
	private SupplierService ss;

	@RequestMapping("index")
	public String index() {
		System.out.println("访问供应商页面");
		return "supplierManagement";
	}

	
	@SystemControllerLog(type=2,description="添加一个供应商")
	@RequestMapping(value = "postSupplier", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData postSupplier(@RequestParam("supplierName") String supplierName,
			@RequestParam("supplierPhone") String supplierPhone,
			@RequestParam("supplierAddress") String supplierAddress,
			@RequestParam("supplierContact") String supplierContact,
			@RequestParam("supplierContactPhone") String supplierContactPhone,
			@RequestParam("cooperateDate") String cooperateDate,
			@RequestParam("cooperateStatus") Integer cooperateStatus) {

	

		JsonData data = new JsonData("postSupplier", ss.postSupplier(supplierName, supplierPhone, supplierAddress,
				supplierContact, supplierContactPhone, cooperateDate, cooperateStatus), "新增供应商", true);
		return data;
	}
	@SystemControllerLog(type=2, description="修改一个供应商")
	@RequestMapping(value = "putSupplier", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData putSupplier(@RequestParam("supplierId") Integer supplierId,
			@RequestParam("supplierName") String supplierName, @RequestParam("supplierPhone") String supplierPhone,
			@RequestParam("supplierAddress") String supplierAddress,
			@RequestParam("supplierContact") String supplierContact,
			@RequestParam("supplierContactPhone") String supplierContactPhone,
			@RequestParam("cooperateDate") String cooperateDate,
			@RequestParam("cooperateStatus") Integer cooperateStatus) {
		Integer result = ss.putSupplierById(supplierId, supplierName, supplierPhone, supplierAddress, supplierContact,
				supplierContactPhone, cooperateDate, cooperateStatus);

		if (result == 1) {
			return new JsonData("putSupplier", result, "执行成功", true);
		} else {
			return new JsonData("putSupplier", result, "执行失败", true);
		}		
	}

	@SystemControllerLog(type=2, description="删除一个供应商")
	@RequestMapping(value = "delSupplier", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData delSupplierById(@RequestParam("supplierId") Integer supplierId) {
		Integer result = ss.delSupplierById(supplierId);
		if (result == -1) {
			return new JsonData("delSupplier", -1, "该供应商有订单信息无法删除", true);
		} else if (result == 0) {
			return new JsonData("delSupplier", 0, "未知数据", true);
		}
		return new JsonData("delSupplier", result, "删除一条记录", true);
	}

	@RequestMapping(value = "getCooperateCount", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getPurchaseCount(@RequestParam("supplierId") Integer supplierId) {
		Integer cooperateCount = ss.getCooperateCountById(supplierId);
		return new JsonData("getCooperateCount", cooperateCount, "合作次数" + cooperateCount, true);
	}

	@RequestMapping(value = "getSupplierByCondition", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getSupplierByCondition(@RequestParam("supplierName") String supplierName,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("cooperateStatus") String cooperateStatus, @RequestParam("currentPage") Integer currentPage,
			@RequestParam("pageSize") Integer pageSize) {

		PageData<Supplier> dynamicGet = ss.dynamicGet(supplierName, cooperateStatus, startDate, endDate, currentPage, pageSize);

		return new JsonData("getSupplierByCondition", dynamicGet, "查询到了" + dynamicGet.getTotal() + "条数据", true);
	}
	@RequestMapping(value = "getById", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getById(@RequestParam("supplierId") Integer supplierId) {
		return new JsonData("getById", ss.getBySupplierId(supplierId), "详情查询", true);
	}

	@RequestMapping(value = "getSupplierOnLine", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getSupplierOnLine() {
		
		return JSON.toJSONString(ss.getSupplierOnLine());
	}
}
