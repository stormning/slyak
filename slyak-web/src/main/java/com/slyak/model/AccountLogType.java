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
 * The persistent class for the t_account_log_type database table.
 * 
 */
@Entity
@Table(name="t_account_log_type")
public class AccountLogType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=24)
	private String name;

	@Column(length=50)
	private String path;

	private boolean share;

	//bi-directional many-to-one association to AccountLog
	@OneToMany(mappedBy="accountLogType")
	private List<AccountLog> accountLogs;

	//bi-directional many-to-one association to AccountLogType
    @ManyToOne
	@JoinColumn(name="parent_id")
	private AccountLogType parent;

	//bi-directional many-to-one association to AccountLogType
	@OneToMany(mappedBy="parent")
	private List<AccountLogType> children;

	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="creator_id")
	private User user;

    public AccountLogType() {
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

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean getShare() {
		return this.share;
	}

	public void setShare(boolean share) {
		this.share = share;
	}

	public List<AccountLog> getAccountLogs() {
		return this.accountLogs;
	}

	public void setAccountLogs(List<AccountLog> accountLogs) {
		this.accountLogs = accountLogs;
	}
	
	public AccountLogType getParent() {
		return this.parent;
	}

	public void setParent(AccountLogType parent) {
		this.parent = parent;
	}
	
	public List<AccountLogType> getChildren() {
		return this.children;
	}

	public void setChildren(List<AccountLogType> children) {
		this.children = children;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}