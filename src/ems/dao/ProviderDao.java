package ems.dao;

import java.util.List;

import ems.model.Provider;

public interface ProviderDao {
	public void save(Provider provider);

	public void delete(int id);

	public void modify(Provider provider);

	public boolean isProvided(Provider provider);

	public List<Provider> getProviders(int start, int limit);

	public List<Provider> getProviders();

	public Provider loadByPid(int pid);

	public Provider loadByPname(String pname);

	public int getTotalNum() throws Exception;

}
