package ems.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import ems.dao.FettleDao;
import ems.model.Fettle;

@Repository("FettleDao")
public class FettleDaoImpl implements FettleDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Fettle fettle) {
		hibernateTemplate.save(fettle);
	}

	public int deleteByid(int id) {

		Fettle fettle = new Fettle();
		fettle.setFid(id);
		hibernateTemplate.delete(fettle);
		return 1;
	}

	public void modify(Fettle fettle) {

		System.out.println("要更新的 fid 为");

		hibernateTemplate.update(fettle);
	}

	public int getTotalNum() {

		String hql = "select count(*) from Fettle as fettle";
		return ((Long) getHibernateTemplate().iterate(hql).next()).intValue();

	}

	public List<Fettle> getFettles(final int start, final int limit) {

		System.out.println("getFettles()");
		List<Fettle> fettles = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("from Fettle");
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<Fettle> list = query.list();
						return list;
					}
				});
		// List<Admin> admins = hibernateTemplate.find("from Admin");

		return fettles;
	}

	public Fettle loadByFid(int fid) {

		return this.hibernateTemplate.load(Fettle.class, fid);

	}

}
