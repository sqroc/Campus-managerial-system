package ems.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Location {//位置信息基表
	private int locNumber;//位置主键
	private String locName;//位置名称
	/**
	 * @return the locNumber
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LocNumberGenerator")
	@SequenceGenerator(name = "LocNumberGenerator", sequenceName = "LocNumberSQE", allocationSize = 1)
	public int getLocNumber() {
		return locNumber;
	}
	/**
	 * @param locNumber the locNumber to set
	 */
	public void setLocNumber(int locNumber) {
		this.locNumber = locNumber;
	}
	/**
	 * @return the locName
	 */
	public String getLocName() {
		return locName;
	}
	/**
	 * @param locName the locName to set
	 */
	public void setLocName(String locName) {
		this.locName = locName;
	}

}
