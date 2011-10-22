package ems.dao;

import java.util.Date;
import java.util.List;

import ems.model.Admin;
import ems.model.ManualWarn;

public interface ManualWarnDao {
	public void save(ManualWarn manualWarn);

	public void delete(int id);

	public void modify(ManualWarn manualWarn);

	// public boolean checkManualWarnExistsWithName(String username);

	public List<ManualWarn> getManualWarns(Admin admin, int start, int limit);

	// public ManualWarn loadByMwid(int mwid);
	public int getTotalNum(Admin admin) throws Exception;

	public int getTotalNumByDate(Admin admin, Date date1, Date date2)
			throws Exception;

	public List<ManualWarn> getManualWarnsByDate(Admin admin, Date date1,
			Date date2, int start, int limit);

}
