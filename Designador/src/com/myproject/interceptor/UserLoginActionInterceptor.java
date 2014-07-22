package com.myproject.interceptor;

import java.util.Map;

import com.myproject.model.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class UserLoginActionInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7417439885096083694L;

	Map<String, Object> session;

	private User user;
	
	@Override
	public void destroy() {
	}

	@Override
	public void init() {		
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		Map<String, Object> session = actionInvocation.getInvocationContext()
				.getSession();
		
		user = (User) session.get("user");
		
		if (user == null) 
			return actionInvocation.invoke();
		else 
			return ActionSupport.SUCCESS;

	}

}
