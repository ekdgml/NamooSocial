package com.namoo.social.dao.sqlmap.mapper;

import java.util.List;

import com.namoo.social.domain.Message;

public interface MessageMapper {
	//
	void insertMessage(Message message);
	Message selectMessage(int messageId);
	List<Message> selectAllMessages(String userId);
	List<Message> selectRelatedMessages(String userId);
	void updateMessage(Message message);
	void deleteMessage(int messageId);
	void deleteAllMessagesByUserId(String userId);
	
}
