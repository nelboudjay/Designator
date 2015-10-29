package com.myproject.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class GetImage extends ActionSupport implements SessionAware, ServletContextAware {

	private static final long serialVersionUID = -7320539330786726206L;
	
	private Map<String, Object> session;
    private ServletContext context;

	private GenericService service;

	private String idUser;
	private byte[] imageInBytes;
	
	@Override
	public String execute() {
	
		User user = (User) session.get("user");
		
		if(idUser == null || idUser.equals("") || idUser.equals(user.getIdUser()))
			imageInBytes = user.getUserProfile().getPicture();
		else
		{
			context = ServletActionContext.getServletContext();  
			user = (User)context.getAttribute("user");
			if(user != null && idUser.equals(user.getIdUser()))
				imageInBytes = user.getUserProfile().getPicture();
			else{
				Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

				eqRestrictions.put("idUser", new FieldCondition(idUser));
				user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
				
				if(user == null){
					addActionError("La foto que quieres mostrar no existe.");
					return INPUT;
				}
				else
					imageInBytes = user.getUserProfile().getPicture();	
			}
		}
			
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public byte[] getImageInBytes() {
		return imageInBytes;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
		
	}
	
	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public void setService(GenericService service) {
		this.service = service;
	}
	
}
