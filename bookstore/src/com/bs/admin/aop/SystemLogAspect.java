package com.bs.admin.aop;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.Log;
import com.bs.admin.pojo.Admin;
import com.bs.admin.service.LogService;

@Aspect
@Component
public class SystemLogAspect {
	
	
	private static final ThreadLocal<Log> logThreadLocal = new NamedThreadLocal<Log>("ThreadLocal log");
	private static final ThreadLocal<Admin> operatorThreadLocal=new NamedThreadLocal<Admin>("ThreadLocal Admin");
	private static final ThreadLocal<String> requestIpThreadLocal=new NamedThreadLocal<String>("ThreadLocal requestIp");
	
	@Autowired(required=false)
	
	private HttpServletRequest request;
	
	@Autowired
	
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	
	private LogService logService;

	
	/**
	 * Controller层切点 注解拦截
	 */
	@Pointcut("@annotation(com.bs.admin.annotation.SystemControllerLog)")
	public void controllerAspect(){}
	
	/**
	 * 方法规则拦截
	 */
	@Pointcut("execution(* com.bs.admin.controller.*.*(..))")
	
	public void controllerPointerCut(){}
	/**
	 * 前置通知 用于拦截Controller层记录用户的操作的开始时间
	 * @param joinPoint 切点
	 * @throws InterruptedException 
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) throws InterruptedException{
		//读取session中的用户 
		HttpSession session = request.getSession();       
	    Admin admin = (Admin) session.getAttribute("admin");
	    if(admin!=null){
	    	operatorThreadLocal.set(admin);
	    }

        String requestIp= "";

        if (request != null) {
            requestIp= request.getHeader("X-FORWARDED-FOR");
            if (requestIp== null || "".equals(requestIp)) {
                requestIp= request.getRemoteAddr();
            }
        }
        requestIpThreadLocal.set(requestIp);
		
	}
	
	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * @param joinPoint 切点
	 */

	@After("controllerAspect()")
	public void doAfter(JoinPoint joinPoint) {
        Admin operator = operatorThreadLocal.get();
    
        //登入login操作 前置通知时用户未校验 所以session中不存在用户信息
        if(operator == null){
    		HttpSession session = request.getSession();       
    		operator = (Admin) session.getAttribute("admin");    
    	    if(operator==null){
    	    	return;
    	    }
        }
        String dept = operator.getEmp().getEmpDept();
        //登入login操作 前置通知时用户未校验 所以session中不存在用户信息
        Map<String, Object> map = getSystemControllerLog(joinPoint);
        String operate=(String)map.get("description");
        Integer operateType=(Integer)map.get("type");
        String detail=null;
        if (operateType==1){
        	detail="登陆或退出操作";
        }else if(operateType==3){
        	detail="删除的日期为";
        	Map<String,String[]> params=request.getParameterMap(); //请求提交的参数
        	StringBuilder sb = getMapValue(params);
        	detail=detail+new String(sb);
        }else {
        	Map<String,String[]> params=request.getParameterMap(); //请求提交的参数
        	StringBuilder sb = getMapValue(params);
        	detail=new String(sb);
        	System.out.println(detail);
        }
    	
    	String requestIp = requestIpThreadLocal.get();
    	Log log=new Log(operator, operateType, operate, detail, requestIp, dept);

   
		
    	
    	 //1.直接执行保存操作
//        this.logService.postLog(log);

        //2.优化:异步保存日志
//		Thread logThread = new Thread(new postLogThread(log, logService));
//	 	logThread.start();
        //3.再优化:通过线程池来执行日志保存
        threadPoolTaskExecutor.execute(new postLogThread(log, logService));
        logThreadLocal.set(log);
	}
	
	/**
	 *  异常通知 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")  
	public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		Log log = logThreadLocal.get();
		if(log != null){
			log.setOperateType(-1);
			log.setDetail("执行出错");
				
		}
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint 切点
	 * @return 方法描述
	 */
	public static Map<String,Object> getSystemControllerLog(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SystemControllerLog controllerLog = method.getAnnotation(SystemControllerLog.class);
		String description = controllerLog.description();
		int type=controllerLog.type();
		Map<String,Object>logInfo =new  HashMap<String,Object>();
		logInfo.put("description", description);
		logInfo.put("type", type);
		return logInfo;
	}
	
	public StringBuilder getMapValue(Map<String,String[]> map){
		Set<String> keySet = map.keySet();
    	Iterator<String> iterator = keySet.iterator();
    	StringBuilder sb = new StringBuilder();
    	 
    	while (iterator.hasNext()) {
			sb.append(map.get((String) iterator.next())[0]+",");
		}
		
		
		return sb;
		
	}



	/**  
	
	* <p>Title: postLogThread</p>  
	
	* <p>Description:保存日志的线程</p>  
	
	* @author 王顺坤  
	
	* <p> @date 2018年12月10日</p>   
	
	*/ 
	private static class postLogThread implements Runnable {
		private Log log;
		private LogService logService;

		public postLogThread(Log log, LogService logService) {
			this.log = log;
			this.logService = logService;
		}

		@Override
		public void run() {
			logService.postLog(log);
		}
	}


}
