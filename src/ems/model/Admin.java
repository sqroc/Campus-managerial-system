package ems.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Admin {//管理员信息基表

	private int aid;//管理员id
	private String password;//密码
	private String name;//登入用户名
	private String gender;//性别
	private int rank;//权限 0,1,2,3   0 最高
	private Date addDate;//该用户添加时间
	private int state;//在线 1 离线 0  
	private Date lastLogin;//最近一次登录时间
	private String accountStatus;//账号状态，是否被锁定
	private String email;//电子邮箱地址


	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the accountStatus
	 */
	public String getAccountStatus() {
		return accountStatus;
	}

	/**
	 * @param accountStatus the accountStatus to set
	 */
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdminGenerator")
	@SequenceGenerator(name = "AdminGenerator", sequenceName = "AdminSQE", allocationSize = 1)
	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}


}
