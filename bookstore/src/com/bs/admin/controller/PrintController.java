package com.bs.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.PrintInfo;
import com.bs.admin.pojo.Printer;
import com.bs.admin.pojo.PrinterModel;
import com.bs.admin.service.PrinterService;
import com.bs.admin.util.DateUtil;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping("printController")
public class PrintController {

	@Autowired
	private PrinterService ps;
	
	@GetMapping("sendPrintPage")
	public String sendPrintPage(){
		return "printPage";
	}
	
	@RequestMapping(value="getPrinter", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData getPrinter(@RequestParam("value")String value){
		String value1 = value.equals("") ? null : value;
		List<PrinterModel> printerModel = ps.queryPrinter(value1);
		PageData<PrinterModel> pageData = new PageData<>(printerModel, printerModel.size());
		JsonData jsonData = new JsonData("pageData", pageData, "成功！", true);
		return jsonData;
	}
	
	@RequestMapping(value="getPrinterByPrintId", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData getPrinterByPrintId(@RequestParam("printId")Integer printId){
		
		List<PrinterModel> print = ps.getPrinterByPrintId(printId);
		PageData<PrinterModel> pageData = new PageData<PrinterModel>(print, print.size());
		Boolean flag = print.size() > 0 && print != null ? true : false;
		String msg = flag ? "数据返回成功！" : "数据返回失败！";
		JsonData jsonData = new JsonData("pageData", pageData, msg, flag);
		return jsonData;
	}
	
	/**
	 * 
	 * <p>Title: addPrint</p>  
	 * <p>Description: 添加印刷厂</p>  
	 * @param printId
	 * @param printerName
	 * @param printSize
	 * @param printDate
	 * @param printQuantity
	 * @param printBatch
	 * @return  
	 * <p> @date 2018年12月4日  </p>
	 */
	@SystemControllerLog(type=2, description="添加了一个印刷商")
	@RequestMapping(value="addPrint", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData addPrint(@RequestParam("printId")Integer printId, @RequestParam("printerName")String printerName,
			@RequestParam("printSize") String printSize, @RequestParam("printDate")String printDate,
			@RequestParam("printQuantity")Integer printQuantity,@RequestParam("printBatch")Integer printBatch){
		
		String date = DateUtil.changeDateString(printDate);
		Printer printer = new Printer(printerName, printId);
		PrintInfo printInfo = new PrintInfo(printId, printSize, date, printQuantity, printBatch);
		Integer createPrinter = ps.createPrinter(printer, printInfo);
		Boolean state = createPrinter >= 2 ? true : false;
		String msg = state ? "印刷厂信息添加成功！" : "印刷厂信息添加失败！";
		JsonData jsonData = new JsonData("createPrinter", createPrinter, msg, state);
 		return jsonData;
	}
	
	/**
	 * 
	 * <p>Title: removePrint</p>  
	 * <p>Description: 删除印刷厂</p>  
	 * @param printid
	 * @return  
	 * <p> @date 2018年12月4日  </p>
	 */
	@SystemControllerLog(type=2, description="删除了一个印刷商")
	@RequestMapping(value="removePrint", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData removePrint(@RequestParam("printId") Integer printid){
		Integer deletePrinter = ps.deletePrinter(printid);
		Boolean state = deletePrinter >= 2 ? true :false;
		String msg = state ? "印刷厂信息删除成功！" : "印刷厂信息删除失败！";
		JsonData jsonData = new JsonData("deletePrinter", deletePrinter, msg, state);
		return jsonData;
	}
	
	/**
	 * 
	 * <p>Title: updatePrint</p>  
	 * <p>Description: 修改印刷厂</p>  
	 * @return  
	 * <p> @date 2018年12月4日  </p>
	 */
	@SystemControllerLog(type=2, description="修改了一个印刷商的信息")
	@RequestMapping(value="updatePrint", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData updatePrint(@RequestParam("printId")Integer printId, @RequestParam("printerName")String printerName,
			@RequestParam("printSize") String printSize, @RequestParam("printDate")String printDate,
			@RequestParam("printQuantity")Integer printQuantity,@RequestParam("printBatch")Integer printBatch){
		
		String date = DateUtil.changeDateString(printDate);
		Printer printer = new Printer(printerName, printId);
		PrintInfo printInfo = new PrintInfo(printId, printSize, date, printQuantity, printBatch);
		Integer updatePrinter = ps.updatePrinter(printer, printInfo);
		Boolean state = updatePrinter >= 2 ? true : false;
		String msg = state ? "印刷厂信息修改成功！" : "印刷厂信息修改失败！";
		JsonData jsonData = new JsonData("updatePrinter", updatePrinter, msg, state);
 		return jsonData;
	}
	
	@RequestMapping(value="getAllPrinter", produces="application/json;charset=utf-8")
	public @ResponseBody String getAllPrinter(){
		List<Printer> allPrinter = ps.getAllPrinter();
		String jsonString = JSON.toJSONString(allPrinter);
		return jsonString;
	}
}
