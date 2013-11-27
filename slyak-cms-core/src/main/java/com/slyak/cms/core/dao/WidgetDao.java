/**
 * Project name : slyak-cms-core
 * File name : WidgetDao.java
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

import com.slyak.cms.core.model.Widget;


public interface WidgetDao extends JpaRepository<Widget, Long> {

	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true"))
	List<Widget> findByPageId(Long id);

	@Query("select w from Widget w,in (w.settings) st where st=?3 and index(st)=?2 and w.name=?1")
	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true"))
	List<Widget> findByNameAndSettingsKeyAndSettingsValue(String name,String attrName,String attrValue);

}
