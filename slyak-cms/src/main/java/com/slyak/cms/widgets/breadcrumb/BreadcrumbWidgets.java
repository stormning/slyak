package com.slyak.cms.widgets.breadcrumb;

import com.slyak.cms.core.annotation.Widget;
import com.slyak.cms.core.annotation.Widgets;

@Widgets("breadcrumb")
public class BreadcrumbWidgets {

	@Widget
	public String line() {
		return "line.tpl";
	}
}
