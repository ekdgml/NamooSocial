package com.namoo.social.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.social.dao.MessageDao;
import com.namoo.social.dao.UserDao;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.service.facade.MessageService;

@Service
public class MessageServiceLogic implements MessageService {
	//
	@Autowired
	private MessageDao dao;
	@Autowired
	private UserDao userdao;

	@Override
	public List<Message> findAllMessages(String userId) {
		//
		return dao.readAllMessages(userId);
	}
	
	@Override
	public int countAllMessages(String userId) {
		//
		return dao.readAllMessages(userId).size();
	}

	@Override
	public Message findMessage(int msgId) {
		//
		return dao.readMessage(msgId);
	}

	@Override
	public int postMessage(String contents, String writerId) {
		//
		Message message = new Message();
		message.setContents(contents);
		User writer = userdao.readUser(writerId);
		message.setWriter(writer);
		return dao.createMessage(message);
		
	}

	@Override
	public void removeMessage(int msgId) {
		//
		dao.removeMessage(msgId);
	}

	@Override
	public List<Message> showTimeLine(String userId) {
		//
		return dao.readTimeLine(userId);
	}
}
