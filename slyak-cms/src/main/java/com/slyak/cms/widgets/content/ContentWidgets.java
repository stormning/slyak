/**
 * Project name : slyak-cms
 * File name : ContentWidgets.java
 * Package name : com.slyak.cms.widgets.content
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.cms.widgets.content;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.enums.InputType;

@Widgets("content")
public class ContentWidgets {

	@Widget(settings={@Setting(key="content",value="",name="内容",inputType=InputType.TEXTAREA)})
	public String simple(){
		return "simple.tpl";
	}
}
