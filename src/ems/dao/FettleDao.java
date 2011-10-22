package ems.dao;

import java.util.List;

import ems.model.Fettle;

public interface FettleDao {

	public void save(Fettle fettle);
	
	public int deleteByid(int id);
	
	public void modify(Fettle fettle);
	
	public Fettle loadByFid(int fid);

    public List<Fettle> getFettles(int start ,int limit);
    
    public int getTotalNum() ;
}
