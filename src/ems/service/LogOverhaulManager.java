package ems.service;

import java.util.List;

import ems.model.Admin;
import ems.model.LogOverhaul;

public interface LogOverhaulManager {

	public abstract void add(LogOverhaul logOverhaul) throws Exception;

	public abstract int deleletByid(int eid) throws Exception;

	public abstract void modify(LogOverhaul logOverhaul) throws Exception;

	public List<LogOverhaul> getLogOverhauls(Admin admin, int start, int limit)
			throws Exception;

	public int getTotalNum() throws Exception;

	public LogOverhaul loadById(int loid) throws Exception;

}
