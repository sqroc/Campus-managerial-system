package ems.dao;

import java.util.Date;
import java.util.List;

import ems.model.OutlayHistory;

public interface OutlayHistoryDao {

	public int deleteByid(int id);

	public List<OutlayHistory> getOutlayHistorys(int start, int limit);

	public int getTotalNum();

	public OutlayHistory loadByid(int ohid);

	public void modify(OutlayHistory outlayHistory);

	public void save(OutlayHistory outlayHistory);

	public List<OutlayHistory> getOutlayHistorysByDate(Date date1, Date date2,
			int start, int limit);

	public int getTotalNumByDate(Date date1, Date date2) throws Exception;

}
