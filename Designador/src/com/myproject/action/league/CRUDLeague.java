package com.myproject.action.league;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.League;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CRUDLeague  extends ActionSupport implements ServletContextAware{

	private static final long serialVersionUID = -4438495252365119204L;
	
	private String idLeague;
	private String leagueName;
	private List<?> leagues;
	private League league;

	private GenericService service;

    private ServletContext context;

	private Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

	@Override
	@SkipValidation
	public String execute() {
		
		if( ActionContext.getContext().getName().equalsIgnoreCase("addEditLeague")){
			
			if (idLeague != null && !idLeague.equals("")){
				eqRestrictions.put("idLeague", new FieldCondition(idLeague));
				league = (League) service.GetUniqueModelData(League.class, eqRestrictions);
				if(league != null){
					setIdLeague(league.getIdLeague());
					setLeagueName(league.getLeagueName());
				}
			}
			
			return NONE;
			
		}else{
			leagues = service.GetModelDataList(League.class, eqRestrictions, "leagueName", true);
			return SUCCESS;
		}
	}

	public String addEditLeague(){
		
		/*Editing an existing League*/
		if(idLeague != null && !idLeague.equals("")){
			eqRestrictions.put("idLeague", new FieldCondition(idLeague));
			league = (League) service.GetUniqueModelData(League.class, eqRestrictions);			
			
			if(league != null){
				if(!leagueName.trim().equalsIgnoreCase(league.getLeagueName())){
					if (leagueAlreadyExists(leagueName.trim())){
						addFieldError("leagueName","El nombre de la competición ya existe. Por favor, elige otro.");
						return INPUT;
					 }
					else{						
						league.setLeagueName(leagueName.trim().substring(0,1).toUpperCase() + leagueName.trim().substring(1));
						service.SaveOrUpdateModelData(league);
					}
				}
				
				addActionMessage("Se ha actualizado el nombre de la competición con exito.");
				return SUCCESS;
	
			}
		}
		
		/*Adding a new League*/
		
		if(leagueAlreadyExists(leagueName.trim())){
			addFieldError("leagueName","El nombre de la competición ya existe. Por favor, elige otro.");
			return INPUT;
		}
		else{
			
			league = new League(leagueName.trim().substring(0,1).toUpperCase() + leagueName.trim().substring(1)	);
			service.SaveOrUpdateModelData(league);
			addActionMessage("Se ha sido añadido una nueva competición con exito.");
			return SUCCESS;
		}
	}
	
	@SkipValidation
	public String deleteLeague(){
		
		if (idLeague == null || idLeague.equals("")){
			
			addActionError("Por favor, introduce el id de la competición que quieres eliminar.");
			leagues = service.GetModelDataList(League.class, eqRestrictions, "leagueName", true);
			context.setAttribute("leagues", leagues);
			return INPUT;
		}
		else{
			eqRestrictions.put("idLeague", new FieldCondition(idLeague));
			league = (League) service.GetUniqueModelData(League.class, eqRestrictions);			
			
			if(league != null){
				service.DeleteModelData(league);
				addActionMessage("La competición ha sido eliminada con exito.");
				return SUCCESS;

			}
			else{
				addActionError("La competición que quieres eliminar no existe o ya se ha eliminado.");
				eqRestrictions.clear();
				leagues = service.GetModelDataList(League.class, eqRestrictions, "leagueName", true);
				context.setAttribute("leagues", leagues);
				return INPUT;
			}

		}
	}
	
	public Boolean leagueAlreadyExists(String leagueName){
		
		eqRestrictions.clear();
		eqRestrictions.put("leagueName", new FieldCondition(leagueName));
			
		return (League) service.GetUniqueModelData(League.class, eqRestrictions) != null;
		
	}
	
	public String getIdLeague() {
		return idLeague;
	}

	public void setIdLeague(String idLeague) {
		this.idLeague = idLeague;
	}
	
	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public List<?> getLeagues() {
		return leagues;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
