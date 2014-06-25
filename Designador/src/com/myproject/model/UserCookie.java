package com.myproject.model;


import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="USER_COOKIE")
public class UserCookie {

	public UserCookie(String idUserCookie, User user) {
		super();
		this.idUserCookie = idUserCookie;
		this.user = user;
	}

	public UserCookie(){
	}
	
	
	@Id
	@Column(name = "idUSER_COOKIE", length = 255)
	private String idUserCookie;
	
	@ManyToOne
	@ForeignKey (name = "FK_USER_COOKIE__USER")
	@JoinColumn(name = "USER", nullable = false)
	private User user;
	
	public String getIdUserCookie() {
		return idUserCookie;
	}

	public void setIdUserCookie(String idUserCookie) {
		this.idUserCookie = idUserCookie;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}

