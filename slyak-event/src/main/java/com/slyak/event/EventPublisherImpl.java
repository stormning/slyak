/**
 * Project name : slyak-event
 * File name : EventPublisherImpl.java
 * Package name : com.slyak.event
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.event;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class EventPublisherImpl implements EventPublisher,ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	private ExecutorService executorService = Executors.newFixedThreadPool(20);
	
	@Override
	public void asyncPublish(final String topicId, final String json) {
		Map<String, EventComsumer> comsumers= applicationContext.getBeansOfType(EventComsumer.class);
		for (final EventComsumer comsumer : comsumers.values()) {
			if(comsumer.accept(topicId)){
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						try{
							comsumer.resolve(json);
						} catch (Exception e){
							//TODO add to failed list
						}
					}
				});
			}
		}
	}

	@Override
	public void publish(String topicId, String json) {
		Map<String, EventComsumer> comsumers= applicationContext.getBeansOfType(EventComsumer.class);
		for (final EventComsumer comsumer : comsumers.values()) {
			if(comsumer.accept(topicId)){
				try {
					comsumer.resolve(json);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
