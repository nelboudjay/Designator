package com.myproject.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.myproject.model.Address;
import com.myproject.model.User;
import com.myproject.model.UserProfile;
import com.myproject.model.UserRole;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class AddMember extends ActionSupport  {

	private static final long serialVersionUID = 7821048979321587609L;


	private String firstName, lastName1, lastName2;
	private String address1, address2, province, city, zipcode;
	private String homePhone, mobilePhone;
	private String email, email2;
	private File picture;
	private String pictureContentType, pictureFileName;
	private Boolean admin;
	
	private GenericService service;

	@Override
	@SkipValidation
	public String execute() {
		
		return NONE;
	}
	
	public String addMember(){
		
		
		String userName = lastName1.substring(0, 1) + firstName;
		
		if (fieldAlreadyExists(userName,"userName")) {
			String newUserName = userName;
			int i = 1;
			do{
				newUserName = userName + i++;
			}while(fieldAlreadyExists(newUserName,"userName"));
			
			userName = newUserName;
		}
		
		if(fieldAlreadyExists(email.trim(),"email")){
			addActionError("El correo eléctronico principal ya existe para otro usuario. Por favor, elige otro.");
			return INPUT;
		}
		
		if(!email2.trim().equals("") && fieldAlreadyExists(email2.trim(),"email")){
			addActionError("El correo eléctronico secundario ya existe para otro usuario. Por favor, elige otro.");
			return INPUT;
		}
		
		Address address = new Address(address1.trim(), address2.trim(), province.trim(), city.trim(), zipcode);

		
		byte[] bPicture = null;
		if(picture != null){
	        bPicture = new byte[(int) picture.length()];
	        
	        try {
		     FileInputStream fileInputStream = new FileInputStream(picture);
		     //convert file into array of bytes
		     fileInputStream.read(bPicture);
		     fileInputStream.close();
	        } catch (Exception e) {
		     e.printStackTrace();
	        }
		}
		
		UserProfile userProfile = new UserProfile(firstName.trim(), lastName1.trim(), lastName2.trim(),address, homePhone.trim(), mobilePhone.trim(), email2.trim(), bPicture);
		
		UserRole userRole;
		if(admin)
			userRole = new UserRole(1,"Admin");
		else
			userRole = new UserRole(2, "Referee");

		User user = new User(userName,email.trim(),userRole,userProfile);
		
		service.SaveOrUpdateModelData(user);

		addActionMessage("El nuevo mienbro ha sido añadido con exito");
		
		return SUCCESS;
	}
	


	public Boolean fieldAlreadyExists(String field, String fieldName){
		
		Map<String, Object> eqRestrictions = new HashMap<String, Object>();	

		eqRestrictions.put("fieldName", field);
			
		if ((User) service.GetUniqueModelData(User.class, eqRestrictions) != null)
			return true;
		else if (fieldName == "email" ){
			
			eqRestrictions.clear();
			eqRestrictions.put("email2", field);
			return ((UserProfile) service.GetUniqueModelData(UserProfile.class, eqRestrictions) != null);
		}
		else
			return false;
		
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName1(String lastName1) {
		this.lastName1 = lastName1;
	}

	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public void setPicture(File picture) {
		this.picture = picture;
	}

	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}

	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public void setService(GenericService service) {
		this.service = service;
	}



}
