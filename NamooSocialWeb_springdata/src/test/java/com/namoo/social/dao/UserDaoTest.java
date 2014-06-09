package com.namoo.social.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.social.domain.User;
import com.namoo.social.shared.AbstractDbUnitTestCase;

public class UserDaoTest extends AbstractDbUnitTestCase {
	//
	private static final String DATASET_XML = "UserDaoTest_dataset.xml";
	
	@Autowired
	private UserDao userDao;

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadUsers() {
		//
		List<User> users = userDao.readAllUsers();
		assertThat(users.size(), is(3));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadFollowings() {
		//
		List<User> users = userDao.readFollowings("hyunohkim");
		
		// assertion
		assertThat(users.size(), is(1));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadFollowers() {
		//
		List<User> users = userDao.readFollowers("hyunohkim");
		
		// assertion
		assertThat(users.size(), is(1));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadUser() {
		//
		User user = userDao.readUser("hyunohkim");
		
		// assertion
		assertThat(user.getUserId(), is("hyunohkim"));
		assertThat(user.getName(), is("김현오"));
		assertThat(user.getEmail(), is("hyunohkim@nextree.co.kr"));
		assertThat(user.getPassword(), is("1234"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testInsertUser() {
		//
		User user = new User();
		user.setUserId("testuser");
		user.setName("테스트");
		user.setEmail("test@testcase.com");
		user.setPassword("1111");
		
		userDao.insertUser(user);
		
		// assertion
		user = userDao.readUser("testuser");
		assertThat(user.getUserId(), is("testuser"));
		assertThat(user.getName(), is("테스트"));
		assertThat(user.getEmail(), is("test@testcase.com"));
		assertThat(user.getPassword(), is("1111"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdateUser() {
		//
		User user = userDao.readUser("hyunohkim");
		user.setEmail("haroldkim@change.com");
		user.setName("Harold Kim");
		user.setPassword("abcd");
		
		userDao.updateUser(user);
		
		// assertion
		user = userDao.readUser("hyunohkim");
		assertThat(user.getUserId(), is("hyunohkim"));
		assertThat(user.getName(), is("Harold Kim"));
		assertThat(user.getEmail(), is("haroldkim@change.com"));
		assertThat(user.getPassword(), is("abcd"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteUser() {
		//
		userDao.deleteUser("hyunohkim");
		
		// assertion
		assertNull(userDao.readUser("hyunohkim"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testInsertRelationship() {
		//
		String fromId = "hyunohkim";
		String toId = "yunakim";
		
		userDao.insertRelationship(fromId, toId);
		
		List<User> followings = userDao.readFollowings(fromId);
		
		// assertion
		assertThat(followings.size(), is(2));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteRelationship() {
		//
		String fromId = "hyunohkim";
		String toId = "gdhong";
		
		userDao.deleteRelationship(fromId, toId);
		
		List<User> followings = userDao.readFollowings(fromId);
		
		// assertion
		assertThat(followings.size(), is(0));
	}
}
