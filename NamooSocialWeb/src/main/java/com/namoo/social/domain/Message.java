package com.namoo.social.domain;

import java.util.Date;

public class Message {
	//
	private int msgId;
	private String contents;
	private User writer;
	private Date reg_dt;

	//------------------------------------------------------------------------------
	
	public int getMessgeId() {
		return msgId;
	}
	public void setMessgeId(int msgId) {
		this.msgId = msgId;
	}
	public String getContents() {
		return contents;
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
	public Date getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(Date reg_dt) {
		this.reg_dt = reg_dt;
	}
}
