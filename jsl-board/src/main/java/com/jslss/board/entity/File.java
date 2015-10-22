package com.jslss.board.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "file")
public class File implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// resolve concurrency issue
	@Version
	private Long version;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private long fileId;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;
    
    @Column(name = "content_type", nullable = false, unique = true)
    private String contentType;
    
    @Column(name = "content", nullable = false, unique = true)
    private String content;
    
    @Column(name = "file_size", nullable = false, unique = true)
    private String fileSize;
    
    @Column(name = "action", nullable = false, unique = true)
    private String action;
   
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToMany
    private Set<User> users = new HashSet<User>();
    
	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public File() {
	}
	
	public File(long fileId, String fileName, String contentType,
			String content, String fileSize, String action, Board board,
			Set<User> users) {
		this.fileId = fileId;
		this.fileName = fileName;
		this.contentType = contentType;
		this.content = content;
		this.fileSize = fileSize;
		this.action = action;
		this.board = board;
		this.users = users;
	}

	@Override
	public String toString() {
		return "File [version=" + version + ", fileId=" + fileId + ", fileName=" + fileName + ", contentType="
				+ contentType + ", content=" + content + ", fileSize=" + fileSize + ", action=" + action + ", board="
				+ board + ", users=" + users + "]";
	}
    

}

