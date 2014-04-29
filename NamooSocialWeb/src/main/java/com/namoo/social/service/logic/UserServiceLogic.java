package com.namoo.social.service.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.social.dao.MessageDao;
import com.namoo.social.dao.UserDao;
import com.namoo.social.domain.User;
import com.namoo.social.service.facade.UserService;
import com.namoo.social.shared.exception.NamooSocialException;

@Service
public class UserServiceLogic implements UserService {
	//
	@Autowired
	private UserDao dao;
	@Autowired
	private MessageDao msgDao;

	// ------------------------------------------------------------------------
	
	@Override
	public boolean loginAsUser(String loginId, String password) {
		//
		User user = dao.readUser(loginId);
		if (user != null && password.equals(user.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public List<User> findAllUser() {
		//
		return dao.readAllUsers();
	}

	@Override
	public void registUser(String userId, String name, String email, String password) {
		//
		if (dao.readUser(userId) != null) {
			throw new NamooSocialException("이미 가입되어 있는 사용자입니다.");
		}
		User user = new User();
		user.setUserId(userId);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		
		dao.createUser(user);
	}

	@Override
	public void removeUser(String userId) {
		//
		dao.deleteUser(userId);
	}

	@Override
	public User findUser(String userId) {
		//
		User user = dao.readUser(userId);
		if (user != null) {
			user.setFollowers(dao.readFollowers(userId));
			user.setFollowings(dao.readFollowings(userId));
			user.setMessages(msgDao.readAllMessages(userId));
		}
		return user;
	}
	
	@Override
	public void adjustUser(String name, String email, String password) {
		//
		User user = new User();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		dao.updateUser(user);
	}

	@Override
	public List<User> findAllFollowings(String userId) {
		//
		return dao.readFollowings(userId);
	}
	
	@Override
	public int countAllFollowing(String userId) {
		//
		return dao.readFollowings(userId).size();
	}
	
	@Override
	public List<User> findAllFollowers(String userId) {
		//
		return dao.readFollowers(userId);
	}
	
	@Override
	public int countAllFollowers(String userId) {
		//
		return dao.readFollowers(userId).size();
	}

	@Override
	public List<User> findAllUserExceptFollowings(String userId) {
		//
		List<User> allUsers = dao.readAllUsers();
		List<User> followings = dao.readFollowings(userId);
		List<User> found = new ArrayList<User>();
		for (User allUser : allUsers) {
			if(allUser.getUserId().equals(userId)) {
				found.add(allUser);
			} 
			for (User following : followings) {
				if (allUser.getUserId().equals(following.getUserId())) {
					 found.add(allUser);
				}
			}
		}
		if (found != null) {
			allUsers.removeAll(found);
		}
		return allUsers;
	}

	@Override
	public void registFollowing(String who, String whom) {
		//
		dao.createFollowing(who, whom);
	}

	@Override
	public void deleteFollowing(String who, String whom) {
		//
		dao.deleteFollowing(who, whom);
	}
}
