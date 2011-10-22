package ems.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import ems.model.Fettle;
import ems.service.FettleManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Fettle")
@Results(

{ @Result(type = "json") })
public class FettleAction extends ActionSupport implements ModelDriven {
	private boolean success;
	private List<Fettle> FettleList;
	private FettleManager fettleManager;
	private Fettle fettle = new Fettle();
	// 分页
	private int start;
	private int limit;
	private int total;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Fettle> getFettleList() {
		return FettleList;
	}

	public void setFettleList(List<Fettle> fettleList) {
		FettleList = fettleList;
	}

	public String add() throws Exception {

		// Fettle newfettle = fettleManager.loadByPname(pname);

		fettleManager.add(fettle);

		this.success = true;

		return SUCCESS;

	}

	public String modify() throws Exception {

		fettleManager.modify(fettle);

		this.success = true;

		return SUCCESS;

	}

	public int deleteByid(int eid) throws Exception {
		return fettleManager.deleteByid(eid);
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

	public String getFettles() throws Exception {
		total = fettleManager.getTotalNum();

		FettleList = fettleManager.getFettles(start, limit);
		this.success = true;

		return SUCCESS;
	}

	public String getFettlesForWork() throws Exception {
		total = fettleManager.getTotalNum();

		FettleList = fettleManager.getFettles(0, 100);
		this.success = true;

		return SUCCESS;
	}

	@JSON(serialize = false)
	public Object getModel() {

		return fettle;
	}

	@JSON(serialize = false)
	public Fettle getFettle() {
		return fettle;
	}

	public void setFettle(Fettle fettle) {
		this.fettle = fettle;
	}

	@JSON(serialize = false)
	public FettleManager getFettleManager() {
		return fettleManager;
	}

	@Resource
	public void setFettleManager(FettleManager fettleManager) {
		this.fettleManager = fettleManager;
	}

	@JSON(serialize = false)
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@JSON(serialize = false)
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@JSON(serialize = false)
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
