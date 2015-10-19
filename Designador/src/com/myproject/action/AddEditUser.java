package com.myproject.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.lang.WordUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.Address;
import com.myproject.model.PasswordChangeRequest;
import com.myproject.model.User;
import com.myproject.model.UserProfile;
import com.myproject.model.UserRole;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddEditUser extends ActionSupport implements SessionAware, ServletContextAware {

	private static final long serialVersionUID = 7821048979321587609L;

	private Map<String, Object> session;
    private ServletContext context;


	private String idUser;
	private String firstName, lastName1, lastName2;
	private String address1, address2, province, city, zipcode;
	private String homePhone, mobilePhone;
	private String email, email2;
	private String userName, password, repassword;
	private boolean currentPicture;
	private File picture;
	private String pictureContentType, pictureFileName;
	private String idUserRole;
	private User user; 
	
	Map<String, Object> eqRestrictions = new HashMap<String, Object>();	
	
	private GenericService service;

	@Override
	public String execute() {
		
		if( ActionContext.getContext().getName().equalsIgnoreCase("addUser")){
			setIdUser("");
			return NONE;
		}
		else {
			
			user = (User)session.get("user");
			
			if (idUser == null || idUser.equals("") || idUser.equals(user.getIdUser())){
				context.setAttribute("user", user);
				return NONE;
			}else if(!user.isAdmin() && ActionContext.getContext().getName().equalsIgnoreCase("editUser")){
				addActionError("No tienes permiso para ver esta página");
				return ERROR;
				
			}
			else{
				
				Map<String, Object> eqRestrictions = new HashMap<String, Object>();	
		
				eqRestrictions.put("idUser", idUser);
		
				user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
				
				if(user != null){
					context.setAttribute("user", user);
					return NONE;
				}else{
					addActionError("El usuario que has introducido no existe o ya se ha eliminado");
					
					eqRestrictions.clear();
					List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
					
					context.setAttribute("users", users);
					
					return "not found";
				}
			}
		}
	}
	
	public String editUser() {
	
		user = (User)context.getAttribute("user");

		/*Check if the username changed already exists ONLY if the user updates his profile*/
		if(idUser.equals(((User)session.get("user")).getIdUser()) && 
				!user.getUserName().equalsIgnoreCase(userName.trim())){

			if(fieldAlreadyExists(userName.trim(), "userName")){
				addFieldError("userName","El nombre de usuario ya existe. Por favor, elige otro.");
				return INPUT;
			}

			user.setUserName(userName.trim());
			user.setPassword(password);
		}
		else {
			user.setUserName(user.getUserName());
			user.setPassword(user.getPassword());
		}
		
		if( !user.getEmail().equalsIgnoreCase(email.trim()) && fieldAlreadyExists(email.trim(),"email")){
			addFieldError("email", "El correo eléctronico principal ya existe. Por favor, elige otro.");
			return INPUT;
		}
		
		if(!email2.trim().equals("") && !user.getUserProfile().getEmail2().equalsIgnoreCase(email2.trim()) 
				&&  fieldAlreadyExists(email2.trim(),"email")){
			addFieldError("email2", "El correo eléctronico secundario ya existe. Por favor, elige otro.");
			return INPUT;
		}
		
		Address address = null;
		if(!address1.trim().equals("")  || !address2.trim().equals("") || !province.trim().equals("") 
				|| !city.trim().equals("") || !zipcode.equals(""))
			address = new Address(address1.trim(), address2.trim(), province.trim(), city.trim(), zipcode);
			
		//save image into database
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
		else if (currentPicture)
			bPicture = user.getUserProfile().getPicture();
        
		UserProfile userProfile = new UserProfile(user.getUserProfile().getIdUserProfile(), 
				firstName.trim(), lastName1.trim(), lastName2.trim(),address, homePhone.trim(), mobilePhone.trim(), email2.trim(), bPicture);
		user.setEmail(email.trim());
		user.setUserProfile(userProfile);

		UserRole userRole;
		
		if(idUserRole == null)
			userRole = user.getUserRole();
		else{
			switch(idUserRole) {
				case "1":	userRole = new UserRole("1","Admin");
							break;
				case "2":	userRole = new UserRole("2", "Referee");
							break;
				case "3":	userRole = new UserRole("3", "All");
							break;
				default:	userRole = user.getUserRole();
							break;
			}
		}
		
		user.setUserRole(userRole);
		
		service.SaveOrUpdateModelData(user);
		
		if(user.getIdUser().equals(((User)session.get("user")).getIdUser())){
			session.put("user", user);
			addActionMessage("Tu perfil ha sido actualizado con exito");
		}
		else
			addActionMessage("El miembro "  + user.getUserFullName() + " ha sido actualizado con exito");
		
		setIdUser(user.getIdUser());
		return SUCCESS;

	}
	
	public Boolean fieldAlreadyExists(String field, String fieldName){
		
		Map<String, Object> eqRestrictions = new HashMap<String, Object>();	

		eqRestrictions.put(fieldName, field);
			
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

	
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
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

	public String getLastName2() {
		return lastName2;
	}
	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
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

	public void setPicture(File picture) {
		this.picture = picture;
	}


	public boolean isCurrentPicture() {
		return currentPicture;
	}

	public void setCurrentPicture(boolean currentPicture) {
		this.currentPicture = currentPicture;
	}

	public String getPictureContentType() {
		return pictureContentType;
	}

	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}

	public String getPictureFileName() {
		return pictureFileName;
	}

	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	
	public String getIdUserRole() {
		return idUserRole;
	}

	public void setIdUserRole(String idUserRole) {
		this.idUserRole = idUserRole;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
	
}
