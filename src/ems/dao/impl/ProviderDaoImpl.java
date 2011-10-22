package ems.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import ems.dao.ProviderDao;
import ems.model.Equipment;
import ems.model.Provider;

@Repository("ProviderDao")
public class ProviderDaoImpl implements ProviderDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Provider provider) {
		hibernateTemplate.save(provider);

	}

	public void delete(int id) {

		Provider tempProvider = new Provider();
		tempProvider.setPid(id);
		hibernateTemplate.delete(tempProvider);

	}

	public void modify(Provider provider) {
		hibernateTemplate.update(provider);

	}

	// public boolean checkProviderExistsWithName(String username) {
	// // TODO Auto-generated method stub
	// return false;
	// }

	public boolean isProvided(Provider provider) {

		String queryString = "from Equipment eq where eq.provider.pid="
				+ provider.getPid();

		List<Equipment> Equipments = hibernateTemplate.find(queryString);

		if (Equipments != null && Equipments.size() > 0)
			return true;

		return false;
	}

	public int getTotalNum() {

		String hql = "select count(*) from Provider as p";
		return ((Long) getHibernateTemplate().iterate(hql).next()).intValue();

	}

	public List<Provider> getProviders(final int start, final int limit) {
		// List<Provider> Providers = hibernateTemplate.find("from Provider");

		List<Provider> Providers = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session
								.createQuery("from Provider");
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<Provider> list = query.list();
						return list;
					}
				});

		if (Providers != null && Providers.size() > 0)

			for (int i = 0; i < Providers.size(); i++) {
				if (isProvided(Providers.get(i)))
					Providers.get(i).setIsProvided("已购买设备");
				else
					Providers.get(i).setIsProvided("未购买设备");

			}

		return Providers;
	}

	public List<Provider> getProviders() {
		List<Provider> Providers = hibernateTemplate.find("from Provider");

		return Providers;
	}

	public Provider loadByPid(int pid) {

		return hibernateTemplate.load(Provider.class, pid);
	}

	public Provider loadByPname(String pname) {

		String queryString = "from  Provider pro where pro.pname='" + pname
				+ "'";

		List<Provider> Providers = hibernateTemplate.find(queryString);

		if (Providers.size() == 1) {
			System.out.println("List<Provider> Providers 为一");
		}
		if (Providers.size() == 0) {
			System.out.println("List<Provider> Providers 为 o");
		}

		return Providers.get(0);

	}


}
