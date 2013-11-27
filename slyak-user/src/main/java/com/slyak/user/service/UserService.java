/**
 * Project name : slyak-user
 * File name : UserService.java
 * Package name : com.slyak.user.service
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	Map<Long,List<Group>> getUsersGroups(Set<Long> userIds);
	
	Map<Long,List<Group>> getUsersGroups(Set<Long> userIds,String biz,String owner);
}
