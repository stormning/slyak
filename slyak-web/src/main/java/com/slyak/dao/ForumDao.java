/**
 * Project name : slyak-web
 * File name : ForumDao.java
 * Package name : com.slyak.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.model.Forum;

public interface ForumDao extends JpaRepository<Forum, Long> {

}
