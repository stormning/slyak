package com.slyak.cms.core.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;

import com.slyak.cms.core.dao.GlobalDao;
import com.slyak.cms.core.dao.PageDao;
import com.slyak.cms.core.dao.WidgetDao;
import com.slyak.cms.core.model.Global;
import com.slyak.cms.core.model.Page;
import com.slyak.cms.core.model.Widget;
import com.slyak.cms.core.service.CmsService;
import com.slyak.core.io.FileUtils;
import com.slyak.core.util.BeanUtils;


@Service
public class CmsServiceImpl implements CmsService,InitializingBean,ServletContextAware {
	
	private static final String GLOBAL_CSS_LOCATION="/static/css/global.css";
	private static final String GLOBAL_JSP_LOCATION="/WEB-INF/views/global.jsp";
	
	
	@Autowired
	private PageDao pageDao;
	
	@Autowired
	private WidgetDao widgetDao;
	
	@Autowired
	private GlobalDao globalDao;
	private ServletContext servletContext;
	
	private static final String DEFAULT_LAYOUT="1-1.tpl";
	
	@Override
	public Page findPageByAlias(String alias) {
		return pageDao.findByAlias(alias);
	}

	@Override
	public Widget findWidgetById(Long widgetId) {
		return widgetDao.findOne(widgetId);
	}

	@Override
	public void updateWidgets(List<Widget> widgets, boolean ignoreNullValue) {
		List<Widget> updateList = new ArrayList<Widget>();
		for (Widget widget : widgets) {
			Widget stored = widgetDao.findOne(widget.getId());
			if(stored!=null){
				if(ignoreNullValue){
					BeanUtils.copyNotNullProperties(widget, stored, null, null);
				}else{
					BeanUtils.copyProperties(widget, stored);
				}
				updateList.add(stored);
			}
		}
		widgetDao.save(updateList);
	}

	@Override
	public void savePage(Page page) {
		Page pageToSave;
		if(page.getId()== null){
			pageToSave = page;
		}else {
			pageToSave = pageDao.findOne(page.getId());
			BeanUtils.copyNotNullProperties(page, pageToSave, null, null);
		}
		String alias = StringUtils.trimAllWhitespace(page.getAlias());
		boolean emptyAlias = StringUtils.isEmpty(alias);
		if(!emptyAlias){
			pageToSave.setAlias(alias);
		}
		if(StringUtils.isEmpty(pageToSave.getLayout())){
			pageToSave.setLayout(DEFAULT_LAYOUT);
		}
		pageDao.save(pageToSave);
		
		if(emptyAlias){
			pageToSave.setAlias(page.getId().toString());
			pageDao.save(pageToSave);
		}
		
		String name = StringUtils.trimAllWhitespace(page.getName());
		boolean emptyName = StringUtils.isEmpty(name);
		if(emptyName){
			pageToSave.setName(pageToSave.getAlias());
			pageDao.save(pageToSave);
		}
		
	}
	
	@Override
	public void removePageById(Long id) {
		Page page = pageDao.findOne(id);
		
		Page parent = page.getParent();
		if(parent!=null){
			parent.getChildren().remove(page);
		}
		
		List<Widget> widgets = findWidgetsByPageId(id);
		if(!CollectionUtils.isEmpty(widgets)){
			widgetDao.delete(findWidgetsByPageId(id));
		}
		pageDao.delete(page);
	}

	@Override
	public List<Page> findRootPages() {
		return pageDao.findByParentIdAndShowOrderByRankDesc(null,true);
	}

	@Override
	public Page findPageById(Long pageId) {
		return pageDao.findOne(pageId);
	}

	@Override
	public void changePageLayout(Page page, String newLayout) {
		if(!page.getLayout().equals(newLayout)){
			String[] newRows = newLayout.replace("_", "-").split("-");
			String[] oldRows = page.getLayout().replace("_", "-").split("-");
			if(newRows.length<oldRows.length){
				//update widgets' container
				int maxIndex = newRows.length-1;
				List<Widget> widgets = /*page.getWidgets()*/findWidgetsByPageId(page.getId());
				for (Widget widget : widgets) {
					String container = widget.getContainer();
					//skip 'row'
					int containerIndex = NumberUtils.parseNumber(container.substring(3), Integer.class) ;
					if(containerIndex > maxIndex){
						widget.setContainer("row"+maxIndex);
						widgetDao.save(widget);
					}
				}
			}
			page.setLayout(newLayout);
			pageDao.save(page);
		}
	}

	@Override
	public void saveWidget(Widget widget) {
		widgetDao.save(widget);
	}

	@Override
	public Page findPageByWidgetName(String widgetName) {
		return pageDao.findByWidgetName(widgetName);
	}

	@Override
	public void removeWidgetById(Long widgetId) {
		widgetDao.delete(widgetId);
	}

	@Override
	@Transactional
	public synchronized void saveGlobal(Global golbel) throws IOException {
		Global globalToSave = findGlobal();
		if(globalToSave == null){
			globalToSave = golbel;
		} else{
			BeanUtils.copyProperties(golbel, globalToSave, new String[]{"id","ver"});
			globalToSave.setVer(globalToSave.getVer()+1);
		}
		globalDao.save(globalToSave);
		FileUtils.writeStringToFile(globalToSave.getCss(), GLOBAL_CSS_LOCATION, servletContext);
		FileUtils.writeStringToFile(globalToSave.getJsp(), GLOBAL_JSP_LOCATION, servletContext);
	}

	@Override
	public Global findGlobal() {
		List<Global> golbels= globalDao.findAll();
		if(!CollectionUtils.isEmpty(golbels)){
			return golbels.get(0);
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Global global = findGlobal();
		if(global == null){
			global = new Global();
			global.setCss(FileUtils.readFileToString(GLOBAL_CSS_LOCATION, servletContext));
			global.setJsp(FileUtils.readFileToString(GLOBAL_JSP_LOCATION, servletContext));
			saveGlobal(global);
		} else{
			FileUtils.writeStringToFile(global.getCss(), GLOBAL_CSS_LOCATION, servletContext);
			FileUtils.writeStringToFile(global.getJsp(), GLOBAL_JSP_LOCATION, servletContext);
		}
		
		//compress js and css
		List<Page> pages = findRootPages();
		if(CollectionUtils.isEmpty(pages)){
			
		}
		
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public List<Widget> findWidgetsByPageId(Long id) {
		return widgetDao.findByPageId(id);
	}

	@Override
	public void saveWidgets(List<Widget> newWidgets) {
//		for (Widget widget : newWidgets) {
//			pageDao.save(findPageById(widget.getPageId()));
//		}
		widgetDao.save(newWidgets);
	}

	@Override
	public List<Widget> findWidgetsByNameAndAttribute(String name,
			String attrName, String attrValue) {
		return widgetDao.findByNameAndSettingsKeyAndSettingsValue(name,attrName,attrValue);
	}
}
