package com.myproject.action.user;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.lang.WordUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.mail.MailService;
import com.myproject.model.Address;
import com.myproject.model.PasswordChangeRequest;
import com.myproject.model.User;
import com.myproject.model.UserProfile;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
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
	private int userRole;
	private boolean refereeType1, refereeType2, refereeType3, refereeType4, refereeType5, refereeType6;
	
	private GenericService service;

	private MailService mailService;

	
	@Override
	@SkipValidation
	public String execute() {

		if( ActionContext.getContext().getName().equalsIgnoreCase("addUser")){			
			setIdUser(null); /*If the link is addUser?idUser=xxx*/
			return NONE;
		}else if (idUser == null || idUser.equals("")){
			
			addActionError("Por favor, introduce el id del usuario que quieres editar.");

			Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();
			List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
			context.setAttribute("users", users);
			
			return "not found";
			
		}	
		else{
			
			User user = (User)session.get("user");
			
			if (idUser.equals(user.getIdUser())){
				setUserName(user.getUserName());
				setPassword(user.getPassword());
			}
			else if(!user.isAdmin()){
				addActionError("No tienes permiso para ver esta página");
				return ERROR;		
			}
			else{
				
				Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	
				eqRestrictions.put("idUser", new FieldCondition(idUser));
				user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
				
				if(user == null){
					addActionError("El usuario que has introducido no existe o ya se ha eliminado");
					
					List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);	
					context.setAttribute("users", users);
					
					return "not found";
				}
			}
			
			context.setAttribute("userFullName", user.getUserFullName());
			
			setIdUser(user.getIdUser());
			setFirstName(user.getUserProfile().getFirstName());
			setLastName1(user.getUserProfile().getLastName1());
			setLastName2(user.getUserProfile().getLastName2());
			if(user.getUserProfile().getAddress() != null){
				setAddress1(user.getUserProfile().getAddress().getAddress1());
				setAddress2(user.getUserProfile().getAddress().getAddress2());
				setCity(user.getUserProfile().getAddress().getCity());
				setProvince(user.getUserProfile().getAddress().getProvince());
				setZipcode(user.getUserProfile().getAddress().getZipcode());
			}
			setMobilePhone(user.getUserProfile().getMobilePhone());
			setHomePhone(user.getUserProfile().getHomePhone());
			setEmail(user.getEmail());
			setEmail2(user.getUserProfile().getEmail2());
			if(((User)session.get("user")).isAdmin()) /*Referee updating his profile*/
				setUserRole(user.getUserRole());

			setCurrentPicture(user.getUserProfile().getPicture() != null);
			return NONE;

		}
	}
	
	public String editUser(){
		
		User user;
		if (idUser == null || idUser.equals("")){
			
			addActionError("Por favor, introduce el id del usuario que quieres editar.");
			
			Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();
			List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
			context.setAttribute("users", users);
			
			return "not found";
			
		}
		else{
			
			user = (User)session.get("user");
			
			if(idUser.equals(user.getIdUser())){
				if( !userName.trim().equalsIgnoreCase(user.getUserName()) 
						&& fieldAlreadyExists(userName.trim(), "userName")){

					addFieldError("userName","El nombre de usuario ya existe. Por favor, elige otro.");
					return INPUT;
				}
				else{
					user.setUserName(userName.trim());
					user.setPassword(password);
				}
			}
			else if(!user.isAdmin()){
				addActionError("No tienes permiso para ver esta página");
				return ERROR;		
			}
			else{

				Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	
				eqRestrictions.put("idUser", new FieldCondition(idUser));
				user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
				
				if(user == null){
					
					addActionError("El usuario que has introducido no existe o ya se ha eliminado");
					
					List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);	
					context.setAttribute("users", users);
					
					return "not found";
				}
			}
			
			if( !user.getEmail().equalsIgnoreCase(email.trim()) && fieldAlreadyExists(email.trim(),"email")){
				addFieldError("email", "El correo eléctronico principal ya existe. Por favor, elige otro.");
				return INPUT;
			}
			else
				user.setEmail(email.trim());
			
			if(!email2.trim().equals("") && !user.getUserProfile().getEmail2().equalsIgnoreCase(email2.trim()) 
					&&  fieldAlreadyExists(email2.trim(),"email")){
				addFieldError("email2", "El correo eléctronico secundario ya existe. Por favor, elige otro.");
				return INPUT;
			}
			
			Address address = null;
			if(!address1.trim().equals("")  || !address2.trim().equals("") || !province.trim().equals("") 
					|| !city.trim().equals("") || !zipcode.equals("")){
				if(user.getUserProfile().getAddress() == null)
					address = new Address(address1.trim(), address2.trim(), province.trim(), city.trim(), zipcode);
				else
					address = new Address(user.getUserProfile().getAddress().getIdAddress(),address1.trim(), address2.trim(), province.trim(), city.trim(), zipcode);

			}
			
			
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
			user.setUserProfile(userProfile);
			

			if(!((User)session.get("user")).isAdmin()) /*Referee updating his profile*/
				userRole = user.getUserRole();
			else{

				if(userRole != User.REFEREE && userRole != User.BOTH)
					userRole = User.ADMIN;
			}
			
			user.setUserRole(userRole);
			
			service.SaveOrUpdateModelData(user);
				
			
			if(user.getIdUser().equals(((User)session.get("user")).getIdUser())){
				session.put("user", user);
				addActionMessage("Tu perfil ha sido actualizado con exito");
			}
			else
				addActionMessage("El miembro "  + user.getUserFullName() + " ha sido actualizado con exito");
			
			return SUCCESS;

		}
	}

	public String addUser(){
		
		String userName = lastName1.substring(0, 1) + firstName;
		
		if (fieldAlreadyExists(userName,"userName")) {
			String newUserName = userName;
			int i = 1;
			do{
				newUserName = userName + i++;
			}while(fieldAlreadyExists(newUserName,"userName"));
			
			userName = newUserName;
		}
		
		Address address = null;
		if(!address1.trim().equals("")  || !address2.trim().equals("") || !province.trim().equals("") 
				|| !city.trim().equals("") || !zipcode.equals(""))
			address = new Address(address1.trim(), address2.trim(), province.trim(), city.trim(), zipcode);
		
		if(fieldAlreadyExists(email.trim(),"email")){
			addActionError("El correo eléctronico principal ya existe para otro usuario. Por favor, elige otro.");
			return INPUT;
		}
		
		if(!email2.trim().equals("") && fieldAlreadyExists(email2.trim(),"email")){
			addActionError("El correo eléctronico secundario ya existe para otro usuario. Por favor, elige otro.");
			return INPUT;
		}
		
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


		if(userRole != User.REFEREE && userRole != User.BOTH)
			userRole = User.ADMIN;
		
		User user = new User(userName,email.trim(),userRole,userProfile);
		service.SaveOrUpdateModelData(user);
		setIdUser(user.getIdUser());
		
		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();
		PasswordChangeRequest passwordChangeRequest;
		String token;
		
		do {
			token = UUID.randomUUID().toString()
					.replaceAll("-", "");
			eqRestrictions.put("token",
					new FieldCondition(token));
			passwordChangeRequest = (PasswordChangeRequest) service
					.GetUniqueModelData(PasswordChangeRequest.class,
							eqRestrictions);
		} while (passwordChangeRequest != null);
		
		/*Save the password request in the database*/
		passwordChangeRequest = new PasswordChangeRequest(user, token);
		service.SaveOrUpdateModelData(passwordChangeRequest);
		
		if (address != null)
			service.CreateEvent("DELETE_PASSWORD_CHANGE_REQUEST_"
				+ user.getIdUser(), "PASSWORD_CHANGE_REQUEST, USER, USER_PROFILE, ADDRESS", 
				"PASSWORD_CHANGE_REQUEST JOIN USER ON PASSWORD_CHANGE_REQUEST.idPASSWORD_CHANGE_REQUEST = USER.idUSER " +
				"JOIN USER_PROFILE ON USER.USER_PROFILE = USER_PROFILE.idUSER_PROFILE " +
				"JOIN ADDRESS ON USER_PROFILE.ADDRESS = ADDRESS.idADDRESS "	,
				"idPASSWORD_CHANGE_REQUEST", "" + user.getIdUser(), "WEEK");
		else
			service.CreateEvent("DELETE_PASSWORD_CHANGE_REQUEST_"
					+ user.getIdUser(), "PASSWORD_CHANGE_REQUEST, USER, USER_PROFILE", 
					"PASSWORD_CHANGE_REQUEST JOIN USER ON PASSWORD_CHANGE_REQUEST.idPASSWORD_CHANGE_REQUEST = USER.idUSER " +
					"JOIN USER_PROFILE ON USER.USER_PROFILE = USER_PROFILE.idUSER_PROFILE " ,
					"idPASSWORD_CHANGE_REQUEST", "" + user.getIdUser(), "WEEK");
		
		Map<String, String> templateData = new HashMap<String, String>();
		
		templateData.put("token", token);
		templateData.put("firstName",WordUtils.capitalize(firstName.trim()));
		templateData.put("adminFirstName", WordUtils.capitalize(((User)session.get("user")).getUserProfile().getFirstName()));
		
		mailService.sendMail(new String[]{email},"Instrucciones para completar tu registro en Designador",
				"confirmRegistrationInstructions.vm", templateData);
		
		addActionMessage("Se ha sido añadido un nuevo miembro con exito. Un correo electrónico ha sido enviado a él para confirmar su registro.");
		
		return SUCCESS;
	}
	
	
	public Boolean fieldAlreadyExists(String field, String fieldName){
		
		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

		eqRestrictions.put(fieldName, new FieldCondition(field));
			
		if ((User) service.GetUniqueModelData(User.class, eqRestrictions) != null)
			return true;
		else if (fieldName == "email" ){
			
			eqRestrictions.clear();
			eqRestrictions.put("email2", new FieldCondition(field));
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
	
	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	
	public boolean isRefereeType1() {
		return refereeType1;
	}

	public void setRefereeType1(boolean refereeType1) {
		this.refereeType1 = refereeType1;
	}

	public boolean isRefereeType2() {
		return refereeType2;
	}

	public void setRefereeType2(boolean refereeType2) {
		this.refereeType2 = refereeType2;
	}

	public boolean isRefereeType3() {
		return refereeType3;
	}

	public void setRefereeType3(boolean refereeType3) {
		this.refereeType3 = refereeType3;
	}

	public boolean isRefereeType4() {
		return refereeType4;
	}

	public void setRefereeType4(boolean refereeType4) {
		this.refereeType4 = refereeType4;
	}

	public boolean isRefereeType5() {
		return refereeType5;
	}

	public void setRefereeType5(boolean refereeType5) {
		this.refereeType5 = refereeType5;
	}

	public boolean isRefereeType6() {
		return refereeType6;
	}

	public void setRefereeType6(boolean refereeType6) {
		this.refereeType6 = refereeType6;
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
	
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
}
