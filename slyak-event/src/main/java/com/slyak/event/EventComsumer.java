package com.slyak.event;

public interface EventComsumer {

	void resolve(String json) throws Exception;
	
	boolean accept(String topic);
}
