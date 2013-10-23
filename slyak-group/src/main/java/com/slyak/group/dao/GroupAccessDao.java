package com.slyak.group.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.group.model.GroupAccess;

public interface GroupAccessDao extends JpaRepository<GroupAccess, Long> {

	List<GroupAccess> findByGroupId(Long groupId);

}
