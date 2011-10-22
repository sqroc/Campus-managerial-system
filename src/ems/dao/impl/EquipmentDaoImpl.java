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

import ems.dao.EquipmentDao;
import ems.model.Equipment;

@Repository("EquipmentDao")
public class EquipmentDaoImpl implements EquipmentDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Equipment equipment) {
		hibernateTemplate.save(equipment);
	}

	public int deleteByid(int id) {

		Equipment Etemp = new Equipment();
		Etemp.setEid(id);
		hibernateTemplate.delete(Etemp);
		return 1;

	}

	public Equipment loadByEname(String ename) {
		String queryString = "from  Equipment eq where eq.ename='" + ename
				+ "'";

		List<Equipment> Equipments = hibernateTemplate.find(queryString);

		if (Equipments.size() == 1) {
			System.out.println("List<Provider> Providers 为一");

		}
		if (Equipments.size() == 0) {
			System.out.println("List<Provider> Providers 为 o");
		}

		return Equipments.get(0);

	}

	public void modify(Equipment equipment) {

		hibernateTemplate.update(hibernateTemplate.merge(equipment));

	}

	public int getTotalNum() {

		String hql = "select count(*) from Equipment as eq";
		return ((Long) getHibernateTemplate().iterate(hql).next()).intValue();

	}

	public List<Equipment> getEquipments(final int start, final int limit) {
		System.out.println("getEquipments()");

		// List<Equipment> equipments =
		// hibernateTemplate.find("from Equipment");

		List<Equipment> equipments = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery("from Equipment");
						query.setFirstResult(start);
						query.setMaxResults(limit);

						List<Equipment> list = query.list();
						return list;
					}
				});

		if (equipments != null && equipments.size() > 0) {
			int tempRank = 0;

			for (int i = 0; i < equipments.size(); i++) {

				tempRank = equipments.get(i).getState();

				if (tempRank == 1) {
					equipments.get(i).setStateRank("待检修");
				} else if (tempRank == 2) {
					equipments.get(i).setStateRank("故障");
				} else {
					equipments.get(i).setStateRank("正常");
				}

			}
		}

		return equipments;

	}

	public List<Equipment> getEquipments() {
		List<Equipment> equipments = hibernateTemplate.find("from Equipment");
		if (equipments != null && equipments.size() > 0) {
			int tempRank = 0;

			for (int i = 0; i < equipments.size(); i++) {

				tempRank = equipments.get(i).getState();

				if (tempRank == 1) {
					equipments.get(i).setStateRank("待检修");
				} else if (tempRank == 2) {
					equipments.get(i).setStateRank("故障");
				} else {
					equipments.get(i).setStateRank("正常");
				}

			}
		}

		return equipments;
	}

	public Equipment loadByEid(int eid) {

		return hibernateTemplate.load(Equipment.class, eid);
	}

	public List<Equipment> getEquipmentsforChart() throws Exception {
		List<Equipment> equipments = hibernateTemplate
				.executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session
								.createQuery("from Equipment e order by e.totalCost desc");

						List<Equipment> list = query.list();
						return list;
					}
				});
		return equipments;
	}

}
