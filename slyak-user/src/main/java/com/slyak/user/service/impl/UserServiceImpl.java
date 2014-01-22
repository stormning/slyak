/**
 * Project name : slyak-user
 * File name : UserServiceImpl.java
 * Package name : com.slyak.user.service.impl
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.user.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.slyak.core.util.BeanUtils;
import com.slyak.core.web.OffsetLimitRequest;
import com.slyak.group.model.Group;
import com.slyak.group.service.GroupService;
import com.slyak.user.dao.UserDao;
import com.slyak.user.dao.UserExtDao;
import com.slyak.user.dao.UserGroupDao;
import com.slyak.user.model.User;
import com.slyak.user.model.UserGroup;
import com.slyak.user.model.UserGroupPK;
import com.slyak.user.service.UserService;
import com.slyak.user.util.Status;
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
		prepareUser(user);
		user.setPassword(generatePassword(user.getPassword()));
		userDao.save(user);
	}
	
	@Override
	public void update(User user) {
		User target = getUser(user.getId());
		if(user.getPassword()!=null) {
			user.setPassword(generatePassword(user.getPassword()));
		}
		BeanUtils.copyNotNullProperties(user, target, null, null);
		userDao.save(target);
	}
	
	private String generatePassword(String password) {
		return new Sha256Hash(password, null, 1024).toBase64();
	}



	private void prepareUser(User user) {
		user.setId(null);
		user.setSalt(null);
		user.setCreateAt(new Date());
		user.setStatus(Status.ENABLED);
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
	

	@Override
	public void toggleStatus(Long userId) {
		User user = userDao.findOne(userId);
		user.setStatus(Status.values()[1-user.getStatus().ordinal()]);
	}
	
	@Override
	public List<User> recentRegist(int fetchSize) {
		OffsetLimitRequest olr = new OffsetLimitRequest(0,fetchSize);
		return userDao.findAllOrderByCreateAt(olr);
	}

	/* (non-Javadoc)
	 * @see com.slyak.user.service.UserService#countRealUser()
	 */
	@Override
	public long countRealUser() {
		return userDao.countByFake(false);
	}

	/* (non-Javadoc)
	 * @see com.slyak.user.service.UserService#randomFakeUser()
	 */
	@Override
	public User randomFakeUser() {
		long total = countRealUser();
		if(total>0){
			int offset = (int)Math.floor(Math.random()*total);
			Page<User> userPage = userDao.findAll(new OffsetLimitRequest(offset, 1));
			return userPage.getContent().get(0);
		}
		return null;
	}
	
	@Override
	public User generateFakeUser(String password) {
		User user = new User();
		user.setPassword(password);
		user.setFake(true);
		user.setEmail(UUID.randomUUID().toString());
		user.setNickName(com.slyak.core.util.StringUtils.randomName()+com.slyak.core.util.StringUtils.randomName());
		regist(user);
		return user;
	}

	/* (non-Javadoc)
	 * @see com.slyak.user.service.UserService#getFakeUser()
	 */
	@Override
	public User findFakeUser() {
		long count = userDao.countByFake(true);
		if(count>0) {
			int offset = (int)Math.floor(Math.random()*count);
			return userDao.findByFake(new OffsetLimitRequest(offset, 1), true).getContent().get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.slyak.user.service.UserService#getUsersByIds(java.util.Set)
	 */
	@Override
	public Map<Long, User> getUsersByIds(Set<Long> userIds) {
		List<User> users = userDao.findByIdIn(userIds);
		if(!CollectionUtils.isEmpty(users)) {
			Map<Long, User> result = new HashMap<Long, User>();
			for (User user : users) {
				result.put(user.getId(), user);
			}
			return result;
		}
		return Collections.emptyMap();
		
	}
}
