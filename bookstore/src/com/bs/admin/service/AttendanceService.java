package com.bs.admin.service;


import org.springframework.stereotype.Service;

import com.bs.admin.pojo.Attendance;
import com.bs.admin.pojo.PageData;
@Service
public interface AttendanceService {
	/**
	 * 
	
	 * <p>Title: findAllAttendance</p>  
	
	 * <p>Description: </p>  
		获取所有考勤信息
	 * @param pageNum	当前页数
	 * @param pageSize	页面容量
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	PageData<Attendance> findAllAttendance(Integer pageNum, Integer pageSize);
	/**
	 * 
	
	 * <p>Title: findAttendanceByEmpIdAndDate</p>  
	
	 * <p>Description: </p>  
		通过员工id和日期获取考勤信息
	 * @param empId
	 * @param Date
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Attendance findAttendanceByEmpIdAndDate(Long empId, String Date);
	/**
	 * 
	
	 * <p>Title: fileInsert</p>  
	
	 * <p>Description: </p>  
		上传Excel考勤表
	 * @param src	路径+文件全名
	 * @param adminId	后台账户id
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer fileInsert(String src, Long adminId);
}
