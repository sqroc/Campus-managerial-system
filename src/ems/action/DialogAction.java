package ems.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import ems.model.Admin;
import ems.service.AdminManager;

@Namespace("")
@Controller("Dialog")
public class DialogAction extends ActionSupport{

	private AdminManager adminManager;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
	}

	public String dialogOnline() throws Exception {
		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");
		tempAdmin.setState(1);
		adminManager.modify(tempAdmin);

		return null;

	}

	public String dialogOutline() throws Exception {
		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");
		tempAdmin.setState(0);
		adminManager.modify(tempAdmin);

		return null;

	}

	public AdminManager getAdminManager() {
		return adminManager;
	}

	@Resource
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}

}
