/**
 * Project name : slyak-standard
 * File name : Location.java
 * Package name : com.slyak.standard.model
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.standard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_location")
public class Location implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -969506701058260651L;
	
	@Id
	private long id;
	
	@Column(length=255,nullable=false)
	private String name;
	
	@Column(length=500)
	private String pinyin;
	
	private long pid=0;
	
	private String path;
	
	private boolean parent;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}
}
