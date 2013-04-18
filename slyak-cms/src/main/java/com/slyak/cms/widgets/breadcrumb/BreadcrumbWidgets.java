package com.slyak.cms.widgets.breadcrumb;

import com.slyak.cms.core.annotation.Setting;
import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;

@Widgets("breadcrumb")
public class BreadcrumbWidgets {

	@Widget(settings = { @Setting(key = "", value = "") })
	public String line() {
		return "line.tpl";
	}
}
