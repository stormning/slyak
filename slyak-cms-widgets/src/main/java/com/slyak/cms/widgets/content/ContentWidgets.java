package com.slyak.cms.widgets.content;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.enums.InputType;

@Widgets("content")
public class ContentWidgets {

	@Widget(settings={@Setting(key="content",value="",inputType=InputType.TEXTAREA)})
	public String simple(){
		return "simple.tpl";
	}
}
