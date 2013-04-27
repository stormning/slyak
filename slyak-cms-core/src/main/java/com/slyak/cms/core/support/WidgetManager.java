package com.slyak.cms.core.support;

import java.net.URL;
import java.util.Map;

import freemarker.template.Configuration;

public interface WidgetManager {

	Configuration getConfiguration(Object handler);
	
	WidgetInfo getWidgetInfo(String region,String widgetName);
	
	Object getRegionHandler(String region);
	
	Map<String,Map<String,WidgetInfo>> getWidgetInfos();
	
	void addRemoteWidgets(URL... urls);
}
