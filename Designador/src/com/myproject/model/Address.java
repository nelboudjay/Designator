package com.myproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ADDRESS")
public class Address {
	
	@Id
	@Column(name = "idADDRESS")
	@GeneratedValue
	private int idAddress;
	
	@Column(name="ADDRESS", length = 255)
	private String address;
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
