package com.slyak.cms.core.model;

import java.util.HashMap;
import java.util.Map;

public class Settings extends HashMap<String, String>{
	
	private static final long serialVersionUID = 1L;
	
	public Settings(Map<String, String> settings) {
		this.putAll(settings);
	}
}
