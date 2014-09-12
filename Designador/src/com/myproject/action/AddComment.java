package com.myproject.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.myproject.model.Comment;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class AddComment extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 7158095113819658848L;

	private Map<String, Object> session;

	private String commentBody;

	private GenericService service;

	@Override
	@SkipValidation
	public String execute() {
		return NONE;
	}

	public String addComment() {
		clearFieldErrors();

		Date date = new Date();

		Timestamp timeStampComment = new Timestamp(date.getTime());

		User user = ((User) session.get("user"));

		Comment comment = new Comment(commentBody, timeStampComment, user);
		service.SaveOrUpdateModelData(comment);

		return SUCCESS;

	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public String getCommentBody() {
		return commentBody;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

}