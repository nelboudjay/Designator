package com.myproject.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.myproject.model.Comment;
import com.myproject.model.User;
import com.myproject.user.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class AddCommentAction extends ActionSupport implements SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7158095113819658848L;

	private Map<String, Object> session;

	private User user;
	private Comment comment;

	private String commentBody;

	private UserService userService;

	@Override
	public String execute() {
		
		clearFieldErrors();
		
		Date date = new Date();
		
		Timestamp timeStampComment = new Timestamp(date.getTime());
		
		user = ((User) session.get("user"));
		
		comment = new Comment(commentBody,timeStampComment,user);
		userService.SaveOrUpdateModelData(comment);;

		return SUCCESS;

	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
