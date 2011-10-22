package ems.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import ems.dao.ReimbursementDao;
import ems.model.Reimbursement;

@Repository("ReimbursementDao")
public class ReimbursementDaoImpl implements ReimbursementDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Reimbursement reimbursement) {
		this.hibernateTemplate.save(hibernateTemplate.merge(reimbursement));

	}

	public int deleteByid(int id) {
		Reimbursement reimbursement = new Reimbursement();
		reimbursement.setRid(id);
		hibernateTemplate.delete(reimbursement);
		return 1;
	}

	public void modify(Reimbursement reimbursement) {
		System.out.println("要更新的 Rid 为");
		hibernateTemplate.update(reimbursement);

	}

	public Reimbursement loadByid(int rid) {
		return this.hibernateTemplate.load(Reimbursement.class, rid);
	}

	public List<Reimbursement> getReimbursements(final int start,
			final int limit) {
		System.out.println("getReimbursements()");
		List<Reimbursement> reimbursements = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("from Reimbursement");
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<Reimbursement> list = query.list();
						return list;
					}
				});
		return reimbursements;
	}

	public int getTotalNum() {
		String hql = "select count(*) from Reimbursement as r";
		return ((Long) getHibernateTemplate().iterate(hql).next()).intValue();
	}

	public List<Reimbursement> getReimbursementsByDate(final Date date1,
			final Date date2, final int start, final int limit) {
		List<Reimbursement> oh = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("from Reimbursement as r where "
										+ " r.createDate<=:date2 and r.createDate>=:date1");
						query.setDate("date2", date2);
						query.setDate("date1", date1);
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<Reimbursement> list = query.list();
						return list;
					}
				});
		return oh;
	}

	public int getTotalNumByDate(final Date date1, final Date date2)
			throws Exception {
		List<Long> nums = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("select count(*) from Reimbursement r where "
										+ " r.createDate<=:date2 and r.createDate>=:date1");
						query.setDate("date2", date2);
						query.setDate("date1", date1);

						List<Long> nums = query.list();
						return nums;
					}
				});
		return nums.get(0).intValue();
	}

}
