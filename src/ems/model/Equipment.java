package ems.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class Equipment {// 设备信息基表

	private int eid;
	private String type;// 设备型号
	private String ename;// 设备名 （唯一）
	private double price;// 价格
	private Date buytime;// 购买时间
	private Provider provider;// 提供商 id
	private Date lasttime;// 上次维修时间
	private int state;// 状态 0,1,2 0 正常 ，1 未检修 2 故障
	private int fixInterval = 30;// 该设备检修间隔(day)
	private int fixedTimes = 0;// 修理总次数
	private String location;// 地址编号外键
	private double totalCost;// 总费用

	private String stateRank;// 状态描述 不参与持久化 描述 state

	public Date getBuytime() {
		return buytime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EquipmentGenerator")
	@SequenceGenerator(name = "EquipmentGenerator", sequenceName = "EquipmentSQE", allocationSize = 1)
	public int getEid() {
		return eid;
	}

	public String getEname() {
		return ename;
	}

	/**
	 * @return the fixedTimes
	 */
	public int getFixedTimes() {
		return fixedTimes;
	}

	/**
	 * @return the fixInterval
	 */
	public int getFixInterval() {
		return fixInterval;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public String getLocation() {
		return location;
	}

	public double getPrice() {
		return price;
	}

	/**
	 * @return the location
	 */

	@ManyToOne
	@JoinColumn(name = "pid")
	public Provider getProvider() {
		return provider;
	}

	public int getState() {
		return state;
	}

	@Transient
	public String getStateRank() {
		return stateRank;
	}

	public String getType() {
		return type;
	}

	public void setBuytime(Date buytime) {
		this.buytime = buytime;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	/**
	 * @param fixedTimes
	 *            the fixedTimes to set
	 */
	public void setFixedTimes(int fixedTimes) {
		this.fixedTimes = fixedTimes;
	}

	/**
	 * @param fixInterval
	 *            the fixInterval to set
	 */
	public void setFixInterval(int fixInterval) {
		this.fixInterval = fixInterval;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setStateRank(String stateRank) {
		this.stateRank = stateRank;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

}
