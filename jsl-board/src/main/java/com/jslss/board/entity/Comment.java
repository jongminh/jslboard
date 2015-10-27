package com.jslss.board.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.jslss.board.consts.Constants;

@Entity
@Table(name = "comment")
public class Comment implements Constants{
	
	// resolve concurrency issue
	@Version
	private Long version;
	
	@Id
	@SequenceGenerator(name="comment_seq", sequenceName="comment_seq", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="comment_seq")
    @Column(name = "id", nullable = false, updatable = false)
    private long commentId;

    @Column(name = "text", nullable = false)
    private String text;
    
    @Column(name = "create_date")
    private Date createDate;
    
    @Column(name = "last_update_date")
    private Date lastUpdateDate;
    
    @Column(name = "action", nullable = false, length = 1)
    private String action;
    
    @ManyToOne
	@JoinColumn(name = "board_id", nullable = false)
    private Board board;
    
    @ManyToOne
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
	
	public static Comment createComment( String text, Board board,
			User user) {

		Comment comment = new Comment();
		comment.text = text;
		comment.board = board;
		comment.user = user;
		comment.createDate = new Date();
		comment.lastUpdateDate = new Date();
		comment.action = ACTION_ADD;

		return comment;
	}
	
	public Comment(long commentId, String text, Date createDate,
			Date lastUpdateDate, String action, Board board,
			User user) {
		this.commentId = commentId;
		this.text = text;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
		this.action = action;
		this.board = board;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [version=" + version + ", commentId=" + commentId + ", text=" + text + ", createDate="
				+ createDate + ", lastUpdateDate=" + lastUpdateDate + ", action=" + action + ", board=" + board
				+ ", user=" + user + "]";
	}
    
}

