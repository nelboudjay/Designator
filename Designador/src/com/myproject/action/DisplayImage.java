package com.myproject.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class DisplayImage implements Result {

	private static final long serialVersionUID = -665591164504609207L;

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		
		GetProfileImage action = (GetProfileImage) invocation.getAction();
		
		HttpServletResponse response = ServletActionContext.getResponse();
 
        response.getOutputStream().write(action.getImageInBytes());
        response.getOutputStream().flush();
	}

}
