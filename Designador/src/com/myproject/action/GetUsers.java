package com.myproject.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class GetUsers extends ActionSupport{

	private static final long serialVersionUID = 8283127213666328713L;
	
	private List<?> users;
	private GenericService service;

	@Override
	public String execute() {
		Map<String, Object> eqRestrictions = new HashMap<String, Object>();	
		
		users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		
		return SUCCESS;
	}
	
	public List<?> getUsers() {
		return users;
	}

	public void setService(GenericService service) {
		this.service = service;
	}
}

