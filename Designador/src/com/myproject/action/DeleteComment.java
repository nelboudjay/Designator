package com.myproject.action;

import java.util.HashMap;
import java.util.Map;

import com.myproject.model.Comment;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteComment extends ActionSupport {

	private static final long serialVersionUID = -9140151919678779260L;

	private String idComment;
	private GenericService service;

	@Override
	public String execute() {
				
		Map<String, Object> eqRestrictions = new HashMap<String, Object>();
		eqRestrictions.put("idComment", idComment);
		
		Comment comment = (Comment)service.GetUniqueModelData(Comment.class, eqRestrictions);
		
		if (comment == null){
			addActionError("El comentario que quieres eliminar no existe o ya se ha eliminado");
			return INPUT;
		}
		else{
			service.DeleteModelData(comment);
			return SUCCESS;
		}
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

	public void setService(GenericService service) {
		this.service = service;
	}
	

}