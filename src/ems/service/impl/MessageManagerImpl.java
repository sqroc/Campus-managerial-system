package ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ems.dao.MessageDao;
import ems.model.Admin;
import ems.model.Message;
import ems.service.MessageManager;

@Service("MessageManager")
public class MessageManagerImpl implements MessageManager {

	private MessageDao messageDao;

	public MessageDao getMessageDao() {
		return messageDao;
	}
	@Resource
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public void add(Message message) throws Exception {
		messageDao.save(message);

	}

	public void modify(Message message) throws Exception {
		messageDao.modify(message);
	}

	public List<Message> getMessages(Admin admin) throws Exception {

		return messageDao.getMessages(admin);
	}

}
