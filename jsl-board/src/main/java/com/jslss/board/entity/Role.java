package com.jslss.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "role")
public class Role {
	
	// resolve concurrency issue
	@Version
	private Long version;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long roleId;

    @Column(name = "role", nullable = false)
    private String role;

//    @ManyToOne(optional = false, cascade=CascadeType.PERSIST)
    @ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public Long getVersion(){
		return version;
	}
	
	public void setVersion(Long version){
		this.version = version;
	}
    
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

	public Role(String role) {
        this.role = role;
    }
	
    public Role(String role, User user) {
        this.role = role;
        this.user = user;
    }

	@Override
	public String toString() {
		return "Role [version=" + version + ", roleId=" + roleId + ", role="
				+ role + ", user=" + user + "]";
	}
}

