package com.bs.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.SalaryDao;
import com.bs.admin.mapper.SalaryMapper;
import com.bs.admin.pojo.Salary;
@Repository
public class SalaryDaoImpl implements SalaryDao{
	@Autowired
	private SalaryMapper sm;
	@Override
	public List<Salary> retrieveAllSalary() {
		return sm.getAllSalary();
	}
	@Override
	public Integer insertSalary(Salary salary) {
		return sm.createSalary(salary);
	}
	@Override
	public Integer removeSalary(Long SalaryId) {
		return sm.deleteSalary(SalaryId);
	}
	@Override
	public Salary retrieveSalaryByEmpIdAndSalaryDate(Long empId, String salaryDate) {
		return sm.getSalaryByEmpIdAndDate(empId, salaryDate);
	}
	@Override
	public List<Salary> retrieveAllSalaryByDate(String date1, String date2) {
		return sm.getAllSalaryByDate(date1, date2);
	}
	@Override
	public Salary retieveSalaryById(Long salaryId) {
		return sm.getSalaryById(salaryId);
	}
	@Override
	public Integer changeSalaryStatus(Salary salary) {
		return sm.updateSalaryStatus(salary);
	}
	@Override
	public List<Salary> retrieveUnfinishedSalary() {
		return sm.getUnfinishedSalary();
	}

}
