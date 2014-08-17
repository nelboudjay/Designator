package com.myproject.action;


import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.myproject.model.PasswordChangeRequest;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import com.opensymphony.xwork2.validator.annotations.Validations;

public class CheckIdRequest extends ActionSupport {
	private static final long serialVersionUID = 1121553022737166788L;
	
	private String id;
	private String password;
	private String repassword;

	private GenericService service;


	@Override
	@SkipValidation
	public String execute() {
		System.out.println("ho ho");

		Map<String, Object> eqRestrictions = new HashMap<String, Object>();
		eqRestrictions.put("idPasswordChangeRequest", id);

		PasswordChangeRequest passwordChangeRequest = (PasswordChangeRequest) service.GetUniqueModelData(
				PasswordChangeRequest.class, eqRestrictions);
		
		if (passwordChangeRequest == null)
			addActionError("Tu petición de recordar contraseña ha caducado o no existe. Deberá volver a realizarla.");

		return SUCCESS;

	}
	
	public String foo(){
		
		System.out.println("hi hi");
		return SUCCESS;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
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
	
	public void setService(GenericService service) {
		this.service = service;
	}


}
