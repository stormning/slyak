/**
 * Project name : slyak-user
 * File name : UserExtDao.java
 * Package name : com.slyak.user.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.user.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.slyak.user.model.User;
import com.slyak.user.util.UserQuery;

public interface UserExtDao {

	Page<User> pageUsers(Pageable pageable, UserQuery query);

}
