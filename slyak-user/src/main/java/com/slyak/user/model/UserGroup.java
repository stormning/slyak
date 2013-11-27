/**
 * Project name : slyak-user
 * File name : UserGroup.java
 * Package name : com.slyak.user.model
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.user.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_group")
public class UserGroup implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private UserGroupPK id;

	public UserGroupPK getId() {
		return id;
	}

	public void setId(UserGroupPK id) {
		this.id = id;
	}
}
