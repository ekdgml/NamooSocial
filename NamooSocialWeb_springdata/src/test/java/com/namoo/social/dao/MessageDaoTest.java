package com.namoo.social.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.shared.AbstractDbUnitTestCase;

public class MessageDaoTest extends AbstractDbUnitTestCase {
	//
	private static final String DATASET_XML = "MessageDaoTest_dataset.xml";
	
	@Autowired
	private MessageDao messageDao;
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadMessages() {
		//
		List<Message> messages = messageDao.readMessages("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(2));
		Message firstMessage = messages.get(0);
		assertThat(firstMessage.getContents(), is("두번째 글입니다."));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadRelatedMessages() {
		//
		List<Message> messages = messageDao.readRelatedMessages("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(3));
		Message firstMessage = messages.get(0);
		assertThat(firstMessage.getContents(), is("길동이의 첫번째 트윗."));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testInsertMessage() {
		//
		Message message = new Message();
		message.setContents("메시지를 남깁니다.");
		message.setWriter(new User("hyunohkim"));
		
		messageDao.insertMessage(message);
		
		// assertion
		List<Message> messages = messageDao.readMessages("hyunohkim");
		assertThat(messages.size(), is(3));
		Message newMessage = messages.get(0);
		
		assertThat(newMessage.getContents(), is("메시지를 남깁니다."));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdateMessage() {
		//
		Message message = messageDao.readMessage(1);
		message.setContents("내용을 수정합니다.");
		
		messageDao.updateMessage(message);
		
		// assertion
		message = messageDao.readMessage(1);
		assertThat(message.getContents(), is("내용을 수정합니다."));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteMessage() {
		//
		// assert precondition
		assertNotNull(messageDao.readMessage(1));
		
		messageDao.deleteMessage(1);
		
		// assertion
		assertNull(messageDao.readMessage(1));
	}

}
