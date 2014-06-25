package com.myproject.action;


import java.util.HashMap;
import java.util.Map;

import com.myproject.model.PasswordChangeRequest;
import com.myproject.user.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class CheckIdRequest extends ActionSupport {
	private static final long serialVersionUID = 1121553022737166788L;
	
	private String id;
	
	private UserService userService;

	private PasswordChangeRequest passwordChangeRequest;


	@Override
	public String execute() {

				
		Map<String, Object> eqRestrictions = new HashMap<String, Object>();
		eqRestrictions.put("idPasswordChangeRequest", id);

		passwordChangeRequest = (PasswordChangeRequest) userService.GetUniqueModelData(
				PasswordChangeRequest.class, eqRestrictions);
		
		if (passwordChangeRequest == null)
			addActionError("Tu petición de recordar contraseña ha caducado o no existe. Deberá volver a realizarla.");

		return SUCCESS;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
