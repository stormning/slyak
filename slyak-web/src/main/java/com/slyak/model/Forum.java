package com.slyak.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.slyak.user.model.User;


/**
 * The persistent class for the t_forum database table.
 * 
 */
@Entity
@Table(name="t_forum")
public class Forum implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=255)
	private String name;

	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="creator", nullable=false)
	private User user;

	//bi-directional many-to-one association to ForumTopic
	@OneToMany(mappedBy="forum")
	private List<ForumTopic> forumTopics;

    public Forum() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public List<ForumTopic> getForumTopics() {
		return this.forumTopics;
	}

	public void setForumTopics(List<ForumTopic> forumTopics) {
		this.forumTopics = forumTopics;
	}
	
}