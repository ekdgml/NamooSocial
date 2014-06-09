package com.namoo.social.service.facade;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.namoo.social.domain.User;
import com.namoo.social.shared.AbstractDbUnitTestCase;

public class UserServiceTest extends AbstractDbUnitTestCase{
	//
	private static final String DATASET_XML="UserServiceTest_dataset.xml";
	
	@Autowired
	private UserService service;
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testLoginAsUser() {
		//
		assertTrue(service.loginAsUser("ekdgml", "asdf"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindAllUser() {
		//
		assertThat(service.findAllUser().size(), is(3));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRegistUser() {
		//
		service.registUser("abcd", "abcd", "abcd", "abcd");
		
		//assertion
		User user = service.findUser("abcd");
		
		assertThat(service.findAllUser().size(), is(4));
		assertThat(user.getEmail(), is("abcd"));
		assertThat(user.getName(), is("abcd"));
		assertThat(user.getPassword(), is("abcd"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRemoveUser() {
		//
		service.removeUser("ekdgml");
		
		//assertion
		assertNull(service.findUser("ekdgml"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindUser() {
		//
		User user = service.findUser("hong");
		
		//assertion
		assertThat(user.getName(), is("홍길동"));
		assertThat(user.getEmail(), is("hong@naver.com"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindAllFollowings() {
		//
		List<User> users = service.findAllFollowings("ekdgml");
		
		//assertion
		assertThat(users.size(), is(1));
		assertThat(users.get(0).getUserId(), is("wntjd"));
		assertThat(users.get(0).getName(), is("이주성"));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindAllFollowers() {
		//
		List<User> users = service.findAllFollowers("ekdgml");
		
		//assertion
		assertThat(users.size(), is(1));
		assertThat(users.get(0).getUserId(), is("hong"));
		assertThat(users.get(0).getName(), is("홍길동"));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindAllUserExceptFollowings() {
		//
		assertThat(service.findAllUserExceptFollowings("ekdgml").size(), is(1));
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRegistFollowing() {
		//
		service.registFollowing("ekdgml", "hong");
		
		//assertion
		List<User> users = service.findAllFollowings("ekdgml");
		assertThat(users.size(), is(2));
		assertThat(users.get(0).getUserId(), is("hong"));
		assertThat(users.get(0).getName(), is("홍길동"));
		
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteFollowing() {
		//
		service.deleteFollowing("ekdgml", "wntjd");
		
		//assertion
		List<User> users = service.findAllFollowings("ekdgml");
		assertThat(users.size(), is(0));
		
	}

}
