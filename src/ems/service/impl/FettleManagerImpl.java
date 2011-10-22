package ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.FettleDao;
import ems.model.Fettle;
import ems.service.FettleManager;

@Service("FettleManager")
public class FettleManagerImpl implements FettleManager {

	private FettleDao fettleDao;

	@Resource
	public void setFettleDao(FettleDao fettleDao) {
		this.fettleDao = fettleDao;
	}

	public void add(Fettle fettle) throws Exception {
		fettleDao.save(fettle);

	}

	public int deleteByid(int eid) throws Exception {
		return fettleDao.deleteByid(eid);
	}

	public void modify(Fettle fettle) throws Exception {
		fettleDao.modify(fettle);
	}

	public List<Fettle> getFettles(int start, int limit) throws Exception {
		return fettleDao.getFettles(start, limit);
	}

	public Fettle loadById(int fid) throws Exception {
		return fettleDao.loadByFid(fid);
	}

	public int getTotalNum() throws Exception {
		return fettleDao.getTotalNum();
	}

}
