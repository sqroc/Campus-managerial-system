package ems.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import ems.dao.ManualWarnDao;
import ems.model.Admin;
import ems.model.ManualWarn;

@Repository("ManualWarnDao")
public class ManualWarnDaoImpl implements ManualWarnDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(ManualWarn manualWarn) {

		this.hibernateTemplate.save(hibernateTemplate.merge(manualWarn));

	}

	public void delete(int id) {

		ManualWarn tempManualWarn = new ManualWarn();
		tempManualWarn.setMwid(id);

		this.hibernateTemplate.delete(tempManualWarn);

	}

	public void modify(ManualWarn manualWarn) {
		this.hibernateTemplate.update(hibernateTemplate.merge(manualWarn));

	}

	public int getTotalNum(Admin admin) {

		String sql = "";
		if (admin.getRank() == 2)
			sql = " select count(*) from ManualWarn mw where mw.admin.aid ='"
					+ admin.getAid() + "'";
		else
			sql = " select count(*) from ManualWarn";

		// String hql = "select count(*) from Equipment as eq";
		return ((Long) getHibernateTemplate().iterate(sql).next()).intValue();

	}

	public List<ManualWarn> getManualWarns(Admin admin, final int start,
			final int limit) {

		String sql = "";
		if (admin.getRank() == 2)
			sql = "from ManualWarn mw where mw.admin.aid ='" + admin.getAid()
					+ "'";
		else
			sql = "from ManualWarn";

		final String HSQL = sql;
		List<ManualWarn> manualWarns = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(HSQL);
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<ManualWarn> list = query.list();
						return list;
					}
				});

		// List<ManualWarn> manualWarns = hibernateTemplate.find(sql);

		return manualWarns;

	}

	public int getTotalNumByDate(final Admin admin, final Date date1,
			final Date date2) throws Exception {

		List<Long> nums = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("select count(*) from ManualWarn mv where mw.admin.aid ='"
										+ admin.getAid()
										+ "' and mv.warnTime<=:date2 and mv.warnTime>=:date1");
						query.setDate("date2", date2);
						query.setDate("date1", date1);

						List<Long> nums = query.list();
						return nums;
					}
				});

		return nums.get(0).intValue();

	}

	public List<ManualWarn> getManualWarnsByDate(final Admin admin,
			final Date date1, final Date date2, final int start, final int limit) {

		List<ManualWarn> manualWarns = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("from ManualWarn as mv where mw.admin.aid ='"
										+ admin.getAid()
										+ "' and mv.warnTime<=:date2 and mv.warnTime>=:date1");
						query.setDate("date2", date2);
						query.setDate("date1", date1);
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<ManualWarn> list = query.list();
						return list;
					}
				});

		return manualWarns;

	}

}
