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

import ems.model.Admin;
import ems.model.LogSession;
import ems.service.AdminManager;
import ems.service.LogSessionManager;

@ParentPackage("json-default")
@Namespace("")
@Controller
@Results(

{ @Result(type = "json") })
public class LogoutAction extends ActionSupport {

	private boolean success;
	private String message;
	private LogSessionManager logSessionManager;
	private AdminManager adminManager;

	@Override
	public String execute() throws Exception {

		System.out.println("进入 action");

		this.success = true;
		this.message = "你已成功推退出";
		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");

		LogSession logSession = new LogSession();
		logSession.setAid(tempAdmin.getAid());
		logSession.setName(tempAdmin.getName());
		logSession.setType(1);
		logSession.setTime(new Date());

		logSessionManager.add(logSession);

		tempAdmin.setState(0);

		adminManager.modify(tempAdmin);
		ServletActionContext.getRequest().getSession()
				.removeAttribute("username");

		return SUCCESS;
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

	@JSON(serialize = false)
	public LogSessionManager getLogSessionManager() {
		return logSessionManager;
	}

	@Resource
	public void setLogSessionManager(LogSessionManager logSessionManager) {
		this.logSessionManager = logSessionManager;
	}

	@JSON(serialize = false)
	public AdminManager getAdminManager() {
		return adminManager;
	}

	@Resource
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}

}
