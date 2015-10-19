package com.myproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="USER_ROLE")
public class UserRole implements Serializable{

	private static final long serialVersionUID = -1495014165546048748L;

	
	public UserRole(String idUserRole, String userRoleName) {
		super();
		this.idUserRole = idUserRole;
		this.userRoleName = userRoleName;
	}

	public UserRole() {
		super();
	}

	@Id
	@Column(name = "idUSER_ROLE")
	@GeneratedValue
	private String idUserRole;
	
	@Column(name="USER_ROLE_NAME", length = 45, nullable = false)
	private String userRoleName;
	
	public String getIdUserRole() {
		return idUserRole;
	}
	
	public String getUserRoleName() {
		return userRoleName;
	}
	
	public void setIdUserRole(String idUserRole) {
		this.idUserRole = idUserRole;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
}
