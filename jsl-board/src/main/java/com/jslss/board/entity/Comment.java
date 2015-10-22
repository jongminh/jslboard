package com.jslss.board.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "comment")
public class Comment {
	
	// resolve concurrency issue
	@Version
	private Long version;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long commentId;

    @Column(name = "text", nullable = false, unique = true)
    private String text;
    
    @Column(name = "create_date")
    private Date createDate;
    
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    
    @Column(name = "action", nullable = false, unique = true)
    private String action;
    
    @Column(name = "file_size", nullable = false, unique = true)
    private String fileSize;
    
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "board_id", nullable = false)
    private Board board;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment() {
	}
	
	public Comment(long commentId, String text, Date createDate,
			Date lastUpdateDate, String action, String fileSize, Board board,
			User user) {
		this.commentId = commentId;
		this.text = text;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
		this.action = action;
		this.fileSize = fileSize;
		this.board = board;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [version=" + version + ", commentId=" + commentId
				+ ", text=" + text + ", createDate=" + createDate
				+ ", lastUpdateDate=" + lastUpdateDate + ", action=" + action
				+ ", fileSize=" + fileSize + ", board=" + board + ", user="
				+ user + "]";
	}
    
}

