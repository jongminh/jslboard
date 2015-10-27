package com.jslss.board.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jslss.board.consts.Constants;


@Entity
@Table(name = "user")
public class User implements Constants{
	
	// resolve concurrency issue
	@Version
	private Long version;
		
	@Id
    @Column(name = "id", nullable = false, updatable = false)
    private String userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    
    @Column(name = "create_date" )
    private Date createDate;
    
    @Column(name = "last_log_in" )
    private Date lastLogIn;
    
    @Column(name = "last_log_out" )
    private Date lastLogOut;
    
    @Column(name = "action", nullable = false, length = 1)
    private String action;

//    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER, 
//    		cascade = {CascadeType.ALL}, orphanRemoval = true)
    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    private List<Role> roles;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards;
    
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_file",
    		joinColumns={@JoinColumn(name="user_id")},
    		inverseJoinColumns={@JoinColumn(name="file_id")}
    )
    private List<BoardFile> boardFiles = new ArrayList<BoardFile>();
    
    
    public Long getVersion(){
		return version;
	}
	
	public void setVersion(Long version){
		this.version = version;
	}
    
    public User() {
    	
    }
    public static User createUser(String userId, String firstName, String lastName, 
    		String email, String password) {
    	PasswordEncoder encod = new BCryptPasswordEncoder(); 
        
    	User user = new User();
    	user.userId = userId;
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = encod.encode(password);
        user.roles = new ArrayList<Role>();
        user.createDate = new Date();
        user.action = ACTION_ADD;

        return user;
    }
    
	public String getUserId() {
		return userId;
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
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Board> getBoards() {
		return boards;
	}
	public void setBoards(List<Board> boards) {
		this.boards = boards;
	}
	public void addBoard(Board board) {
		if(this.boards == null)
			this.boards = new ArrayList<Board>();
		
		this.boards.add(board);
	}
	public void removeBoard(Board board) {
		this.boards.remove(board);
	}

	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public void addRole(Role role) {
		this.roles.add(role);
	}
	public void removeRole(Role role) {
		this.roles.remove(role);
	}
	
	public List<BoardFile> getBoardFiles() {
		return boardFiles;
	}

	public void setBoardFiles(List<BoardFile> files) {
		this.boardFiles = files;
	}
		
	@Override
	public String toString() {
		return "User [version=" + version + ", userId=" + userId + ", email="
				+ email + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", password=" + password + ", enabled=" + enabled
				+ ", createDate=" + createDate + ", lastLogIn=" + lastLogIn
				+ ", lastLogOut=" + lastLogOut + ", action=" + action
				+ ", roles=" + roles + ", comments=" + comments + ", boards="
				+ boards + ", files=" + boardFiles + "]";
	}

}
