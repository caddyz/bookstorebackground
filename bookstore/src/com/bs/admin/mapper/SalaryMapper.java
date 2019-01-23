package com.bs.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.admin.pojo.Salary;

public interface SalaryMapper {
	/**
	 * 
	
	 * <p>Title: getAllSalary</p>  
	
	 * <p>Description: </p>  
		获取所有薪资信息
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<Salary> getAllSalary();
	/**
	 * 
	
	 * <p>Title: createSalary</p>  
	
	 * <p>Description: </p>  
		新增薪资信息
	 * @param salary
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer createSalary(Salary salary);
	/**
	 * 
	
	 * <p>Title: deleteSalary</p>  
	
	 * <p>Description: </p>  
		删除薪资信息
	 * @param salaryId
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer deleteSalary(Long salaryId);
	/**
	 * 
	
	 * <p>Title: getSalaryByEmpIdAndDate</p>  
	
	 * <p>Description: </p>  
		根据员工编号和日期获取工资信息
	 * @param empId
	 * @param salaryDate
	 * @return  
	 * <p> @date 2018年12月6日  </p>
	 */
	Salary getSalaryByEmpIdAndDate(@Param("empId")Long empId, @Param("salaryDate")String salaryDate);
	/**
	 * 
	
	 * <p>Title: getAllSalaryByDate</p>  
	
	 * <p>Description: </p>  
		获取指定时间段的薪资信息
	 * @param date1
	 * @param date2
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<Salary> getAllSalaryByDate(@Param("date1")String date1,@Param("date2")String date2);
	
	/**
	 * 通过id查询薪资信息
	 * @param salaryId
	 * @return
	 */
	Salary getSalaryById(Long salaryId);
	/**
	 * 修改薪资状态
	 * @param salary
	 * @return
	 */
	Integer updateSalaryStatus(Salary salary);
	/**
	 * 获取所有未发放状态的工资信息
	 * @return
	 */
	List<Salary> getUnfinishedSalary();
}
