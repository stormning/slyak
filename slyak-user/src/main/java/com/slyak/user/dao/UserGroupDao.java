/**
 * Project name : slyak-user
 * File name : UserGroupDao.java
 * Package name : com.slyak.user.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.user.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.slyak.user.model.UserGroup;

public interface UserGroupDao extends JpaRepository<UserGroup, Long> {

	@QueryHints(value = { @QueryHint(name = org.hibernate.annotations.QueryHints.CACHEABLE, value = "true") })
	List<UserGroup> findByIdUserId(Long userId);

	List<UserGroup> findByIdUserIdIn(Set<Long> userIds);

	@Query("from UserGroup ug,Group g where ug.id.groupId=g.id and ug.id.userId in ?1 and g.biz=?2 and g.owner=?3")
	List<UserGroup> findByIdUserIdInBizAndOwner(Set<Long> userIds, String biz,
			String owner);

}
