package ems.test;

import java.util.Date;

import org.junit.Test;

import ems.dao.AdminDao;
import ems.dao.impl.AdminDaoImpl;
import ems.model.Admin;


public class TestAdminDaoImpl {
	@Test
	public void testadd(){
		AdminDao admindao = new AdminDaoImpl();
		Admin admin = new Admin();
		admin.setAddDate(new Date());
		admin.setName("testeee");
		admin.setPassword("dsfsfdsf");
		admin.setRank(1);
		admindao.save(admin);
	}
	

}
