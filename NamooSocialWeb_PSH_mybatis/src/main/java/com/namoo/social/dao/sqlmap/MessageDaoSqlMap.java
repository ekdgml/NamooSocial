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
	public List<Message> readAllMessages(String userId) {
		//
		return mapper.selectAllMessages(userId);
	}

	@Override
	public Message readMessage(int msgId) {
		//
		return mapper.selectMessage(msgId);
	}

	@Override
	public int createMessage(Message msg) {
		//
		mapper.insertMessage(msg);
		return msg.getMessgeId();
	}

	@Override
	public void updateMessage(Message msg) {
		//
		mapper.updateMessage(msg);
	}

	@Override
	public void removeMessage(int msgId) {
		//
		mapper.deleteMessage(msgId);
	}

	@Override
	public List<Message> readTimeLine(String userId) {
		//
		return mapper.selectTimeLine(userId);
	}
}
