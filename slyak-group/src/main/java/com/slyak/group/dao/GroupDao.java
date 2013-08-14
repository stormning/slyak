package com.slyak.group.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.slyak.group.model.Group;

public interface GroupDao extends JpaRepository<Group, Long> {

	List<Group> findByPid(Long pid);

	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CALLABLE,value="true"))
	List<Group> findByBizAndOwnerAndPidIsNullAndLeafFalse(String biz, String owner);

	Group findOneByBizAndOwnerAndLeafTrue(String biz, String owner);

	@QueryHints(@QueryHint(name=org.hibernate.annotations.QueryHints.CALLABLE,value="true"))
	List<Group> findByLeafTrueAndPathStartingWith(String path);

	List<Group> findByBizAndOwnerAndPidAndLeafFalse(String biz, String owner,Long pid);

	List<Group> findByBizAndOwnerAndLeafTrue(String biz, String owner);

	@Query("delete from Group where path like ?1")
	@Modifying
	void deleteByPathLike(String path);

	List<Group> findByIdIn(List<Long> groupIds);

}
