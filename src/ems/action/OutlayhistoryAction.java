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

import ems.model.OutlayHistory;
import ems.service.OutlayHistoryManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Outlayhistory")
@Results(

{ @Result(type = "json") })
public class OutlayhistoryAction extends ActionSupport implements ModelDriven {
	private boolean success;
	private List<OutlayHistory> OutlayhistoryList;
	private OutlayHistoryManager outlayhistoryManager;
	private OutlayHistory outlayhistory = new OutlayHistory();
	private String ListForchart;

	private int start;
	private int limit;
	private int total;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String add() throws Exception {

		// Fettle newfettle = fettleManager.loadByPname(pname);

		outlayhistoryManager.add(outlayhistory);

		this.success = true;

		return SUCCESS;

	}

	public String modify() throws Exception {

		outlayhistoryManager.modify(outlayhistory);

		this.success = true;

		return SUCCESS;

	}

	public int deleteByid(int eid) throws Exception {
		return outlayhistoryManager.deleteByid(eid);
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

	public String getOutlayHistorys() throws Exception {
		total = outlayhistoryManager.getTotalNum();
		OutlayhistoryList = outlayhistoryManager
				.getOutlayHistorys(start, limit);
		this.success = true;

		return SUCCESS;
	}

	@JSON(serialize = false)
	public Object getModel() {

		return outlayhistory;
	}

	public List<OutlayHistory> getOutlayhistoryList() {
		return OutlayhistoryList;
	}

	public void setOutlayhistoryList(List<OutlayHistory> outlayhistoryList) {
		OutlayhistoryList = outlayhistoryList;
	}

	@JSON(serialize = false)
	public OutlayHistoryManager getOutlayhistoryManager() {
		return outlayhistoryManager;
	}

	@Resource
	public void setOutlayhistoryManager(
			OutlayHistoryManager outlayhistoryManager) {
		this.outlayhistoryManager = outlayhistoryManager;
	}

	@JSON(serialize = false)
	public OutlayHistory getOutlayhistory() {
		return outlayhistory;
	}

	public void setOutlayhistory(OutlayHistory outlayhistory) {
		this.outlayhistory = outlayhistory;
	}

	public void setListForchart(String listForchart) {
		ListForchart = listForchart;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
