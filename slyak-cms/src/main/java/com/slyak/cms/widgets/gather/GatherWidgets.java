/**
 * Project name : slyak-cms
 * File name : GatherWidgets.java
 * Package name : com.slyak.cms.widgets.gather
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.cms.widgets.gather;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.enums.InputType;
import com.slyak.core.js.JavaScriptHandler;
import com.slyak.core.util.StringUtils;

@Widgets("gather")
public class GatherWidgets{
	
	@Autowired
	private JavaScriptHandler javaScriptHandler;

	@Widget(settings = { @Setting(key = "url", value = "",name="网址"),
			@Setting(key = "width", value = "100%",name="宽度"),
			@Setting(key = "height", value = "100%",name="高度") })
	public String iframe() {
		return "iframe.tpl";
	}

	@Widget(settings = { @Setting(key = "uri", value = "",name="网址"),
			@Setting(key = "method", value = "GET",name="请求方式"),@Setting(key="callback",value="",name="JS回调函数(处理内容并返回)",inputType=InputType.TEXTAREA)})
	public String proxy(com.slyak.cms.core.model.Widget widget, ModelMap modelMap) {
		Map<String,String> settings = widget.getSettings();
		String uri = StringUtils.trimWhitespace(settings.get("uri"));
		if (StringUtils.hasText(uri) && uri.toLowerCase().startsWith("http")) {
			String method = settings.get("method");
			HttpClient client = new DefaultHttpClient();
			client.getParams().setParameter(
					CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
			HttpUriRequest request = null;
			if (!StringUtils.hasText(method) || "get".equalsIgnoreCase(method)) {
				request = new HttpGet(uri);
			} else {
				request = new HttpPost(uri);
			}
			try {
				String result = client.execute(request,new BasicResponseHandler());
				// do basic cleaning
				String callback = StringUtils.trimWhitespace(settings.get("callback"));
				if(StringUtils.hasText(callback)){
					result = (String)javaScriptHandler.evalFunction(callback, result);
				}
				modelMap.put("content", result);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "proxy.tpl";
	}
}
