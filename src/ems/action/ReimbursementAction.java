package ems.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import ems.model.Admin;
import ems.model.Reimbursement;
import ems.service.ReimbursementManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Reimbursement")
@Results(

{ @Result(type = "json") })
public class ReimbursementAction extends ActionSupport implements ModelDriven {

	private boolean success;
	private List<Reimbursement> ReimbursementList;
	private ReimbursementManager reimbursementManager;
	private Reimbursement reimbursement = new Reimbursement();
	// 分页
	private int start;
	private int limit;
	private int total;

	public String add() throws Exception {

		success = true;
		return SUCCESS;
	}

	public int deleteBatch(int[] eids) throws Exception {
		int deleteNum = 0;
		for (int i = 0; i < eids.length; i++) {
			if (deleteByid(eids[i]) == 1) {
				deleteNum++;
			}
		}

		return deleteNum;
	}

	public int deleteByid(int id) throws Exception {

		return reimbursementManager.deleteByid(id);

	}

	@JSON(serialize = false)
	public int getLimit() {
		return limit;
	}

	@JSON(serialize = false)
	public Reimbursement getManualWarn() {
		return reimbursement;
	}

	@JSON(serialize = false)
	public Object getModel() {

		return reimbursement;
	}

	public List<Reimbursement> getReimbursementList() {
		return ReimbursementList;
	}

	@JSON(serialize = false)
	public ReimbursementManager getReimbursementManager() {
		return reimbursementManager;
	}

	public String getReimbursements() throws Exception {

		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");
		total = reimbursementManager.getTotalNum();
		ReimbursementList = reimbursementManager
				.getReimbursements(start, limit);

		this.success = true;

		return SUCCESS;
	}

	@JSON(serialize = false)
	public int getStart() {
		return start;
	}

	public int getTotal() {
		return total;
	}

	public boolean isSuccess() {
		return success;
	}

	public String modify() throws Exception {
		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");
		if (!(reimbursement.getOperator().equals("未审核") || reimbursement
				.getOperator().equals(""))) {

			reimbursementManager.modify(reimbursement);
		}
		success = true;
		return SUCCESS;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setReimbursement(Reimbursement reimbursement) {
		this.reimbursement = reimbursement;
	}

	public void setReimbursementList(List<Reimbursement> reimbursementList) {
		ReimbursementList = reimbursementList;
	}

	@Resource
	public void setReimbursementManager(
			ReimbursementManager reimbursementManager) {
		this.reimbursementManager = reimbursementManager;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
