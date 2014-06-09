package com.namoo.social.dao;

import java.util.List;

import com.namoo.social.domain.User;

public interface UserDao {
	//
	List<User> readAllUsers();
	User readUser(String userId);
	void createUser(User user);
	void updateUser(User user);
	void deleteUser(String userId);
	
	List<User> readFollowings(String userId);
	List<User> readFollowers(String userId);
	void createFollowing(String who, String whom);
	void deleteFollowing(String who, String whom);
}
