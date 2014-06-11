package com.namoo.social.service.facade;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.social.domain.Message;
import com.namoo.social.shared.BaseMongoTestCase;

public class MessageServiceTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON = "/com/namoo/social/service/facade/MessageService.json";
	
	@Autowired
	private MessageService service;
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testFindAllMessages() {
		//
		List<Message> messages = service.findAllMessages("ekdgml"); 
		
		//assertion
		assertThat(messages.size(), is(2));
		assertThat(messages.get(0).getWriter().getName(), is("박상희"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testFindMessage() {
		//
		Message message = service.findMessage("1");
		
		//assertion
		assertThat(message.getContents(), is("First message"));
		assertThat(message.getWriter().getUserId(), is("ekdgml"));
		assertThat(message.getWriter().getName(), is("박상희"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testPostMessage() {
		//
		String msgId = service.postMessage("testContents", "ekdgml");
		
		//assertion
		Message message = service.findMessage(msgId);
		assertThat(message.getContents(), is("testContents"));
		assertThat(message.getWriter().getUserId(), is("ekdgml"));
		assertThat(message.getWriter().getName(), is("박상희"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testRemoveMessage() {
		//
		service.removeMessage("4");
		
		//assertion
		assertNull(service.findMessage("4"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
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
