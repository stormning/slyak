/**
 * Project name : slyak-core
 * File name : SlyakContextLoaderListener.java
 * Package name : com.slyak.core.web
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.web;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.ContextLoaderListener;

public class SlyakContextLoaderListener extends ContextLoaderListener {
	private DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
	
	private static final String SPRING_PROFILE_ACTIVE="spring.profiles.active";
	private static final String SPRING_PROFILE_ACTIVE_DEFAULT="test";
	private static final String CONFIG_FILE_LOCATION="classpath:config.properties";
	

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Resource resource = defaultResourceLoader.getResource(CONFIG_FILE_LOCATION);
		String activeProfile = SPRING_PROFILE_ACTIVE_DEFAULT;
		if(resource!=null){
			try {
				Properties ps = PropertiesLoaderUtils.loadProperties(resource);
				activeProfile = ps.getProperty(SPRING_PROFILE_ACTIVE, SPRING_PROFILE_ACTIVE_DEFAULT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.setProperty(SPRING_PROFILE_ACTIVE, activeProfile);
		super.contextInitialized(event);
	}

	
}
