package com.slyak.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.slyak.user.model.User;


/**
 * The persistent class for the t_account_log database table.
 * 
 */
@Entity
@Table(name="t_account_log")
public class AccountLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(name="comment_count", nullable=false)
	private int commentCount;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(length=500)
	private String description;

	@Column(name="happen_place", length=140)
	private String happenPlace;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="happen_time")
	private Date happenTime;

	@Column(name="pic_count", nullable=false)
	private int picCount;

	@Column(nullable=false, length=280)
	private String title;

	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal units;

	//bi-directional many-to-one association to AccountLogMember
    @ManyToOne
	@JoinColumn(name="log_member_id")
	private AccountLogMember accountLogMember;

	//bi-directional many-to-one association to AccountLogType
    @ManyToOne
	@JoinColumn(name="log_type_id")
	private AccountLogType accountLogType;

	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

    public AccountLog() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHappenPlace() {
		return this.happenPlace;
	}

	public void setHappenPlace(String happenPlace) {
		this.happenPlace = happenPlace;
	}

	public Date getHappenTime() {
		return this.happenTime;
	}

	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}

	public int getPicCount() {
		return this.picCount;
	}

	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getUnits() {
		return this.units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public AccountLogMember getAccountLogMember() {
		return this.accountLogMember;
	}

	public void setAccountLogMember(AccountLogMember accountLogMember) {
		this.accountLogMember = accountLogMember;
	}
	
	public AccountLogType getAccountLogType() {
		return this.accountLogType;
	}

	public void setAccountLogType(AccountLogType accountLogType) {
		this.accountLogType = accountLogType;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}