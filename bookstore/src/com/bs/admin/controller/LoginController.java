package com.bs.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.Admin;
import com.bs.admin.pojo.Permission;
import com.bs.admin.service.AdminService;
import com.bs.admin.service.JobService;
import com.bs.admin.service.impl.AdminServiceImpl;
/**
 * 

* <p>Title: LoginController</p>  

* <p>Description: </p>  
	控制管理员的登录。登陆页面的提交目标
* @author zhengjian  

* <p> @date 2018年11月21日</p>
 */
@Controller
@RequestMapping("loginCtrl")
public class LoginController {
	private static final String Admin = null;
	@Autowired
	private AdminService as;
	/**
	 * 
	
	 * <p>Title: adminLogin</p>  
	
	 * <p>Description: </p>  
		验证密码和权限
	 * @param adminName
	 * @param psw
	 * @return  
	 * <p> @date 2018年11月23日  </p>
	 */
	@RequestMapping("check")
	@ResponseBody
	@SystemControllerLog(type=1,description="登录")
	public ModelAndView adminLogin(@RequestParam("adminName")String adminName, @RequestParam("psw")String psw, HttpServletRequest req, HttpServletResponse resp){
		Boolean check = as.checkAdmin(adminName, psw, req);
		ModelAndView mav = new ModelAndView("");
		if(check){
			// session保存三十分钟
			HttpSession session = req.getSession();
			session.setMaxInactiveInterval(60*30);
			// 访问index.jsp
			mav.setViewName("index");
			return mav;
		}else{
			try {
				req.getSession().setAttribute("message","用户名或密码错误");
				resp.sendRedirect("/bookstore/login.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
	
	/**
	 * 
	
	 * <p>Title: getPermission</p>  
	
	 * <p>Description: </p>  
		获取管理员的对象。ajax可以通过url : '/bookstore/loginCtrl/getAdmin'获取admin
	 * @param req
	 * @return  
	 * <p> @date 2018年12月5日  </p>
	 */
	@RequestMapping(value="getAdmin", produces="application/json;charset=utf-8")
	@ResponseBody
	public Admin getAdmin(HttpServletRequest req){
		return (Admin)req.getSession().getAttribute("admin");
	}
	
	/**
	 * 获取当前管理员的权限信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value="getPermissions", produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Integer> getPermissions(HttpServletRequest req){
		Admin admin = (Admin)req.getSession().getAttribute("admin");
		List<Permission> permissions = admin.getPermissions();
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < permissions.size(); i++){
			list.add(permissions.get(i).getPermissonId().intValue());
		}
		return list;
	}
	
	/**
	 * 
	
	 * <p>Title: logOut</p>  
	
	 * <p>Description: </p>  
		管理员登出
	 * @param req
	 * @param resp
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	@RequestMapping("loginOut")
	@SystemControllerLog(type=1,description="登出")
	public String logOut(HttpServletRequest req, HttpServletResponse resp){
		HttpSession session = req.getSession();
		session.setAttribute("message", "已登出");
		session.removeAttribute("admin");
		session.setMaxInactiveInterval(0);
		try {
			resp.sendRedirect("/bookstore/login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="getAdminPermissions", produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Integer> getAdminPermissions(@RequestParam("adminAccount")String adminAccount, HttpServletRequest req){
		Admin admin = as.getAdminByAdminAccount(adminAccount);
		List<Permission> permissions = admin.getPermissions();
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < permissions.size(); i++){
			list.add(permissions.get(i).getPermissonId().intValue());
		}
		return list;
	}
	
	
}
