/**
 * Project name : slyak-web
 * File name : ForumTopicDao.java
 * Package name : com.slyak.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slyak.model.ForumTopic;

public interface ForumTopicDao extends JpaRepository<ForumTopic, Long> {
	
	@Query("select ft from ForumTopic ft")
	List<ForumTopic> getTopics(Pageable pageable);
	
	@Query("select ft from ForumTopic ft")
	Page<ForumTopic> getTopicsPage(long forumId,Pageable pageable);
}
