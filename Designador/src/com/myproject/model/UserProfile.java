package com.myproject.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="USER_PROFILE")
public class UserProfile {
	
	@Id
	@Column(name = "idUSER_PROFILE")
	@GeneratedValue
	private int idUserProfile;
	
	@Column(name="FIRSTNAME", length = 45, nullable = false)
	private String userFirstName;
	
	@Column(name="LASTNAME1", length = 45, nullable = false)
	private String lastName1;
	
	@Column(name="LASTNAME2", length = 45)
	private String lastName2;
	
	@OneToOne
	@ForeignKey (name = "FK_USER_PROFILE__ADDRESS")
	@JoinColumn(name = "ADDRESS", unique = true)
	private Address address;
	
	@Column(name="HOME_PHONE", length = 45)
	private String homePhone;
	
	@Column(name="MOBILE_PHONE", length = 45)
	private String mobilePhone;
	
	@Column(name="PICTURE", length = 45)
	private Blob picture;

	public int getIdUserProfile() {
		return idUserProfile;
	}

	public void setIdUserProfile(int idUserProfile) {
		this.idUserProfile = idUserProfile;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName1() {
		return lastName1;
	}

	public void setUserLastName1(String lastName1) {
		this.lastName1 = lastName1;
	}

	public String getUserLastName2() {
		return lastName2;
	}

	public void setUserLastName2(String lastName2) {
		this.lastName2 = lastName2;
	}

	public Address getUserAddress() {
		return address;
	}

	public void setUserAddress(Address address) {
		this.address = address;
	}

	public String getUserHomePhone() {
		return homePhone;
	}

	public void setUserHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getUserMobilePhone() {
		return mobilePhone;
	}

	public void setUserMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Blob getUserPicture() {
		return picture;
	}

	public void setUserPicture(Blob picture) {
		this.picture = picture;
	}

}




	
	
	
	
