/**
 * Project name : slyak-standard
 * File name : LocationDAO.java
 * Package name : com.slyak.standard.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.standard.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.standard.model.Location;

public interface LocationDAO extends JpaRepository<Location, Long>{

	List<Location> findByPidAndPinyinStartingWith(long pid, String pinyin);

	List<Location> findByPid(long pid);

}
