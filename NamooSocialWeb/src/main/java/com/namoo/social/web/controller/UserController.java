package com.namoo.social.web.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.namoo.social.domain.ImageFile;
import com.namoo.social.domain.Message;
import com.namoo.social.domain.User;
import com.namoo.social.service.facade.MessageService;
import com.namoo.social.service.facade.UserService;
import com.namoo.social.web.controller.cmd.UserCommand;
import com.namoo.social.web.session.SessionManager;

@Controller
public class UserController {
	//
	@Value("#{social['imageRootPath']}")
	private String imageRoot;
	
	@Autowired
	private UserService service;
	@Autowired
	private MessageService msgService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		//
		return "/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest req, @RequestParam("userId") String loginId, @RequestParam("password") String password) {
		//
		SessionManager manager = new SessionManager(req);
		if (manager.login(loginId, password)) {
			return "redirect:/main";
		}
		return "/login";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		//
		return "/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public RedirectView join(UserCommand command, @RequestParam("imageFile") MultipartFile file) throws IOException {
		//
		User user = new User();
		user.setEmail(command.getEmail());
		user.setName(command.getName());
		user.setPassword(command.getPassword());
		user.setUserId(command.getUserId());
		
		setupImage(user, file);
		service.registUser(command.getUserId(), command.getName(), command.getEmail(), command.getPassword());
		return new RedirectView("/login", true);
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public RedirectView logout(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		manager.logout();
		return new RedirectView("/login", true);
	}
	
	@RequestMapping(value="/myInfo", method=RequestMethod.GET) 
	public ModelAndView myInfo(HttpServletRequest req) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		SessionManager manager = new SessionManager(req);
		String userId = manager.getLoginId();
		
		List<Message> messages = msgService.showTimeLine(userId);
		User user = service.findUser(userId);
		List<User> notFollowings = service.findAllUserExceptFollowings(userId);
		
		map.put("messages", messages);
		map.put("user", user);
		map.put("notFollowings", notFollowings);
		return new ModelAndView("/myInfo", map);
	}
	
	@RequestMapping(value="/adjustInfo", method=RequestMethod.GET)
	public ModelAndView adjustInfo(HttpServletRequest req) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		SessionManager manager = new SessionManager(req);
		String userId = manager.getLoginId();
		
		List<Message> messages = msgService.showTimeLine(userId);
		User user = service.findUser(userId);
		List<User> notFollowings = service.findAllUserExceptFollowings(userId);
		
		map.put("messages", messages);
		map.put("user", user);
		map.put("notFollowings", notFollowings);
		return new ModelAndView("/adjustInfo", map);
	}
	
	@RequestMapping(value="/adjust", method=RequestMethod.GET)
	public String adjust(UserCommand command) {
		//
		service.adjustUser(command.getName(), command.getEmail(), command.getPassword());
		return "/myInfo";
	}
	
	@RequestMapping(value="/withdrawal", method=RequestMethod.GET)
	public ModelAndView withdrawalCheck(HttpServletRequest req) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		SessionManager manager = new SessionManager(req);
		String userId = manager.getLoginId();
		
		User user = service.findUser(userId);
		List<User> notFollowings = service.findAllUserExceptFollowings(userId);
		
		map.put("user", user);
		map.put("notFollowings", notFollowings);
		return new ModelAndView("/withdrawal", map);
	}
	
	@RequestMapping(value="/withdrawal", method=RequestMethod.POST)
	public String withdrawal(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		service.removeUser(manager.getLoginId());
		return "redirect:/login";
	}
	
	//-----------------------------------------------------------------------------
		//private method
		
		private void setupImage(User user, MultipartFile file) throws IOException {
			//
			if (file.isEmpty()) return;
			
			StringBuffer sb = new StringBuffer();
			sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
			sb.append(".");
			sb.append(FilenameUtils.getExtension(file.getOriginalFilename()));
			String saveFileName = sb.toString();
			File saveFile = new File(imageRoot + saveFileName);
			
			FileCopyUtils.copy(file.getBytes(), saveFile);
			System.out.println("image saved in " + saveFile.getCanonicalPath());
			
			String contentType = file.getContentType();
			user.setProfileImage(new ImageFile(contentType, saveFileName));
		}
}
