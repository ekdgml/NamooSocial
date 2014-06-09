package com.namoo.social.domain;

public class ImageFile {
	//
	private String contentType;
	private String fileName;
	
	//--------------------------------------------------------------------------
	//constructor
	
	public ImageFile() {
		//
	}
	
	public ImageFile(String contentType, String fileName) {
		//
		this.contentType = contentType;
		this.fileName = fileName;
	}

	//------------------------------------------------------------------------------
	//getter/setter
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		// 
		StringBuffer sb = new StringBuffer(128);
		sb.append("contentType : " + contentType);
		sb.append(", fileName : " + fileName);
		
		return sb.toString();
	}
	
}
