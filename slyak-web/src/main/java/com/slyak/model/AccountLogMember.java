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
 * The persistent class for the t_account_log_member database table.
 * 
 */
@Entity
@Table(name="t_account_log_member")
public class AccountLogMember implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=50)
	private String name;

	//bi-directional many-to-one association to AccountLog
	@OneToMany(mappedBy="accountLogMember")
	private List<AccountLog> accountLogs;

	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

    public AccountLogMember() {
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

	public List<AccountLog> getAccountLogs() {
		return this.accountLogs;
	}

	public void setAccountLogs(List<AccountLog> accountLogs) {
		this.accountLogs = accountLogs;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}