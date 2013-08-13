package com.slyak.config.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the t_config database table.
 * 
 */
@Entity
@Table(name="t_config")
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