package com.myproject.action;

import java.nio.file.Files;
import java.nio.file.Paths;


import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

public class DisplayImage implements Result {

	private static final long serialVersionUID = -665591164504609207L;


	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		
		GetImage action = (GetImage) invocation.getAction();
		
		HttpServletResponse response = ServletActionContext.getResponse();
 
		byte[] image = action.getImageInBytes();
		if(image == null)
			image = Files.readAllBytes(Paths.get(action.getContext().getRealPath("/images/avatar-icon.png")));

        response.getOutputStream().write(image);
        response.getOutputStream().flush();
	}



}
