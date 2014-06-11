package com.namoo.social.dao.mongo.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.social.domain.Message;

@Document(collection="messages")
public class MessageDoc {
	//
	@Id
	private String id;
	private String contents;
	private UserDoc writer;
	private Date regDate;

	//--------------------------------------------------------------------------
	// constructor - 필요에 따라 추가 
	
	public MessageDoc() {
		//
	}
	public MessageDoc(Message message) {
		//
		this.id = message.getMsgId();
		this.contents = message.getContents();
		this.writer = new UserDoc(message.getWriter());
		this.regDate = new Date();
	}
	
	//--------------------------------------------------------------------------
	public Message createDomain() {
		//
		Message message = new Message();
		message.setMsgId(id);
		message.setContents(contents);
		message.setWriter(writer.createDomain());
		message.setReg_dt(regDate);
		
		return message;
	}
	//--------------------------------------------------------------------------
	// getter, setter

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public UserDoc getWriter() {
		return writer;
	}
	public void setWriter(UserDoc writer) {
		this.writer = writer;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	//--------------------------------------------------------------------------
	// constructor - 필요에 따라 추가 
	
	//--------------------------------------------------------------------------
	// getter, setter
	
	
}
