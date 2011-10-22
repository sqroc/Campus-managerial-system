package ems.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import javassist.expr.NewArray;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import ems.model.Admin;
import ems.model.Message;

import ems.service.MessageManager;

//@ParentPackage("json-default")
@Namespace("")
@Controller("Message")
// @Results(
// { @Result(type = "json") })
public class MessageAction implements ModelDriven, ServletResponseAware {

	private List<Message> MessageList;
	private Message message = new Message();
	private MessageManager messageManager;
	JSONObject jsonObj = new JSONObject();
	private HttpServletResponse response;


	public String execute(){

		return null;
	}

	public String add() throws Exception {

		
		message.setState(0);
		message.setSendDate(new Date().toString());
		messageManager.add(message);

		return null;

	}

	public String getMessages() throws Exception {
		Admin rankAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");

		MessageList = messageManager.getMessages(rankAdmin);

		
		JSONArray jsonArr = new JSONArray();

		if (MessageList.size() > 0) {
			for (int i = 0; i < MessageList.size(); i++) {
				JSONObject tempJsonObj = new JSONObject();
				tempJsonObj.put("fromUserName", MessageList.get(i).getFromName());
				tempJsonObj.put("toUserName", MessageList.get(i)
						.getReceiveName());
				tempJsonObj.put("sendDate", MessageList.get(i).getSendDate());
				tempJsonObj.put("message", MessageList.get(i).getMessage());
				jsonArr.add(tempJsonObj);
			}

			jsonObj.put("jsonArr", jsonArr);
		}

		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObj.toString());
		
		return null;
		
		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	// @JSON(serialize = false)
	public Object getModel() {

		return message;
	}

	public List<Message> getMessageList() {
		return MessageList;
	}

	public void setMessageList(List<Message> messageList) {
		MessageList = messageList;
	}

	// @JSON(serialize = false)
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	// sf@JSON(serialize = false)
	public MessageManager getMessageManager() {
		return messageManager;
	}

	@Resource
	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public JSONObject getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}

}
