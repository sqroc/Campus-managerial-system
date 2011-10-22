package ems.service;

import java.util.List;

import ems.model.Admin;
import ems.model.ManualWarn;

public interface ManualWarnManager {
	public abstract void add(ManualWarn manualWarn) throws Exception;

	public abstract int deleletByid(int eid) throws Exception;

	public abstract void modify(ManualWarn manualWarn) throws Exception;

	public List<ManualWarn> getManualWarns(Admin admin, int start, int limit)
			throws Exception;

	public int getTotalNum(Admin admin) throws Exception;

	public int getTotalNumByDate(Admin admin, String date1, String date2)
			throws Exception;

	public List<ManualWarn> getManualWarnsByDate(Admin admin, String date1,
			String date2, int start, int limit);

}
