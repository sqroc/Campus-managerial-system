package ems.action;

import java.util.Date;
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

import ems.model.Admin;
import ems.service.AdminManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Admin")
@Results(

{ @Result(type = "json") })
public class AdminAction extends ActionSupport implements ModelDriven {

	private boolean success = false;
	private String message;
	private String nowDate;
	private List<Admin> AdminList;

	private Admin admin = new Admin();
	private AdminManager adminManager;

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

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		Date date = new Date();
		admin.setAddDate(date);
	}

	public String add() throws Exception {

		// admin.setIsdel(1);
		adminManager.add(admin);

		this.success = true;

		return SUCCESS;

	}

	public String modify() throws Exception {
		adminManager.modify(admin);
		this.success = true;

		return SUCCESS;

	}

	public int deleteByid(int id) {
		try {
			return adminManager.deleletByid(id);
		} catch (Exception e) {
	
			return -1;
		}
	}

	public int deleteBatch(int[] ids) {
		int deleteNum = 0;
		for (int i = 0; i < ids.length; i++) {
			if (deleteByid(ids[i]) == 1)
				deleteNum++;
		}

		return deleteNum;
	}

	public Admin loadByAid(int aid) throws Exception {
		return adminManager.loadById(aid);
	}

	public String getAdmins() throws Exception {

		total = adminManager.getTotalNum();
		System.out.println("total" + total);
		AdminList = adminManager.getAdmins(start, limit);
		this.success = true;

		return SUCCESS;
	}

	@JSON(serialize = false)
	public Object getModel() {

		return admin;
	}

	/**
	 * @return the admin
	 */
	@JSON(serialize = false)
	public Admin getAdmin() {
		return admin;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	/**
	 * @return the adminManager
	 */

	@JSON(serialize = false)
	public AdminManager getAdminManager() {
		return adminManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@JSON(serialize = false)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param adminManager
	 *            the adminManager to set
	 */

	@Resource
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}

	public List<Admin> getAdminList() {
		return AdminList;
	}

	public void setAdminList(List<Admin> adminList) {
		AdminList = adminList;
	}

	//

}
