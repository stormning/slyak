package com.slyak.standard.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slyak.standard.dao.LocationDAO;
import com.slyak.standard.model.Location;
import com.slyak.standard.service.StandardService;

@Service
public class StandardServiceImpl implements StandardService {
	
	@Autowired
	private LocationDAO locationDAO;
	
	@Override
	public List<Location> findByPidAndPinyinStartingWith(long pid, String pinyin) {
		return locationDAO.findByPidAndPinyinStartingWith(pid,pinyin);
	}

	@Override
	public List<Location> findByPid(long pid) {
		return locationDAO.findByPid(pid);
	}

	@Override
	public List<Location> findPath(long id) {
		Location location = locationDAO.findOne(id);
		String[] pidPath = StringUtils.split(location.getPath(),"|");
		if(pidPath!=null&&pidPath.length>0){
			List<Location> result = new ArrayList<Location>();
			result.add(location);
			for (int i=0;i<pidPath.length-1;i++) {
				result.add(locationDAO.findOne(Long.valueOf(pidPath[i])));
			}
			return result;
		}else{
			return Collections.singletonList(location);
		}
	}

	@Override
	public Location findOne(long id) {
		return locationDAO.findOne(id);
	}
}
