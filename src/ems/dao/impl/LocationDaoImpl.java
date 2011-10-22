package ems.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import ems.dao.LocationDao;
import ems.model.Location;

@Repository("LocationDao")
public class LocationDaoImpl implements LocationDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Location location) {
		hibernateTemplate.save(location);
	}

	public int deleteByid(int id) {

		Location location = new Location();
		location.setLocNumber(id);
		hibernateTemplate.delete(location);
		return 1;
	}

	public void modify(Location location) {

		System.out.println("要更新的 id 为");

		hibernateTemplate.update(location);
	}

	public int getTotalNum() {

		String hql = "select count(*) from Location as l";
		return ((Long) getHibernateTemplate().iterate(hql).next()).intValue();

	}

	public List<Location> getLocations(final int start, final int limit) {

		System.out.println("getLocations()");
		List<Location> locations = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query query = session
								.createQuery("from Location");
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<Location> list = query.list();
						return list;
					}
				});
		return locations;
	}

	public Location loadByid(int locNumber) {
		return this.hibernateTemplate.load(Location.class, locNumber);
	}

	public Location loadByName(String locName) {
		String queryString = "from  Location l where l.locName='" + locName
				+ "'";

		List<Location> locations = hibernateTemplate.find(queryString);

		if (locations.size() == 1) {
			System.out.println("List<Location> Locations have 1");

		}
		if (locations.size() == 0) {
			System.out.println("List<Location> Locations have 0");
		}

		return locations.get(0);
	}

}
