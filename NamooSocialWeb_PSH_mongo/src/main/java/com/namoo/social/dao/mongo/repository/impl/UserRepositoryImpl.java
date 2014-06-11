package com.namoo.social.dao.mongo.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.mongodb.BasicDBObject;
import com.namoo.social.dao.mongo.document.UserDoc;
import com.namoo.social.dao.mongo.repository.UserRepositoryCustom;

public class UserRepositoryImpl extends SimpleMongoRepository<UserDoc, String> implements UserRepositoryCustom{
	//
	@Autowired
	public UserRepositoryImpl(MongoRepositoryFactory factory, MongoTemplate template) {
		super(factory.<UserDoc, String>getEntityInformation(UserDoc.class), template);
	}

	@Override
	public void insertRelationship(String fromId, String toId) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query fromQuery = new Query(Criteria.where("_id").is(fromId));
		Query toQuery = new Query(Criteria.where("_id").is(toId));
		// 1. 내가 팔로윙 할 사람 조회
		UserDoc userDocTo = operations.findOne(toQuery, UserDoc.class);
		UserDoc embeddedUserTo = new UserDoc();
		embeddedUserTo.setEmail(userDocTo.getEmail());
		embeddedUserTo.setUserId(userDocTo.getUserId());
		embeddedUserTo.setName(userDocTo.getName());
		// 2. 내 팔로윙 목록에 푸쉬
		Update update = new Update();
		update.push("followings", embeddedUserTo);
		operations.updateFirst(fromQuery, update, UserDoc.class);
		// 3. 내 정보 조회
		UserDoc userDocFrom = operations.findOne(fromQuery, UserDoc.class);
		UserDoc embeddedUserFrom = new UserDoc();
		embeddedUserFrom.setEmail(userDocFrom.getEmail());
		embeddedUserFrom.setUserId(userDocFrom.getUserId());
		embeddedUserFrom.setName(userDocFrom.getName());
		// 4. 그 사람 팔로워 목록에 나를 푸쉬
		Update update2 = new Update();
		update2.push("followers", embeddedUserFrom);
		operations.updateFirst(toQuery, update2, UserDoc.class);
	}

	@Override
	public void deleteRelationship(String fromId, String toId) {
		//
		MongoOperations operations = getMongoOperations();
		
		Query fromQuery = new Query(Criteria.where("_id").is(fromId));
		Query toQuery = new Query(Criteria.where("_id").is(toId));
		
		// 1. 내가 팔로윙 하고 있는 사람 조회
		// 2. 내 정보 조회
		// 3. 내가 팔로윙 하고 있는 사람의 팔로워 목록에서 나를 제거
		Update update = new Update();
		update.pull("followers", new BasicDBObject("_id" , fromId));
		
		operations.updateFirst(toQuery, update, UserDoc.class);
		
		// 4. 내 팔로윙 목록에서 상대방 정보 제거
		update = new Update();
		update.pull("followings", new BasicDBObject("_id", toId));
		
		operations.updateFirst(fromQuery, update, UserDoc.class);
	}

	@Override
	public void deleteAllRelationship(String fromId) {
		//
		MongoOperations operations = getMongoOperations();
		Query query = new Query(Criteria.where("followings.id").is(fromId));
		Query query2 = new Query(Criteria.where("followers.id").is(fromId));
		Update update = new Update();
		update.pull("followings", new BasicDBObject("_id", fromId));
		
		operations.updateFirst(query, update, UserDoc.class);
		
		// 팔로워 제거
		update.pull("followers", new BasicDBObject("_id", fromId));
		operations.updateFirst(query2, update, UserDoc.class);
	}
}
