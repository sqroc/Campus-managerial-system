package ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.AdminDao;
import ems.dao.LogSessionDao;
import ems.model.LogSession;
import ems.service.LogSessionManager;

@Service("LogSessionManager")
public class LogSessionManagerImpl implements LogSessionManager {

	private LogSessionDao logSessionDao;

	public LogSessionDao getLogSessionDao() {
		return logSessionDao;
	}

	@Resource
	public void setLogSessionDao(LogSessionDao logSessionDao) {
		this.logSessionDao = logSessionDao;
	}

	public boolean check(LogSession logSession) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public void add(LogSession logSession) throws Exception {
		logSessionDao.save(logSession);
	}

	public int deleletByid(int id) throws Exception {
		logSessionDao.delete(id);
		return 1;
	}

	public void modify(LogSession logSession) throws Exception {
		// TODO Auto-generated method stub

	}

	public List<LogSession> getLogSessions(int start,int limit) throws Exception {
		
           return logSessionDao.getLogSessions(start,limit);
	}

	public LogSession loadById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getTotalNum() throws Exception {
		// TODO Auto-generated method stub
		return logSessionDao.getTotalNum();
	}
	

}
