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

		Map<String, Object> session = actionInvocation.getInvocationContext()
				.getSession();
		
		user = (User) session.get("user");

		if (user == null) {
			addActionError(actionInvocation ,"Por favor, inicia sesión para continuar");
			return ActionSupport.LOGIN;
		}
		else if (!user.isAdmin()){
			addActionError(actionInvocation ,"No tienes permiso para ver esta página");
			return ActionSupport.ERROR;

		}

		return actionInvocation.invoke();
	}
	
	void addActionError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if(action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}
	
}
