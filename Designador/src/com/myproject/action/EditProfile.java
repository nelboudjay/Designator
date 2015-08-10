package com.myproject.action;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.myproject.model.Address;
import com.myproject.model.Comment;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class EditProfile extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 7821048979321587609L;

	private Map<String, Object> session;
	
	private String address1, address2, province, city, zipCode;
	private Address address;
	
	private GenericService service;

	@Override
	public String execute() {

		clearFieldErrors();



		User user = (User) session.get("user");

		System.out.println("hello " + address1 + address2 + province + city + zipCode);
		/*Comment comment;
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
		
		service.SaveOrUpdateModelData(comment);*/

		return SUCCESS;

	}

	
	
	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}



	public String getAddress2() {
		return address2;
	}



	public String getProvince() {
		return province;
	}



	public String getCity() {
		return city;
	}



	public String getZipCode() {
		return zipCode;
	}



	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

}
