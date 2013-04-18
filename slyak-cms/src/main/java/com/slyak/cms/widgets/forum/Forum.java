package com.slyak.cms.widgets.forum;

import org.springframework.ui.ModelMap;

import com.slyak.cms.core.annotation.Widgets;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.model.Settings;

@Widgets("forum")
public class Forum {

	@Widget(settings={@Setting(key="size",value="10"),@Setting(key="style",value="11")})
	public String list(Settings settings,ModelMap modelMap){
		modelMap.put("list", "this is forum list");
		return "list.tpl";
	}
	
}
