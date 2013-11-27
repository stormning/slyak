/**
 * Project name : slyak-event
 * File name : EventPublisher.java
 * Package name : com.slyak.event
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.event;

public interface EventPublisher {

	void asyncPublish(String topicId,String json);
	
	void publish(String topicId,String json);
	
}
