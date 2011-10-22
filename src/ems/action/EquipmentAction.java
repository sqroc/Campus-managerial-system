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

import ems.model.Equipment;
import ems.model.Provider;
import ems.service.EquipmentManager;
import ems.service.ProviderManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Equipment")
@Results(

{ @Result(type = "json") })
public class EquipmentAction extends ActionSupport implements ModelDriven {

	private boolean success;
	private String message;
	private String pname;
	private String locationName;// 地址名
	private List<Equipment> EquipmentList;
	private Equipment equipment = new Equipment();
	private EquipmentManager equipmentManager;
	private ProviderManager providerManager;
	// private LocationManager locationManager;
	// 分页
	private int start;
	private int limit;

	private int total;

	public String add() throws Exception {

		Provider newProvider = providerManager.loadByPname(pname);
		// Location newLocation = locationManager.loadByName(locationName);
		equipment.setLasttime(equipment.getBuytime());
		equipment.setProvider(newProvider);
		// equipment.setLocation(newLocation);
		equipment.setTotalCost(0);
		equipmentManager.add(equipment);

		this.success = true;

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

	public int deleteByid(int eid) throws Exception {
		return equipmentManager.deleletByid(eid);
	}

	/**
	 * @return the admin
	 */
	@JSON(serialize = false)
	public Equipment getEquipment() {
		return equipment;
	}

	public List<Equipment> getEquipmentList() {
		return EquipmentList;
	}

	/**
	 * @return the adminManager
	 */

	@JSON(serialize = false)
	public EquipmentManager getEquipmentManager() {
		return equipmentManager;
	}

	public String getEquipmentname() throws Exception {
		EquipmentList = equipmentManager.getEquipments();
		this.success = true;

		return SUCCESS;
	}

	public String getEquipments() throws Exception {
		total = equipmentManager.getTotalNum();
		EquipmentList = equipmentManager.getEquipments(start, limit);
		this.success = true;

		return SUCCESS;
	}

	@JSON(serialize = false)
	public int getLimit() {
		return limit;
	}

	@JSON(serialize = false)
	public String getMessage() {
		return message;
	}

	@JSON(serialize = false)
	public Object getModel() {

		return equipment;
	}

	public String getPname() {
		return pname;
	}

	@JSON(serialize = false)
	public ProviderManager getProviderManager() {
		return providerManager;
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

		Provider newProvider = providerManager.loadByPname(pname);
		// Location newLocation = locationManager.loadByName(locationName);
		equipment.setProvider(newProvider);
		// equipment.setLocation(newLocation);
		equipmentManager.modify(equipment);

		this.success = true;

		return SUCCESS;

	}

	/**
	 * @param admin
	 *            the admin to set
	 */

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		EquipmentList = equipmentList;
	}

	/**
	 * @param adminManager
	 *            the adminManager to set
	 */

	@Resource
	public void setEquipmentManager(EquipmentManager equipmentManager) {
		this.equipmentManager = equipmentManager;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	@Resource
	public void setProviderManager(ProviderManager providerManager) {
		this.providerManager = providerManager;
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
