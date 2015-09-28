package com.myproject.action;

import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.myproject.model.Address;
import com.myproject.model.User;
import com.myproject.model.UserProfile;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class EditProfile extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 7821048979321587609L;

	private Map<String, Object> session;
	
	private String firstName, lastName1, lastName2;
	private String address1, address2, province, city, zipcode;
	private String homePhone, mobilePhone;
	private String email, email2;
	private String userName, password, repassword;
	private Blob picture;
	
	private User user; 

	Map<String, Object> eqRestrictions = new HashMap<String, Object>();	
	
	private GenericService service;

	@Override
	@SkipValidation
	public String execute() {
		return NONE;
	}
	
	public String editProfile() {

		user =  (User) session.get("user");
		
		/*Check if the username changed already exists*/
		if(!user.getUserName().equalsIgnoreCase(userName.trim())){

			eqRestrictions.put("userName", userName.trim());
			
			if ((User) service.GetUniqueModelData(User.class, eqRestrictions) != null) {
				addActionError("El nombre de usuario ya existe. Por favor, elige otro.");
				return INPUT;
			}			
		}
		
		if(emailAlreadyExists(email.trim())){
			addActionError("El correo eléctronico principal ya existe. Por favor, elige otro.");
			return INPUT;
		}
		
		if(!email2.trim().equals("") && emailAlreadyExists(email2.trim())){
			addActionError("El correo eléctronico secundario ya existe. Por favor, elige otro.");
			return INPUT;
		}
		
		eqRestrictions.clear();
		eqRestrictions.put("idUser", user.getIdUser());
		user = (User)service.GetUniqueModelData(User.class, eqRestrictions);
		if (user == null){
			addActionError("No se puede actualizar el perfil. Este usuario ya no existe");
			return INPUT;
		}
		else{
			Address address = new Address(user.getUserProfile().getAddress().getIdAddress(), address1.trim(), address2.trim(), province.trim(), city.trim(), zipcode);
			service.SaveOrUpdateModelData(address);

			UserProfile userProfile = new UserProfile(user.getUserProfile().getIdUserProfile(), 
					firstName.trim(), lastName1.trim(), lastName2.trim(),address, homePhone.trim(), mobilePhone.trim(), email2.trim(), picture);
			service.SaveOrUpdateModelData(userProfile);
			
			user.setEmail(email.trim());
			user.setPassword(password);
			user.setUserName(userName.trim());
			user.setUserProfile(userProfile);
			service.SaveOrUpdateModelData(user);

			session.put("user", user);
			addActionMessage("El perfil has sido actualizado con exito");
		}
		return SUCCESS;

	}

	public Boolean emailAlreadyExists(String email){
		
		if(!user.getEmail().equalsIgnoreCase(email) && 
				((user.getUserProfile().getEmail2() == null) || !user.getUserProfile().getEmail2().equalsIgnoreCase(email))){
			eqRestrictions.clear();
			eqRestrictions.put("email", email);
			
			if ((User) service.GetUniqueModelData(User.class, eqRestrictions) != null)
				return true;
			else{
				
				eqRestrictions.clear();
				eqRestrictions.put("email2", email);
				return ((UserProfile) service.GetUniqueModelData(UserProfile.class, eqRestrictions) != null);
				
			}
		}
		else
			return false;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName1() {
		return lastName1;
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
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail2() {
		return email2;
	}


	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepassword() {
		return repassword;
	}

	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

}
