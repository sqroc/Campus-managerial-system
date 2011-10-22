package ems.dao;

import java.util.List;

import ems.model.Location;

public interface LocationDao{

	public void save(Location location);
	
	public int deleteByid(int id);
	
	public void modify(Location location);
	
	public Location loadByid(int locNumber);
	
	public Location loadByName(String locName);

    public List<Location> getLocations(int start ,int limit);
    
    public int getTotalNum() ;
}
