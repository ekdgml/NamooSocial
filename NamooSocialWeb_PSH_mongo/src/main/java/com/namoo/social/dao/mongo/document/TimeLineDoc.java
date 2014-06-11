package com.namoo.social.dao.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.social.domain.Message;

@Document(collection="timelines")
public class TimeLineDoc {
	//
	@Id
	private String id;
	@Indexed
	private String ownerId;
	private MessageDoc message;
	
	//--------------------------------------------------------------------------
	// constructor - 필요에 따라 추가
	public TimeLineDoc() {
		//
	}
	public TimeLineDoc(String ownerId, Message message) {
		//
		this.ownerId = ownerId;
		this.message = new MessageDoc(message);
	}
	
	//--------------------------------------------------------------------------
	// TimeLineDoc를 Message객체로 변환하는 것
	public Message createDomain() { 
		//
//		Message message2 = new Message();
//		message2.setMsgId(message.getId());
//		message2.setContents(message.getContents());
//		message2.setReg_dt(message.getRegDate());
//		message2.setWriter(message.getWriter().createDomain());
		
		return message.createDomain();
	}
	//--------------------------------------------------------------------------
	// getter, setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public MessageDoc getMessage() {
		return message;
	}
	public void setMessage(MessageDoc message) {
		this.message = message;
	}
}
	
