package ems.action;

import java.util.Date;
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
import ems.model.Fettle;
import ems.model.LogOverhaul;
import ems.model.OutlayHistory;
import ems.model.Reimbursement;
import ems.service.AdminManager;
import ems.service.EquipmentManager;
import ems.service.FettleManager;
import ems.service.LogOverhaulManager;
import ems.service.ManualWarnManager;
import ems.service.OutlayHistoryManager;
import ems.service.ReimbursementManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Logoverhaul")
@Results(

{ @Result(type = "json") })
public class LogoverhaulAction extends ActionSupport implements ModelDriven {

	/**
	 * 
	 */
	private boolean success;
	private List<LogOverhaul> LogOverhaulList;
	private LogOverhaul logOverhaul = new LogOverhaul();
	private LogOverhaulManager logOverhaulManager;
	private OutlayHistoryManager outlayHistoryManager;
	private ManualWarnManager manualWarntManager;
	private EquipmentManager equipmentManager;
	private FettleManager fettleManager;
	private ReimbursementManager reimbursementManager;
	private AdminManager adminManager;

	private int fid;
	private int lgd;

	private int cost;

	// 分页
	private int start;
	private int limit;

	private int total;

	public String add() throws Exception {

		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");

		// 修改设备状态 检修 将 2 改为 0 正常;

		// tempEquipment.setState(0);
		//
		// tempEquipment.setLasttime(new Date());//将上次维修时间 改为添加日志时间
		// equipmentManager.modify(tempEquipment);

		// logOverhaul.setAdmin(tempAdmin);
		// logOverhaul.setEquipment(tempEquipment);

		// logOverhaulManager.add(logOverhaul);

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

		return logOverhaulManager.deleletByid(id);

	}

	@JSON(serialize = false)
	public AdminManager getAdminManager() {
		return adminManager;
	}

	@JSON(serialize = false)
	public EquipmentManager getEquipmentManager() {
		return equipmentManager;
	}

	@JSON(serialize = false)
	public FettleManager getFettleManager() {
		return fettleManager;
	}

	@JSON(serialize = false)
	public int getLimit() {
		return limit;
	}

	@JSON(serialize = false)
	public LogOverhaul getLogOverhaul() {
		return logOverhaul;
	}

	public List<LogOverhaul> getLogOverhaulList() {
		return LogOverhaulList;
	}

	@JSON(serialize = false)
	public LogOverhaulManager getLogOverhaulManager() {
		return logOverhaulManager;
	}

	public String getLogOverhauls() throws Exception {
		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");

		System.out.println("limit" + limit);
		total = logOverhaulManager.getTotalNum();
		LogOverhaulList = logOverhaulManager.getLogOverhauls(tempAdmin, start,
				limit);

		for (int i = 0; i < LogOverhaulList.size(); i++) {
			LogOverhaulList.get(i)
					.setAdminName(
							LogOverhaulList.get(i).getManualWarn().getAdmin()
									.getName());
			LogOverhaulList.get(i).setEquipName(
					(LogOverhaulList.get(i).getManualWarn().getEquipment()
							.getEname()));
			LogOverhaulList.get(i).setManualnatation(
					LogOverhaulList.get(i).getManualWarn().getNatation());
		}

		this.success = true;

		return SUCCESS;
	}

	@JSON(serialize = false)
	public ManualWarnManager getManualWarntManager() {
		return manualWarntManager;
	}

	@JSON(serialize = false)
	public Object getModel() {

		return logOverhaul;
	}

	/**
	 * @return the reimbursementManager
	 */
	@JSON(serialize = false)
	public ReimbursementManager getReimbursementManager() {
		return reimbursementManager;
	}

	@JSON(serialize = false)
	public int getStart() {
		return start;
	}

	public int getTotal() {
		return total;
	}

	@JSON(serialize = false)
	public OutlayHistoryManager getOutlayHistoryManager() {
		return outlayHistoryManager;
	}

	@Resource
	public void setOutlayHistoryManager(
			OutlayHistoryManager outlayHistoryManager) {
		this.outlayHistoryManager = outlayHistoryManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public String modify() throws Exception {

		int newid = Integer.parseInt(ServletActionContext.getRequest()
				.getParameter("lgd"));

		LogOverhaul tempLogOverhaul = logOverhaulManager.loadById(newid);
		tempLogOverhaul.setLgd(newid);
		if (tempLogOverhaul.getState().equals("未处理")) {
			Fettle tempFettle = fettleManager.loadById(fid);
			tempLogOverhaul.setFettle(tempFettle);
			tempLogOverhaul.setState("已派遣");
			logOverhaulManager.modify(tempLogOverhaul);
		} else if (tempLogOverhaul.getState().equals("已派遣")) {

			String callProvider = (ServletActionContext.getRequest()
					.getParameter("callProvider"));
			String natation = (ServletActionContext.getRequest()
					.getParameter("natation"));

			String fixDate2 = (ServletActionContext.getRequest()
					.getParameter("fixDate"));

			String[] fixDate1 = fixDate2.split("-");

			Date fixDate = new Date(Integer.parseInt(fixDate1[0]),
					Integer.parseInt(fixDate1[1]),
					Integer.parseInt(fixDate1[2]));

			System.out.println(fixDate.toString());

			int cost = Integer.parseInt(ServletActionContext.getRequest()
					.getParameter("cost"));
			double tempNum = tempLogOverhaul.getManualWarn().getEquipment()
					.getTotalCost();

			tempLogOverhaul.getManualWarn().getEquipment()
					.setTotalCost(tempNum + cost);
			tempLogOverhaul.getManualWarn().getEquipment().setState(0);
			tempLogOverhaul.getManualWarn().getEquipment()
					.setLasttime(new Date());
			tempLogOverhaul.getManualWarn().setIsDeal("已解决");
			tempLogOverhaul.setCallProvider(callProvider);
			tempLogOverhaul.setNatation(natation);
			tempLogOverhaul.setCost(cost);

			tempLogOverhaul.setFixDate(fixDate);

			tempLogOverhaul.setState("处理完成");

			logOverhaulManager.modify(tempLogOverhaul);

			// 生成outlayhistory
			OutlayHistory oh = new OutlayHistory();
			oh.setOutlayDate(new Date());
			oh.setNotation(natation);

			oh.setCost(cost);
			oh.setEquipment(tempLogOverhaul.getManualWarn().getEquipment());
			outlayHistoryManager.add(oh);
			// 生成reimbursement;

			Reimbursement reim = new Reimbursement();
			reim.setCreateDate(new Date());
			reim.setOperator("未审核");
			reim.setCost(cost);
			reim.setLogOverhaulId(newid);
			reimbursementManager.add(reim);

		}

		success = true;
		return SUCCESS;
	}

	@Resource
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}

	@Resource
	public void setEquipmentManager(EquipmentManager equipmentManager) {
		this.equipmentManager = equipmentManager;
	}

	@Resource
	public void setFettleManager(FettleManager fettleManager) {
		this.fettleManager = fettleManager;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@JSON(serialize = false)
	public void setLogOverhaul(LogOverhaul logOverhaul) {
		this.logOverhaul = logOverhaul;
	}

	public void setLogOverhaulList(List<LogOverhaul> logOverhaulList) {
		LogOverhaulList = logOverhaulList;
	}

	@Resource
	public void setLogOverhaulManager(LogOverhaulManager logOverhaulManager) {
		this.logOverhaulManager = logOverhaulManager;
	}

	@Resource
	public void setManualWarntManager(ManualWarnManager manualWarntManager) {
		this.manualWarntManager = manualWarntManager;
	}

	/**
	 * @param reimbursementManager
	 *            the reimbursementManager to set
	 */
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

	@JSON(serialize = false)
	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	@JSON(serialize = false)
	public int getLgd() {
		return lgd;
	}

	public void setLgd(int lgd) {
		this.lgd = lgd;
	}

	@JSON(serialize = false)
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

}
