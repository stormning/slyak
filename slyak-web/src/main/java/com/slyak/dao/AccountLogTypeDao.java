/*
 * Project:  any
 * Module:   slyak-web
 * File:     AccountLogTypeDao.java
 * Modifier: zhouning
 * Modified: 2012-12-7 下午8:51:30 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.slyak.model.AccountLog;
import com.slyak.model.AccountLogType;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-7
 */
public interface AccountLogTypeDao extends JpaRepository<AccountLogType, Long> {

	/**
	 * @param object
	 * @return
	 */
	List<AccountLogType> findByParentIdAndUserId(Long parentId,Long userId);

	@Query("from AccountLogType lt where lt.id=?1 and lt.user.id=?2")
	AccountLogType findByIdAndUserId(Long logType, Long userId);

	@Query(value = "from AccountLog l order by l.id desc", countQuery="from AccountLog l")
	Page<AccountLog> findRecentSharedLogsPage(Pageable pageable);

	@Query("from AccountLog l order by l.id desc")
	List<AccountLog> findRecentSharedLogs(Pageable pageable);

	List<AccountLogType> findByParentIdAndShare(Long pid,boolean share);

}
