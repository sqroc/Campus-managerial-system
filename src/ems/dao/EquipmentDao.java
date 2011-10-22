package ems.dao;

import java.util.List;

import ems.model.Equipment;

public interface EquipmentDao {

	public void save(Equipment equipment);

	public int deleteByid(int id);

	public void modify(Equipment equipment);

	public List<Equipment> getEquipments(int start, int limit);

	public List<Equipment> getEquipments();

	public Equipment loadByEid(int eid);

	public Equipment loadByEname(String ename);

	public int getTotalNum() throws Exception;

	public List<Equipment> getEquipmentsforChart() throws Exception;
}
