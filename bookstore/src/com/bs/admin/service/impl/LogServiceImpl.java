package com.bs.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.admin.dao.LogDao;
import com.bs.admin.pojo.Admin;
import com.bs.admin.pojo.Log;
import com.bs.admin.pojo.PageData;
import com.bs.admin.service.LogService;
import com.bs.admin.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class LogServiceImpl implements LogService{
@Autowired
private LogDao ld;
	public Integer transferType(String status){
		switch (status) {
		case "所有类型":
			return null;
		case "错误":
			return -1;
		case "未知":
			return 0;
		case "登陆与注销":
			return 1;
		case "数据库操作":
			return 2;
		case "日志操作":
			return 3;
		default:
			return null;
		}

		
	}
	
	@Override
	public Integer postLog(Log log) {
		return ld.insertLog(log);
	}

	@Override
	public Integer delLog(String startTime, String endTime) {
		if(startTime!=null&&startTime.length()!=0){
			
			startTime = startTime+" 00:00:00";
		}
		if(endTime!=null&&endTime.length()!=0){
			
			endTime = endTime+" 23:59:59";
		}
		return ld.deleteLogByTime(startTime, endTime);
	}

	@Override
	public PageData<Log> dynamicGet(Admin admin, String operateType, String operate, String requestIp, String dept,
			String startTime, String endTime,Integer pageNum, Integer pageSize) {
		
		if(startTime!=null&startTime.length()!=0){
			startTime =startTime+" 00:00:00";
		}
		if(endTime!=null&endTime.length()!=0){
		 endTime = endTime+" 23:59:59";
		}
		 Integer type=null;
		 if(operateType!=null){
			  type = transferType(operateType);
		 }
		 
		 if(dept.equals("全部")){
			 dept=null;
		 }
		Log log = new Log(admin, type, operate, null, requestIp, dept);
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("log_time desc");
		List<Log> list = ld.dynamicSelectLog(log, startTime, endTime);
		Long total = new PageInfo<>(list).getTotal();
		PageData<Log>data=new PageData<>(list,total.intValue());
		
		return	data;
	}


}
