package com.namoo.social.service.facade;

import java.util.List;

import com.namoo.social.domain.User;

public interface UserService {
	//
	boolean loginAsUser(String loginId, String password);
	List<User> findAllUser();
	void registUser(String userId, String name, String email, String password);
	void removeUser(String userId);
	User findUser(String userId);
	void adjustUser(String name, String email, String password);
	
	List<User> findAllFollowings(String userId);
	int countAllFollowing(String userId);
	List<User> findAllFollowers(String userId);
	int countAllFollowers(String userId);
	List<User> findAllUserExceptFollowings(String userId);
	void registFollowing(String who, String whom);
	void deleteFollowing(String who, String whom);
}
