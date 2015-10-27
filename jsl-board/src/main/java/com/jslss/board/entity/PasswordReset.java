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
@Table(name = "password_reset")
public class PasswordReset {
	
	// resolve concurrency issue
	@Version
	private Long version;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long pwResetId;

    @Column(name = "reset_code", nullable = false, unique = true)
    private String resetCode;

    @ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public Long getVersion(){
		return version;
	}
	
	public void setVersion(Long version){
		this.version = version;
	}

	public long getPwResetId() {
		return pwResetId;
	}

	public void setPwResetId(long pwResetId) {
		this.pwResetId = pwResetId;
	}

	public String getResetCode() {
		return resetCode;
	}

	public void setResetCode(String resetCode) {
		this.resetCode = resetCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PasswordReset() {
	}

	public PasswordReset(String resetCode, User user) {
		this.resetCode = resetCode;
		this.user = user;
	}

	@Override
	public String toString() {
		return "PasswordReset [version=" + version + ", pwResetId=" + pwResetId
				+ ", resetCode=" + resetCode + ", user=" + user + "]";
	}

}

