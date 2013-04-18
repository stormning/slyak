package com.slyak.cms.core.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="t_widget")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Widget implements Serializable,Comparable<Widget>{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length=100)
	private String title;
	
	@Column(name="borderTpl",length=100,nullable=false)
	private String borderTpl;
	
	@Column(name="border_class",length=100)
	private String borderClass;
	
	@ManyToOne
	@JoinColumn(name="page_id")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Page page;
	
	@Column(name="container",nullable=true,length=50)
	private String container;
	
	@Column(name="name",nullable=true,length=50)
	private String name;
	
	@ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "t_widget_setting", joinColumns = {@JoinColumn(name = "widget_id")})
    @MapKeyColumn(length = 64, name = "attr_key")
    @Column(name = "attr_value", length = 100000)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Map<String,String> settings;
	
	private int rank;

	@Transient
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getSettings() {
		return settings;
	}

	public void setSettings(Map<String, String> settings) {
		this.settings = settings;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBorderTpl() {
		return borderTpl;
	}

	public void setBorderTpl(String borderTpl) {
		this.borderTpl = borderTpl;
	}

	public String getBorderClass() {
		return borderClass;
	}

	public void setBorderClass(String borderClass) {
		this.borderClass = borderClass;
	}

	@Override
	public int compareTo(Widget o) {
		int dif = this.getRank()-o.getRank();
		if(dif == 0){
			return o.getId().intValue()-this.getId().intValue();
		}else{
			return dif;
		}
	}
}