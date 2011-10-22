package ems.service;

import java.util.List;

import ems.model.Location;

public interface LocationManager {
	public abstract void add(Location location) throws Exception;

	public abstract int deleteByid(int locNumber) throws Exception;

	public abstract void modify(Location location) throws Exception;

	public List<Location> getLocations(int start ,int limit) throws Exception;
	
	
	public Location loadById(int locNumber) throws Exception;
	
	public Location loadByName(String locName)throws Exception;
	
	public int getTotalNum() throws Exception;

}
