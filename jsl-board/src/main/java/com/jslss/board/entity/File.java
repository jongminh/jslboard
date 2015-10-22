package com.jslss.board.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "file")
public class File {
	
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

    
	@Override
	public String toString() {
		return "File [version=" + version + ", fileId=" + fileId
				+ ", fileName=" + fileName + ", contentType=" + contentType
				+ ", content=" + content + ", fileSize=" + fileSize
				+ ", action=" + action + ", board=" + board + "]";
	}
    

}

