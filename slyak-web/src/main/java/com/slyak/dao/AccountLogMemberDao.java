/**
 * Project name : slyak-web
 * File name : AccountLogMemberDao.java
 * Package name : com.slyak.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
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
