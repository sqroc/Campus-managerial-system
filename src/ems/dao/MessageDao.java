package ems.dao;

import java.util.List;

import ems.model.Admin;
import ems.model.Message;

public interface MessageDao {
	public void save(Message message);

	//public int deleteByid(int id);

	public void modify(Message message);

	public List<Message> getMessages(Admin admin);
	
	
}
