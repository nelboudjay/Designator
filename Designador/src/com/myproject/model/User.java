package com.myproject.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.annotations.ForeignKey;

import com.myproject.tools.DesEncrypter;
import com.opensymphony.xwork2.ActionSupport;

@Entity
@Table(name="USER")
public class User extends ActionSupport implements Serializable{

	private static final long serialVersionUID = -4142991539282053208L;

	public User(User user){
		super();
		this.idUser = user.getIdUser();
		this.userName = user.getUserName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.userRole = user.getUserRole();	
		this.userProfile = user.getUserProfile();
	}
	
	public User(String userName, String email, UserRole userRole, UserProfile userProfile){
		super();
		this.userName = userName;
		this.email = email;
		this.password = "";
		this.userRole = userRole;	
		this.userProfile = userProfile;
	}
	
	
	public User(){
	}
	
	@Id
	@Column(name = "idUSER")
	@GeneratedValue
	private int idUser;
	
	@Column(name="USERNAME", nullable = false, unique = true, length = 45)
	private String userName;
	
	@Column(name="EMAIL", nullable = false, unique = true, length = 45)
	private String email;

	@Column(name="PASSWORD", nullable = false, length = 45)
	private String password;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@ForeignKey (name = "FK_USER__USER_ROLE")
	@JoinColumn(name = "USER_ROLE", nullable = false)
	private UserRole userRole;
	
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey (name = "FK_USER__USER_PROFILE")
	@JoinColumn(name = "USER_PROFILE", nullable = false, unique = true)
	private UserProfile userProfile;

	public int getIdUser() {
		return idUser;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
        DesEncrypter decrypter = new DesEncrypter(getText("loginPass"));
		return decrypter.decrypt(password);
	}
	
	public UserRole getUserRole() {
		return userRole;
	}
	
	public UserProfile getUserProfile() {
		return userProfile;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {

		DesEncrypter encrypter = new DesEncrypter(getText("loginPass"));
		this.password =	encrypter.encrypt(password);
	}
	
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	public String getUserFullName(){
		
		return WordUtils.capitalize(getUserProfile().getFirstName() + " "
				+ getUserProfile().getLastName1() + " " 
				+ ((getUserProfile().getLastName2() == null) ? "" : getUserProfile().getLastName2()));
	}
	
	public boolean isAdmin(){
		return getUserRole().getUserRoleName().equals("Admin");
	}

}
