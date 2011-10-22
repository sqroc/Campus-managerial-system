package ems.dao;

import java.util.List;

import ems.model.Admin;
import ems.model.LogSession;

public interface LogSessionDao {
	
	public void save(LogSession logSession);

	public int delete(int id);

	public void modify(LogSession logSession);

	public boolean checkLogSessionExistsWithName(String username);

	public List<LogSession> getLogSessions(int start ,int limit);

	public LogSession loadByLsid(int lsid);
	
	public int getTotalNum() throws Exception ;

}
