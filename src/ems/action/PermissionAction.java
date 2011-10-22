package ems.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import ems.model.Admin;

@Namespace("")
@Controller("Permission")
public class PermissionAction extends ActionSupport {

	// private Admin admin = new Admin();
	public int getRank() throws Exception {

		Admin rankAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");
		if(rankAdmin!=null){
			return rankAdmin.getRank();
		}else{
			return 3;
		}
		
	}
	
	public String getMe() throws Exception {

		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");
		
		if(tempAdmin!=null)
		{
			return tempAdmin.getName();
		}
		else {
			return "没登入";
		}
	}
	
	// public Admin getAdmin() {
	// return admin;
	// }
	// public void setAdmin(Admin admin) {
	// this.admin = admin;
	// }

}
