package ems.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class OutlayHistory {// 后勤部门支出信息表
	private int ohid;// 流水账编号
	private Date outlayDate;// 支出日期
	// private Operator operator;//经办人
	private double cost;// 费用
	private String notation;// 附言
	private Equipment equipment;// 相关设备

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OutlayHistoryGenerator")
	@SequenceGenerator(name = "OutlayHistoryGenerator", sequenceName = "OutlayHistorySQE", allocationSize = 1)
	public int getOhid() {
		return ohid;
	}

	/**
	 * @param ohid
	 *            the ohid to set
	 */
	public void setOhid(int ohid) {
		this.ohid = ohid;
	}

	/**
	 * @return the outlayDate
	 */
	public Date getOutlayDate() {
		return outlayDate;
	}

	/**
	 * @param outlayDate
	 *            the outlayDate to set
	 */
	public void setOutlayDate(Date outlayDate) {
		this.outlayDate = outlayDate;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	public String getNotation() {
		return notation;
	}

	public void setNotation(String notation) {
		this.notation = notation;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@ManyToOne
	@JoinColumn(name = "pid")
	public Equipment getEquipment() {
		return equipment;
	}

	/**
	 * @param equipment
	 *            the equipment to set
	 */
	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

}
