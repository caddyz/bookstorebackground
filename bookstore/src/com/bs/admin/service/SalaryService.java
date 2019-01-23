package com.bs.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bs.admin.pojo.Admin;
import com.bs.admin.pojo.Employee;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Salary;
@Service
public interface SalaryService {
	/**
	 * 
	
	 * <p>Title: findAllSalary</p>  
	
	 * <p>Description: </p>  
		获取所有薪资信息
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	List<Salary> findAllSalary();
	/**
	 * 
	
	 * <p>Title: getPageData</p>  
	
	 * <p>Description: </p>  
		获取分页信息
	 * @param pageNum	当前页数
	 * @param pageSize	页面容量
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	PageData<Salary> getPageData(Integer pageNum, Integer pageSize);
	
	/**
	 * 
	
	 * <p>Title: addSalary</p>  
	
	 * <p>Description: </p>  
		根据员工id和当前日期查询考勤信息，结合底薪、津贴，以及后台操作者，新增工资信息
	 * @param empId
	 * @param admin
	 * @return  如果员工不存在，返回-1。如果工资信息已存在（重复）返回-2
	 * <p> @date 2018年12月6日  </p>
	 */
	Integer addSalary(Integer empId, Admin admin);
	
	
	/**
	 * 
	
	 * <p>Title: getSalaryMap</p>  
	
	 * <p>Description: </p>  
		获取所有员工的工资
	 * @param date 工资发放的日期"2018-12-06"
	 * @return  key:员工对象。value:总工资
	 * <p> @date 2018年12月6日  </p>
	 */
	Map<Employee, Integer> getSalaryMap(String date1, String date2);
	
	/**
	 * 
	
	 * <p>Title: getAttendanceOfSalary</p>  
	
	 * <p>Description: </p>  
		查询员工在对应日期中考勤的具体影响
	 * @param empId
	 * @param date
	 * @return  
	 * <p> @date 2018年12月6日  </p>
	 */
	Integer getAttendanceOfSalary(Long empId,String date);
	/**
	 * 
	
	 * <p>Title: deleteSalary</p>  
	
	 * <p>Description: </p>  
		删除薪资信息
	 * @param salaryId
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	Integer deleteSalary(Long salaryId);
	/**
	 * 
	
	 * <p>Title: getSalaryByEmpIdAndDate</p>  
	
	 * <p>Description: </p>  
		通过员工id和发放日期查询薪资信息
	 * @param empId
	 * @param date
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	Salary getSalaryByEmpIdAndDate(Integer empId, String date);
	
	/**
	 * 修改工资状态
	 * @param salaryId		工资编号
	 * @param salaryStatus	工资状态
	 * @return
	 */
	Integer updateSalaryStatus(Long salaryId, boolean salaryStatus);
	/**
	 * 获取所有未发放的薪资
	 * @return
	 */
	PageData<Salary> getUnfinishedSalary(Integer pageNum, Integer pageSize);
}
