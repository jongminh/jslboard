package com.jslss.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private long roleId;

    @Column(name = "role", nullable = false, unique = true)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
    private User user;
    
    
	public long getRoleId() {
		return roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Role() {
    }

    public Role(String role, User user) {
        this.role = role;
        this.user = user;
    }

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", role=" + role + ", user=" + user + "]";
	}
}

