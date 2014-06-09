package com.namoo.social.dao.sqlmap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.social.dao.UserDao;
import com.namoo.social.dao.sqlmap.mapper.UserMapper;
import com.namoo.social.domain.User;

@Repository
public class UserDaoSqlMap implements UserDao{
	//
	@Autowired
	private UserMapper mapper;
	
	@Override
	public List<User> readAllUsers() {
		//
		return mapper.selectAllUsers();
	}

	@Override
	public User readUser(String userId) {
		//
		return mapper.selectUser(userId);
	}

	@Override
	public void createUser(User user) {
		//
		mapper.insertUser(user);
	}

	@Override
	public void updateUser(User user) {
		//
		mapper.updateUser(user);
	}

	@Override
	public void deleteUser(String userId) {
		//
		mapper.deleteUser(userId);
	}

	@Override
	public List<User> readFollowings(String userId) {
		//
		return mapper.selectFollowings(userId);
	}

	@Override
	public List<User> readFollowers(String userId) {
		//
		return mapper.selectFollowers(userId);
	}

	@Override
	public void createFollowing(String who, String whom) {
		//
		mapper.insertFollowing(who, whom);
	}

	@Override
	public void deleteFollowing(String who, String whom) {
		//
		mapper.deleteFollowing(who, whom);
	}

}
