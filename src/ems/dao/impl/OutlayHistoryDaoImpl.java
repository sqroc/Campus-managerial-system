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

import ems.dao.OutlayHistoryDao;
import ems.model.OutlayHistory;

@Repository("OutlayHistoryDao")
public class OutlayHistoryDaoImpl implements OutlayHistoryDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(OutlayHistory outlayHistory) {
		System.out.print("outlayHistory save");
		this.hibernateTemplate.save(hibernateTemplate.merge(outlayHistory));
	}

	public int deleteByid(int id) {
		OutlayHistory outlayHistory = new OutlayHistory();
		outlayHistory.setOhid(id);

		hibernateTemplate.delete(outlayHistory);
		return 1;
	}

	public void modify(OutlayHistory outlayHistory) {
		System.out.println("要更新的 ohid 为");
		hibernateTemplate.update(outlayHistory);

	}

	public OutlayHistory loadByid(int ohid) {
		return this.hibernateTemplate.load(OutlayHistory.class, ohid);
	}

	public List<OutlayHistory> getOutlayHistorys(final int start,
			final int limit) {
		System.out.println("getOutlayHistorys()");
		List<OutlayHistory> outlayHistorys = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("from OutlayHistory");
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<OutlayHistory> list = query.list();
						return list;
					}
				});
		return outlayHistorys;
	}

	public int getTotalNum() {
		String hql = "select count(*) from OutlayHistory as oh";
		return ((Long) getHibernateTemplate().iterate(hql).next()).intValue();
	}

	public List<OutlayHistory> getOutlayHistorysByDate(final Date date1,
			final Date date2, final int start, final int limit) {
		List<OutlayHistory> oh = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("from OutlayHistory as oh where "
										+ " oh.outlayDate<=:date2 and oh.outlayDate>=:date1");
						query.setDate("date2", date2);
						query.setDate("date1", date1);
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<OutlayHistory> list = query.list();
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
								.createQuery("select count(*) from OutlayHistory oh where "
										+ " oh.outlayDate<=:date2 and oh.outlayDate>=:date1");
						query.setDate("date2", date2);
						query.setDate("date1", date1);

						List<Long> nums = query.list();
						return nums;
					}
				});

		return nums.get(0).intValue();
	}

}
