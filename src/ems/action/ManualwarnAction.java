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
import ems.model.Equipment;
import ems.model.LogOverhaul;
import ems.model.ManualWarn;
import ems.service.AdminManager;
import ems.service.EquipmentManager;
import ems.service.LogOverhaulManager;
import ems.service.ManualWarnManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Manualwarn")
@Results(

{ @Result(type = "json") })
public class ManualwarnAction extends ActionSupport implements ModelDriven {

	private boolean success;
	private List<ManualWarn> ManualWarnList;
	private ManualWarn manualWarn = new ManualWarn();
	private ManualWarnManager manualWarnManager;
	private EquipmentManager equipmentManager;
	private AdminManager adminManager;
	private LogOverhaulManager logoverhaulManager;
	private String ename;

	// 分页
	private int start;
	private int limit;

	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public String add() throws Exception {

		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");

		Equipment tempEquipment = equipmentManager.loadByEname(ename);

		tempEquipment.setState(2);
		equipmentManager.modify(tempEquipment);

		manualWarn.setAdmin(tempAdmin);
		manualWarn.setEquipment(tempEquipment);

		if (manualWarn.getIsDeal() == null) {
			manualWarn.setIsDeal("未解决");
		}

		// manualWarnManager.add(manualWarn);
		//
		// // 添加LogOverhaul
		LogOverhaul newLogOverhaul = new LogOverhaul();

		newLogOverhaul.setManualWarn(manualWarn);
		newLogOverhaul.setNatation(manualWarn.getNatation());
		newLogOverhaul.setState("未处理");
		logoverhaulManager.add(newLogOverhaul);

		success = true;
		return SUCCESS;

	}

	public String modify() throws Exception {
		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");

		Equipment tempEquipment = equipmentManager.loadByEname(ename);
		manualWarn.setAdmin(tempAdmin);
		manualWarn.setEquipment(tempEquipment);

		if (manualWarn.getIsDeal() == null) {
			manualWarn.setIsDeal("未解决");
		}

		else {
			tempEquipment.setState(0);

			equipmentManager.modify(tempEquipment);
		}

		manualWarn.setAdmin(tempAdmin);
		manualWarn.setEquipment(tempEquipment);
		manualWarnManager.modify(manualWarn);
		success = true;
		return SUCCESS;
	}

	public int deleteByid(int id) throws Exception {

		return manualWarnManager.deleletByid(id);

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

	public String getManualWarns() throws Exception {

		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");
		total = manualWarnManager.getTotalNum(tempAdmin);
		ManualWarnList = manualWarnManager.getManualWarns(tempAdmin, start,
				limit);

		this.success = true;

		return SUCCESS;
	}

	@JSON(serialize = false)
	public Object getModel() {

		return manualWarn;
	}

	@JSON(serialize = false)
	public ManualWarn getManualWarn() {
		return manualWarn;
	}

	public void setManualWarn(ManualWarn manualWarn) {
		this.manualWarn = manualWarn;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<ManualWarn> getManualWarnList() {
		return ManualWarnList;
	}

	public void setManualWarnList(List<ManualWarn> manualWarnList) {
		ManualWarnList = manualWarnList;
	}

	@JSON(serialize = false)
	public ManualWarnManager getManualWarnManager() {
		return manualWarnManager;
	}

	@JSON(serialize = false)
	public EquipmentManager getEquipmentManager() {
		return equipmentManager;
	}

	@Resource
	public void setEquipmentManager(EquipmentManager equipmentManager) {
		this.equipmentManager = equipmentManager;
	}

	@Resource
	public void setManualWarnManager(ManualWarnManager manualWarnManager) {
		this.manualWarnManager = manualWarnManager;
	}

	@JSON(serialize = false)
	public AdminManager getAdminManager() {
		return adminManager;
	}

	@Resource
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}

	@JSON(serialize = false)
	public LogOverhaulManager getLogoverhaulManager() {
		return logoverhaulManager;
	}

	@Resource
	public void setLogoverhaulManager(LogOverhaulManager logoverhaulManager) {
		this.logoverhaulManager = logoverhaulManager;
	}

}
