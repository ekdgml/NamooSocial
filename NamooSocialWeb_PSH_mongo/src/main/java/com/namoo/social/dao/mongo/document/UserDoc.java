package com.namoo.social.dao.mongo.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.shared.type.ImageFile;

@Document(collection="users")
public class UserDoc  {
	//
	@Id
	private String userId;
	private String name;
	private String email;
	private String password;
	private ImageFile profileImage;
	private List<Message> messages;
	
	//관계
	private List<UserDoc> followings;
	private List<UserDoc> followers;
	
	//--------------------------------------------------------------------------
	//constructor
	public UserDoc() {
		//
	}
	public UserDoc (User user) {
		//
		this.userId = user.getUserId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		
		this.profileImage = user.getProfileImage();
	}
	public UserDoc(String fromId, String toId) { 
		//
	}
	
	//--------------------------------------------------------------------------
	// UserDoc를 User객체로 변환하는 것
		public User createDomain() {
			//
			User user = new User();
			user.setUserId(userId);
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			
			user.setProfileImage(profileImage);
			
			return user;
		}
	//--------------------------------------------------------------------------

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
	public ImageFile getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(ImageFile profileImage) {
		this.profileImage = profileImage;
	}
	public List<Message> getMessages() {
		return messages;
	}
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	public List<UserDoc> getFollowings() {
		return followings;
	}
	public void setFollowings(List<UserDoc> followings) {
		this.followings = followings;
	}
	public List<UserDoc> getFollowers() {
		return followers;
	}
	public void setFollowers(List<UserDoc> followers) {
		this.followers = followers;
	}
}
