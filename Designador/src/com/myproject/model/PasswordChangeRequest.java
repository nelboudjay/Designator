package com.myproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="PASSWORD_CHANGE_REQUEST")
public class PasswordChangeRequest {
	


	
	public PasswordChangeRequest(String idPasswordChangeRequest, User user) {
		super();
		this.idPasswordChangeRequest = idPasswordChangeRequest;
		this.user = user;
	}

	public PasswordChangeRequest() {		
	}

	@Id
	@Column(name = "idPASSWORD_CHANGE_REQUEST", length = 255)
	private String idPasswordChangeRequest;
	
	@OneToOne
	@ForeignKey (name = "FK_PASSWORD_CHANGE_REQUEST__USER")
	@JoinColumn(name = "USER", nullable = false, unique = true)
	private User user;
	
	public String getIdPasswordChangeRequest() {
		return idPasswordChangeRequest;
	}

	public void setIdPasswordChangeRequest(String idPasswordChangeRequest) {
		this.idPasswordChangeRequest = idPasswordChangeRequest;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}
