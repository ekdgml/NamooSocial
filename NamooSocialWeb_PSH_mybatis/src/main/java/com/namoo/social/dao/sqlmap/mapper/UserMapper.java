package com.namoo.social.dao.sqlmap.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.namoo.social.domain.User;

public interface UserMapper {
	//
	List<User> selectAllUsers();
	User selectUser(String userId);
	void insertUser(User user);
	void updateUser(User user);
	void deleteUser(String userId);
	
	List<User> selectFollowings(String userId);
	List<User> selectFollowers(String userId);
	void insertFollowing(@Param("who") String who, @Param("whom") String whom);
	void deleteFollowing(@Param("who") String who, @Param("whom") String whom);
}
