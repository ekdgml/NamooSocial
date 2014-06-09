package com.namoo.social.dao;

import java.util.List;

import com.namoo.social.domain.Message;

public interface MessageDao {
	//
	List<Message> readMessages(String userId);
	List<Message> readRelatedMessages(String userId);
	Message readMessage(int messageId);
	int insertMessage(Message message);
	void updateMessage(Message message);
	void deleteMessage(int messageId);
	void deleteAllMessagesByUserId(String userId);
}
