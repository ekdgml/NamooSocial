package com.namoo.social.domain;

import java.util.List;

import com.namoo.social.shared.type.ImageFile;

public class User {
	//
	private String userId;
	private String name;
	private String email;
	private String password;
	private ImageFile profileImage;
	private List<Message> messages;
	
	//관계
	private List<User> followings;
	private List<User> followers;
	
	//------------------------------------------------------------------------------
	//constructor
	
	public User() {
		//
	}
	
	public User(String userId, String name) {
		//
		this.userId = userId;
		this.name = name;
	}
	
	//------------------------------------------------------------------------------
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public List<User> getFollowings() {
		return followings;
	}
	public void setFollowings(List<User> followings) {
		this.followings = followings;
	}
	public List<User> getFollowers() {
		return followers;
	}
	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public ImageFile getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(ImageFile profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		// 
		StringBuffer sb = new StringBuffer();
		sb.append("userId:" + userId);
		sb.append(", password:" + password);
		sb.append(", name:" + name);
		sb.append(", email:" + email);
		return super.toString();
	}
	
}
