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

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slyak.user.dao.UserDao;
import com.slyak.user.model.User;
import com.slyak.user.service.UserService;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-14
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void regist(User user) {
		cleanBeforeRegist(user);
		//the same as shiro.xml
		Sha256Hash hash = new Sha256Hash(user.getPassword(), null, 1024);
		user.setPassword(hash.toBase64());
		userDao.save(user);
	}
	
	private void cleanBeforeRegist(User user){
		user.setId(null);
		user.setSalt(null);
	}

	@Override
	public boolean exist(String eamil) {
		return userDao.countByEmail(eamil)>0;
	}

	@Override
	public User getUser(Long userId) {
		return userDao.findOne(userId);
	}

}
