/*
 * Project:  any
 * Module:   slyak-web
 * File:     UserService.java
 * Modifier: zhouning
 * Modified: 2012-12-8 下午3:43:38 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.user.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.slyak.group.model.Group;
import com.slyak.user.model.User;
import com.slyak.user.util.UserQuery;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-8
 */
public interface UserService {

	void regist(User user);
	
	boolean exist(String eamil);
	
	User getUser(Long userId);

	Page<User> pageUsers(Pageable pageable, UserQuery query);
	
	List<Group> getUserGroups(Long userId);
	
	List<Group> getUserGroups(Long userId,String biz,String owner);
}
