package com.bs.admin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bs.admin.pojo.Salary;
/**
 * 

* <p>Title: SalaryDao</p>  

* <p>Description: </p>  
	薪资dao层
* @author zhengjian  

* <p> @date 2018年12月10日</p>
 */
@Repository
public interface SalaryDao {
	/**
	 * 
	
	 * <p>Title: retrieveAllSalary</p>  
	
	 * <p>Description: </p>  
		获取所有薪资信息
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<Salary> retrieveAllSalary();
	/**
	 * 
	
	 * <p>Title: insertSalary</p>  
	
	 * <p>Description: </p>  
		添加薪资信息
	 * @param salary
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer insertSalary(Salary salary);
	/**
	 * 
	
	 * <p>Title: removeSalary</p>  
	
	 * <p>Description: </p>  
		删除薪资信息
	 * @param SalaryId
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer removeSalary(Long SalaryId);
	/**
	 * 
	
	 * <p>Title: retrieveSalaryByEmpIdAndSalaryDate</p>  
	
	 * <p>Description: </p>  
		根基员工id和发放日期查询工资信息
	 * @param empId
	 * @param salaryDate
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Salary retrieveSalaryByEmpIdAndSalaryDate(Long empId, String salaryDate);
	/**
	 * 
	
	 * <p>Title: retrieveAllSalaryByDate</p>  
	
	 * <p>Description: </p>  
		获取时间段内所有薪资信息
	 * @param date1
	 * @param date2
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<Salary> retrieveAllSalaryByDate(String date1, String date2);
	
	Salary retieveSalaryById(Long salaryId);
	/**
	 * 修改薪资状态
	 * @param salary
	 * @return
	 */
	Integer changeSalaryStatus(Salary salary);
	/**
	 * 获取未发放状态的薪资
	 * @return
	 */
	List<Salary> retrieveUnfinishedSalary();
}
