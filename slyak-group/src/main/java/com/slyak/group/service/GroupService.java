/**
 * Project name : slyak-group
 * File name : GroupService.java
 * Package name : com.slyak.group.service
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.group.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.slyak.group.model.Group;
import com.slyak.group.model.GroupAccess;

public interface GroupService {
	
	@Transactional
	void save(Group group);

	@Transactional
	void delete(Long groupId);

	List<Group> getRootGroups(String biz, String owner);

	Group findOne(Long groupId);

	List<Group> getPathGroups(Long groupId);

	Group getFirstLeaf(String biz, String owner);

	List<Group> findChildLeavesByPath(String path);

	List<Group> getChildren(Long pid);

	List<Group> findAll();

	List<Group> findAllLeaves(String biz, String owner);

	List<Group> findByIdIn(List<Long> groupIds);
	
	List<GroupAccess> findGroupAccesses(Long groupId);
}
