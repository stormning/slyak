/*
 * Project:  any
 * Module:   slyak-web
 * File:     SqlScriptExecutor.java
 * Modifier: zhouning
 * Modified: 2012-12-8 下午5:06:35 
 *
 * Copyright (c) 2012 Sanyuan Ltd. All Rights Reserved.
 *
 * Copying of this document or code and giving it to others and the
 * use or communication of the contents thereof, are forbidden without
 * expressed authority. Offenders are liable to the payment of damages.
 * All rights reserved in the event of the grant of a invention patent or the
 * registration of a utility model, design or code.
 */
package com.slyak.core.database;


import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:stormning@163.com">stormning</a>
 * @version V1.0, 2012-12-8
 */
public abstract class AbstractSqlScriptExecutor implements InitializingBean{
	
	private ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	
	private Resource[] scripts;
	
	private DataSource dataSource;
	
	private String charset = "UTF-8";
	
	public void setScripts(Resource[] scripts) {
		this.scripts = scripts;
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public abstract boolean canExecute();

	@Override
	public void afterPropertiesSet() throws Exception {
		if(canExecute()){
			populator.setSqlScriptEncoding(charset);
			populator.setIgnoreFailedDrops(false);
			populator.setContinueOnError(false);
			for (Resource script : scripts) {
				populator.addScript(script);
			}
			DatabasePopulatorUtils.execute(populator, dataSource);
		}
	}
}