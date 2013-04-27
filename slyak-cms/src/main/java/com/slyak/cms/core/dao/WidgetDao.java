package com.slyak.cms.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.cms.core.model.Widget;


public interface WidgetDao extends JpaRepository<Widget, Long> {

	List<Widget> findByPageId(Long id);

}
