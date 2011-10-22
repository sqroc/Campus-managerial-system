package ems.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class LogSession {//管理员登录信息日志

	private int lsid;
	private int aid; //管理员  id
	private String name;//用户名
	private Date time;//登入时间
	private int type;// 0 登入 ，1 登出

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LogSessionGenerator")
	@SequenceGenerator(name = "LogSessionGenerator", sequenceName = "LogSessionSQE", allocationSize = 1)
	public int getLsid() {
		return lsid;
	}

	public void setLsid(int lsid) {
		this.lsid = lsid;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
