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

import ems.dao.LogSessionDao;

import ems.model.LogSession;

@Repository("LogSessionDao")
public class LogSessionDaoImpl implements LogSessionDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(LogSession logSession) {

		hibernateTemplate.save(logSession);
	}

	public int delete(int id) {

		LogSession logSession = new LogSession();
		logSession.setLsid(id);
		hibernateTemplate.delete(logSession);
		return 1;

	}

	public void modify(LogSession logSession) {
		// TODO Auto-generated method stub

	}

	public boolean checkLogSessionExistsWithName(String username) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getTotalNum() {

		String hql = "select count(*) from LogSession as ls";
		return ((Long) getHibernateTemplate().iterate(hql).next()).intValue();

	}


	public List<LogSession> getLogSessions(final int start, final int limit) {
		// List<LogSession> logSessions = hibernateTemplate
		// .find("from LogSession");

		List<LogSession> logSessions = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = (Query) session
								.createQuery("from LogSession");
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<LogSession> list = (List<LogSession>) query.list();
						return list;
					}
				});

		return logSessions;
	}

	public LogSession loadByLsid(int lsid) {
		// TODO Auto-generated method stub
		return null;
	}

}
