package ems.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import ems.model.Admin;
import ems.model.LogSession;
import ems.service.AdminManager;
import ems.service.LogSessionManager;

@ParentPackage("json-default")
@Namespace("")
@Controller("Logsession")
@Results(

{ @Result(type = "json") })
public class LogsessionAction extends ActionSupport implements ModelDriven {

	private boolean success;
	private String message;

	private List<LogSession> LogSessionList;

	private LogSession logSession = new LogSession();
	private LogSessionManager logSessionManager;
	
	
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

	public int deleteByid(int id) throws Exception {
		return logSessionManager.deleletByid(id);
	}

	public int deleteBatch(int[] ids) throws Exception {
		int deleteNum = 0;
		for (int i = 0; i < ids.length; i++) {
			if (deleteByid(ids[i]) == 1)
				deleteNum++;
		}

		return deleteNum;
	}

	public String getLogSessions() throws Exception {
		total = logSessionManager.getTotalNum();
		LogSessionList = logSessionManager.getLogSessions(start,limit);
		this.success = true;

		return SUCCESS;
	}

	@JSON(serialize = false)
	public Object getModel() {

		return logSession;
	}

	/**
	 * @return the admin
	 */
	@JSON(serialize = false)
	public LogSession getLogSession() {
		return logSession;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */

	public void setLogSession(LogSession LogSession) {
		this.logSession = LogSession;
	}

	/**
	 * @return the adminManager
	 */

	@JSON(serialize = false)
	public LogSessionManager getLogSessionManager() {
		return logSessionManager;
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
	public void setLogSessionManager(LogSessionManager LogSessionManager) {
		this.logSessionManager = LogSessionManager;
	}

	public List<LogSession> getLogSessionList() {
		return LogSessionList;
	}

	public void setLogSessionList(List<LogSession> logSessionList) {
		LogSessionList = logSessionList;
	}

}
