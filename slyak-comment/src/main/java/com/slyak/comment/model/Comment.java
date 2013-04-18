package com.slyak.comment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.slyak.user.model.User;


/**
 * The persistent class for the t_comment database table.
 * 
 */
@Entity
@Table(name="t_comment")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

    @Lob
	@Column(nullable=false)
    @Basic(fetch=FetchType.LAZY)
	private String content;

	@Column(length=240)
	private String title;
	
	@Column(name="create_at",nullable=false)
	private Date createAt;

	@Column(name="modify_at")
	private Date modifyAt;

	//bi-directional many-to-one association to Comment
    @ManyToOne
	@JoinColumn(name="parent_id")
	private Comment parent;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="parent")
	private List<Comment> children;

	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="creator")
	private User creator;

    @ManyToOne
	@JoinColumn(name="modifier")
	private User modifier;
    
    @ManyToOne
    @JoinColumn(name="comment_type_id")
    @Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private CommentType commentType;
    
    public Comment() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Comment getParent() {
		return this.parent;
	}

	public void setParent(Comment parent) {
		this.parent = parent;
	}
	
	public List<Comment> getChildren() {
		return this.children;
	}

	public void setChildren(List<Comment> children) {
		this.children = children;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getModifyAt() {
		return modifyAt;
	}

	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}

	public CommentType getCommentType() {
		return commentType;
	}

	public void setCommentType(CommentType commentType) {
		this.commentType = commentType;
	}
	
}