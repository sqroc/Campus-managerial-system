package ems.service;

import java.util.List;


import ems.model.LogSession;

public interface LogSessionManager {
	public abstract boolean check(LogSession logSession) throws Exception;

	public abstract void add(LogSession logSession) throws Exception;

	public abstract int deleletByid(int id) throws Exception;

	public abstract void modify(LogSession logSession) throws Exception;

	public List<LogSession> getLogSessions(int start ,int limit) throws Exception;

	public LogSession loadById(int id) throws Exception;
	
	public int getTotalNum() throws Exception;
}
