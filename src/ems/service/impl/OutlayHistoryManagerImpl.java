package ems.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.OutlayHistoryDao;
import ems.model.OutlayHistory;
import ems.service.OutlayHistoryManager;

@Service("OutlayhistoryManager")
public class OutlayHistoryManagerImpl implements OutlayHistoryManager {

	private OutlayHistoryDao outlayHistoryDao;

	@Resource
	public void setOutlayHistoryDao(OutlayHistoryDao outlayHistoryDao) {
		this.outlayHistoryDao = outlayHistoryDao;
	}

	public void add(OutlayHistory outlayHistory) throws Exception {
		outlayHistoryDao.save(outlayHistory);
	}

	public int deleteByid(int eid) throws Exception {
		return outlayHistoryDao.deleteByid(eid);
	}

	public void modify(OutlayHistory outlayHistory) throws Exception {
		outlayHistoryDao.modify(outlayHistory);

	}

	public List<OutlayHistory> getOutlayHistorys(int start, int limit)
			throws Exception {
		return outlayHistoryDao.getOutlayHistorys(start, limit);
	}

	public OutlayHistory loadById(int ohid) throws Exception {
		return outlayHistoryDao.loadByid(ohid);
	}

	public int getTotalNum() throws Exception {
		return outlayHistoryDao.getTotalNum();
	}

	public String getListForchartZ() {
		double[] v = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		Date tempDate = new Date();
		String tempString = "[";
		List<OutlayHistory> Olist = outlayHistoryDao.getOutlayHistorys(0, 1000);
		for (int i = 0; i < Olist.size(); i++) {

			int month = Olist.get(i).getOutlayDate().getMonth();
			double value = Olist.get(i).getCost();

			v[month - 1] = v[month - 1] + value;

		}

		for (int i = 0; i <= 11; i++) {

			String unit = "{" + "\"name\":" + "\"" + (i + 1) + "月\","
					+ "\"values\":" + "\"" + v[i] + "\"" + "}";

			if (i <= 10) {
				tempString += unit + ",";
			} else if (i == 11) {
				tempString += unit + "]";
			}

		}

		return tempString;
	}

}
