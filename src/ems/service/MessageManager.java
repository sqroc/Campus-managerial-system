package ems.service;

import java.util.List;

import ems.model.Admin;
import ems.model.Message;

public interface MessageManager {

	public abstract void add(Message message) throws Exception;

	// public abstract int deleletByid(int aid) throws Exception;

	public abstract void modify(Message message) throws Exception;

	public List<Message> getMessages(Admin admin) throws Exception;
}
