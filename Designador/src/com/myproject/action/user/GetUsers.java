package com.myproject.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class GetUsers extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 8283127213666328713L;
	
	private List<?> users;
	private GenericService service;

	private Map<String, Object> session;

	@Override
	public String execute() {
		
		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	
		users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);

		User user = (User)session.get("user");
		
		if(!user.isAdmin())
			users.removeIf(usr -> ((User)usr).isPrivacy() && !((User)usr).getIdUser().equals(user.getIdUser()));
		
		return SUCCESS;
	}
	
	public List<?> getUsers() {
		return users;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}
}

