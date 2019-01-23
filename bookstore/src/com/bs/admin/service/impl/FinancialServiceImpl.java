package com.bs.admin.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.FinancialDao;
import com.bs.admin.dao.PurchaseDao;
import com.bs.admin.pojo.Financial;
import com.bs.admin.pojo.FinancialDatas;
import com.bs.admin.pojo.OrderModel;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.Purchase;
import com.bs.admin.pojo.QueryBoxData;
import com.bs.admin.pojo.Salary;
import com.bs.admin.service.FinancialService;
import com.bs.admin.service.OrderService;
import com.bs.admin.service.SalaryService;
import com.bs.admin.util.JsonData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Repository
public class FinancialServiceImpl implements FinancialService {
	@Autowired
	private FinancialDao fd;
	@Autowired
	private SalaryService ss;
	@Autowired
	private PurchaseDao pd;
	@Autowired
	private OrderService os;

	@Override
	public List<QueryBoxData> getAllStastus() {
		List<String> statusList = fd.getAllFinancialStatus();
		List<QueryBoxData> status = new ArrayList<QueryBoxData>();
		for(int i =0;i<statusList.size();i++) {
			status.add(new QueryBoxData(i, statusList.get(i), false));
		}
		return status;
	}

	@Override
	public List<QueryBoxData> getAllTypes() {
		List<String> typesList = fd.getAllFinancaialTypes();
		List<QueryBoxData> types = new ArrayList<QueryBoxData>();
		for(int i =0;i<typesList.size();i++) {
			types.add(new QueryBoxData(i, typesList.get(i), false));
		}
		return types;
	}

	@Override
	public PageData<Financial> getAllFinancial(Integer pageNum, Integer pageSize, String financialStatus,String financialTypes, String financialStart, String financialEnd) {
		Integer total = fd.getFinancialTotal(financialStatus, financialTypes, financialStart, financialEnd);
		// 获取开始位置
		Integer start = (pageNum - 1) * pageSize;
		// 获取结束位置
		Integer end = (pageNum <= (total / pageSize)) ? pageSize : total;
		return new PageData<Financial>(fd.getAllFinancial(financialStatus, financialTypes, financialStart, financialEnd, start, end), total);
	}
	
	@Override
	public Integer addFinancial(Financial financial) {
		return fd.insertFinancial(financial);
	}

	@Override
	public Integer delFinancial(Financial financial) {
		return fd.delFinancial(financial);
	}

	@Override
	public Integer updateFinancial(Financial financial) {
		return fd.updateFinancial(financial);
	}
	
	@Override
	public PageData<Salary> getSalaryData(Integer pageNum, Integer pageSize) {
		return ss.getUnfinishedSalary(pageNum, pageSize);
	}
	
	// 添加工资支出
	@Override
	public JsonData addSalaryExpend(List<FinancialDatas> financialDatas) {
		Integer index = 0;
		String status ="支出";
		String type = "工资";
		String financialDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		for (FinancialDatas datas : financialDatas) {
			String details =datas.getDate()+"支付员工"+datas.getName()+"工资"+datas.getMoney()+"元";
			Financial financial = new Financial(status, type,datas.getMoney(), financialDate, datas.getDate(), details);
			if(fd.getIdByFinancial(financial)==null) {
				if(fd.insertFinancial(financial)>0) {
					ss.updateSalaryStatus(Long.valueOf(datas.getId()), true);
					index++;
				}
			}
		}
		if (index > 0) {
			return new JsonData(null, null, "添加" + index + "条数据成功", true);
		} else {
			return new JsonData(null, null, "添加失败", false);
		}
	}
	
	@Override
	public PageData<Purchase> getPurchaseData(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Purchase> rows = pd.selectPurchaseBuget();
		Long total = new PageInfo<Purchase>(rows).getTotal();
		return new PageData<Purchase>(rows, total.intValue());
	}
		
	// 添加采购支出
	@Override
	public JsonData addPurchaseExpend(List<FinancialDatas> financialDatas) {
		Integer index =0;
		String status ="支出";
		String type = "采购";
		String financialDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		for (FinancialDatas datas : financialDatas) {
			String details =datas.getDate()+"采购-编号为"+datas.getId()+"的商品向"+datas.getName()+"支付"+datas.getMoney()+"元";
			Financial financial = new Financial(status, type,datas.getMoney(), financialDate, datas.getDate(), details);
			if(fd.getIdByFinancial(financial)==null) {
				if(fd.insertFinancial(financial)>0) {
					pd.updatePurchase(new Purchase(datas.getId(), 2));
					index++;
				}
			}
		}
		if (index > 0) {
			return new JsonData(null, null, "添加" + index + "条数据成功", true);
		} else {
			return new JsonData(null, null, "添加失败", false);
		}
	}
	
	@Override
	public PageData<OrderModel> getOrderData(Integer pageNum, Integer pageSize) {
		Integer total = os.getAchieveTotal();
		Integer start = (pageNum - 1) * pageSize;
		Integer end = (pageNum <= (total / pageSize)) ? pageSize : total;
		return new PageData<OrderModel>(os.getAchieveOrder(start, end), total);
	}
	
	// 添加订单收入
	@Override
	public JsonData addOrderReveNue(List<FinancialDatas> financialDatas) {
		Integer index = 0;
		String status ="收入";
		String type = "售书";
		String financialDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		for (FinancialDatas datas : financialDatas) {
			String details =datas.getDate()+"用户"+datas.getName()+"购书支付"+datas.getMoney()+"元";
			Financial financial = new Financial(status, type,datas.getMoney(), financialDate, datas.getDate(), details);
			if(fd.getIdByFinancial(financial)==null) {
				if(fd.insertFinancial(financial)>0) {
					os.updateAchieve(true, datas.getCount());
					index++;
				}
			}
		}	
		if (index > 0) {
			return new JsonData(null, null, "添加" + index + "条数据成功", true);
		} else {
			return new JsonData(null, null, "添加失败", false);
		}
	}
}
