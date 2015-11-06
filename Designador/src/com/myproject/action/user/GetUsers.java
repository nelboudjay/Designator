package com.myproject.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class GetUsers extends ActionSupport{

	private static final long serialVersionUID = 8283127213666328713L;
	
	private List<?> users;
	private GenericService service;

	@Override
	public String execute() {
		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	
		
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

