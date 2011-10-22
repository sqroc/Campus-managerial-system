package ems.service;

import java.util.List;

import ems.model.Reimbursement;

public interface ReimbursementManager {

	public abstract void add(Reimbursement reimbursement) throws Exception;

	public abstract int deleteByid(int eid) throws Exception;

	public abstract void modify(Reimbursement reimbursement) throws Exception;

	public List<Reimbursement> getReimbursements(int start ,int limit) throws Exception;
	
	
	public Reimbursement loadById(int ohid) throws Exception;
	
	public int getTotalNum() throws Exception;
}
