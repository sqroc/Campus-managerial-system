package ems.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Fettle {// 维修工信息基表
	private int fid;// 维修工工号
	private String fname;// 维修工姓名
	private Date hireDate;// 雇佣时间
	private Date lastDate;// 上一次维修时间
	private double salary;// 薪水
	private String workField;// 工作领域
	private String introduction;// 简介
	private String contact1;// 联系方式1
	private String contact2;// 联系方式2
	private int fixTimes = 0;// 总参与维修次数

	/**
	 * @return the fid
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FettleGenerator")
	@SequenceGenerator(name = "FettleGenerator", sequenceName = "FettleSQE", allocationSize = 1)
	public int getFid() {
		return fid;
	}

	/**
	 * @param fid
	 *            the fid to set
	 */
	public void setFid(int fid) {
		this.fid = fid;
	}

	/**
	 * @return the hireDate
	 */
	public Date getHireDate() {
		return hireDate;
	}

	/**
	 * @param hireDate
	 *            the hireDate to set
	 */
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	/**
	 * @return the lastDate
	 */
	public Date getLastDate() {
		return lastDate;
	}

	/**
	 * @param lastDate
	 *            the lastDate to set
	 */
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary
	 *            the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * @return the workField
	 */
	public String getWorkField() {
		return workField;
	}

	/**
	 * @param workField
	 *            the workField to set
	 */
	public void setWorkField(String workField) {
		this.workField = workField;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction
	 *            the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return the contact1
	 */
	public String getContact1() {
		return contact1;
	}

	/**
	 * @param contact1
	 *            the contact1 to set
	 */
	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}

	/**
	 * @return the contact2
	 */
	public String getContact2() {
		return contact2;
	}

	/**
	 * @param contact2
	 *            the contact2 to set
	 */
	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}

	/**
	 * @return the fixTimes
	 */
	public int getFixTimes() {
		return fixTimes;
	}

	/**
	 * @param fixTimes
	 *            the fixTimes to set
	 */
	public void setFixTimes(int fixTimes) {
		this.fixTimes = fixTimes;
	}

	/**
	 * @param fname the fname to set
	 */
	public void setFname(String fname) {
		this.fname = fname;
	}

	/**
	 * @return the fname
	 */
	public String getFname() {
		return fname;
	}

}
