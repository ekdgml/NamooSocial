package com.namoo.social.service.facade;

import java.util.List;

import com.namoo.social.domain.Message;

public interface MessageService {
	//
	List<Message> findAllMessages(String userId);
	int countAllMessages(String userId);
	Message findMessage(String msgId);
	String postMessage(String contents, String writerId);
	void removeMessage(String msgId);
	
	List<Message> showTimeLine(String userId);
}
