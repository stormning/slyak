/**
 * Project name : slyak-web
 * File name : ForumTopic.java
 * Package name : com.slyak.model
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.slyak.user.model.User;


/**
 * The persistent class for the t_forum_topic database table.
 * 
 */
@Entity
@Table(name="t_forum_topic")
public class ForumTopic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="attachment_count")
	private int attachmentCount;

	@Column(name="child_count")
	private long childCount;

    @Lob()
	private String content;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="create_at", nullable=false)
	private Date createAt;

	private long modifier;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="modify_at")
	private Date modifyAt;

	@Column(nullable=false)
	private long rank;

	private int status;

	@Column(length=255)
	private String title;

	//bi-directional many-to-one association to Forum
    @ManyToOne
	@JoinColumn(name="forum_id", nullable=false)
	private Forum forum;

	//bi-directional many-to-one association to ForumTopic
    @ManyToOne
	@JoinColumn(name="parent_id")
	private ForumTopic parent;

	//bi-directional many-to-one association to ForumTopic
	@OneToMany(mappedBy="parent")
	private List<ForumTopic> children;

	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="creator", nullable=false)
	private User user;

    public ForumTopic() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAttachmentCount() {
		return this.attachmentCount;
	}

	public void setAttachmentCount(int attachmentCount) {
		this.attachmentCount = attachmentCount;
	}

	public long getChildCount() {
		return this.childCount;
	}

	public void setChildCount(long childCount) {
		this.childCount = childCount;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateAt() {
		return this.createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public long getModifier() {
		return this.modifier;
	}

	public void setModifier(long modifier) {
		this.modifier = modifier;
	}

	public Date getModifyAt() {
		return this.modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

	public long getRank() {
		return this.rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Forum getForum() {
		return this.forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}
	
	public ForumTopic getParent() {
		return this.parent;
	}

	public void setParent(ForumTopic parent) {
		this.parent = parent;
	}
	
	public List<ForumTopic> getChildren() {
		return this.children;
	}

	public void setChildren(List<ForumTopic> children) {
		this.children = children;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}