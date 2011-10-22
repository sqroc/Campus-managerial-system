package ems.service.impl;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.ManualWarnDao;
import ems.model.Admin;
import ems.model.ManualWarn;
import ems.service.ManualWarnManager;

@Service("ManualWarnManager")
public class ManualWarnManagerImpl implements ManualWarnManager {

	private ManualWarnDao manualWarnDao;

	public ManualWarnDao getManualWarnDao() {
		return manualWarnDao;
	}

	@Resource
	public void setManualWarnDao(ManualWarnDao manualWarnDao) {
		this.manualWarnDao = manualWarnDao;
	}

	public void add(ManualWarn manualWarn) throws Exception {
		manualWarnDao.save(manualWarn);

	}

	public int deleletByid(int id) throws Exception {
		manualWarnDao.delete(id);
		return 1;
	}

	public void modify(ManualWarn manualWarn) throws Exception {
		manualWarnDao.modify(manualWarn);

	}

	public List<ManualWarn> getManualWarns(Admin admin, int start, int limit)
			throws Exception {
		return manualWarnDao.getManualWarns(admin, start, limit);

	}

	public int getTotalNum(Admin admin) throws Exception {

		return manualWarnDao.getTotalNum(admin);
	}

	public int getTotalNumByDate(Admin admin, String date1, String date2)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<ManualWarn> getManualWarnsByDate(Admin admin, String date11,
			String date22, int start, int limit) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date date1 = formatter.parse(date11, pos);
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pos = new ParsePosition(0);
		Date date2 = formatter.parse(date22, pos);
		return manualWarnDao.getManualWarnsByDate(admin, date1, date2, start,
				limit);
	}

}
