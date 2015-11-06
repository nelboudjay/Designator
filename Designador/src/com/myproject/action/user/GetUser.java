package com.myproject.action.user;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class GetUser extends ActionSupport implements SessionAware, ServletContextAware {

	private static final long serialVersionUID = 7821048979321587609L;

	private Map<String, Object> session;
    private ServletContext context;

	private String idUser;
	private String email, userFullName, userRoleName, homePhone, mobilePhone;
	private boolean confirmed;
	
	
	private GenericService service;

	@Override
	public String execute() {
		
		User user;
		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

		List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		context.setAttribute("users", users);
		
		if (idUser == null || idUser.equals("")){		
			addActionError("Por favor, introduce el id del usuario que quieres ver.");
			return "not found";	
		}	
		else{
			
			user = (User)session.get("user");
	
			if (!idUser.equals(user.getIdUser())){
				eqRestrictions.put("idUser", new FieldCondition(idUser));
				user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
				if(user == null){
					addActionError("El usuario que has introducido no existe o ya se ha eliminado");
					return "not found";
				}
			}
			
			setUserFullName(user.getUserFullName());
			setEmail(user.getEmail());
			setConfirmed(!user.getPassword().equals(""));
			setUserRoleName(user.getUserRoleName());
			setHomePhone(user.getUserProfile().getHomePhone());
			setMobilePhone(user.getUserProfile().getMobilePhone());
			
			return NONE;
			
		}
	}
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
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

	
}
