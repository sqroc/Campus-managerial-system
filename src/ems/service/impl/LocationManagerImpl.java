package ems.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ems.dao.LocationDao;
import ems.model.Location;
import ems.service.LocationManager;

@Service("LocationManager")
public class LocationManagerImpl implements LocationManager {

	private LocationDao locationDao;

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	public void add(Location location) throws Exception {
		locationDao.save(location);

	}

	public int deleteByid(int locNumber) throws Exception {
		return locationDao.deleteByid(locNumber);
	}

	public void modify(Location location) throws Exception {
		locationDao.modify(location);

	}

	public List<Location> getLocations(int start, int limit) throws Exception {
		return locationDao.getLocations(start, limit);
	}

	public Location loadById(int locNumber) throws Exception {
		return locationDao.loadByid(locNumber);
	}

	public Location loadByName(String locName) throws Exception {
		return locationDao.loadByName(locName);
	}

	public int getTotalNum() throws Exception {
		return locationDao.getTotalNum();
	}

}
