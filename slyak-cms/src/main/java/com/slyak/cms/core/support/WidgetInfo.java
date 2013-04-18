package com.slyak.cms.core.support;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class WidgetInfo {
	
	private String region;

	private String name;

	private Object handler;

	private Method method;

	private Map<String, String> settings;

	private Method onAdd;
	
	private Method onEdit;
	
	private Method onRemove;
	
	private boolean show;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Object getHandler() {
		return handler;
	}

	public void setHandler(Object handler) {
		this.handler = handler;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Map<String, String> getSettings() {
		return this.settings;
	}

	public void addSetting(String key, String value) {
		if (settings == null) {
			settings = new HashMap<String, String>();
		}
		settings.put(key, value);
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Method getOnAdd() {
		return onAdd;
	}

	public void setOnAdd(Method onAdd) {
		this.onAdd = onAdd;
	}
	
	public Method getOnEdit() {
		return onEdit;
	}

	public void setOnEdit(Method onEdit) {
		this.onEdit = onEdit;
	}

	public Method getOnRemove() {
		return onRemove;
	}

	public void setOnRemove(Method onRemove) {
		this.onRemove = onRemove;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
}
