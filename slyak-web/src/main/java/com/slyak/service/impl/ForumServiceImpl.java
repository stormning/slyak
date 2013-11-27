/**
 * Project name : slyak-web
 * File name : ForumServiceImpl.java
 * Package name : com.slyak.service.impl
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.slyak.dao.ForumDao;
import com.slyak.dao.ForumTopicDao;
import com.slyak.model.Forum;
import com.slyak.model.ForumTopic;
import com.slyak.service.ForumService;

@Service
public class ForumServiceImpl implements ForumService{
	
	@Autowired
	private ForumDao forumDao;
	
	@Autowired
	private ForumTopicDao forumTopicDao;

	@Override
	public List<ForumTopic> getLatestTopics(int offset, int limit) {
		Pageable pageable = new PageRequest(offset/limit, limit);
		return forumTopicDao.getTopics(pageable);
	}

	@Override
	public List<Forum> findAll() {
		return forumDao.findAll();
	}

	@Override
	public Page<ForumTopic> getTopicsPage(long forumId, Pageable pageable) {
		return forumTopicDao.getTopicsPage(forumId, pageable);
	}

	@Override
	public void saveForumTopic(ForumTopic forumTopic) {
		forumTopicDao.save(forumTopic);
	}

	@Override
	public ForumTopic findForumTopic(long topicId) {
		return forumTopicDao.findOne(topicId);
	}

	@Override
	public void deleteForumTopic(long topicId) {
		forumTopicDao.delete(topicId);
	}

	@Override
	public void saveForum(Forum forum) {
		forumDao.save(forum);
	}

}
