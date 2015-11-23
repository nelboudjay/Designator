package com.myproject.action.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.Game;
import com.myproject.model.PasswordChangeRequest;
import com.myproject.model.User;
import com.myproject.model.UserRefereeType;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteUser extends ActionSupport implements  SessionAware, ServletContextAware {

	private static final long serialVersionUID = -9140151919678779260L;

	private String idUser;
	private GenericService service;

	private Map<String, Object> session;
    private ServletContext context;

	@Override
	public String execute() {
				
		if (idUser == null || idUser.equals("")){
			
			addActionError("Por favor, introduce el id del usuario que quieres eliminar.");
			return returnInput();
			
		}
		else{
			
			User user = (User)session.get("user");
			
			if(idUser.equals(user.getIdUser())){
				
				addActionError("No puedes eliminar tu propio perfil.");
				return returnInput();
			}
			else{
				
				Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();
				eqRestrictions.put("idUser", new FieldCondition (idUser));
				user = (User)service.GetUniqueModelData(User.class, eqRestrictions);
				
				if(user == null){
					addActionError("El miembro que quieres eliminar no existe o ya se ha eliminado");
					return returnInput();
				}
				else{
					eqRestrictions.clear();
					eqRestrictions.put("idPasswordChangeRequest", new FieldCondition (user));
					
					PasswordChangeRequest passwordChangeRequest = (PasswordChangeRequest)service.GetUniqueModelData(PasswordChangeRequest.class, eqRestrictions);
					if(passwordChangeRequest != null){
						service.DropEvent("DELETE_PASSWORD_CHANGE_REQUEST_" + idUser);
						service.DeleteModelData(passwordChangeRequest);	
					}
					
					eqRestrictions.clear();
					eqRestrictions.put("user", new FieldCondition(user));

					List<?> userRefereeTypes = service.GetModelDataList(UserRefereeType.class, eqRestrictions, null, null);	
					userRefereeTypes.forEach(userRefereeType -> service.DeleteModelData(userRefereeType));
					
					
					if(user.isAdmin()){
						
						eqRestrictions.clear();
						eqRestrictions.put("lastUpdaterUser", new FieldCondition(user));
						List<?> games = service.GetModelDataList(Game.class, eqRestrictions, null, null);	
						
						User sessionUser = (User)session.get("user");
						Date date = new Date();

						games.forEach(game -> {
								((Game)game).setLastUpdaterUser(sessionUser);
								((Game)game).setLastUpdatedDate(new Timestamp(date.getTime()));
								service.SaveOrUpdateModelData(game);
								}
						);
					}
					
					service.DeleteModelData(user);	

					addActionMessage("El miembro ha sido eliminado con exito");
					return SUCCESS;
				}
			}
		}
	}

	String returnInput(){
		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();
		List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		context.setAttribute("users", users);
		return INPUT;
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
