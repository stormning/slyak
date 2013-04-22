package com.slyak.cms.widgets.gather;

import java.io.IOException;

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
import com.slyak.cms.core.model.Settings;
import com.slyak.core.js.JavaScriptHandler;
import com.slyak.core.util.StringUtils;

@Widgets("gather")
public class GatherWidgets{
	
	@Autowired
	private JavaScriptHandler javaScriptHandler;

	@Widget(settings = { @Setting(key = "url", value = ""),
			@Setting(key = "width", value = "100%"),
			@Setting(key = "height", value = "100%") })
	public String iframe() {
		return "iframe.tpl";
	}

	@Widget(settings = { @Setting(key = "uri", value = ""),
			@Setting(key = "method", value = "GET"),@Setting(key="callback",value="",inputType=InputType.TEXTAREA)})
	public String proxy(Settings settings, ModelMap modelMap) {
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
