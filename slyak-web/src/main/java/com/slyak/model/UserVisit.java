package com.slyak.model;

import java.io.Serializable;
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
 * The persistent class for the t_user_visit database table.
 * 
 */
@Entity
@Table(name="t_user_visit")
public class UserVisit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Long id;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="last_visit_time", nullable=false)
	private Date lastVisitTime;

	@Column(nullable=false)
	private int unit;

	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="visitor_id")
	private User visitor;

	//uni-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="visited__id")
	private User visited;

    public UserVisit() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLastVisitTime() {
		return this.lastVisitTime;
	}

	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	public int getUnit() {
		return this.unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public User getVisitor() {
		return this.visitor;
	}

	public void setVisitor(User visitor) {
		this.visitor = visitor;
	}
	
	public User getVisited() {
		return this.visited;
	}

	public void setVisited(User visited) {
		this.visited = visited;
	}
	
}