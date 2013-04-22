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
