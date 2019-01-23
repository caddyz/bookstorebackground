package com.bs.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bs.admin.service.AdminService;
import com.bs.admin.service.impl.AdminServiceImpl;

/**
 * 
* <p>Title: BookInterceptor</p>  
* <p>Description: admin登录拦截器</p>  
* @author 郑健
* @date 2018年11月23日
 */
public class adminInterceptor implements HandlerInterceptor {
	private AdminService as = new AdminServiceImpl();
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("preHandle---------------------------------------------------");
		System.out.println("handler：" + handler);
		//如果是login页面则返回true
		if(request.getServletPath().contains("login")){
			System.out.println(request.getServletPath());
			return true;
		}
		//如果已经登录，放行
		if(null!=request.getSession().getAttribute("admin")){
			return true;
		}
		
		//其他非法访问，需要登录。重定向到登陆页面
		request.getSession().setAttribute("message","请先登录~");
		response.sendRedirect(request.getContextPath().concat("/login.jsp"));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("postHandle---------------------------------------------------");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("afterCompletion---------------------------------------------------");
	}

}
