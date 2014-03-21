/**
 * Project name : slyak-file
 * File name : StaticResourceConfig.java
 * Package name : com.slyak.file.web
 * Date : 2014年2月11日
 * Copyright : 2014 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.file.web;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
public class StaticResourceConfig implements ApplicationContextAware,ServletContextAware,InitializingBean{

	private ServletContext servletContext;
	
	private ApplicationContext applicationContext;

	public void init() {
		SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
		Map<String, HttpRequestHandler> urlMap = new LinkedHashMap<String, HttpRequestHandler>();
		
		//init static resources
		ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
		requestHandler.setLocations(Collections.singletonList(applicationContext.getResource("classpath:/META-INF/static/")));
		requestHandler.setCacheSeconds(31556926);
		requestHandler.setServletContext(servletContext);
		requestHandler.setApplicationContext(applicationContext);
		urlMap.put("/fileResource/**", requestHandler);
		
		ResourceHttpRequestHandler textEditorRequestHandler = new ResourceHttpRequestHandler();
		textEditorRequestHandler.setLocations(Collections.singletonList(applicationContext.getResource("file:///opt/upload/textEditor/")));
		textEditorRequestHandler.setCacheSeconds(31556926);
		textEditorRequestHandler.setServletContext(servletContext);
		textEditorRequestHandler.setApplicationContext(applicationContext);
		urlMap.put("/file/textEditor/**", textEditorRequestHandler);
		
		handlerMapping.setUrlMap(urlMap);
		
		applicationContext.getAutowireCapableBeanFactory().initializeBean(handlerMapping, null);
		
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;  
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
		defaultListableBeanFactory.registerSingleton("fileResourceHandlerMapping", handlerMapping);
		//refresh context
		applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
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

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}
}
