package com.myproject.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.myproject.model.User;
import com.opensymphony.xwork2.ActionSupport;

public class GetImage extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = -7320539330786726206L;
	
	private Map<String, Object> session;

	private boolean profileImage;
	
	private byte[] imageInBytes;
	
	@Override
	public String execute() {
		
		if(profileImage){
			User user = (User) session.get("user");
			imageInBytes = user.getUserProfile().getPicture();
		}
		else		
			imageInBytes = null;
		
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public byte[] getImageInBytes() {
		return imageInBytes;
	}

	public void setProfileImage(boolean profileImage) {
		this.profileImage = profileImage;
	}
	

}
