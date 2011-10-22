package ems.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import ems.dao.MessageDao;
import ems.model.Admin;
import ems.model.Message;

@Repository("MessageDao")
public class MessageDaoImpl implements MessageDao {

	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public void save(Message message) {
		hibernateTemplate.save(message);
	}

	public void modify(Message message) {
		hibernateTemplate.update(message);
	}

	public List<Message> getMessages(Admin admin) {

		String queryString = "from Message ms where ms.receiveName='"
				+ admin.getName() + "' and ms.state<>" + 1;

		List<Message> messages = hibernateTemplate.find(queryString);
		
		if(messages.size()>0)
		{
			System.out.println("将信息状态改为 1    已获得");
			
			for(int i=0 ; i<messages.size();i++)
			{
				messages.get(i).setState(1);
				modify(messages.get(i));
			}
		}

		return messages;
	}

}
