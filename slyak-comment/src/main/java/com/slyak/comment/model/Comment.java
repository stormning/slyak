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
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.slyak.user.model.User;

/**
 * The persistent class for the t_comment database table.
 * 
 */
@Entity
@Table(name = "t_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(nullable = false)
	private String content;
	
	@Column(length = 500)
	private String fragment;

	@Column(length = 240)
	private String title;

	@Column(name = "create_at", nullable = false)
	private Date createAt;

	@Column(name = "modify_at")
	private Date modifyAt;
	
	@Column
	private String owner;

	@Column
	private String biz;

	@Column
	private long viewed = 0;

	@Column
	private long liked = 0;

	@Column
	private long unliked = 0;

	@Column
	private long rank = 0;

	@Column
	private long commented = 0;
	
	@Column
	private int level = 0;
	
	@Column
	private long referer = 0;
	
	@Column(name = "img_count")
	private int imgCount = 0;
	
	@Column
	private int ver = 0;

	// uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "creator")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private User creator;

	@ManyToOne
	@JoinColumn(name = "modifier")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private User modifier;
	
	@Column(name = "tag_ids")
	private String tagIds;

	//TODO
//	private List<Comment> children;

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public long getViewed() {
		return viewed;
	}

	public void setViewed(long viewed) {
		this.viewed = viewed;
	}

	public long getLiked() {
		return liked;
	}

	public void setLiked(long liked) {
		this.liked = liked;
	}

	public long getUnliked() {
		return unliked;
	}

	public void setUnliked(long unliked) {
		this.unliked = unliked;
	}

	public long getRank() {
		return rank;
	}

	public void setRank(long rank) {
		this.rank = rank;
	}

	public long getCommented() {
		return commented;
	}

	public void setCommented(long commented) {
		this.commented = commented;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getReferer() {
		return referer;
	}

	public void setReferer(long referer) {
		this.referer = referer;
	}

	public String getFragment() {
		return fragment;
	}

	public void setFragment(String fragment) {
		this.fragment = fragment;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public int getImgCount() {
		return imgCount;
	}

	public void setImgCount(int imgCount) {
		this.imgCount = imgCount;
	}

	public int getVer() {
		return ver;
	}

	public void setVer(int ver) {
		this.ver = ver;
	}
}