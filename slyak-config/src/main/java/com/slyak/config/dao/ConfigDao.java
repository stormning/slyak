package com.slyak.config.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.slyak.config.model.Config;
import com.slyak.config.model.ConfigPK;


public interface ConfigDao extends JpaRepository<Config, ConfigPK>,JpaSpecificationExecutor<Config> {

	
}
