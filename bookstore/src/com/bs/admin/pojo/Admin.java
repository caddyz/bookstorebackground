package com.bs.admin.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 

* <p>Title: Admin</p>  

* <p>Description: </p>  
	后台管理员
* @author zhengjian  

* <p> @date 2018年11月23日</p>
 */
public class Admin implements Serializable{
	/** serialVersionUID*/ 
	private static final long serialVersionUID = -1285800037912691985L;
	private Long adminId;
	/**
	 * 登录帐号
	 */
	private String adminAccount;
	/**
	 * 登录密码
	 */
	private String adminPassWord;
	/**
	 * 
	 */
	private Employee emp;
	
	/**
	 * 管理员角色。
	 */
	private List<Permission> permissions;
	
	private List<ChatMessage> msgs;



	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminAccount=" + adminAccount + ", adminPassWord=" + adminPassWord
				+ ", emp=" + emp + ", permissions=" + permissions + ", msgs=" + msgs + "]";
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getAdminAccount() {
		return adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public String getAdminPassWord() {
		return adminPassWord;
	}

	public void setAdminPassWord(String adminPassWord) {
		this.adminPassWord = adminPassWord;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<ChatMessage> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<ChatMessage> msgs) {
		this.msgs = msgs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(Long adminId, String adminAccount, String adminPassWord, Employee emp, List<Permission> permissions,
			List<ChatMessage> msgs) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminPassWord = adminPassWord;
		this.emp = emp;
		this.permissions = permissions;
		this.msgs = msgs;
	}

	


	
}
