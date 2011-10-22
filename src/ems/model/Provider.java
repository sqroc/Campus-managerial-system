package ems.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class Provider {//厂家信息基表

	private int pid;//厂家编号
	private String telephone;//联系方式1
	private String address;//地址
	private String pname;//厂家名称
	private String isProvided;//是否提供服务
	private String serviceComment;//维修效果评价
	private String qualityComment;//产品质量评价
	private String speedComment;//反应速度评价
	private String contact2;//第二联系方式
	

	/**
	 * @return the serviceComment
	 */
	public String getServiceComment() {
		return serviceComment;
	}

	/**
	 * @param serviceComment the serviceComment to set
	 */
	public void setServiceComment(String serviceComment) {
		this.serviceComment = serviceComment;
	}

	/**
	 * @return the qualityComment
	 */
	public String getQualityComment() {
		return qualityComment;
	}

	/**
	 * @param qualityComment the qualityComment to set
	 */
	public void setQualityComment(String qualityComment) {
		this.qualityComment = qualityComment;
	}

	/**
	 * @return the speedComment
	 */
	public String getSpeedComment() {
		return speedComment;
	}

	/**
	 * @param speedComment the speedComment to set
	 */
	public void setSpeedComment(String speedComment) {
		this.speedComment = speedComment;
	}

	/**
	 * @return the contact2
	 */
	public String getContact2() {
		return contact2;
	}

	/**
	 * @param contact2 the contact2 to set
	 */
	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProviderGenerator")
	@SequenceGenerator(name = "ProviderGenerator", sequenceName = "ProviderSQE", allocationSize = 1)
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}



	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Transient
	public String getIsProvided() {
		return isProvided;
	}

	public void setIsProvided(String isProvided) {
		this.isProvided = isProvided;
	}

}
