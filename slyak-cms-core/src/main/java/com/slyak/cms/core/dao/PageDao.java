package com.slyak.cms.core.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slyak.cms.core.model.Page;

public interface PageDao extends JpaRepository<Page, Long> {

	Page findByAlias(String alias);

	List<Page> findAll();

	List<Page> findByParentIdAndShowOrderByRankDesc(Long parentId,boolean show);

	@Query("select p from Page p,Widget w where p = w.page and w.name=?1")
	Page findByWidgetName(String widgetName);
}
