package com.slyak.cms.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.cms.core.model.Widget;

public interface WidgetDao extends JpaRepository<Widget, Long> {

}
