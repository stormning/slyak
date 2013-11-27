/**
 * Project name : slyak-tag
 * File name : TagService.java
 * Package name : com.slyak.tag.service
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.tag.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.slyak.tag.model.Tag;

public interface TagService {

	List<Tag> listOrderByUsed(int fetchSize);
	
	Page<Tag> findAll(Pageable pageable);
	
	@Transactional
	void save(Tag tag);
	
	Tag findOne(Long id);
	
	
}
