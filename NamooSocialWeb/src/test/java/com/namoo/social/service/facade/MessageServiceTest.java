package com.namoo.social.service.facade;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.social.domain.Message;

public class MessageServiceTest extends DbCommonTest{
	//
	private static final String DATASET_XML = "MessageServiceTest_dataset.xml";
	
	@Autowired
	private MessageService service;
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindAllMessages() {
		//
		List<Message> messages = service.findAllMessages("ekdgml"); 
		
		//assertion
		assertThat(messages.size(), is(2));
		assertThat(messages.get(0).getWriter().getName(), is("박상희"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindMessage() {
		//
		Message message = service.findMessage(1);
		
		//assertion
		assertThat(message.getContents(), is("First message"));
		assertThat(message.getWriter().getUserId(), is("ekdgml"));
		assertThat(message.getWriter().getName(), is("박상희"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testPostMessage() {
		//
		int msgId = service.postMessage("testContents", "ekdgml");
		
		//assertion
		Message message = service.findMessage(msgId);
		assertThat(message.getContents(), is("testContents"));
		assertThat(message.getWriter().getUserId(), is("ekdgml"));
		assertThat(message.getWriter().getName(), is("박상희"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRemoveMessage() {
		//
		service.removeMessage(4);
		
		//assertion
		assertNull(service.findMessage(4));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testShowTimeLine() {
		//
		List<Message> messages = service.showTimeLine("ekdgml");
		
		//assertion
		assertThat(messages.get(0).getContents(), is("First message"));
		assertThat(messages.get(0).getWriter().getUserId(), is("ekdgml"));
		assertThat(messages.get(0).getWriter().getName(), is("박상희"));
		assertThat(messages.get(1).getContents(), is("Third message"));
		assertThat(messages.get(1).getWriter().getUserId(), is("wntjd"));
		assertThat(messages.get(1).getWriter().getName(), is("이주성"));
		
	}

}
