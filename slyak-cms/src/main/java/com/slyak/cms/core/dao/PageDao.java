package com.slyak.cms.core.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.slyak.cms.core.model.Page;

public interface PageDao extends JpaRepository<Page, Long> {

	@QueryHints(value={@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true")})
	Page findByAlias(String alias);

	@QueryHints(value={@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true")})
	List<Page> findAll();

	List<Page> findByParentId(Long parentId);

	@Query("select p from Page p,Widget w where p = w.page and w.name=?1")
	Page findByWidgetName(String widgetName);
}
