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
import com.namoo.social.shared.type.ImageFile;

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
	public void registUser(String userId, String name, String email, String password, ImageFile imageFile) {
		//
		if (dao.readUser(userId) != null) {
			throw new NamooSocialException("이미 가입되어 있는 사용자입니다.");
		}
		User user = new User();
		user.setUserId(userId);
		user.setEmail(email);
		user.setName(name);
		user.setPassword(password);
		
		user.setProfileImage(imageFile);
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
	public void adjustUser(String userId, String name, String email, String password) {
		//
		User user = new User();
		user.setUserId(userId);
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
		List<User> founds = new ArrayList<User>();
		
		// 로그인 유저를 삭제대상으로 
		for (User user : allUsers) {
			if(user.getUserId().equals(userId)) {
				founds.add(user);
				break;
			}
		}
		  
		// 로그인 유저의 팔로윙 유저 찾기
		if (followings != null) {
			for (User user : allUsers) {
				for (User following : followings) {
					if (user.getUserId().equals(following.getUserId())) {
						founds.add(user);
					}
				}
			}
		}
		// 삭제대상이 있으면 제거
		if (!founds.isEmpty()) {
			allUsers.removeAll(founds);
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
