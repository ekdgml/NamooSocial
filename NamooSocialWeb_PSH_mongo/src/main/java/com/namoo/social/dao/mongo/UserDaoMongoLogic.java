package com.namoo.social.dao.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.social.dao.UserDao;
import com.namoo.social.dao.mongo.document.UserDoc;
import com.namoo.social.dao.mongo.repository.UserRepository;
import com.namoo.social.domain.User;

@Repository
public class UserDaoMongoLogic implements UserDao{
	//
	@Autowired
	UserRepository repository;
	
	@Override
	public List<User> readAllUsers() {
		//
		List<UserDoc> docs = repository.findAll();
		if (docs != null) {
			List<User> users = new ArrayList<User>();
			for(UserDoc doc : docs) {
				users.add(doc.createDomain());
			}
			return users;
		}
		return null;
	}

	@Override
	public User readUser(String userId) {
		//
		UserDoc doc = repository.findOne(userId);
		if (doc != null) {	
			return doc.createDomain();
		}
		return null;
	}

	@Override
	public void createUser(User user) {
		//
		UserDoc doc = new UserDoc(user);
		repository.save(doc);
	}

	@Override
	public void updateUser(User user) {
		// 
		UserDoc doc = repository.findOne(user.getUserId()); 
		doc = new UserDoc(user);
		repository.save(doc);
	}

	@Override
	public void deleteUser(String userId) {
		//
		UserDoc doc = repository.findOne(userId);
		repository.delete(doc);
	}

	@Override
	public List<User> readFollowings(String userId) {
		//
		UserDoc userDoc = repository.findOne(userId);
		if (userDoc != null && userDoc.getFollowings() != null) {
			List<User> users= new ArrayList<User>();
			for(UserDoc doc : userDoc.getFollowings()) {
				users.add(doc.createDomain());
			}
			return users;
		}
		return null;
	}

	@Override
	public List<User> readFollowers(String userId) {
		//
		UserDoc userDoc = repository.findOne(userId);
		if (userDoc != null && userDoc.getFollowers() != null) {
			List<User> users = new ArrayList<User>();
			for(UserDoc doc : userDoc.getFollowers()) {
				users.add(doc.createDomain());
			}
			return users;
		}
		return null;
	}

	@Override
	public void createFollowing(String who, String whom) {
		//
		repository.insertRelationship(who, whom);
	}

	@Override
	public void deleteFollowing(String who, String whom) {
		//
		repository.deleteRelationship(who, whom);
	}
}
