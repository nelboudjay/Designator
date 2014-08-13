package com.myproject.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.myproject.model.PasswordChangeRequest;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class ChangePasswordAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = -4842105484814818581L;

	private Map<String, Object> session;
	
	private String password;
	private String repassword;
	private String id;

	private GenericService service;

	@Override
	public String execute() {

		Map<String, Object> eqRestrictions = new HashMap<String, Object>();
		eqRestrictions.put("idPasswordChangeRequest", id);

		PasswordChangeRequest passwordChangeRequest = (PasswordChangeRequest) service.GetUniqueModelData(
				PasswordChangeRequest.class, eqRestrictions);

		if (passwordChangeRequest == null)
			addActionError("Tu petición de recordar contraseña ha caducado o no existe. Deberá volver a realizarla.");
		else {

			User user = passwordChangeRequest.getUser();
			DesEncrypter encrypter = new DesEncrypter(getText("loginPass"));
			user.setPassword(encrypter.encrypt(getPassword()));
			service.SaveOrUpdateModelData(user);

			session.put("user", user);
			String userFullName = user.getUserFullName();
			session.put("userFullName", userFullName);
		}

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

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public void setService(GenericService service) {
		this.service = service;
	}


}
