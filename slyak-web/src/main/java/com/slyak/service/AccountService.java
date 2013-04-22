/*
 * Project:  any
 * Module:   slyak-web
 * File:     AccountService.java
 * Modifier: zhouning
 * Modified: 2012-12-8 下午3:43:47 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.service;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.slyak.model.AccountLog;
import com.slyak.model.AccountLogMember;
import com.slyak.model.AccountLogType;
import com.slyak.model.Report;
import com.slyak.user.model.User;


/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-8
 */
public interface AccountService {

	/**
	 * @param pageable
	 * @param logType
	 * @param userId
	 */
	Page<AccountLog> findAccountLogs(Pageable pageable, long logType, long userId);

	/**
	 * @return
	 */
	List<AccountLogType> findRootAccountLogTypes(long userId);

	/**
	 * @param logType
	 * @param userId
	 * @return
	 */
	AccountLogType findAccountLogType(Long logType,Long userId);

	Page<AccountLog> findRecentSharedLogsPage(Pageable pageable);

	List<AccountLog> findRecentSharedLogs(int offset ,int limit);

	List<User> findMostActiveUsers(int offset, int limit);

	void saveAccountLog(AccountLog accountLog);

	void saveAccountLogType(AccountLogType accountLogType);

	List<Report> getLogReportByParentLogTypeIdGroupByMember(Long userId, Long parentLogTypeId, Date from,Date to, boolean dateByMonth);

	List<Report> getLogReportByParentLogTypeIdGroupByType(Long userId, Long parentTypeId, Date from,Date to, boolean dateByMonth);

	AccountLogMember findAccountLogMember(long ownerId);

	AccountLogType findAccountLogType(Long userId);

	void deleteTypes(List<AccountLogType> accountLogTypes);

	void saveAccountLogMember(AccountLogMember accountLogMember);

	void deleteMembers(List<AccountLogMember> accountLogMembers);

	List<AccountLogMember> getUserAccountLogMembers(Long loginUserId);
}
