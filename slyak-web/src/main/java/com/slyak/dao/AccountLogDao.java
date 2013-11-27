/**
 * Project name : slyak-web
 * File name : AccountLogDao.java
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
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.slyak.model.AccountLog;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-7
 */
public interface AccountLogDao extends JpaRepository<AccountLog, Long> {

	/**
	 * @param pageable
	 * @param logType
	 */
	Page<AccountLog> findByAccountLogTypeId(Pageable pageable, long logType);

	Page<AccountLog> findByAccountLogTypeIdAndUserId(Pageable pageable, long logType,
			long userId);

	Page<AccountLog> findByUserId(Pageable pageable, long userId);

	Page<AccountLog> findByAccountLogTypePathLikeAndUserId(Pageable pageable,
			String pathLike, long userId);

	@Modifying
	@Query("update AccountLog al set al.accountLogType=null where al.accountLogType.id in ?1")
	void clearRemovedTypeIds(List<Long> tids);

	@Modifying
	@Query("update AccountLog al set al.accountLogMember=null where al.accountLogMember.id in ?1")
	void clearRemovedMemberIds(List<Long> mids);

}
