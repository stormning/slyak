/*
 * Project:  any
 * Module:   slyak-web
 * File:     AccountServiceImpl.java
 * Modifier: zhouning
 * Modified: 2012-12-8 下午3:48:13 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.slyak.dao.AccountLogDao;
import com.slyak.dao.AccountLogMemberDao;
import com.slyak.dao.AccountLogTypeDao;
import com.slyak.dao.InitDao;
import com.slyak.dao.SpecificDao;
import com.slyak.model.AccountLog;
import com.slyak.model.AccountLogMember;
import com.slyak.model.AccountLogType;
import com.slyak.model.Init;
import com.slyak.model.Report;
import com.slyak.service.AccountService;
import com.slyak.user.dao.UserDao;
import com.slyak.user.model.User;
import com.slyak.util.Constants;
import com.slyak.util.DateUtils;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-8
 */
@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountLogDao accountLogDao;
	
	@Autowired
	private AccountLogTypeDao accountLogTypeDao;
	
	@Autowired
	private AccountLogMemberDao accountLogMemberDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private InitDao initDao;
	
	@Autowired
	private SpecificDao specificDao;
	
	@Override
	public Page<AccountLog> findAccountLogs(Pageable pageable, long logType, long userId) {
		if(logType>0){
			return accountLogDao.findByAccountLogTypePathLikeAndUserId(pageable, "%|"+logType+"|%", userId);
		}else{
			return accountLogDao.findByUserId(pageable,userId);
		}
	}

	@Override
	public List<AccountLogType> findRootAccountLogTypes(long userId) {
		List<AccountLogType> returned = initUserAccountLogTypes(userId);
		//if returned is null, means inited, so search it
		if(returned==null){
			return accountLogTypeDao.findByParentIdAndUserId(null,userId);
		}else{
			return returned;
		}
	}

	//event ??
	private List<AccountLogType> initUserAccountLogTypes(long userId) {
		Init init = initDao.findByUserIdAndItem(userId, Constants.INIT_ITEM.ACCOUNT_LOG_TYPE);
		if(init == null){
			List<AccountLogType> accountLogTypes = _initUserAccountLogTypes(userId);
			if(!CollectionUtils.isEmpty(accountLogTypes)){
				//init
				init = new Init();
				init.setItem(Constants.INIT_ITEM.ACCOUNT_LOG_TYPE);
				init.setUser(userDao.findOne(userId));
				initDao.save(init);
			}
			return accountLogTypes;
		}
		return null;
	}
	
	private List<AccountLogType> _initUserAccountLogTypes(Long userId){
		List<AccountLogType> rootSharedLogTypes = accountLogTypeDao.findByParentIdAndShare(null,true);
		if(!CollectionUtils.isEmpty(rootSharedLogTypes)){
			List<AccountLogType> userRootLogTypes = accountLogTypeDao.findByParentIdAndUserId(null, userId);
			if(CollectionUtils.isEmpty(userRootLogTypes)){
				List<AccountLogType> tmp = new ArrayList<AccountLogType>();
				loopAddLogTypes(tmp,userDao.findOne(userId),null,rootSharedLogTypes);
				return tmp;
			}
		}
		return null;
	}
	
	private void loopAddLogTypes(List<AccountLogType> tmp,User user ,AccountLogType parent,List<AccountLogType> children){
		if(!CollectionUtils.isEmpty(children)){
			for (AccountLogType alt : children) {
				AccountLogType accountLogType = new AccountLogType();
				accountLogType.setName(alt.getName());
				accountLogType.setShare(false);
				accountLogType.setUser(user);
				accountLogType.setParent(parent);
				accountLogTypeDao.save(accountLogType);
				
				if(parent == null){
					accountLogType.setPath("|"+accountLogType.getId()+"|");
				}else{
					accountLogType.setPath(parent.getPath()+accountLogType.getId()+"|");
				}
				tmp.add(accountLogType);
				List<AccountLogType> tmpc = new ArrayList<AccountLogType>();
				accountLogType.setChildren(tmpc);
				loopAddLogTypes(tmpc,user,accountLogType,alt.getChildren());
			}
		}
	}

	@Override
	public AccountLogType findAccountLogType(Long logType,Long userId) {
		if(logType==null||logType<=0){
			return null;
		}else{
			return accountLogTypeDao.findByIdAndUserId(logType,userId);
		}
	}

	@Override
	public Page<AccountLog> findRecentSharedLogsPage(Pageable pageable) {
		return accountLogTypeDao.findRecentSharedLogsPage(pageable);
	}

	@Override
	public List<AccountLog> findRecentSharedLogs(int offset,int limit) {
		Pageable pageable = new PageRequest(offset/limit, limit);
		return accountLogTypeDao.findRecentSharedLogs(pageable);
	}

	@Override
	public List<User> findMostActiveUsers(int offset, int limit) {
		Sort sort = new Sort(Direction.DESC,"logCount");
		Pageable pageable = new PageRequest(offset/limit, limit,sort);
		return userDao.listUsers(pageable);
	}

	@Override
	public void saveAccountLog(AccountLog accountLog) {
		AccountLogMember accountLogMember = accountLog.getAccountLogMember();
		if(accountLogMember!=null&&accountLogMember.getId()!=null){
			accountLog.setAccountLogMember(accountLogMemberDao.findOne(accountLogMember.getId()));
		}else{
			accountLog.setAccountLogMember(null);
		}
		AccountLogType accountLogType = accountLog.getAccountLogType();
		if(accountLogType!=null&&accountLogType.getId()!=null){
			accountLog.setAccountLogType(accountLogTypeDao.findOne(accountLogType.getId()));
		}else{
			accountLog.setAccountLogType(null);
		}
		Date current = new Date();
		if(accountLog.getHappenTime()== null){
			accountLog.setHappenTime(current);
		}
		accountLog.setCreateTime(current);
		accountLogDao.save(accountLog);
	}

	@Override
	public void saveAccountLogType(AccountLogType accountLogType) {
		AccountLogType parent = accountLogType.getParent();
		if(parent!=null&&parent.getId()!=null){
			accountLogTypeDao.save(accountLogType);
			parent = accountLogTypeDao.findOne(parent.getId());
			if(parent!=null){
				accountLogType.setPath(parent.getPath()+accountLogType.getId()+"|");
			}
		}
	}

	@Override
	public List<Report> getLogReportByParentLogTypeIdGroupByMember(Long userId, Long parentLogTypeId, Date from,
			Date to, boolean dateByMonth) {
		
		List<Long> logTypeIds = getChildrenIds(parentLogTypeId,userId);
		
		if(dateByMonth){
			if(from!=null&&to!=null&&from.getTime()==to.getTime()){
				return specificDao.getLogReportGroupByMemberAndDay(userId,logTypeIds,DateUtils.getFirstDayOf(from, Calendar.DAY_OF_MONTH).getTime(), DateUtils.getLastDayEnding(from, Calendar.DAY_OF_MONTH).getTime());
			}else{
				return specificDao.getLogReportGroupByMemberAndMonth(userId,logTypeIds,from, to);
			}
		}else{
			if(from!=null&&to!=null&&from.getTime()==to.getTime()){
				return specificDao.getLogReportGroupByMemberAndMonth(userId,logTypeIds,DateUtils.getFirstDayOf(from, Calendar.DAY_OF_YEAR).getTime(), DateUtils.getLastDayEnding(from, Calendar.DAY_OF_YEAR).getTime());
			}else{
				return specificDao.getLogReportGroupByMemberAndYear(userId,logTypeIds,from, to);
			}
		}
	}
	
	private List<Long> getChildrenIds(Long parentLogTypeId,Long userId){
		List<Long> logTypeIds = null;
		List<AccountLogType> logTypes = accountLogTypeDao.findByParentIdAndUserId(parentLogTypeId, userId);
		if(!CollectionUtils.isEmpty(logTypes)){
			logTypeIds = new ArrayList<Long>();
			for (AccountLogType accountLogType : logTypes) {
				logTypeIds.add(accountLogType.getId());
			}
		}
		return logTypeIds;
	}

	@Override
	public List<Report> getLogReportByParentLogTypeIdGroupByType(Long userId, Long parentLogTypeId, Date from,
			Date to, boolean dateByMonth) {
		List<Long> logTypeIds = getChildrenIds(parentLogTypeId,userId);
		if(dateByMonth){
			if(from!=null&&to!=null&&from.getTime()==to.getTime()){
				return specificDao.getLogReportGroupByTypeAndDay(userId,logTypeIds,DateUtils.getFirstDayOf(from, Calendar.DAY_OF_MONTH).getTime(), DateUtils.getLastDayEnding(from, Calendar.DAY_OF_MONTH).getTime());
			}else{
				return specificDao.getLogReportGroupByTypeAndMonth(userId,logTypeIds,from, to);
			}
		}else{
			if(from!=null&&to!=null&&from.getTime()==to.getTime()){
				return specificDao.getLogReportGroupByTypeAndMonth(userId,logTypeIds,DateUtils.getFirstDayOf(from, Calendar.DAY_OF_YEAR).getTime(), DateUtils.getLastDayEnding(from, Calendar.DAY_OF_YEAR).getTime());
			}else{
				return specificDao.getLogReportGroupByTypeAndYear(userId,logTypeIds,from, to);
			}
		}
	}

	@Override
	public AccountLogMember findAccountLogMember(long logMemberId) {
		return accountLogMemberDao.findOne(logMemberId);
	}

	@Override
	public AccountLogType findAccountLogType(Long logTypeId) {
		return accountLogTypeDao.findOne(logTypeId);
	}

	@Override
	public void deleteTypes(List<AccountLogType> accountLogTypes) {
		List<Long> tids = new ArrayList<Long>();
		for (AccountLogType accountLogType : accountLogTypes) {
			tids.add(accountLogType.getId());
		}
		accountLogDao.clearRemovedTypeIds(tids);
		accountLogTypeDao.deleteInBatch(accountLogTypes);
	}

	@Override
	public void saveAccountLogMember(AccountLogMember accountLogMember) {
		accountLogMemberDao.save(accountLogMember);
	}

	@Override
	public void deleteMembers(List<AccountLogMember> accountLogMembers) {
		List<Long> mids = new ArrayList<Long>();
		for (AccountLogMember accountLogMember : accountLogMembers) {
			mids.add(accountLogMember.getId());
		}
		accountLogDao.clearRemovedMemberIds(mids);
		accountLogMemberDao.deleteInBatch(accountLogMembers);
	}

	@Override
	public List<AccountLogMember> getUserAccountLogMembers(Long loginUserId) {
		return accountLogMemberDao.findByUserId(loginUserId);
	}

}
