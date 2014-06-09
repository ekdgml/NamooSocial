package com.namoo.social.dao.sqlmap.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.namoo.social.domain.User;

public interface UserMapper {
	//
	void insertUser(User user);
	User selectUser(String userId);
	List<User> selectAllUsers();
	void updateUser(User user);
	void deleteUser(String userId);
	
	//relationship
	List<User> selectFollowings(String userId);
	List<User> selectFollowers(String userId);
	void insertRelationship(@Param("fromId") String fromId, @Param("toId") String toId);
	void deleteRelationship(@Param("fromId") String fromId, @Param("toId") String toId);
	void deleteAllRelationship(String fromId);
}
