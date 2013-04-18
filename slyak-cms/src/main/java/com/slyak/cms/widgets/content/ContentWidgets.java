package com.slyak.cms.widgets.content;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;

@Widgets("content")
public class ContentWidgets {

	@Widget(settings={@Setting(key="content",value=""),@Setting(key="content",value="")})
	public String simple(){
		return "simple.tpl";
	}
}
