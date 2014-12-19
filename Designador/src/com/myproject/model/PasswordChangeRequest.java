package com.myproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="PASSWORD_CHANGE_REQUEST")
public class PasswordChangeRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5046218170591398401L;

	public PasswordChangeRequest(User idPasswordChangeRequest, String token) {
		super();
		this.idPasswordChangeRequest = idPasswordChangeRequest;
		this.token = token;
	}

	public PasswordChangeRequest() {		
	}

	@Id
	@OneToOne
	@ForeignKey (name = "FK_PASSWORD_CHANGE_REQUEST__USER")
	@JoinColumn(name = "idPASSWORD_CHANGE_REQUEST")
	private User idPasswordChangeRequest;
	
	@Column(name = "TOKEN", length = 255, nullable = false, unique = true)
	private String token;
	
	public User getIdPasswordChangeRequest() {
		return idPasswordChangeRequest;
	}

	public void setIdPasswordChangeRequest(User idPasswordChangeRequest) {
		this.idPasswordChangeRequest = idPasswordChangeRequest;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
