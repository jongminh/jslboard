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

	@Override
	public String toString() {
		return "Comment [version=" + version + ", commentId=" + commentId
				+ ", text=" + text + ", createDate=" + createDate
				+ ", lastUpdateDate=" + lastUpdateDate + ", action=" + action
				+ ", fileSize=" + fileSize + ", board=" + board + ", user="
				+ user + "]";
	}
    
}

