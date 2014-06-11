package com.namoo.social.dao;

import java.util.List;

import com.namoo.social.domain.Message;

public interface MessageDao {
	//
	List<Message> readAllMessages(String userId);
	Message readMessage(String msgId);
	String createMessage(Message msg);
	void updateMessage(Message msg);
	void removeMessage(String msgId);
	
	List<Message> readTimeLine(String userId);
}
