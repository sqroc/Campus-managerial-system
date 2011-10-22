package ems.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@SuppressWarnings("serial")
@Entity
public class Message implements Serializable {
	//聊天信息记录表

	private int mid;
	private String fromName; //发送人
	private String receiveName;//接受者
	private String sendDate;//发送日期
	private String message;//消息
	private int state;//是否已被获取 

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MessageGenerator")
	@SequenceGenerator(name = "MessageGenerator", sequenceName = "MessageSQE", allocationSize = 1)
	
	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
