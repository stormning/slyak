package com.slyak.user.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;

import com.slyak.user.model.UserGroup;


public interface UserGroupDao extends JpaRepository<UserGroup, Long>{

	@QueryHints(value={@QueryHint(name=org.hibernate.annotations.QueryHints.CACHEABLE,value="true")})
	List<UserGroup> findByIdUserId(Long userId);

}
