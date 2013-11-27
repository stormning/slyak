/**
 * Project name : slyak-config
 * File name : Config.java
 * Package name : com.slyak.config.model
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.config.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * The persistent class for the t_config database table.
 * 
 */
@Entity
@Table(name="t_config",uniqueConstraints={@UniqueConstraint(columnNames={"biz","owner"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConfigPK id;

	@Column(length=1000)
	private String name;
	
    @Lob()
	private String data;
    
    @Column
    private int status=0;

    public Config() {
    }

	public ConfigPK getId() {
		return this.id;
	}

	public void setId(ConfigPK id) {
		this.id = id;
	}
	
	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String toBiz(){
		return id.getBiz()+':'+id.getOwner()+':';
	}
}