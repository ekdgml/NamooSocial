package com.namoo.social.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.shared.BaseMongoTestCase;

public class MessageDaoTest extends BaseMongoTestCase {
	//
	private static final String DATASET_JSON = "/com/namoo/social/dao/messages.json";
	
	@Autowired
	private MessageDao dao;
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadAllMessages() {
		//
		List<Message> messages = dao.readAllMessages("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(2));
		Message firstMessage = messages.get(0);
		assertThat(firstMessage.getContents(), is("첫번째 글입니다."));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadMessage() {
		//
		List<Message> messages = dao.readAllMessages("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(2));
		Message firstMessage = messages.get(0);
		assertThat(firstMessage.getContents(), is("첫번째 글입니다."));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testCreateMessage() {
		//
		Message message = new Message();
		message.setContents("Test Message");
		message.setWriter(new User("ekdgml", "박상희"));
		
		String msgId = dao.createMessage(message);
		
		//assertion
		message = dao.readMessage(msgId);
		assertThat(message.getContents(), is("Test Message"));
		assertThat(message.getWriter().getUserId(), is("ekdgml"));
		assertThat(message.getWriter().getName(), is("박상희"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateMessage() {
		//
		Message message = dao.readMessage("1");
		message.setContents("Test Message");
		
		dao.updateMessage(message);
		
		//assertion
		message = dao.readMessage("1");
		assertThat(message.getContents(), is("Test Message"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testRemoveMessage() {
		//
		dao.removeMessage("1");
		
		//assertion
		assertNull(dao.readMessage("1"));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadTimeLine() {
		//
		List<Message> messages = dao.readTimeLine("hyunohkim");
		
		// assertion
		assertThat(messages.size(), is(3));
		Message firstMessage = messages.get(2);
		assertThat(firstMessage.getContents(), is("길동이의 첫번째 트윗."));
	}

}
