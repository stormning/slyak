package com.slyak.dao;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import com.slyak.model.Init;

public interface InitDao extends JpaRepository<Init, Long> {
	
	@QueryHints({@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true")}) 
	Init findByUserIdAndItem(Long userId,String name);

}
