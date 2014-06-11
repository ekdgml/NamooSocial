package com.namoo.social.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.social.domain.User;
import com.namoo.social.shared.BaseMongoTestCase;

public class UserDaoTest extends BaseMongoTestCase {
	//
	private static final String DATASET_JSON = "/com/namoo/social/dao/users.json";
	
	@Autowired
	private UserDao dao;
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadAllUser() {
		//
		int userSize = dao.readAllUsers().size();
		assertThat(userSize, is(3));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadUser() {
		//
		User user = dao.readUser("hyunohkim");
		
		// assertion
		assertThat(user.getUserId(), is("hyunohkim"));
		assertThat(user.getName(), is("김현오"));
		assertThat(user.getEmail(), is("hyunohkim@nextree.co.kr"));
		assertThat(user.getPassword(), is("1234"));
	}

	@Test
	public void testCreateUser() {
		//
		User user = new User();
		user.setUserId("asdf");
		user.setEmail("asdf");
		user.setName("asdf");
		user.setPassword("asdf");
		
		dao.createUser(user);
		
		//검증
		user = dao.readUser("asdf");
		assertThat(user.getEmail(), is("asdf"));
		assertThat(user.getName(), is("asdf"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testUpdateUser() {
		//
		User user = dao.readUser("hyunohkim");
		user.setEmail("haroldkim@change.com");
		user.setName("Harold Kim");
		user.setPassword("abcd");
		dao.updateUser(user);
		
		// assertion
		user = dao.readUser("hyunohkim");
		assertThat(user.getUserId(), is("hyunohkim"));
		assertThat(user.getName(), is("Harold Kim"));
		assertThat(user.getEmail(), is("haroldkim@change.com"));
		assertThat(user.getPassword(), is("abcd"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testRemoveUser() {
		//
		dao.deleteUser("hyunohkim");
		
		// assertion
		assertNull(dao.readUser("hyunohkim"));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadFollowings() {
		//
		List<User> users = dao.readFollowings("hyunohkim");
		
		// assertion
		assertThat(users.size(), is(2));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testReadFollowers() {
		//
		List<User> users = dao.readFollowers("hyunohkim");
		
		// assertion
		assertThat(users.size(), is(2));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testCreateFollowing() {
		//
		String fromId = "yunakim";
		String toId = "hyunohkim";
		
		dao.createFollowing(fromId, toId);
		
		List<User> followings = dao.readFollowings(fromId);
		
		// assertion
		assertThat(followings.size(), is(3));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteFollowing() {
		//
		String fromId = "yunakim";
		String toId = "gdhong";
		
		dao.deleteFollowing(fromId, toId);
		
		List<User> followings = dao.readFollowings(fromId);
		
		// assertion
		assertThat(followings.size(), is(1));
	}

}
