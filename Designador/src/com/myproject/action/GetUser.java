package com.myproject.action;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class GetUser extends ActionSupport implements SessionAware, ServletContextAware {

	private static final long serialVersionUID = 7821048979321587609L;

	private Map<String, Object> session;
    private ServletContext context;


	private String idUser;

	
	Map<String, Object> eqRestrictions = new HashMap<String, Object>();	
	
	private GenericService service;

	@Override
	public String execute() {
		
		User user;
		List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		context.setAttribute("users", users);
		
		if (idUser == null || idUser.equals("")){		
			addActionError("Por favor, introduce el id del usuario que quieres ver.");
			return "not found";	
		}	
		else{
			user = (User)session.get("user");
	
			if (idUser.equals(user.getIdUser())){
				
				context.setAttribute("userFullName", user.getUserFullName());
				context.setAttribute("email", user.getEmail());
				context.setAttribute("confirmed", !user.getPassword().equals(""));
				context.setAttribute("userRoleName", user.getUserRole().getIdUserRole());

				return NONE;
			}
			else{
				
				Map<String, Object> eqRestrictions = new HashMap<String, Object>();	
		
				eqRestrictions.put("idUser", idUser);
				user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
				
				if(user != null){
					context.setAttribute("userFullName", user.getUserFullName());
					context.setAttribute("email", user.getEmail());
					context.setAttribute("confirmed", !user.getPassword().equals(""));
					context.setAttribute("userRoleName", user.getUserRole().getIdUserRole());
					return NONE;
				}else{
					addActionError("El usuario que has introducido no existe o ya se ha eliminado");
					return "not found";
				}
			}
		}
	}
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
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
