package com.slyak.config.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the t_config database table.
 * 
 */
@Embeddable
public class ConfigPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(nullable=false, length=50)
	private String biz;

	@Column(nullable=false, length=50)
	private String owner;

    public ConfigPK() {
    }
    
    public ConfigPK(String biz) {
		this.biz = biz;
	}
    
	public ConfigPK(String biz, String owner) {
		this.biz = biz;
		this.owner = owner;
	}

	public String getBiz() {
		return this.biz;
	}
	public void setBiz(String biz) {
		this.biz = biz;
	}
	public String getOwner() {
		return this.owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConfigPK)) {
			return false;
		}
		ConfigPK castOther = (ConfigPK)other;
		return 
			this.biz.equals(castOther.biz)
			&& this.owner.equals(castOther.owner);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.biz.hashCode();
		hash = hash * prime + this.owner.hashCode();
		
		return hash;
    }
}