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

@Entity
public class ManualWarn {//报修信息源记录
	private int mwid;
	private Date warnTime; //报警日期
	private String natation;//描述
	private String isDeal;//是否解决
	private Admin admin;//负责人
	private Equipment equipment;//设备
	private String warnType;//是未检修0，还是故障1
	

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ManualWarnGenerator")
	@SequenceGenerator(name = "ManualWarnGenerator", sequenceName = "ManualWarnSQE", allocationSize = 1)
	public int getMwid() {
		return mwid;
	}

	public void setMwid(int mwid) {
		this.mwid = mwid;
	}

	public Date getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}

	public String getNatation() {
		return natation;
	}

	public void setNatation(String natation) {
		this.natation = natation;
	}

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "aid")
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "eid")
	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public String getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(String isDeal) {
		this.isDeal = isDeal;
	}

	public String getWarnType() {
		return warnType;
	}

	public void setWarnType(String warnType) {
		this.warnType = warnType;
	}

}
