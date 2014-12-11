package com.myproject.interceptor;

import java.util.Map;

import com.myproject.model.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class PermissionInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7776599849281368327L;

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

		AuthenticationInterceptor authenticationInterceptor = new AuthenticationInterceptor();
		
		if (authenticationInterceptor.intercept(actionInvocation).equals(ActionSupport.SUCCESS)){
			
			user = authenticationInterceptor.getUser();
			
			if (!user.isAdmin()){
				addActionError(actionInvocation ,"No tienes permiso para ver esta página");
				return ActionSupport.ERROR;
			}
			else
				return actionInvocation.invoke();
		}
		else
			return ActionSupport.LOGIN;
		
	}
	
	void addActionError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if(action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}
	
}
