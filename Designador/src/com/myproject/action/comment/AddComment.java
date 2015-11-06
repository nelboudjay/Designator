package com.myproject.action.comment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.myproject.mail.MailService;
import com.myproject.model.Comment;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class AddComment extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 7158095113819658848L;

	private Map<String, Object> session;
	
	private String commentId;
	private String commentBody;
	
	private GenericService service;

	private MailService mailService;

	@Override
	public String execute() {

		Date date = new Date();

		Timestamp commentDate = new Timestamp(date.getTime());
		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();

		User user = (User) session.get("user");

		Comment comment;
		String emailSubject = user.getUserFullName() + " ha añadido un aviso";
		String emailbodyTitle = user.getUserFullName() + " ha añadido el siguiente aviso";
		if (commentId == null ||commentId.equals(""))
			comment = new Comment(commentBody.trim(), commentDate, user);
		else{
			eqRestrictions.put("commentId", new FieldCondition(commentId));
			comment = (Comment)service.GetUniqueModelData(Comment.class, eqRestrictions);
			if (comment == null)
				comment = new Comment(commentBody.trim(), commentDate, user);
			else{
				comment.setCommentBody(commentBody.trim());
				comment.setCommentDate(commentDate);
				emailSubject = user.getUserFullName() + " ha modificado un aviso";
				emailbodyTitle = user.getUserFullName() + " ha modificado el siguiente aviso";
			}
		}
		
		service.SaveOrUpdateModelData(comment);

		eqRestrictions.clear();
		List<?> users =  service.GetModelDataList(User.class, eqRestrictions, null, null);

		List<String> emailAddressesList = new  ArrayList<String>();
		users.stream().filter(us -> !((User)us).getEmail().equals(user.getEmail())).
			forEach(us ->   emailAddressesList.add(((User)us).getEmail()));
		
		if(emailAddressesList.size() > 0){
			String[] emailAddressesArray = new String[ emailAddressesList.size() ];
			emailAddressesList.toArray( emailAddressesArray );
			
			Map<String, String> templateData = new HashMap<String, String>();
			
			templateData.put("emailbodyTitle", emailbodyTitle);
			templateData.put("firstName",WordUtils.capitalize(user.getUserProfile().getFirstName()));
			templateData.put("commentBody", commentBody.trim().replaceAll("(\r\n|\n)", "<br />"));
			
			/*Send the email*/
			mailService.sendMail(emailAddressesArray, emailSubject, "comment.vm", templateData);
		}
		
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
	
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

}
