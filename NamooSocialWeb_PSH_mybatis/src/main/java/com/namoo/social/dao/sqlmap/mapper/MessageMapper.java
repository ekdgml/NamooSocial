package com.namoo.social.dao.sqlmap.mapper;

import java.util.List;

import com.namoo.social.domain.Message;

public interface MessageMapper {
	//
	List<Message> selectAllMessages(String userId);
	Message selectMessage(int msgId);
	int insertMessage(Message msg);
	void updateMessage(Message msg);
	void deleteMessage(int msgId);
	
	List<Message> selectTimeLine(String userId);
}
