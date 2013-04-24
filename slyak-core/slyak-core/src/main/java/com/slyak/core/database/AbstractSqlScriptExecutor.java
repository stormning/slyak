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