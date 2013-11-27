/**
 * Project name : slyak-cms-core
 * File name : PageDao.java
 * Package name : com.slyak.cms.core.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.cms.core.dao;

import java.util.List;



import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.slyak.cms.core.model.Page;

public interface PageDao extends JpaRepository<Page, Long> {

	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true"))
	Page findByAlias(String alias);

	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true"))
	List<Page> findAll();

	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true"))
	List<Page> findByParentIdAndShowOrderByRankDesc(Long parentId,boolean show);

	@Query("select p from Page p,Widget w where p.id = w.pageId and w.name=?1")
	Page findByWidgetName(String widgetName);
}
