package com.slyak.user.model;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class UserGroupPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private Long groupId;
	
	public UserGroupPK(){
		
	}
	
	public UserGroupPK(Long userId,Long groupId){
		this.userId = userId;
		this.groupId = groupId;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserGroupPK)) {
			return false;
		}
		UserGroupPK castOther = (UserGroupPK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.groupId.equals(castOther.groupId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.groupId.hashCode();
		
		return hash;
    }

}
