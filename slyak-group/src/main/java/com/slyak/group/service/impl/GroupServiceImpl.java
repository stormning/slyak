package com.slyak.group.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.slyak.group.dao.GroupDao;
import com.slyak.group.model.Group;
import com.slyak.group.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;

	@Override
	@Transactional
	public void save(Group group) {
		Long pid = group.getPid();
		String pathPrefix = "|";
		if (pid != null) {
			Group p = groupDao.findOne(pid);
			if (p != null) {
				pathPrefix = p.getPath();
				p.setLeaf(false);
			}
		}
		groupDao.save(group);
		group.setPath(pathPrefix+group.getId()+"|");
		groupDao.save(group);
	}

	@Override
	@Transactional
	public void delete(Long groupId) {
		Group group = groupDao.findOne(groupId);
		if(group==null){
			return;
		}
		Long pid = group.getPid();
		groupDao.deleteByPathLike(group.getPath()+"%");
		
		if(pid!=0){
			Group pgroup = groupDao.findOne(pid);
			if(pgroup!=null){
				List<Group> children = groupDao.findByPid(pid);
				if(CollectionUtils.isEmpty(children)){
					pgroup.setLeaf(true);
					groupDao.save(pgroup);
				}
			}
		}
	}

	@Override
	public List<Group> getRootGroups(String biz, String owner) {
		return groupDao.findByBizAndOwnerAndPidAndLeafFalse(biz,owner,0L);
	}

	@Override
	public Group findOne(Long groupId) {
		return groupDao.findOne(groupId);
	}

	@Override
	public List<Group> getPathGroups(Long groupId) {
		Group last = groupDao.findOne(groupId);
		if(last==null){
			return Collections.emptyList();
		}else{
			String path = last.getPath();
			String[] paths = org.apache.commons.lang.StringUtils.split(path, "|");
			List<Group> pathGroups = null;
			if(paths.length>1){
				pathGroups = new ArrayList<Group>();
				for (int i=0;i<paths.length-1;i++) {
					pathGroups.add(groupDao.findOne(Long.parseLong(paths[i])));
				}
			}else{
				pathGroups = Collections.singletonList(last);
			}
			return pathGroups;
		}
	}

	@Override
	public Group getFirstLeaf(String biz, String owner) {
		return groupDao.findOneByBizAndOwnerAndLeafTrue(biz,owner);
	}

	@Override
	public List<Group> findChildLeavesByPath(String path) {
		return groupDao.findByLeafTrueAndPathStartingWith(path);
	}

	@Override
	public List<Group> getChildren(Long pid) {
		return groupDao.findByPid(pid);
	}

	@Override
	public List<Group> findAll() {
		return groupDao.findAll();
	}

	@Override
	public List<Group> findAllLeaves(String biz, String owner) {
		return groupDao.findByBizAndOwnerAndLeafTrue(biz, owner);
	}

	@Override
	public List<Group> findByIdIn(List<Long> groupIds) {
		return groupDao.findByIdIn(groupIds);
	}
}
