package com.slyak.cms.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slyak.cms.core.model.Widget;


public interface WidgetDao extends JpaRepository<Widget, Long> {

	List<Widget> findByPageId(Long id);

	@Query("select w from Widget w,in (w.settings) st where st=?3 and index(st)=?2 and w.name=?1")
	List<Widget> findByNameAndSettingsKeyAndSettingsValue(String name,String attrName,String attrValue);

}
