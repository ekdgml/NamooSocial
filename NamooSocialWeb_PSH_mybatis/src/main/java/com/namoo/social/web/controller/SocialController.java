package com.namoo.social.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.social.domain.ImageFile;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.service.facade.MessageService;
import com.namoo.social.service.facade.UserService;
import com.namoo.social.web.session.LoginRequired;
import com.namoo.social.web.session.SessionManager;

@Controller
@LoginRequired
public class SocialController {
	//
	@Value("#{social['imageRootPath']}")
	private String imageRoot;
	
	@Autowired
	private MessageService msgService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public ModelAndView main(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		String userId = manager.getLoginId();
		User user = userService.findUser(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Message> messages = msgService.showTimeLine(userId);
		List<User> notFollowings = userService.findAllUserExceptFollowings(userId);
		
		map.put("messages", messages);
		map.put("user", user);
		map.put("notFollowings", notFollowings);
		
		return new ModelAndView("/main", map);
	}
	
	@RequestMapping(value="/main/image1", method=RequestMethod.GET)
	public void getProfileImage1(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		//
		SessionManager manager = new SessionManager(req);
		User user = userService.findUser(manager.getLoginId());
		ImageFile imageFile = user.getProfileImage();
		String contentType = null;
		InputStream in = null;
		if (imageFile != null) {
			contentType = imageFile.getContentType();
			in = new FileInputStream(new File(imageRoot + imageFile.getFileName()));
		} else {
			contentType = "image/jpeg";
			in = this.getClass().getResourceAsStream("/default.jpg");
		}
		try {
			resp.setContentType(contentType);
			IOUtils.copy(in, resp.getOutputStream());
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
	@RequestMapping(value="/follow/{whom}", method=RequestMethod.GET)
	public String follow(@PathVariable("whom") String whom, HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		userService.registFollowing(manager.getLoginId(), whom);
		return "redirect:/main";
	}
	
	@RequestMapping(value="/followings", method=RequestMethod.GET)
	public ModelAndView followingsList(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		String userId = manager.getLoginId();
		User user = userService.findUser(userId);
		Map<String, Object> map = new HashMap<String, Object>();

		List<User> followings = userService.findAllFollowings(userId);
		List<User> notFollowings = userService.findAllUserExceptFollowings(userId);
		
		map.put("followings", followings);
		map.put("notFollowings", notFollowings);
		map.put("user", user);
		return new ModelAndView("/followings", map);
	}
	
	@RequestMapping(value="/unfollow/{whom}", method=RequestMethod.GET)
	public String unfollow(HttpServletRequest req, @PathVariable("whom") String whom) {
		//
		SessionManager manager = new SessionManager(req);
		userService.deleteFollowing(manager.getLoginId(), whom);
		return "redirect:/followings";
	}
	
	@RequestMapping(value="/myMessages", method=RequestMethod.GET)
	public ModelAndView myMessageList(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		
		User user = userService.findUser(manager.getLoginId());
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = manager.getLoginId();
		
		List<User> notFollowings = userService.findAllUserExceptFollowings(userId);
		List<Message> messages = msgService.findAllMessages(userId);
		
		map.put("messages", messages);
		map.put("notFollowings", notFollowings);
		map.put("user", user);
		
		return new ModelAndView("/myMessages", map);
	}
	
	@RequestMapping(value="/posting", method=RequestMethod.GET)
	public String posting(HttpServletRequest req, @RequestParam("contents") String contents) {
		//
		SessionManager manager = new SessionManager(req);
		msgService.postMessage(contents, manager.getLoginId());
		return "redirect:/main";
	}
}
