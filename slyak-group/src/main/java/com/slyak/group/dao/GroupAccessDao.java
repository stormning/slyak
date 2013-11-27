/**
 * Project name : slyak-group
 * File name : GroupAccessDao.java
 * Package name : com.slyak.group.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.group.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.group.model.GroupAccess;

public interface GroupAccessDao extends JpaRepository<GroupAccess, Long> {

	List<GroupAccess> findByGroupId(Long groupId);

}
