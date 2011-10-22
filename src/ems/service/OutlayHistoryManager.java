package ems.service;

import java.util.List;

import ems.model.OutlayHistory;

public interface OutlayHistoryManager {

	public abstract void add(OutlayHistory outlayHistory) throws Exception;

	public abstract int deleteByid(int eid) throws Exception;

	public abstract void modify(OutlayHistory outlayHistory) throws Exception;

	public List<OutlayHistory> getOutlayHistorys(int start, int limit)
			throws Exception;

	public String getListForchartZ();

	public OutlayHistory loadById(int ohid) throws Exception;

	public int getTotalNum() throws Exception;
}
