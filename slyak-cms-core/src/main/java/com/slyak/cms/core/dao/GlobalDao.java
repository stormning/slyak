package com.slyak.cms.core.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import com.slyak.cms.core.model.Global;

public interface GlobalDao extends JpaRepository<Global, Long> {
	
	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true"))
	List<Global> findAll();

}
