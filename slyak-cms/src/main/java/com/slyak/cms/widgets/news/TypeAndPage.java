/**
 * Project name : slyak-cms
 * File name : TypeAndPage.java
 * Package name : com.slyak.cms.widgets.news
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.cms.widgets.news;

import com.slyak.cms.core.model.Page;
import com.slyak.group.model.Group;

public class TypeAndPage {

	private Group type;
	private Page page;
	private Page detailPage;
	public Group getType() {
		return type;
	}
	public void setType(Group type) {
		this.type = type;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	public Page getDetailPage() {
		return detailPage;
	}
	public void setDetailPage(Page detailPage) {
		this.detailPage = detailPage;
	}
	@Override
	public String toString() {
		return "TypeAndPage [type=" + type + ", page=" + page + ", detailPage="
				+ detailPage + "]";
	}
}
