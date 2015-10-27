package com.jslss.board.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.jslss.board.consts.Constants;

@Entity
@Table(name = "board")
public class Board implements Constants{
	
	// resolve concurrency issue
	@Version
	private Long version;
	
	@Id
	@SequenceGenerator(name="board_seq", sequenceName="board_seq", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="board_seq")
    @Column(name = "id", nullable = false, updatable = false)
    private long boardId;

    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "content", nullable = false)
    private String content;
    
    @Column(name = "view_count", nullable = false)
    private int viewCount;
    
    @Column(name = "create_date", nullable = false)
    private Date createDate;

    @Column(name = "last_update_date", nullable = false)
    private Date lastUpdateDate;

    @Column(name = "action", nullable = false, length = 1)
    private String action;

    @Column(name = "notice")		// TODO:: what do we put in it?
    private String notice;
    
    @Column(name = "notice_expired")
    private Date noticeExpired;

    @OneToMany(mappedBy = "board")
    private List<BoardFile> files;
    
    @OneToMany(mappedBy = "board")
    private List<Comment> comments;
    
    @ManyToOne
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

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
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

	public List<BoardFile> getFiles() {
		return files;
	}

	public void setFiles(List<BoardFile> files) {
		this.files = files;
	}
	
	public void addFile(BoardFile file) {
		if(this.files == null)
			this.files = new ArrayList<BoardFile>();
		
		this.files.add(file);
	}
	public void removeFile(BoardFile file) {
		this.files.remove(file);
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		if(this.comments == null)
			this.comments = new ArrayList<Comment>();
		
		this.comments.add(comment);
	}
	public void removeComment(Comment comment) {
		this.comments.remove(comment);
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Board() {
	}

	public Board(long boardId, String title, String content, int viewCount,
			Date createDate, Date lastUpdateDate, String action, String notice,
			Date noticeExpired, List<BoardFile> files, List<Comment> comments,
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
	
	public static Board createBoard(String title, String content,
			String notice, Date noticeExpired, 
			List<BoardFile> files, List<Comment> comments,
			User user) {

		Board board = new Board();
		board.title = title;
		board.content = content;
		board.viewCount = 0;
		board.createDate = new Date();
		board.lastUpdateDate = new Date();
		board.action = ACTION_ADD;
		board.user = user;

		if(notice != null) {
			board.notice = notice;
			board.noticeExpired = noticeExpired;
		}

		if(files == null) {
			board.files = new ArrayList<BoardFile>();
		}else{
			board.files = files;	
		}

		if(comments == null) {
			board.comments = new ArrayList<Comment>();
		} else { 
			board.comments = comments;
		}

		return board;
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

