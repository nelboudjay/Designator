package com.myproject.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteUser extends ActionSupport implements  SessionAware, ServletContextAware {

	private static final long serialVersionUID = -9140151919678779260L;

	private String idUser;
	private GenericService service;

	private Map<String, Object> session;
    private ServletContext context;

	@Override
	public String execute() {
				
		Map<String, Object> eqRestrictions = new HashMap<String, Object>();
		eqRestrictions.put("idUser", idUser);
		
		User user = (User)service.GetUniqueModelData(User.class, eqRestrictions);
		
		System.out.println("idUser " + idUser);
		System.out.println(" session idUser " + ((User)session.get("user")).getIdUser());

		if(user != null && !idUser.equals(((User)session.get("user")).getIdUser())){
			addActionMessage("El miembro ha sido eliminado con exito");
			service.DeleteModelData(user);	
			return SUCCESS;
		}
		else{
			
			if(user == null)
				addActionError("El miembro que quieres eliminar no existe o ya se ha eliminado");
			else
				addActionError("No puedes eliminar tu perfil");
			
			eqRestrictions.clear();
			List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
			
			context.setAttribute("users", users);
			return INPUT;

		}
		
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}


	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
		
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
}
