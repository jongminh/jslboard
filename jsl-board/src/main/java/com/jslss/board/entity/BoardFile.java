package com.jslss.board.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.jslss.board.consts.Constants;

@Entity
@Table(name = "file")
public class BoardFile implements Serializable, Constants{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// resolve concurrency issue
	@Version
	private Long version;
	
	@Id
	@SequenceGenerator(name="file_seq", sequenceName="file_seq", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="file_seq")
    @Column(name = "id", nullable = false, updatable = false)
    private long fileId;

    @Column(name = "file_name", nullable = false)
    private String fileName;
    
    @Column(name = "content_type", nullable = false)	// MimeType
    private String contentType;
    
    //@Lob
    //@Basic(fetch=FetchType.LAZY)
    @Column(name = "content", nullable = false)
    private byte[] content;
    
    @Column(name = "file_size", nullable = false)
    private long fileSize;
    
    @Column(name = "action", nullable = false, length = 1)
    private String action;
   
    @ManyToOne
	@JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="boardFiles")
    private List<User> users = new ArrayList<User>();
    
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


	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public BoardFile() {
	}
	
	public static BoardFile createBoardFile(String fileName, String contentType,
			byte[] content, long fileSize, Board board,
			List<User> users) {

		BoardFile boardFile = new BoardFile();
		boardFile.fileName = fileName;
		boardFile.contentType = contentType;
		boardFile.content = content;
		boardFile.fileSize = fileSize;
		boardFile.board = board;
		boardFile.action = ACTION_ADD;
		boardFile.users = users;

		return boardFile;
	}
	
	public BoardFile(long fileId, String fileName, String contentType,
			byte[] content, long fileSize, String action, Board board,
			List<User> users) {
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

