package com.namoo.social.dao;

import java.util.List;

import com.namoo.social.domain.Message;

public interface MessageDao {
	//
	List<Message> readAllMessages(String userId);
	Message readMessage(int msgId);
	int createMessage(Message msg);
	void updateMessage(Message msg);
	void removeMessage(int msgId);
	
	List<Message> readTimeLine(String userId);
}
