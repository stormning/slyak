/*
 * Project:  any
 * Module:   slyak-web
 * File:     AccountLongMemberDao.java
 * Modifier: zhouning
 * Modified: 2012-12-7 下午8:50:31 
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

import org.springframework.data.jpa.repository.JpaRepository;

import com.slyak.model.AccountLogMember;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-7
 */
public interface AccountLogMemberDao extends
		JpaRepository<AccountLogMember, Long> {

	List<AccountLogMember> findByUserId(Long loginUserId);

}
