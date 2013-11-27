/**
 * Project name : slyak-web
 * File name : IndexController.java
 * Package name : com.slyak.web
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.slyak.model.AccountLog;
import com.slyak.model.ForumTopic;
import com.slyak.service.AccountService;
import com.slyak.service.ForumService;
import com.slyak.user.model.User;

@Controller
public class IndexController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ForumService forumService;

	@ModelAttribute
	public void populateModel(Model model) {
       model.addAttribute("nav", "index");
    }
	
	@RequestMapping("/")
	public String index(ModelMap model){
		//最新公开的
		List<AccountLog> accountLogs = accountService.findRecentSharedLogs(0,10);
		model.put("recentLogs", accountLogs);
		
		//公开的、且记账数多的记账之星
		List<User> stars = accountService.findMostActiveUsers(0,10);
		model.put("stars", stars);
		
		//最新讨论
		List<ForumTopic> latestTopics = forumService.getLatestTopics(0,10);
		model.put("latestTopics", latestTopics);
		
		return "index.index";
	}
}
