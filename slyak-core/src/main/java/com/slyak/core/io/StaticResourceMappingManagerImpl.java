/**
 * Project name : slyak-core
 * File name : StaticResourceMappingManagerImpl.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

public class StaticResourceMappingManagerImpl implements StaticResourceMappingManager,
		ServletContextAware, ApplicationContextAware, InitializingBean {
	private ServletContext servletContext;

	private ApplicationContext applicationContext;

	private static final String DEFAULT_UPLOAD_PATH = "/WEB-INF/upload";

	private int cacheSeconds = 31556926;

	private String uploadPath = DEFAULT_UPLOAD_PATH;
	
	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	@Override
	public String getUploadPath() {
		return uploadPath;
	}
	
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	@Override
	public String getHttpPathPrefix() {
		return "/file";
	}

	private void initStaticResourceHandlerMapping() {
		Map<String, HttpRequestHandler> urlMap = new LinkedHashMap<String, HttpRequestHandler>();
		ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
		//file system
		requestHandler.setLocations(Collections.singletonList(resourceLoader.getResource(StringUtils.cleanPath(uploadPath) + File.separator)));
		requestHandler.setCacheSeconds(cacheSeconds);
		requestHandler.setServletContext(servletContext);
		requestHandler.setApplicationContext(applicationContext);
		urlMap.put(getHttpPathPrefix()+"/**", requestHandler);

		SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
		handlerMapping.setUrlMap(urlMap);
		applicationContext.getAutowireCapableBeanFactory().initializeBean(
				handlerMapping, null);
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext
				.getBeanFactory();
		defaultListableBeanFactory.registerSingleton(
				"fileResourceHandlerMapping", handlerMapping);

		// refresh context
		applicationContext.publishEvent(new ContextRefreshedEvent(
				applicationContext));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initStaticResourceHandlerMapping();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public Resource getRealPathByBizAndOwner(String biz, String owner) {
		String path = StringUtils.cleanPath(getUploadPath()+File.separator+biz+com.slyak.core.util.StringUtils.devidePath(owner,File.separator));
		Resource resource = resourceLoader.getResource(path);
		try {
			if(!resource.exists()){
				FileUtils.forceMkdir(resource.getFile());
			}
			return resource;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getHttpPathByBizAndOwner(String biz, String owner) {
		return getHttpPathPrefix()+"/"+biz+com.slyak.core.util.StringUtils.devidePath(owner,"/");
	}
	
	
	public static void main(String[] args) {
		String owner = "123456";
		int len = owner.length();
		int start = 0;
		StringBuffer sb = new StringBuffer();
		int step = 2;
		while(start<len){
			if(len-start<step){
				step = len-start;
			}
			sb.append("/"+owner.substring(start,start+step));
			start+=2;
		}
		System.out.println(sb.toString());
	}

	/* (non-Javadoc)
	 * @see com.slyak.core.io.StaticResourceMappingManager#getResourceByPath(java.lang.String)
	 */
	@Override
	public Resource getResourceByPath(String path) {
		return resourceLoader.getResource(StringUtils.cleanPath(getUploadPath()+path.replaceFirst(getHttpPathPrefix(), "")));
	}
	
}
