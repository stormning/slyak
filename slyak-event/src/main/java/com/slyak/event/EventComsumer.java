/**
 * Project name : slyak-event
 * File name : EventComsumer.java
 * Package name : com.slyak.event
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.event;

public interface EventComsumer {

	void resolve(String json) throws Exception;
	
	boolean accept(String topic);
}
