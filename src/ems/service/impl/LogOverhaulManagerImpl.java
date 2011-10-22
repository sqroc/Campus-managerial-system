package ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.LogOverhaulDao;
import ems.model.Admin;
import ems.model.LogOverhaul;
import ems.service.LogOverhaulManager;

@Service("LogoverhaulManager")
public class LogOverhaulManagerImpl implements LogOverhaulManager {

	private LogOverhaulDao logOverhaulDao;

	public LogOverhaulDao getLogOverhaulDao() {
		return logOverhaulDao;
	}

	@Resource
	public void setLogOverhaulDao(LogOverhaulDao logOverhaulDao) {
		this.logOverhaulDao = logOverhaulDao;
	}

	public void add(LogOverhaul logOverhaul) throws Exception {
		logOverhaulDao.save(logOverhaul);

	}

	public int deleletByid(int eid) throws Exception {

		logOverhaulDao.delete(eid);
		return 1;

	}

	public void modify(LogOverhaul logOverhaul) throws Exception {
		logOverhaulDao.modify(logOverhaul);
	}

	public List<LogOverhaul> getLogOverhauls(Admin admin, int start, int limit)
			throws Exception {

		return logOverhaulDao.getLogOverhauls(start, limit);
	}

	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stubs
		return logOverhaulDao.getTotalNum();
	}

	public LogOverhaul loadById(int loid) throws Exception {
		return logOverhaulDao.loadById(loid);
	}

}
