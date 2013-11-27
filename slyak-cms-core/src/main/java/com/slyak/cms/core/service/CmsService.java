/**
 * Project name : slyak-cms-core
 * File name : CmsService.java
 * Package name : com.slyak.cms.core.service
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.cms.core.service;

import java.io.IOException;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import com.slyak.cms.core.model.Global;
import com.slyak.cms.core.model.Page;
import com.slyak.cms.core.model.Widget;

public interface CmsService {

	String ALIAS_CACHE = "ALIAS_CACHE";
	String ID_CACHE = "ID_CACHE";
	String ROOT_CACHE = "ROOT_CACHE";
	
	String PAGE_WIDGET="PAGE_WIDGET";
	
	String GLOBAL = "GLOBAL";
	
	
	@Cacheable(ALIAS_CACHE)
	Page findPageByAlias(String alias);
	
	Widget findWidgetById(Long widgetId);

	@CacheEvict(value={PAGE_WIDGET},allEntries=true)
	@Transactional
	void updateWidgets(List<Widget> widgets, boolean ignoreNullValue);

	@CacheEvict(value={ALIAS_CACHE,ID_CACHE,ROOT_CACHE},allEntries=true)
	@Transactional
	void savePage(Page page);

	@Cacheable(ROOT_CACHE)
	List<Page> findRootPages();

	@Cacheable(ID_CACHE)
	Page findPageById(Long pageId);

	@CacheEvict(value={ALIAS_CACHE,ID_CACHE,ROOT_CACHE},allEntries=true)
	@Transactional
	void removePageById(Long id);

	@CacheEvict(value={ALIAS_CACHE,ID_CACHE,ROOT_CACHE,PAGE_WIDGET},allEntries=true)
	@Transactional
	void changePageLayout(Page page, String newLayout);

	@CacheEvict(value=PAGE_WIDGET,allEntries=true)
	@Transactional
	void saveWidget(Widget widget);

	Page findPageByWidgetName(String string);

	@CacheEvict(value=PAGE_WIDGET,allEntries=true)
	@Transactional
	void removeWidgetById(Long widgetId);

	@CacheEvict(value=GLOBAL,allEntries=true)
	@Transactional
	void saveGlobal(Global golbel) throws IOException;
	
	@Cacheable(GLOBAL)
	Global findGlobal();

	@Cacheable(PAGE_WIDGET)
	List<Widget> findWidgetsByPageId(Long id);

	@CacheEvict(value=PAGE_WIDGET,allEntries=true)
	@Transactional
	void saveWidgets(List<Widget> newWidgets);

	List<com.slyak.cms.core.model.Widget> findWidgetsByNameAndAttribute(
			String name, String attrName, String attrValue);

}
