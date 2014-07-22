package com.myproject.action;


import java.util.HashMap;
import java.util.Map;

import com.myproject.model.PasswordChangeRequest;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class CheckIdRequest extends ActionSupport {
	private static final long serialVersionUID = 1121553022737166788L;
	
	private String id;
	
	private GenericService service;

	private PasswordChangeRequest passwordChangeRequest;


	@Override
	public String execute() {

				
		Map<String, Object> eqRestrictions = new HashMap<String, Object>();
		eqRestrictions.put("idPasswordChangeRequest", id);

		passwordChangeRequest = (PasswordChangeRequest) service.GetUniqueModelData(
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

	public GenericService getService() {
		return service;
	}

	public void setService(GenericService service) {
		this.service = service;
	}


}
