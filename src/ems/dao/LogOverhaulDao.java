package ems.dao;

import java.util.List;

import ems.model.LogOverhaul;

public interface LogOverhaulDao {

	public void save(LogOverhaul logOverhaul);

	public void delete(int id);

	public void modify(LogOverhaul logOverhaul);

	public List<LogOverhaul> getLogOverhauls(int start, int limit);

	public int getTotalNum() throws Exception;

	public LogOverhaul loadById(int loid);

}
