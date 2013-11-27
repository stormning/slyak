/**
 * Project name : slyak-cms-core
 * File name : WidgetInfo.java
 * Package name : com.slyak.cms.core.support
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.cms.core.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WidgetInfo {
	
	private String region;

	private String name;

	private Object handler;

	private Method method;

	private List<Setting> settings = new ArrayList<Setting>();
	
	private Map<String,String> settingsMap = new HashMap<String, String>();

	private Method onAdd;
	
	private Method onEdit;
	
	private Method onRemove;
	
	private boolean show;
	
	private String[] js;
	
	private String[] css;
	
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

	
	public List<Setting> getSettings() {
		return settings;
	}
	
	public Map<String, String> getSettingsMap() {
		return settingsMap;
	}

	public void addSetting(Setting setting) {
		this.settings.add(setting);
		this.settingsMap.put(setting.getKey(), setting.getValue());
	}
	
	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String[] getJs() {
		return js;
	}

	public void setJs(String[] js) {
		this.js = js;
	}

	public String[] getCss() {
		return css;
	}

	public void setCss(String[] css) {
		this.css = css;
	}
}
