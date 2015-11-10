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

	
	public Address(String idAddress, String address1, String address2,
			String province, String city, String zipcode) {
		super();
		this.idAddress = idAddress;
		this.address1 = address1;
		this.address2 = address2;
		this.province = province;
		this.city = city;
		this.zipcode = zipcode;
	}

	public Address(String address1, String address2,
			String province, String city, String zipcode) {
		super();
		this.address1 = address1;
		this.address2 = address2;
		this.province = province;
		this.city = city;
		this.zipcode = zipcode;
	}
	
	public Address() {
		super();
	}



	@Id
	@Column(name = "idADDRESS")
	@GeneratedValue
	private String idAddress;
	
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

	public String getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(String idAddress) {
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
	
	public String getFullAddress() {
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
	
	@Override
	public boolean equals(Object o){
		
		boolean result = false;
		if(o instanceof Address){
			Address add = (Address)o;
			result = add.getAddress1().equalsIgnoreCase(this.address1)
					&& add.getAddress2().equalsIgnoreCase(this.address2)
					&& add.getCity().equalsIgnoreCase(this.city)
					&& add.getProvince().equalsIgnoreCase(this.province)
					&& add.getZipcode().equals(this.zipcode);
		}

		return result;
	}

}
