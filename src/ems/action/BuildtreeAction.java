package ems.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;

import org.apache.struts2.interceptor.ServletResponseAware;

import org.springframework.stereotype.Controller;

import ems.model.Admin;
import ems.model.Tree;
import ems.service.AdminManager;

@Namespace("")
@Controller("Buildtree")
public class BuildtreeAction implements ServletResponseAware {

	private AdminManager adminManager;
	private HttpServletResponse response;
	private JSONArray jsonObject;

	List<Tree> tree = new ArrayList<Tree>();

	public String execute() throws Exception {

		Admin tempAdmin = (Admin) ServletActionContext.getRequest()
				.getSession().getAttribute("username");
		List<Admin> onlineAdmins = adminManager.getOnlineAdmins(tempAdmin);

		Tree rootTree = new Tree();
		rootTree.setText("在线用户");
		rootTree.setLeaf(false);

		tree.add(rootTree);

		List<Tree> treeList = new ArrayList<Tree>();
		rootTree.setChildren(treeList);

		Tree tempTree1 = new Tree();
		tempTree1.setText(tempAdmin.getName());
		tempTree1.setLeaf(true);

		treeList.add(tempTree1);


		if (onlineAdmins.size() > 0) {
			for (int i = 0; i < onlineAdmins.size(); i++) {
				Tree tempTree = new Tree();
				tempTree.setText(onlineAdmins.get(i).getName());
				tempTree.setLeaf(true);

				treeList.add(tempTree);
			}
		}
		JSONArray jsonObject = JSONArray.fromObject(tree);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObject.toString());
		System.out.println(jsonObject.toString());

		return null;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public AdminManager getAdminManager() {
		return adminManager;
	}

	@Resource
	public void setAdminManager(AdminManager adminManager) {
		this.adminManager = adminManager;
	}

	public List<Tree> getTree() {
		return tree;
	}

	public void setTree(List<Tree> tree) {
		this.tree = tree;
	}

}
