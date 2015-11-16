package com.myproject.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="USER_PROFILE")
public class UserProfile implements Serializable{

	private static final long serialVersionUID = 7906067534274348077L;
	
	public UserProfile(String idUserProfile, String firstName, String lastName1,
			String lastName2, Address address, String homePhone,
			String mobilePhone, String email2, byte[] picture) {
		super();
		this.idUserProfile = idUserProfile;
		this.firstName = firstName;
		this.lastName1 = lastName1;
		this.lastName2 = lastName2;
		this.address = address;
		this.homePhone = homePhone;
		this.mobilePhone = mobilePhone;
		this.email2 = email2;
		this.picture = picture;
	}
	
	public UserProfile(String firstName, String lastName1,
			String lastName2, Address address, String homePhone,
			String mobilePhone, String email2, byte[] picture) {
		super();
		this.firstName = firstName;
		this.lastName1 = lastName1;
		this.lastName2 = lastName2;
		this.address = address;
		this.homePhone = homePhone;
		this.mobilePhone = mobilePhone;
		this.email2 = email2;
		this.picture = picture;
	}
	
	public UserProfile() {
		super();
	}

	@Id
	@Column(name = "idUSER_PROFILE")
	@GeneratedValue
	private String idUserProfile;
	
	@Column(name="FIRSTNAME", length = 45, nullable = false)
	private String firstName;
	
	@Column(name="LASTNAME1", length = 45, nullable = false)
	private String lastName1;
	
	@Column(name="LASTNAME2", length = 45)
	private String lastName2;
	
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey (name = "FK_USER_PROFILE__ADDRESS")
	@JoinColumn(name = "ADDRESS", unique = true)
	private Address address;
	
	@Column(name="HOME_PHONE", length = 45)
	private String homePhone;
	
	@Column(name="MOBILE_PHONE", length = 45)
	private String mobilePhone;
	
	@Column(name="EMAIL2", length = 45)
	private String email2;

	@Column(name="PICTURE", length = 45)
	private byte[] picture;

	public String getIdUserProfile() {
		return idUserProfile;
	}

	public void setIdUserProfile(String idUserProfile) {
		this.idUserProfile = idUserProfile;
	}

	public String getFirstName() {
		return WordUtils.capitalize(firstName);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName1() {
		return WordUtils.capitalize(lastName1);
	}

	public void setLastName1(String lastName1) {
		this.lastName1 = lastName1;
	}

	public String getLastName2() {
		return WordUtils.capitalize(lastName2);
	}

	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

}

