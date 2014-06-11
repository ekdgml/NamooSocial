package com.namoo.social.service.facade;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.namoo.social.domain.User;
import com.namoo.social.shared.BaseMongoTestCase;
import com.namoo.social.shared.type.ImageFile;

public class UserServiceTest extends BaseMongoTestCase{
	//
	private static final String DATASET_JSON = "/com/namoo/social/service/facade/UserService.json";
	
	@Autowired
	private UserService service;
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testLoginAsUser() {
		//
		assertTrue(service.loginAsUser("ekdgml", "asdf"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testFindAllUser() {
		//
		assertThat(service.findAllUser().size(), is(3));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testRegistUser() {
		//
		ImageFile file = new ImageFile("aaaa", "aaa.jpg", "image/jpeg");
		service.registUser("ccc", "ccc", "ccc", "ccc", file);
		
		//assertion
		User user = service.findUser("ccc");
		
		assertThat(service.findAllUser().size(), is(4));
		assertThat(user.getEmail(), is("ccc"));
		assertThat(user.getName(), is("ccc"));
		assertThat(user.getPassword(), is("ccc"));
		assertThat(user.getProfileImage().getFileName(), is("aaa.jpg"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testRemoveUser() {
		//
		service.removeUser("ekdgml");
		
		//assertion
		assertNull(service.findUser("ekdgml"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testFindUser() {
		//
		User user = service.findUser("hong");
		
		//assertion
		assertThat(user.getName(), is("홍길동"));
		assertThat(user.getEmail(), is("hong@naver.com"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testFindAllFollowings() {
		//
		List<User> users = service.findAllFollowings("ekdgml");
		
		//assertion
		assertThat(users.size(), is(1));
		assertThat(users.get(0).getUserId(), is("wntjd"));
		assertThat(users.get(0).getName(), is("이주성"));
	}

	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testFindAllFollowers() {
		//
		List<User> users = service.findAllFollowers("ekdgml");
		
		//assertion
		assertThat(users.size(), is(1));
		assertThat(users.get(0).getUserId(), is("hong"));
		assertThat(users.get(0).getName(), is("홍길동"));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testFindAllUserExceptFollowings() {
		//
		assertThat(service.findAllUserExceptFollowings("ekdgml").size(), is(1));
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testRegistFollowing() {
		//
		service.registFollowing("ekdgml", "hong");
		
		//assertion
		List<User> users = service.findAllFollowings("ekdgml");
		assertThat(users.size(), is(2));
		assertThat(users.get(1).getUserId(), is("hong"));
		assertThat(users.get(1).getName(), is("홍길동"));
		
	}
	
	@Test
	@UsingDataSet(locations=DATASET_JSON, loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void testDeleteFollowing() {
		//
		service.deleteFollowing("ekdgml", "wntjd");
		
		//assertion
		List<User> users = service.findAllFollowings("ekdgml");
		assertThat(users.size(), is(0));
		
	}

}
