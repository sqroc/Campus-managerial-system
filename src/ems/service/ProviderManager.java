package ems.service;

import java.util.List;

import ems.model.Provider;



public interface ProviderManager {
	

	public abstract void add(Provider provider) throws Exception;

	public abstract int deleletByid(int aid) throws Exception;

	public abstract void modify(Provider provider) throws Exception;

	public List<Provider> getProviders(int start ,int limit) throws Exception;
	
	public List<Provider> getProviders() throws Exception;

	public Provider loadById(int aid) throws Exception;
	
	public Provider loadByPname(String pname) throws Exception;
	
	public int getTotalNum() throws Exception;
}
