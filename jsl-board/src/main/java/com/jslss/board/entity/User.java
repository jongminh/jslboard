package com.jslss.board.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Table(name = "user")
public class User {
	
	// resolve concurrency issue
	@Version
	private Long version;
		
	@Id
    @Column(name = "id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "lastName", nullable = false)
    private String lastName;
    
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    
    @Column(name = "createDate" )
    private Date createDate;
    
    @Column(name = "lastLogIn" )
    private Date lastLogIn;
    
    @Column(name = "lastLogOut" )
    private Date lastLogOut;
    
    @Column(name = "action", nullable = false)
    private String action;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Long getVersion(){
		return version;
	}
	
	public void setVersion(Long version){
		this.version = version;
	}
    
    public User() {
    	
    }
    public static User createUser(String id,String firstName, String lastName, 
    		String email, String password) {
    	PasswordEncoder encod = new BCryptPasswordEncoder(); 
        
    	User user = new User();
    	user.userId = id;
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = encod.encode(password);
        user.enabled = false;

        if(user.roles == null) {
            user.roles = new HashSet<Role>();
            user.createDate = new Date();
            user.action = "A";
        }
        return user;
    }
    
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastLogIn() {
		return lastLogIn;
	}
	public void setLastLogIn(Date lastLogIn) {
		this.lastLogIn = lastLogIn;
	}
	public Date getLastLogOut() {
		return lastLogOut;
	}
	public void setLastLogOut(Date lastLogOut) {
		this.lastLogOut = lastLogOut;
	}
	public String isAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "User [version=" + version + ", userId=" + userId + ", email=" + email + ", lastName=" + lastName
				+ ", firstName=" + firstName + ", password=" + password + ", enabled=" + enabled + ", createDate="
				+ createDate + ", lastLogIn=" + lastLogIn + ", lastLogOut=" + lastLogOut + ", action=" + action
				+ ", roles=" + roles + "]";
	}
    
    
}
