/**
 * Project name : slyak-web
 * File name : AccountService.java
 * Package name : com.slyak.service
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
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
