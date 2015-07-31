package com.myproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class Address implements Serializable{

	private static final long serialVersionUID = 5261261581605625466L;

	@Id
	@Column(name = "idADDRESS")
	@GeneratedValue
	private int idAddress;
	
	@Column(name="ADDRESS1", length = 255)
	private String address1;
	
	@Column(name="ADDRESS2", length = 255)
	private String address2;
	
	@Column(name="PROVINCE", length = 45)
	private String province;
	
	@Column(name="CITY", length = 45)
	private String city;
	
	@Column(name="ZIPCODE", length = 5)
	private String zipcode;

	public int getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getAddress() {
		return getAddress1() + " " + getAddress2();
	}


	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
