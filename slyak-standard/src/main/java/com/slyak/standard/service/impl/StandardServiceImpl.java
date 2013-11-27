/**
 * Project name : slyak-standard
 * File name : StandardServiceImpl.java
 * Package name : com.slyak.standard.service.impl
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.standard.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.slyak.core.util.PinyinUtils;
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
			for (int i=0;i<pidPath.length-1;i++) {
				result.add(locationDAO.findOne(Long.valueOf(pidPath[i])));
			}
			result.add(location);
			return result;
		}else{
			return Collections.singletonList(location);
		}
	}

	@Override
	public Location findOne(long id) {
		return locationDAO.findOne(id);
	}
	
	public static String loop(Location l,int index ,List<Location> locations){
		String path = l.getPath();
		if(l.getPath() == null){
			long pid = l.getPid();
			if(pid == 0){
				path = "|"+l.getId()+"|";
				l.setPath(path);
			}else{
				path = "";
				for (int i=0;i<locations.size();i++) {
					if(i!=index){
						Location location = locations.get(i);
						if(location.getId() == pid){
							location.setParent(true);
							path = loop(location,i,locations) + l.getId()+"|";
						}
					}
				}
				l.setPath(path);
			}
		}
		return path;
	}
	
	public static void main(String[] args) {
		try {
			File file = ResourceUtils.getFile("classpath:location.json");
			List<String> jsonStrs = FileUtils.readLines(file);
			List<Location> locations = new ArrayList<Location>();
			for (String json : jsonStrs) {
				locations.add(JSON.parseObject(json,Location.class));
			}
			for (int i=0;i<locations.size();i++) {
				loop(locations.get(i),i,locations);
			}
			List<String> lines = new ArrayList<String>();
			for (Location location : locations) {
				String lname = location.getName();
				String py = PinyinUtils.generatePinyin(lname.substring(0, lname.length()-1));
				lines.add("INSERT INTO t_location (id,`name`,parent,path,pid,pinyin) VALUES ("+location.getId()+",'"+location.getName()+"',"+(location.isParent()?1:0)+",'"+location.getPath()+"',"+location.getPid()+",'"+py+"');");
			}
			FileUtils.writeLines(ResourceUtils.getFile("file:///opt/location.sql"), lines);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
