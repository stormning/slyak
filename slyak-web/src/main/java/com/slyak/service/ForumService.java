/**
 * Project name : slyak-web
 * File name : ForumService.java
 * Package name : com.slyak.service
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.slyak.model.Forum;
import com.slyak.model.ForumTopic;

public interface ForumService {

	List<ForumTopic> getLatestTopics(int offset, int limit);

	List<Forum> findAll();
	
	Page<ForumTopic> getTopicsPage(long forumId,Pageable pageable);

	void saveForumTopic(ForumTopic forumTopic);

	ForumTopic findForumTopic(long topicId);

	void deleteForumTopic(long topicId);

	void saveForum(Forum forum);
}
