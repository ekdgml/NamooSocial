package com.namoo.social.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.namoo.social.dao.MessageDao;
import com.namoo.social.dao.mongo.document.MessageDoc;
import com.namoo.social.dao.mongo.document.TimeLineDoc;
import com.namoo.social.dao.mongo.document.UserDoc;
import com.namoo.social.dao.mongo.repository.MessageRepository;
import com.namoo.social.dao.mongo.repository.TimeLineRepository;
import com.namoo.social.dao.mongo.repository.UserRepository;
import com.namoo.social.domain.Message;

@Repository
public class MessageDaoMongoLogic implements MessageDao{
	//
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageRepository repository;
	@Autowired
	private TimeLineRepository timeRepository;

	@Override
	public List<Message> readAllMessages(String userId) {
		//
		Sort sort = new Sort(Sort.Direction.DESC, "regDate");
		List<MessageDoc> docs = repository.findAllByWriterUserId(userId, sort);
		if(docs != null) {
			List<Message> messages = new ArrayList<Message>();
			for(MessageDoc doc : docs) {
				messages.add(doc.createDomain());
			}
			return messages;
		}
		return null;
	}

	@Override
	public Message readMessage(String msgId) {
		//
		MessageDoc doc = repository.findOne(msgId);
		if (doc != null) {
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public String createMessage(Message msg) {
		// 
		MessageDoc doc = new MessageDoc(msg);
		repository.save(doc);
		
		String writerId = msg.getWriter().getUserId();
		UserDoc userDoc = userRepository.findOne(writerId);
		
		//작성자의 타임라인에 추가
		TimeLineDoc tlDoc = new TimeLineDoc(writerId, msg);
		timeRepository.save(tlDoc);
		
		//작성자의 팔로워의 타임라인에 추가
		if (userDoc != null && userDoc.getFollowers() != null) {
			for (UserDoc user : userDoc.getFollowers()) {
				tlDoc = new TimeLineDoc(user.getUserId(), msg);
				timeRepository.save(tlDoc);
			}
		}
		
		return doc.getId();
	}

	@Override
	public void updateMessage(Message msg) {
		//
		MessageDoc doc = repository.findOne(msg.getMsgId());
		doc = new MessageDoc(msg);
		repository.save(doc);
	}

	@Override
	public void removeMessage(String msgId) {
		//
		MessageDoc doc = repository.findOne(msgId);
		repository.delete(doc);
	}

	@Override
	public List<Message> readTimeLine(String userId) {
		//
		List<TimeLineDoc> docs = timeRepository.findByOwnerId(userId);
		if (docs != null) {
			List<Message> messages = new ArrayList<Message>();
			for (TimeLineDoc doc : docs) {
				messages.add(doc.createDomain());
			}
			return messages;
		}
		return null;
	}
}
