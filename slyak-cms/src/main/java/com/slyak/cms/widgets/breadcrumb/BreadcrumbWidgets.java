/**
 * Project name : slyak-cms
 * File name : BreadcrumbWidgets.java
 * Package name : com.slyak.cms.widgets.breadcrumb
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
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
