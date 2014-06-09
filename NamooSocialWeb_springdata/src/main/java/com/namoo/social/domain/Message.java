package com.namoo.social.domain;

import java.util.Date;

public class Message {
	//
	private Integer id;
	private String contents;
	private User writer;
	private Date regDate;
	
	//--------------------------------------------------------------------------
	public Message() {
		//
	}
	
	public Message(int id) {
		this.id = id;
	}
	
	//--------------------------------------------------------------------------
	
	public String getContents() {
		return contents;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public User getWriter() {
		return writer;
	}
	public void setWriter(User writer) {
		this.writer = writer;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
