package ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.ReimbursementDao;
import ems.model.Reimbursement;
import ems.service.ReimbursementManager;

@Service("ReimbursementManager")
public class ReimbursementManagerImpl implements ReimbursementManager {

	private ReimbursementDao reimbursementDao;

	@Resource
	public void setReimbursementDao(ReimbursementDao reimbursementDao) {
		this.reimbursementDao = reimbursementDao;
	}

	public void add(Reimbursement reimbursement) throws Exception {
		System.out.print("Reimbursement save");
		reimbursementDao.save(reimbursement);

	}

	public int deleteByid(int eid) throws Exception {
		return reimbursementDao.deleteByid(eid);
	}

	public void modify(Reimbursement reimbursement) throws Exception {
		reimbursementDao.modify(reimbursement);

	}

	public List<Reimbursement> getReimbursements(int start, int limit)
			throws Exception {
		return reimbursementDao.getReimbursements(start, limit);
	}

	public Reimbursement loadById(int ohid) throws Exception {
		return reimbursementDao.loadByid(ohid);
	}

	public int getTotalNum() throws Exception {
		return reimbursementDao.getTotalNum();
	}

}
