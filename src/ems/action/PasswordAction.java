package ems.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import ems.model.Admin;
import ems.service.AdminManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Password")
@Results(

{ @Result(type = "json") })
public class PasswordAction extends ActionSupport {

	private String oldPassword;
	private String newPassword;
	private AdminManager adminManager;
	private boolean success;

	@Override
	public String execute() throws Exception {

		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");

		System.out.println(tempAdmin.getPassword() + "tempp");
		System.out.println(oldPassword + "old");
		if (!tempAdmin.getPassword().equals(oldPassword)) {
			System.out.println(tempAdmin.getPassword() + "sdf");
			this.success = false;
		}

		else {
			tempAdmin.setPassword(newPassword);
			adminManager.modify(tempAdmin);
			this.success = true;
		}
		System.out.println(this.success);
		return SUCCESS;

	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@JSON(serialize = false)
	public AdminManager getAdminManager() {
		return adminManager;
	}

	@Resource
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
