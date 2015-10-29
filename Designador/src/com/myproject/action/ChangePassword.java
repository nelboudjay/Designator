package com.myproject.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.PasswordChangeRequest;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
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

			Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();
			eqRestrictions.put("token", new FieldCondition(id));

			passwordChangeRequest = (PasswordChangeRequest) service
					.GetUniqueModelData(PasswordChangeRequest.class,
							eqRestrictions);

			if (passwordChangeRequest == null){
				addActionError("Tu petición para restablecer la contraseña ha caducado o no existe. Deberás volver a realizarla.");
				addActionError("Si tu petición es para crear contraseña para tu nueva cuenta. La cuenta ya ha sido eliminada." +
								" Ponte en contacto con tu administrador para más detalles.");
				return ERROR;

			}else{
				/** We store the user in the ServletContext and not in the SessionAware because we don't want
				  to open a session **/
				context.setAttribute("user",
						passwordChangeRequest.getIdPasswordChangeRequest());
				context.setAttribute("token", id);
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

			if (user == null){
				
				addActionError("Tu contraseña no ha podido ser actualizada. Vuelve a intentarlo más tarde o ponte en contacto con tu administrador.");
				return ERROR;
			}else {
								
				user.setPassword(getPassword());
				service.SaveOrUpdateModelData(user);
	
				service.DropEvent("DELETE_PASSWORD_CHANGE_REQUEST_"
						+ user.getIdUser());
				
				String token = (String)context.getAttribute("token");
				
				passwordChangeRequest = new PasswordChangeRequest(user, token);
				service.DeleteModelData(passwordChangeRequest);
				
				session.put("user", user);
				
			}
		}
		else{
			
			user.setPassword(getPassword());
			service.SaveOrUpdateModelData(user);
		}
			
		addActionMessage("Tu contraseña ha sido actualizada correctamente");

		return SUCCESS;
	}

	
	public String getId() {
		return id;
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

	public void setPasswordChangeRequest(PasswordChangeRequest passwordChangeRequest) {
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