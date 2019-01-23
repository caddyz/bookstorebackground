package com.bs.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.Financial;
import com.bs.admin.pojo.FinancialDatas;
import com.bs.admin.pojo.Order;
import com.bs.admin.pojo.OrderModel;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Purchase;
import com.bs.admin.pojo.Salary;
import com.bs.admin.service.FinancialService;
import com.bs.admin.util.DateUtil;
import com.bs.admin.util.EffectRow;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping("financial")
public class FinancialController {
	@Autowired
	private FinancialService fs;

	@RequestMapping("pagination")
	public String financial() {
		return "FinancialManagement";
	}

	/**
	 * 获取支出、收入
	 * 
	 * @return JsonData数据
	 */
	@RequestMapping(value = "getStatus", produces = "application/json;charset=utf-8")
	public @ResponseBody String getFinancialStatus() {
		return JSON.toJSONString(fs.getAllStastus());
	}

	/**
	 * 获取所有财务类型
	 * 
	 * @return JsonData数据
	 */
	@RequestMapping(value = "getTypes", produces = "application/json;charset=utf-8")
	public @ResponseBody String getFinancialTypes() {
		return JSON.toJSONString(fs.getAllTypes());
	}

	/**
	 * 查询数据
	 * @param pageNum         页数
	 * @param pageSize        每页数据条数
	 * @param financialStatus 支出、收入
	 * @param financialType   支出、收入的类型
	 * @param financialStart  开始时间
	 * @param financialEnd    结束时间
	 * @return JsonData数据
	 */
	@RequestMapping(value = "getPageData/{pageNum}/{pageSize}", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getPageData(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize, 
			@RequestParam("financialStatus") String financialStatus,
			@RequestParam("financialTypes") String financialTypes,
			@RequestParam("financialStart") String financialStart, 
			@RequestParam("financialEnd") String financialEnd) {
		return JSON.toJSONString(fs.getAllFinancial(pageNum, pageSize, financialStatus, financialTypes, financialStart, financialEnd));
	}

	/**
	 * 页面直接编辑提交过来的数据
	 * 
	 * @return任意对象
	 */
	@SystemControllerLog(type=2, description="批量编辑财务信息")
	@PostMapping("commit")
	public @ResponseBody JsonData commit(@RequestBody(required=false) List<Financial> financials) {
//		/*EffectRow<Financial> effectRow
//		 * List<Financial> updatedData = effectRow.getUpdated();
//		List<Financial> addData = effectRow.getInserted();
//		if (updatedData != null) {
//			for (Financial financial : updatedData) {
//				financial.setFinancialDate(DateUtil.changeDateString(financial.getFinancialDate()));
//				Integer row = fs.updateFinancial(financial);
//				if (row != null) {
//					return new JsonData("row", row, "修改成功", true);
//				} else {
//					return new JsonData("row", row, "修改失败", false);
//				}
//			}
//		}*/
		if(financials!=null) {
			for (Financial financial : financials) {
				financial.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				financial.setFinancialDate(DateUtil.changeDateString(financial.getFinancialDate()));
				Integer add = fs.addFinancial(financial);
				if (add != null) {
					return new JsonData("row", add, "添加成功", true);
				} else {
					return new JsonData("row", add, "添加失败", false);
				}
			}
		}
		return null;
	}
	
	/**  
	 * <p>Title: salaryIssue</p>  
	 * <p>Description: 工资发放确认页面</p>  
	 * @return  
	 * <p> @date 2018年12月20日  </p> 
	 */
	@RequestMapping("salaryIssue")
	public String salaryIssue() {
		return "SalaryIssue";
	}
	
	/**  
	 * <p>Title: getSalaryData</p>  
	 * <p>Description: 分页查询未发放工资信息</p>  
	 * @param pageNum
	 * @param pageSize
	 * @return  页面数据
	 * <p> @date 2018年12月20日  </p> 
	 */
	@RequestMapping(value = "getSalaryData/{pageNum}/{pageSize}", produces = "application/json;charset=UTF-8")
	public @ResponseBody PageData<Salary> getSalaryData(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize) {
		return fs.getSalaryData(pageNum, pageSize);
	}
	
	/**  
	 * <p>Title: addSalaryExpend</p>  
	 * <p>Description: 批量添加工资支出信息</p>  
	 * @param salaries 添加的集合
	 * @return JsonData结果集
	 * <p> @date 2018年12月20日  </p> 
	 */
	@SystemControllerLog(type=2, description="批量添加工资支出信息")
	@RequestMapping(value = "addSalaryExpend",produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonData addSalaryExpend(
			@RequestBody List<FinancialDatas> financialDatas) {	
		return fs.addSalaryExpend(financialDatas);	
	}
	
	/**  
	 * <p>Title: orderEntry</p>  
	 * <p>Description: 订单入账确认页面</p>  
	 * @return  
	 * <p> @date 2018年12月20日  </p> 
	 */
	@RequestMapping("orderEntry")
	public String orderEntry() {
		return "OrderEntry";
	}
	
	/**  
	 * <p>Title: getgetOrderData</p>  
	 * <p>Description: 分页查询未入账订单信息</p>  
	 * @param pageNum
	 * @param pageSize
	 * @return  页面数据
	 * <p> @date 2018年12月20日  </p> 
	 */
	@RequestMapping(value = "getOrderData/{pageNum}/{pageSize}", produces = "application/json;charset=UTF-8")
	public @ResponseBody PageData<OrderModel> getgetOrderData(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize) {
		return fs.getOrderData(pageNum, pageSize);
	}
	
	/**  
	 * <p>Title: addOrderRevenue</p>  
	 * <p>Description: 批量添加订单收入信息</p>  
	 * @param orders 添加的集合
	 * @return JsonData结果集
	 * <p> @date 2018年12月20日  </p> 
	 */
	@SystemControllerLog(type=2, description="批量添加订单收入信息")
	@RequestMapping(value = "addOrderRevenue",produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonData addOrderRevenue(
			@RequestBody List<FinancialDatas> financialDatas) {
		return fs.addOrderReveNue(financialDatas);	
	}
	
	/**  
	 * <p>Title: purchaseApply</p>  
	 * <p>Description: 采购申请确认</p>  
	 * @return  
	 * <p> @date 2018年12月20日  </p> 
	 */
	@RequestMapping("purchaseApply")
	public String purchaseApply() {
		return "PurchaseApply";
	}
	
	/**  
	 * <p>Title: getPurchaseData</p>  
	 * <p>Description: 分页查询采购申请信息</p>  
	 * @param pageNum
	 * @param pageSize
	 * @return  页面数据
	 * <p> @date 2018年12月20日  </p> 
	 */
	@RequestMapping(value = "getPurchaseData/{pageNum}/{pageSize}", produces = "application/json;charset=UTF-8")
	public @ResponseBody PageData<Purchase> getPurchaseData(
			@PathVariable("pageNum") Integer pageNum,
			@PathVariable("pageSize") Integer pageSize) {
		return fs.getPurchaseData(pageNum, pageSize);
	}
	
	/**  
	 * <p>Title: addSalaryExpend</p>  
	 * <p>Description: 批量添加采购支出信息</p>  
	 * @param purchases 添加的集合
	 * @return JsonData结果集
	 * <p> @date 2018年12月20日  </p> 
	 */
	@SystemControllerLog(type=2, description="批量添加采购支出信息")
	@RequestMapping(value = "addPurchaseExpsnd",produces = "application/json;charset=UTF-8")
	public @ResponseBody JsonData addPurchaseExpsnd(
			@RequestBody List<FinancialDatas> financialDatas) {
		return fs.addPurchaseExpend(financialDatas);	
	}
	
	
	
	
}
