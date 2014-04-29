package com.namoo.social.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.social.domain.User;

public class UserDaoTest extends DbCommonTest{
	//
	private static final String DATASET_XML="UserDaoTest_dataset.xml";
	
	@Autowired
	private UserDao dao;
	
	//-------------------------------------------------------------------------
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllUser() {
		//
		assertThat(dao.readAllUsers().size(), is(3));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadUser() {
		//
		User user = dao.readUser("ekdgml");
		
		//검증
		assertThat(user.getName(), is("박상희"));
		assertThat(user.getEmail(), is("ekdgml@naver.com"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
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
	@DatabaseSetup(DATASET_XML)
	public void testUpdateUser() {
		//
		User user = dao.readUser("ekdgml");
		user.setEmail("abcd@abcd.a");
		user.setName("abcd");
		user.setPassword("abcd");
		
		dao.updateUser(user);
		
		//검증
		user = dao.readUser("ekdgml");
		assertThat(user.getEmail(), is("abcd@abcd.a"));
		assertThat(user.getName(), is("abcd"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRemoveUser() {
		//
		dao.deleteUser("ekdgml");
		
		//assertion
		assertNull(dao.readUser("ekdgml"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadFollowings() {
		//
		List<User> users = dao.readFollowings("ekdgml");
		assertThat(users.size(), is(1));
		assertThat(users.get(0).getUserId(), is("wntjd"));
		assertThat(users.get(0).getName(), is("이주성"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadFollowers() {
		//
		List<User> users = dao.readFollowers("ekdgml");
		assertThat(users.size(), is(1));
		assertThat(users.get(0).getUserId(), is("hong"));
		assertThat(users.get(0).getName(), is("홍길동"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML) 
	public void testCreateFollowing() {
		//
		dao.createFollowing("ekdgml", "hong");
		
		//assertion
		List<User> users = dao.readFollowings("ekdgml");
		assertThat(users.size(), is(2));
		assertThat(users.get(0).getUserId(), is("hong"));
		assertThat(users.get(0).getName(), is("홍길동"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteFollowing() {
		//
		dao.deleteFollowing("ekdgml", "wntjd");
		
		//assertion
		List<User> users = dao.readFollowings("ekdgml");
		assertThat(users.size(), is(0));
	}

}
