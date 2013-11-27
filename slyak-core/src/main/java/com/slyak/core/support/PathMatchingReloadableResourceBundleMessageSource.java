/**
 * Project name : slyak-core
 * File name : PathMatchingReloadableResourceBundleMessageSource.java
 * Package name : com.slyak.core.support
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class PathMatchingReloadableResourceBundleMessageSource extends
		ReloadableResourceBundleMessageSource {
	
	private static final String PROPERTIES_SUFFIX = "*.properties";
	
	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	@Override
	public void setBasenames(String... basenames) {
		try{
			List<String> resourceNames = new ArrayList<String>(); 
			for (String basename : basenames) {
				Resource[] resources = resourcePatternResolver.getResources(basename+PROPERTIES_SUFFIX);
				for (Resource resource : resources) {
					resourceNames.add(resource.getURI().toString().replace(".properties", ""));
				}
			}
			super.setBasenames(resourceNames.toArray(new String[resourceNames.size()]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
