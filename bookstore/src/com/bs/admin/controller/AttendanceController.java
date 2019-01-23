package com.bs.admin.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.Admin;
import com.bs.admin.pojo.Attendance;
import com.bs.admin.pojo.PageData;
import com.bs.admin.service.AttendanceService;
import com.bs.admin.util.JsonData;

/**
 * 

* <p>Title: AttendanceController</p>  

* <p>Description: </p>  
	考勤信息控制层
* @author zhengjian  

* <p> @date 2018年12月10日</p>
 */
@Controller
@RequestMapping("attendance")
public class AttendanceController {
	@Autowired
	private AttendanceService as;
	/**
	 * 考勤表上传的指定保存路径
	 */
//	private String uploadDir = "E://uploaddir/";
	
	
	/**
	 * 
	
	 * <p>Title: toAttendPage</p>  
	
	 * <p>Description: </p>  
		跳转到attendPage.jsp
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	@RequestMapping("pagination")
	public String toAttendPage(){
		return "attendPage";
	}
	
	/**
	 * 
	
	 * <p>Title: page</p>  
	
	 * <p>Description: </p>  
		考勤信息分页
	 * @param pageNum	当前页数
	 * @param pageSize	页面容量
	 * @return  JsonData
	 * <p> @date 2018年12月10日  </p>
	 */
	@RequestMapping("page/{pageNum}/{pageSize}")
	@ResponseBody
	public JsonData page(@PathVariable("pageNum")Integer pageNum,@PathVariable("pageSize")Integer pageSize){
		PageData<Attendance> value = as.findAllAttendance(pageNum, pageSize);
		return new JsonData("allAttendance", value, "所有的考勤信息", true);
	}
	
	/**
	 * 
	
	 * <p>Title: upload</p>  
	
	 * <p>Description: </p>  
		上传考勤表
	 * @param files	上传的文件数组
	 * @param req	request
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	@RequestMapping("upload")
	@SystemControllerLog(type=2,description="上传考勤表")
	@ResponseBody
	public JsonData upload(@RequestParam("file")MultipartFile[] files, HttpServletRequest req){
		String upDirRealPath = req.getServletContext().getRealPath("/uploadDir/");
		String msg = null;
		Boolean state = null;
		Integer value = null;
		// 从session中获取操作者
		Admin admin = (Admin)(req.getSession().getAttribute("admin"));
		// 上传到指定路径
		for(MultipartFile file : files){
			String src = upDirRealPath.concat(file.getOriginalFilename());
			System.out.println("controller中的src ： "+src);
			String suff = src.substring(src.lastIndexOf(".")+1);
			// 判断文件类型
			if(suff.equals("xls")||suff.equals("xlsx")){
				try {
					file.transferTo(new File(src));
					value = as.fileInsert(src, admin.getAdminId());
					state = true;
				} catch (IllegalStateException e) {
					msg = "IllegalStateException";
					e.printStackTrace();
				} catch (IOException e) {
					msg = "IOException";
					e.printStackTrace();
				}
			}else{
				value = 0;
				state = false;
			}
		}
		System.out.println("state : "+state);
		return new JsonData("上传文件", value, msg, state);
	}
	
	
	
}
