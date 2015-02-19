package com.myproject.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.Comment;
import com.myproject.model.PasswordChangeRequest;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.DesEncrypter;
import com.opensymphony.xwork2.ActionSupport;

public class ChangePassword extends ActionSupport implements SessionAware,
		ServletContextAware {

	private static final long serialVersionUID = 1121553022737166788L;

	private Map<String, Object> session;
	private ServletContext context;

	private String id;
	private String password;
	private String repassword;

	private PasswordChangeRequest passwordChangeRequest;
	
	private GenericService service;

	@Override
	@SkipValidation
	public String execute() {

		User user = (User) session.get("user");

		if (user == null) {

			Map<String, Object> eqRestrictions = new HashMap<String, Object>();
			eqRestrictions.put("token", id);

			passwordChangeRequest = (PasswordChangeRequest) service
					.GetUniqueModelData(PasswordChangeRequest.class,
							eqRestrictions);

			if (passwordChangeRequest == null){
				addActionError("Tu petición de recordar contraseña ha caducado o no existe. Deberá volver a realizarla.");
				return ERROR;

			}else{
				/* We store the user in the ServletContext and not in the SessionAware because we don't want
				 * to open a session */
				context.setAttribute("user",
						passwordChangeRequest.getIdPasswordChangeRequest());
				return NONE;

			}
				
		}
		else
			return NONE;

	}

	public String changePassword() {

		User user = (User) session.get("user");
		
		if (user == null){
		 
			user = (User) context.getAttribute("user");

			if (user != null) {
	
				DesEncrypter encrypter = new DesEncrypter(getText("loginPass"));
				user.setPassword(encrypter.encrypt(getPassword()));
				service.SaveOrUpdateModelData(user);
	
				session.put("user", user);
				String userFullName = user.getUserFullName();
				session.put("userFullName", userFullName);
				
				Map<String, Object> eqRestrictions = new HashMap<String, Object>();
				
				List<?> comments = service.GetModelDataList(Comment.class, eqRestrictions);
    			session.put("comments",comments);
			}
		}
		else{
			
			DesEncrypter encrypter = new DesEncrypter(getText("loginPass"));
			user.setPassword(encrypter.encrypt(getPassword()));
			service.SaveOrUpdateModelData(user);
		}
			
		addActionMessage("Tu contraseña ha sido cambiada correctamente");

		return SUCCESS;
	}

	public void setId(String id) {
		this.id = id;
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

	public PasswordChangeRequest getPasswordChangeRequest() {
		return passwordChangeRequest;
	}

	public void setPasswordChangeRequest(
			PasswordChangeRequest passwordChangeRequest) {
		this.passwordChangeRequest = passwordChangeRequest;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;

	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}