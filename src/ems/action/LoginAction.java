package ems.action;

import java.util.Date;

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
import ems.model.LogSession;
import ems.service.AdminManager;
import ems.service.LogSessionManager;

@ParentPackage("json-default")
@Namespace("")
@Controller
@Results(

{ @Result(type = "json") })
public class LoginAction extends ActionSupport implements ModelDriven {

	private boolean success;
	private String message;

	private Admin admin = new Admin();
	private AdminManager adminManager;
	private LogSessionManager logSessionManager;

	@Override
	public String execute() throws Exception {

		System.out.println("进入 action");
		System.out.print(admin.getName());
		System.out.print(admin.getPassword());
		Admin tempAdmin = check();
		
		
		if (tempAdmin !=null) {
			this.success = true;

		//	tempAdmin.setState(1);
			ServletActionContext.getRequest().getSession()
					.setAttribute("username",tempAdmin);
			
			LogSession logSession = new LogSession();
			logSession.setAid(tempAdmin.getAid());
			logSession.setName(tempAdmin.getName());
			logSession.setType(0);
			logSession.setTime(new Date());
			logSessionManager.add(logSession);
			
		//	adminManager.modify(tempAdmin);
			
			}

		else
			this.success = false;

		return SUCCESS;
	}

	public Admin check() throws Exception {
		return adminManager.check(admin);
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
	@JSON(serialize = false)
	public LogSessionManager getLogSessionManager() {
		return logSessionManager;
	}
	@Resource
	public void setLogSessionManager(LogSessionManager logSessionManager) {
		this.logSessionManager = logSessionManager;
	}
	
	
	

}
