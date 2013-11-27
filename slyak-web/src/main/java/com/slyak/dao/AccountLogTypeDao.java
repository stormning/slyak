/**
 * Project name : slyak-web
 * File name : AccountLogTypeDao.java
 * Package name : com.slyak.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
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
