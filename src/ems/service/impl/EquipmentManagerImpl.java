package ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.EquipmentDao;
import ems.model.Equipment;
import ems.service.EquipmentManager;

@Service("EquipmentManager")
public class EquipmentManagerImpl implements EquipmentManager {

	private EquipmentDao equipmentDao;

	public EquipmentDao getEquipmentDao() {
		return equipmentDao;
	}

	@Resource
	public void setEquipmentDao(EquipmentDao equipmentDao) {
		this.equipmentDao = equipmentDao;
	}

	public void add(Equipment equipment) throws Exception {

		equipmentDao.save(equipment);
	}

	public int deleletByid(int eid) throws Exception {

		equipmentDao.deleteByid(eid);
		return 1;
	}

	public void modify(Equipment equipment) throws Exception {
		equipmentDao.modify(equipment);
	}

	public List<Equipment> getEquipments(int start, int limit) throws Exception {

		return equipmentDao.getEquipments(start, limit);

	}

	public List<Equipment> getEquipments() throws Exception {

		return equipmentDao.getEquipments();

	}

	public Equipment loadById(int eid) throws Exception {

		return equipmentDao.loadByEid(eid);
	}

	public Equipment loadByEname(String ename) throws Exception {
		// TODO Auto-generated method stub
		return equipmentDao.loadByEname(ename);
	}

	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stub
		return equipmentDao.getTotalNum();
	}

	public String getChartForE() {
		String tempString = "[";
		try {
			List<Equipment> eList = equipmentDao.getEquipmentsforChart();

			if (eList.size() >= 10) {

				for (int i = 0; i <= 9; i++) {

					String unit = "{" + "\"name\":" + "\""
							+ eList.get(i).getEname() + "\"," + "\"values\":"
							+ "\"" + eList.get(i).getTotalCost() + "\"" + "}";

					if (i <= 8) {
						tempString += unit + ",";
					} else if (i == 9) {
						tempString += unit + "]";
					}

				}

			}

			else {

				for (int i = 0; i < eList.size(); i++) {

					String unit = "{" + "\"name\":" + "\""
							+ eList.get(i).getEname() + "\"," + "\"values\":"
							+ "\"" + eList.get(i).getTotalCost() + "\"" + "}";

					if (i <= eList.size() - 2) {
						tempString += unit + ",";
					} else if (i == eList.size() - 1) {
						tempString += unit + "]";
					}

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tempString;

	}
}
