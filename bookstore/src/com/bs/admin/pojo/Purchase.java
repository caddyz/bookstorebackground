package com.bs.admin.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  

* <p>Title: Purchase</p>  

* <p>Description: 采购信息</p>  

* @author 王顺坤 

* <p> @date 2018年11月21日</p>   

*/ 

/**  

* <p>Title: Purchase</p>  

* <p>Description: </p>  

* @author 王顺坤  

* <p> @date 2018年12月20日</p>   

*/ 
public class Purchase implements Serializable{
	/** serialVersionUID*/ 
	private static final long serialVersionUID = 1L;
	private Integer purchaseId;
	/** purchaseBatch 采购批次*/ 
	private String  purchaseBatch;
	/** purchaseDate 采购日期*/ 
	private String purchaseDate;
	/** employee 采购负责的员工*/ 
	private Employee employee;
	/** supplier 供应商信息*/ 
	private Supplier supplier;
	/** purchaseStatus 采购状态  应该有
	 * 1. 计划中
	 * 2. 进行中
	 * 3. 待入库
	 * 4. 已入库
	 * 0. 已取消
	 * */ 
	private Integer purchaseStatus;
	/** purchaseLog
	 * 订单备注
	 * 
	 * */ 
	private String purchaseLog;
	/** booklist
	 * 该订单包含的数目
	 * */ 
	private List<PurchaseItem> booklist;
	private Double totalPay;

	public Purchase() {
	}

	public Purchase(Integer purchaseId, String purchaseBatch, String purchaseDate, Employee employee, Supplier supplier,
			Integer purchaseStatus, String purchaseLog) {
		this.purchaseId = purchaseId;
		this.purchaseBatch = purchaseBatch;
		this.purchaseDate = purchaseDate;
		this.employee = employee;
		this.supplier = supplier;
		this.purchaseStatus = purchaseStatus;
		this.purchaseLog = purchaseLog;
	}
	

	public Purchase(Integer purchaseId, Integer purchaseStatus) {
		super();
		this.purchaseId = purchaseId;
		this.purchaseStatus = purchaseStatus;
	}

	public Purchase(Integer purchaseId, String purchaseBatch, String purchaseDate, Employee employee, Supplier supplier,
			Integer purchaseStatus, String purchaseLog, List<PurchaseItem> booklist, Double totalPay) {
		this.purchaseId = purchaseId;
		this.purchaseBatch = purchaseBatch;
		this.purchaseDate = purchaseDate;
		this.employee = employee;
		this.supplier = supplier;
		this.purchaseStatus = purchaseStatus;
		this.purchaseLog = purchaseLog;
		this.booklist = booklist;
		this.totalPay = totalPay;
	}

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getPurchaseBatch() {
		return purchaseBatch;
	}

	public void setPurchaseBatch(String purchaseBatch) {
		this.purchaseBatch = purchaseBatch;
	}

	public String getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Integer getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(Integer purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	public String getPurchaseLog() {
		return purchaseLog;
	}

	public void setPurchaseLog(String purchaseLog) {
		this.purchaseLog = purchaseLog;
	}

	public List<PurchaseItem> getBooklist() {
		return booklist;
	}

	public void setBooklist(List<PurchaseItem> booklist) {
		this.booklist = booklist;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	public Double getTotalPay() {
		return totalPay;
	}

	public void setTotalPay(Double totalPay) {
		this.totalPay = totalPay;
	}

	@Override
	public String toString() {
		return "Purchase [purchaseId=" + purchaseId + ", purchaseBatch=" + purchaseBatch + ", purchaseDate="
				+ purchaseDate + ", employee=" + employee + ", supplier=" + supplier + ", purchaseStatus="
				+ purchaseStatus + ", purchaseLog=" + purchaseLog + ", booklist=" + booklist + ", totalPay=" + totalPay
				+ "]";
	}




	
}
