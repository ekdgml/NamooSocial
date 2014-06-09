package com.namoo.social.dao.sqlmap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.social.dao.MessageDao;
import com.namoo.social.dao.sqlmap.mapper.MessageMapper;
import com.namoo.social.domain.Message;

@Repository
public class MessageDaoSqlMap implements MessageDao {
	//
	@Autowired
	private MessageMapper mapper;
	
	@Override
	public List<Message> readMessages(String userId) {
		//
		return mapper.selectAllMessages(userId);
	}

	@Override
	public List<Message> readRelatedMessages(String userId) {
		//
		return mapper.selectRelatedMessages(userId);
	}

	@Override
	public Message readMessage(int messageId) {
		//
		return mapper.selectMessage(messageId);
	}

	@Override
	public int insertMessage(Message message) {
		//
		mapper.insertMessage(message);
		return message.getId();
	}

	@Override
	public void updateMessage(Message message) {
		//
		mapper.updateMessage(message);
	}

	@Override
	public void deleteMessage(int messageId) {
		//
		mapper.deleteMessage(messageId);
	}

	@Override
	public void deleteAllMessagesByUserId(String userId) {
		//
		mapper.deleteAllMessagesByUserId(userId);
	}

}
