/**
 * Project name : slyak-web
 * File name : ForumController.java
 * Package name : com.slyak.web
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.slyak.model.Forum;
import com.slyak.model.ForumTopic;
import com.slyak.service.ForumService;

@Controller
@RequestMapping("/forum")
public class ForumController {
	
	@Autowired
	private ForumService forumService;
	
	@ModelAttribute
	public void populateModel(Model model) {
       model.addAttribute("nav", "forum");
    }
	
	@RequestMapping
	public String index(ModelMap modelMap){
		List<Forum> forums = forumService.findAll();
		modelMap.put("forums", forums);
		return "forum.index";
	}
	
	@RequestMapping("/create")
	public String createForum(Forum forum){
		forumService.saveForum(forum);
		return "";
	}
	
	@RequestMapping("/{forumId}/topics")
	public String topics(@PathVariable long forumId,Pageable pageable, ModelMap modelMap){
		Page<ForumTopic> topicPage = forumService.getTopicsPage(forumId, pageable);
		modelMap.put("topicPage", topicPage);
		return "forum.topics";
	}
	
	@RequestMapping("/{forumId}/createTopic")
	public String createTopic(@PathVariable long forumId,ForumTopic forumTopic){
		forumService.saveForumTopic(forumTopic);
		return null;
	}
	
	@RequestMapping("/topic/{topicId}/view")
	public String viewTopic(@PathVariable long topicId, ModelMap modelMap){
		ForumTopic forumTopic = forumService.findForumTopic(topicId);
		modelMap.put("topic", forumTopic);
		return "forum.viewTopic";
	}
	
	@RequestMapping("/topic/{topicId}/remove")
	public String removeTopic(@PathVariable long topicId, ModelMap modelMap){
		forumService.deleteForumTopic(topicId);
		return "";
	}
}
