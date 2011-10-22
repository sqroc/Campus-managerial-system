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

import ems.dao.LogOverhaulDao;
import ems.model.LogOverhaul;

@Repository("LogOverhaulDao")
public class LogOverhaulDaoImpl implements LogOverhaulDao {
	private HibernateTemplate hibernateTemplate;

	public void delete(int id) {

		LogOverhaul templogOverhaul = new LogOverhaul();
		templogOverhaul.setLgd(id);

		this.hibernateTemplate.delete(templogOverhaul);

	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public List<LogOverhaul> getLogOverhauls(final int start, final int limit) {
		System.out.println("getLogOverhauls()");

		String sql = "from LogOverhaul";

		final String HSQL = sql;
		List<LogOverhaul> logOverhauls = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(HSQL);
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<LogOverhaul> list = query.list();
						return list;
					}
				});

		return logOverhauls;
	}

	public int getTotalNum() {

		String sql = " select count(*) from LogOverhaul";

		// String hql = "select count(*) from Equipment as eq";
		return ((Long) getHibernateTemplate().iterate(sql).next()).intValue();

	}

	public void modify(LogOverhaul logOverhaul) {

		this.hibernateTemplate.update(hibernateTemplate.merge(logOverhaul));

	}

	public void save(LogOverhaul logOverhaul) {
		this.hibernateTemplate.save(hibernateTemplate.merge(logOverhaul));

	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public LogOverhaul loadById(int loid) {
		return this.hibernateTemplate.load(LogOverhaul.class, loid);
	}
}
