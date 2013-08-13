package com.slyak.group.service;

import java.util.List;

import com.slyak.group.model.Group;

public interface GroupService {
	
	void save(Group group);

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
}
