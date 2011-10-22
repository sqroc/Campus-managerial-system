package ems.dao;

import java.util.Date;
import java.util.List;

import ems.model.Reimbursement;

public interface ReimbursementDao {

	public void save(Reimbursement reimbursement);

	public int deleteByid(int id);

	public void modify(Reimbursement reimbursement);

	public Reimbursement loadByid(int rid);

	public List<Reimbursement> getReimbursements(int start, int limit);

	public int getTotalNum();

	public List<Reimbursement> getReimbursementsByDate(Date date1, Date date2,
			int start, int limit);

	public int getTotalNumByDate(Date date1, Date date2) throws Exception;
}
