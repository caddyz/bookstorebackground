package com.bs.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.admin.dao.SalaryDao;
import com.bs.admin.pojo.Admin;
import com.bs.admin.pojo.Attendance;
import com.bs.admin.pojo.Employee;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Salary;
import com.bs.admin.service.AttendanceService;
import com.bs.admin.service.EmployeeService;
import com.bs.admin.service.SalaryService;
import com.bs.admin.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class SalaryServiceImpl implements SalaryService {
	@Autowired
	private SalaryDao sd;
	@Autowired
	private EmployeeService es;
	@Autowired
	private AttendanceService as;

	@Override
	public List<Salary> findAllSalary() {
		return sd.retrieveAllSalary();
	}

	@Override
	public PageData<Salary> getPageData(Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);
		List<Salary> rows = findAllSalary();
		Long total = new PageInfo<Salary>(rows).getTotal();
		int total1 = total.intValue();
		return new PageData<Salary>(rows, total1);
	}
	

	@Override
	public Integer addSalary(Integer empId, Admin admin) {
		Long empId1 = Long.valueOf(empId);
		Employee emp = es.getEmpByEmpId(empId1);
		// 判断员工是否存在
		if(emp == null){
			return -1;
		}else{
			
			// 获取当前日期
			String date = DateUtil.getCurrentTimeString();
			//判断工资信息是否已经存在
			Salary salary0 = sd.retrieveSalaryByEmpIdAndSalaryDate(empId1, date);
			if(null != salary0){
				System.out.println("已存在工资信息");
				return -2;
			}
			// 获取员工当月的考勤信息
			Integer other = getAttendanceOfSalary(empId1, date);
			Salary salary = new Salary(null, emp, date, other, admin);
			return sd.insertSalary(salary);
		}
	}

	@Override
	public Map<Employee, Integer> getSalaryMap(String date1, String date2) {
		Map<Employee, Integer> salaryMap = new HashMap<>();
		List<Salary> allSalary = sd.retrieveAllSalaryByDate(date1, date2);
		for (Salary salary : allSalary) {
			Employee emp = salary.getEmployee();
			Integer totalSalary = emp.getJob().getJobBasicPay() + emp.getJob().getJobAllowance() + salary.getOther();
			salaryMap.put(emp, totalSalary);
		}
		return salaryMap;
	}

	@Override
	public Integer getAttendanceOfSalary(Long empId, String date) {
		Attendance att = as.findAttendanceByEmpIdAndDate(empId, date);
		// 将考勤奖罚记入other。满勤奖励300，迟到、早退每次罚40，请假扣100
		Integer other = 0;
		if(null != att){
			if (att.getLeaveDay() == 0 && att.getLateDay() == 0 && att.getEarlyDay() == 0) {
				System.out.println("该员工满勤");
				other += 300;
			} else {
				other -= 40 * (att.getLateDay() + att.getEarlyDay());
				other -= 100 * (att.getLeaveDay());
			}
		}
		
		return other;
	}

	@Override
	public Integer deleteSalary(Long salaryId) {
		Integer remove = sd.removeSalary(salaryId);
		return remove;
	}

	@Override
	public Salary getSalaryByEmpIdAndDate(Integer empId, String date) {
		Long empId1 = Long.valueOf(empId);
		return sd.retrieveSalaryByEmpIdAndSalaryDate(empId1, date);
	}

	@Override
	public Integer updateSalaryStatus(Long salaryId, boolean salaryStatus) {
		Salary salary = new Salary(salaryId, null, null, null, null);
		salary.setSalaryStatus(salaryStatus);
		return sd.changeSalaryStatus(salary);
	}

	@Override
	public PageData<Salary> getUnfinishedSalary(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Salary> rows = sd.retrieveUnfinishedSalary();
		Long total = new PageInfo<Salary>(rows).getTotal();
		int total1 = total.intValue();
		return new PageData<Salary>(rows, total1);
	}

}
