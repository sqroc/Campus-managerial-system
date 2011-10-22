package ems.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import ems.dao.AdminDao;
import ems.model.Admin;

@Repository("AdminDao")
public class AdminDaoImpl implements AdminDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Admin admin) {
		hibernateTemplate.save(admin);
	}

	public int deleletByid(int id) {

		Admin Atemp = new Admin();
		Atemp.setAid(id);

		hibernateTemplate.delete(Atemp);
		return 1;
	}

	public void modify(Admin admin) {

		// Admin admin = new Admin();
		// admin.setAid(id);
		System.out.println("要更新的 aid 为");

		hibernateTemplate.update(admin);

	}

	public Admin check(Admin admin) {

		System.out.println("check" + admin.getName());

		String queryString = "from Admin ad where ad.name='" + admin.getName()
				+ "' and ad.password='" + admin.getPassword() + "'";

		List<Admin> admins = hibernateTemplate.find(queryString);

		if (admins != null && admins.size() > 0)
			return admins.get(0);

		else
			return null;
	}

	public int getTotalNum() {

		String hql = "select count(*) from Admin as admin";
		return ((Long) getHibernateTemplate().iterate(hql).next()).intValue();

	}

	public List<Admin> getAdmins(final int start, final int limit) {

		System.out.println("getAdmins()");
		List<Admin> admins = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = (org.hibernate.Query) session
								.createQuery("from Admin");
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<Admin> list = (List<Admin>) query.list();
						return list;
					}
				});
		// List<Admin> admins = hibernateTemplate.find("from Admin");

		return admins;
	}

	public Admin loadByAid(int aid) {

		return (Admin) this.hibernateTemplate.load(Admin.class, aid);

	}

	public List<Admin> getOnlineAdmins(Admin admin) {
		System.out.println("getOnlineAdmins()");
		String sql = "from Admin as ad where ad.state = 1 and ad.name <> '"
				+ admin.getName() + "'";
		List<Admin> admins = hibernateTemplate.find(sql);

		return admins;

	}

}
