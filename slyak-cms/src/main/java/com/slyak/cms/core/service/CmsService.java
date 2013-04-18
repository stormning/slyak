package com.slyak.cms.core.service;

import java.io.IOException;
import java.util.List;

import com.slyak.cms.core.model.Global;
import com.slyak.cms.core.model.Page;
import com.slyak.cms.core.model.Widget;

public interface CmsService {

	Page findPageByAlias(String alias);

	Widget findWidgetById(Long widgetId);

	void updateWidgets(List<Widget> widgets, boolean ignoreNullValue);

	void savePage(Page page);

	List<Page> findRootPages();

	Page findPageById(Long pageId);

	void removePageById(Long id);

	void changePageLayout(Page page, String newLayout);

	void saveWidget(Widget widget);

	Page findPageByWidgetName(String string);

	void removeWidgetById(Long widgetId);

	void saveGlobal(Global golbel) throws IOException;
	
	Global findGlobal();

}
