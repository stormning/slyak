/**
 * Project name : slyak-standard
 * File name : StandardService.java
 * Package name : com.slyak.standard.service
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.standard.service;

import java.util.List;

import com.slyak.standard.model.Location;

public interface StandardService {
	
	List<Location> findByPidAndPinyinStartingWith(long pid, String pinyin);

	List<Location> findByPid(long pid);
	
	List<Location> findPath(long id);
	
	Location findOne(long id);
}
