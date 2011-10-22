package ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.AdminDao;
import ems.model.Admin;
import ems.service.AdminManager;

@Service("AdminManager")
public class AdminManagerImpl implements AdminManager {
	private AdminDao admindao;

	public AdminDao getAdmindao() {
		return admindao;
	}

	@Resource
	public void setAdmindao(AdminDao admindao) {
		this.admindao = admindao;
	}

	public void add(Admin admin) throws Exception {
		admindao.save(admin);

	}

	public int deleletByid(int id) throws Exception {
		return admindao.deleletByid(id);
	}

	public void modify(Admin admin) throws Exception {
		admindao.modify(admin);
	}

	public Admin check(Admin admin) throws Exception {
		return admindao.check(admin);
	}

	public List<Admin> getAdmins(int start, int limit) throws Exception {
		return admindao.getAdmins(start, limit);
	}

	public Admin loadById(int aid) throws Exception {

		return admindao.loadByAid(aid);
	}

	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stub
		return admindao.getTotalNum();
	}

	public List<Admin> getOnlineAdmins(Admin admin) throws Exception {
		return admindao.getOnlineAdmins(admin);
	}

}
