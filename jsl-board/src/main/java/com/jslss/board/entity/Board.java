package com.jslss.board.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "board")
public class Board {
	
	// resolve concurrency issue
	@Version
	private Long version;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long boardId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;
    
    @Column(name = "content", nullable = false, unique = true)
    private String content;
    
    @Column(name = "view_count", nullable = false, unique = true)
    private String viewCount;
    
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "action", nullable = false, unique = true)
    private String action;

    @Column(name = "notice", nullable = false, unique = true)
    private String notice;
    
    @Column(name = "notice_expired")
    private Date noticeExpired;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private Set<File> files;
    
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private Set<Comment> comments;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
    private User user;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public long getBoardId() {
		return boardId;
	}

	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
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

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Date getNoticeExpired() {
		return noticeExpired;
	}

	public void setNoticeExpired(Date noticeExpired) {
		this.noticeExpired = noticeExpired;
	}

	public Set<File> getFiles() {
		return files;
	}

	public void setFiles(Set<File> files) {
		this.files = files;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Board() {
	}

	public Board(long boardId, String title, String content, String viewCount,
			Date createDate, Date lastUpdateDate, String action, String notice,
			Date noticeExpired, Set<File> files, Set<Comment> comments,
			User user) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.viewCount = viewCount;
		this.createDate = createDate;
		this.lastUpdateDate = lastUpdateDate;
		this.action = action;
		this.notice = notice;
		this.noticeExpired = noticeExpired;
		this.files = files;
		this.comments = comments;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Board [version=" + version + ", boardId=" + boardId
				+ ", title=" + title + ", content=" + content + ", viewCount="
				+ viewCount + ", createDate=" + createDate
				+ ", lastUpdateDate=" + lastUpdateDate + ", action=" + action
				+ ", notice=" + notice + ", noticeExpired=" + noticeExpired
				+ ", files=" + files + ", comments=" + comments + ", user="
				+ user + "]";
	}
    

}

