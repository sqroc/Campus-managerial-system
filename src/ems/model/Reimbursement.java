package ems.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Reimbursement {// 财务部门报销清单
	private int rid;// 报销单编号
	private Date createDate;// 创建时间
	private String operator = "";// 经手人
	private double cost;// 实际报销费用
	private int logOverhaulId;//

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ReimbursementGenerator")
	@SequenceGenerator(name = "ReimbursementGenerator", sequenceName = "ReimbursementSQE", allocationSize = 1)
	public int getRid() {
		return rid;
	}

	/**
	 * @param rid
	 *            the rid to set
	 */
	public void setRid(int rid) {
		this.rid = rid;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 *            the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * @param logOverhaulId
	 *            the logOverhaulId to set
	 */
	public void setLogOverhaulId(int logOverhaulId) {
		this.logOverhaulId = logOverhaulId;
	}

	/**
	 * @return the logOverhaulId
	 */
	public int getLogOverhaulId() {
		return logOverhaulId;
	}

}
