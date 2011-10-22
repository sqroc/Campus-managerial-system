package ems.service;

import java.util.List;

import ems.model.Fettle;

public interface FettleManager {
	public abstract void add(Fettle fettle) throws Exception;

	public abstract int deleteByid(int eid) throws Exception;

	public abstract void modify(Fettle fettle) throws Exception;

	public List<Fettle> getFettles(int start ,int limit) throws Exception;
	
	public Fettle loadById(int fid) throws Exception;
	
	public int getTotalNum() throws Exception;

}
