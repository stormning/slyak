/**
 * Project name : slyak-web
 * File name : Report.java
 * Package name : com.slyak.model
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7161273697183116463L;

	private int time;
	
	private Long ownerId;

	private BigDecimal units;

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
}