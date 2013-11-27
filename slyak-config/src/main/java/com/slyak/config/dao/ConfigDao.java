/**
 * Project name : slyak-config
 * File name : ConfigDao.java
 * Package name : com.slyak.config.dao
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.config.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.slyak.config.model.Config;
import com.slyak.config.model.ConfigPK;


public interface ConfigDao extends JpaRepository<Config, ConfigPK>,JpaSpecificationExecutor<Config> {

	
}
