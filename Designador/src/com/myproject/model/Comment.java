package com.myproject.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;



@Entity
@Table(name = "COMMENT")
public class Comment {

	
	public Comment(String commentBody,
			Timestamp commentDate, User user) {
		super();
		this.commentBody = commentBody;
		this.commentDate = commentDate;
		this.user = user;
	}

	public Comment(){
		
	}
	
	@Id
	@Column(name = "idCOMMENT")
	@GeneratedValue
	private String idComment;

	@Column(name = "COMMENT_BODY", nullable = false)
	private String commentBody;
	
	@Column(name = "COMMENT_DATE", nullable = false)
	private Timestamp commentDate ;

	@ManyToOne
	@ForeignKey (name = "FK_COMMENT__USER")
	@JoinColumn(name = "USER", nullable = false)
	private User user;

	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public Timestamp getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
