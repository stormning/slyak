/*
 * Project:  any
 * Module:   slyak-web
 * File:     UserServiceImpl.java
 * Modifier: zhouning
 * Modified: 2012-12-14 下午3:35:16 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.slyak.group.model.Group;
import com.slyak.group.service.GroupService;
import com.slyak.user.dao.UserDao;
import com.slyak.user.dao.UserExtDao;
import com.slyak.user.dao.UserGroupDao;
import com.slyak.user.model.User;
import com.slyak.user.model.UserGroup;
import com.slyak.user.model.UserGroupPK;
import com.slyak.user.service.UserService;
import com.slyak.user.util.UserQuery;

/**
 * .
 * <p/>
 * 
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-14
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserExtDao userExtDao;

	@Autowired
	private UserGroupDao userGroupDao;

	@Autowired
	private GroupService groupService;

	private ThreadLocal<List<Group>> userGroupsContext = new ThreadLocal<List<Group>>();

	@Override
	public void regist(User user) {
		cleanBeforeRegist(user);
		// the same as shiro.xml
		Sha256Hash hash = new Sha256Hash(user.getPassword(), null, 1024);
		user.setPassword(hash.toBase64());
		userDao.save(user);
	}

	private void cleanBeforeRegist(User user) {
		user.setId(null);
		user.setSalt(null);
	}

	@Override
	public boolean exist(String eamil) {
		return userDao.countByEmail(eamil) > 0;
	}

	@Override
	public User getUser(Long userId) {
		return userDao.findOne(userId);
	}

	@Override
	public Page<User> pageUsers(Pageable pageable, UserQuery query) {
		return userExtDao.pageUsers(pageable, query);
	}

	@Override
	public List<Group> getUserGroups(Long userId) {
		return getUserAllGroups(userId);
	}

	@Override
	public List<Group> getUserGroups(Long userId, final String biz,
			final String owner) {
		List<Group> gs = getUserAllGroups(userId);
		if (gs == null) {
			return Collections.emptyList();
		} else {
			List<Group> gsFiltered = new ArrayList<Group>();
			for (Group g : gs) {
				if (!(StringUtils.equalsIgnoreCase(biz, g.getBiz()) && StringUtils
						.endsWithIgnoreCase(owner, g.getOwner()))) {
					gsFiltered.add(g);
				}
			}
			return gsFiltered;
		}
	}

	private List<Group> getUserAllGroups(Long userId) {
		List<Group> groups = userGroupsContext.get();
		if ((groups = userGroupsContext.get()) == null) {
			List<UserGroup> ugs = userGroupDao.findByIdUserId(userId);
			if (CollectionUtils.isEmpty(ugs)) {
				groups = Collections.emptyList();
			} else {
				groups = new ArrayList<Group>();
				for (UserGroup ug : ugs) {
					groups.add(groupService.findOne(ug.getId().getGroupId()));
				}
				userGroupsContext.set(groups);
			}
		}
		return groups;
	}

	@Override
	public Map<Long, List<Group>> getUsersGroups(Set<Long> userIds) {
		return convertToUsersGroups(userGroupDao.findByIdUserIdIn(userIds));
	}

	private Map<Long, List<Group>> convertToUsersGroups(List<UserGroup> ugs) {
		if (CollectionUtils.isEmpty(ugs)) {
			return Collections.emptyMap();
		} else {
			Map<Long, List<Group>> usgs = new HashMap<Long, List<Group>>();
			for (UserGroup ug : ugs) {
				UserGroupPK ugp = ug.getId();
				Long uid = ugp.getUserId();
				List<Group> gs = usgs.get(uid);
				if (gs == null) {
					gs = new ArrayList<Group>();
					usgs.put(uid, gs);
				}
				gs.add(groupService.findOne(ugp.getGroupId()));
			}
			return usgs;
		}
	}

	@Override
	public Map<Long, List<Group>> getUsersGroups(Set<Long> userIds, String biz,
			String owner) {
		return convertToUsersGroups(userGroupDao.findByIdUserIdInBizAndOwner(
				userIds, biz, owner));
	}

}
