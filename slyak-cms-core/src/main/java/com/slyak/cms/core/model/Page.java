package com.slyak.cms.core.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="t_page")
public class Page implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;
	
	@Column(length=255)
	private String title;
	
	@Column(length=500)
	private String keywords;
	
	@Column(length=1000)
	private String description;
	
	@Column(length=50,nullable = false)
	private String name;
	
	@Column(length=50)
	private String alias;

	@Column(length=100,nullable=false)
	private String layout;
	
	@Lob
	@Column(name="custom_css")
	private String customCss;
	
//	@OneToMany(mappedBy="page",cascade={CascadeType.ALL})
//	private List<Widget> widgets;
	
	@OneToMany(mappedBy="parent",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	private List<Page> children;

	@ManyToOne
	@JoinColumn(name="parent_id")
	private Page parent;
	
	@Column(name="can_show",nullable=false)
	private boolean show = true;
	
	public String getCustomCss() {
		return customCss;
	}

	public void setCustomCss(String customCss) {
		this.customCss = customCss;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

//	public List<Widget> getWidgets() {
//		return widgets;
//	}
//
//	public void setWidgets(List<Widget> widgets) {
//		this.widgets = widgets;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<Page> getChildren() {
		return children;
	}

	public void setChildren(List<Page> children) {
		this.children = children;
	}

	public Page getParent() {
		return parent;
	}

	public void setParent(Page parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}
}
