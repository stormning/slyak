package com.slyak.standard.service;

import java.util.List;

import com.slyak.standard.model.Location;

public interface StandardService {
	
	List<Location> findByPidAndPinyinStartingWith(long pid, String pinyin);

	List<Location> findByPid(long pid);
	
	List<Location> findPath(long id);
	
	Location findOne(long id);
}
