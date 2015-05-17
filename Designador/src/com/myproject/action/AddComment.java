package com.myproject.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.myproject.model.Comment;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class AddComment extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 7158095113819658848L;

	private Map<String, Object> session;
	
	private String commentId;
	private String commentBody;
	private GenericService service;

	@Override
	public String execute() {

		clearFieldErrors();

		Date date = new Date();

		Timestamp commentDate = new Timestamp(date.getTime());

		User user = (User) session.get("user");

		Comment comment;
		if (commentId == null ||commentId.equals(""))
			comment = new Comment(commentBody.trim(), commentDate, user);
		else{
			Map<String, Object> eqRestrictions = new HashMap<String, Object>();
			eqRestrictions.put("commentId", commentId);
			comment = (Comment)service.GetUniqueModelData(Comment.class, eqRestrictions);
			if (comment == null)
				comment = new Comment(commentBody.trim(), commentDate, user);
			else{
				comment.setCommentBody(commentBody.trim());
				comment.setCommentDate(commentDate);
			}
		}
		
		service.SaveOrUpdateModelData(comment);

		return SUCCESS;

	}
	
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
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
