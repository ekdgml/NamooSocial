package com.namoo.social.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;


public class MessageDaoTest extends DbCommonTest {
	//
	private static final String DATASET_XML="MessageDaoTest_dataset.xml";
	
	@Autowired
	private MessageDao dao;
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllMessages() {
		//
		List<Message> messages = dao.readAllMessages("ekdgml"); 
		
		//assertion
		assertThat(messages.size(), is(2));
		for (Message message : messages) {
			assertThat(message.getWriter().getName(), is("박상희"));
		}
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadMessage() {
		//
		Message message = dao.readMessage(1);
		
		//assertion
		assertThat(message.getContents(), is("First message"));
		assertThat(message.getWriter().getUserId(), is("ekdgml"));
		assertThat(message.getWriter().getName(), is("박상희"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testCreateMessage() {
		//
		Message message = new Message();
		message.setContents("Test Message");
		message.setWriter(new User("ekdgml", "박상희"));
		
		int msgId = dao.createMessage(message);
		
		//assertion
		message = dao.readMessage(msgId);
		assertThat(message.getContents(), is("Test Message"));
		assertThat(message.getWriter().getUserId(), is("ekdgml"));
		assertThat(message.getWriter().getName(), is("박상희"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdateMessage() {
		//
		Message message = dao.readMessage(1);
		message.setContents("Test Message");
		
		dao.updateMessage(message);
		
		//assertion
		message = dao.readMessage(1);
		assertThat(message.getContents(), is("Test Message"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRemoveMessage() {
		//
		dao.removeMessage(1);
		
		//assertion
		assertNull(dao.readMessage(1));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML) 
	public void testReadTimeLine() {
		//
		List<Message> messages = dao.readTimeLine("ekdgml");
		
		//assetion
		assertThat(messages.get(0).getWriter().getUserId(), is("ekdgml"));
		assertThat(messages.get(0).getWriter().getName(), is("박상희"));
		assertThat(messages.get(1).getWriter().getUserId(), is("wntjd"));
		assertThat(messages.get(1).getWriter().getName(), is("이주성"));
	}

}
