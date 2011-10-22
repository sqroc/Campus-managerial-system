package ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.AdminDao;
import ems.dao.ProviderDao;
import ems.model.Provider;
import ems.service.ProviderManager;

@Service("ProviderManager")
public class ProviderManagerImpl implements ProviderManager {

	private ProviderDao providerDao;

	public ProviderDao getProviderdao() {
		return providerDao;
	}

	@Resource
	public void setProviderdao(ProviderDao providerdao) {
		this.providerDao = providerdao;
	}

	public List<Provider> getProviders(int start, int limit) throws Exception {

		return providerDao.getProviders(start, limit);
	}

	public List<Provider> getProviders() throws Exception {

		return providerDao.getProviders();
	}

	public Provider loadByPname(String pname) throws Exception {
		return providerDao.loadByPname(pname);

	}

	public void add(Provider provider) throws Exception {
		providerDao.save(provider);

	}

	public int deleletByid(int id) throws Exception {
		providerDao.delete(id);
		return 1;
	}

	public void modify(Provider provider) throws Exception {
		providerDao.modify(provider);

	}

	public Provider loadById(int id) throws Exception {

		return providerDao.loadByPid(id);
	}

	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stub
		return providerDao.getTotalNum();
	}

}
