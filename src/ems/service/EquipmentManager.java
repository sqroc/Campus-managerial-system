package ems.service;

import java.util.List;

import ems.model.Equipment;

public interface EquipmentManager {
	public abstract void add(Equipment equipment) throws Exception;

	public abstract int deleletByid(int eid) throws Exception;

	public abstract void modify(Equipment equipment) throws Exception;

	public List<Equipment> getEquipments(int start, int limit) throws Exception;

	public List<Equipment> getEquipments() throws Exception;

	public Equipment loadById(int eid) throws Exception;

	public Equipment loadByEname(String ename) throws Exception;

	public int getTotalNum() throws Exception;

	public String getChartForE();
}
