package ems.service;

import java.util.List;

import ems.model.Admin;

public interface AdminManager {

	public abstract Admin check(Admin admin) throws Exception;

	public abstract void add(Admin admin) throws Exception;

	public abstract int deleletByid(int aid) throws Exception;

	public abstract void modify(Admin admin) throws Exception;

	public List<Admin> getAdmins(int start ,int limit) throws Exception;

	public Admin loadById(int aid) throws Exception;
	
	public int getTotalNum() throws Exception;
	
	public List<Admin> getOnlineAdmins(Admin admin) throws Exception;
 
}