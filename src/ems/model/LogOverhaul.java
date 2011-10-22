package ems.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class LogOverhaul {// 维修信息表
	private int lgd;

	private String natation; // 检修描述
	private Fettle fettle;// 派出的修理工
	private ManualWarn manualWarn;// 相关报修记录

	private double cost;// 本次修理花费
	private Date fixDate;// 修理日期
	private String callProvider;// 是否由厂家维修
	private String state;// 未处理，已派遣，处理完成

	private String adminName;
	private String equipName;
	private String Manualnatation;

	public LogOverhaul() {
		this.natation = "";

		this.cost = 0;
		this.fixDate = new Date();
		this.callProvider = "";

	}

	/**
	 * @return the callProvider
	 */
	public String getCallProvider() {
		return callProvider;
	}

	public double getCost() {
		return cost;
	}

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "fid")
	public Fettle getFettle() {
		return fettle;
	}

	/**
	 * @return the fixDate
	 */
	public Date getFixDate() {
		return fixDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LogOverhaulGenerator")
	@SequenceGenerator(name = "LogOverhaulGenerator", sequenceName = "LogOverhaulSQE", allocationSize = 1)
	public int getLgd() {
		return lgd;
	}

	/**
	 * @return the manualWarn
	 */
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "mwid")
	public ManualWarn getManualWarn() {
		return manualWarn;
	}

	public String getNatation() {
		return natation;
	}

	public String getState() {
		return state;
	}

	/**
	 * @param callProvider
	 *            the callProvider to set
	 */
	public void setCallProvider(String callProvider) {
		this.callProvider = callProvider;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * @param fettle
	 *            the fettle to set
	 */
	public void setFettle(Fettle fettle) {
		this.fettle = fettle;
	}

	/**
	 * @param fixDate
	 *            the fixDate to set
	 */
	public void setFixDate(Date fixDate) {
		this.fixDate = fixDate;
	}

	public void setLgd(int lgd) {
		this.lgd = lgd;
	}

	/**
	 * @param manualWarn
	 *            the manualWarn to set
	 */
	public void setManualWarn(ManualWarn manualWarn) {
		this.manualWarn = manualWarn;
	}

	public void setNatation(String natation) {
		this.natation = natation;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Transient
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	@Transient
	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	@Transient
	public String getManualnatation() {
		return Manualnatation;
	}

	public void setManualnatation(String manualnatation) {
		Manualnatation = manualnatation;
	}

}
