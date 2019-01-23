package com.bs.admin.dao;

import java.util.List;

import com.bs.admin.pojo.Financial;

public interface FinancialDao {

	/**
	 * 获取支出、收入
	 * 
	 * @return 类型集合
	 */
	List<String> getAllFinancialStatus();

	/**
	 * 获取支出、收入的类型
	 * 
	 * @return 类型集合
	 */
	List<String> getAllFinancaialTypes();

	/**
	 * 财务信息查询
	 * 
	 * @return 按页返回的集合
	 */
	List<Financial> getAllFinancial(String financialStatus, String financialTypes, String financialStart, String financialEnd, Integer start, Integer end);

	/**
	 * 查询总条数
	 * 
	 * @return 总条数
	 */
	Integer getFinancialTotal(String financialStatus, String financialTypes, String financialStart, String financialEnd);

	/**
	 * 查询满足条件的支出信息有无(判断id是否为空)
	 * 
	 * @return coupon
	 */
	Financial getIdByFinancial(Financial financial);

	/**
	 * 财务信息添加
	 *
	 * @return
	 */
	Integer insertFinancial(Financial financial);

	/**
	 * 财务信息删除
	 *
	 * @return
	 */
	Integer delFinancial(Financial financial);

	/**
	 * 财务信息修改
	 * 
	 * @return Integer
	 */
	Integer updateFinancial(Financial financial);
}
