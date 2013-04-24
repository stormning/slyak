/*
 * Project:  any
 * Module:   slyak-web
 * File:     UserDao.java
 * Modifier: zhouning
 * Modified: 2012-12-7 下午8:53:06 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.user.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.slyak.user.model.User;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-7
 */
public interface UserDao extends JpaRepository<User, Long> {

	/**
	 * @param email
	 * @return
	 */
	@QueryHints({@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true")}) 
	User findByEmail(String email);

	@Query("select count(u) from User u where u.email=?1")
	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true"))
	long countByEmail(String eamil);

	@Query("select u from User u")
	List<User> listUsers(Pageable pageable);

}
