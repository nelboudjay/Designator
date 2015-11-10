package com.myproject.action.team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.WordUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.Team;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CRUDTeam  extends ActionSupport implements ServletContextAware{

	private static final long serialVersionUID = -4438495252365119204L;
	
	private String idTeam;
	private String teamName;
	private String teamLocation;
	private List<?> teams;
	private Team team;

	private GenericService service;

    private ServletContext context;

	private Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

	@Override
	@SkipValidation
	public String execute() {
		
		if( ActionContext.getContext().getName().equalsIgnoreCase("addEditTeam")){
			
			if (idTeam != null && !idTeam.equals("")){
				eqRestrictions.put("idTeam", new FieldCondition(idTeam));
				team = (Team) service.GetUniqueModelData(Team.class, eqRestrictions);
				if(team != null){
					setIdTeam(team.getIdTeam());
					setTeamName(team.getTeamName());
					setTeamLocation(team.getTeamLocation());
				}
			}
			
			return NONE;
			
		}else{
			teams = service.GetModelDataList(Team.class, eqRestrictions, "teamName", true);
			return SUCCESS;
		}
	}

	public String addEditTeam(){
		
		/*Editing an existing Team*/
		if(idTeam != null && !idTeam.equals("")){
			eqRestrictions.put("idTeam", new FieldCondition(idTeam));
			team = (Team) service.GetUniqueModelData(Team.class, eqRestrictions);			
			
			if(team != null){
				if(!new Team(teamName.trim(),teamLocation.trim()).equals(team)){
					if (!teamName.trim().equalsIgnoreCase(team.getTeamName()) && teamNameAlreadyExists(teamName.trim())){
						addFieldError("teamName","El nombre del equipo ya existe. Por favor, elige otro.");
						return INPUT;
					 }
											
					team.setTeamName(teamName.trim().substring(0,1).toUpperCase() + teamName.trim().substring(1));
					team.setTeamLocation(WordUtils.capitalize(teamLocation.trim()));
					
					service.SaveOrUpdateModelData(team);
					
				}
				
				addActionMessage("Se ha actualizado el nombre del equipo con exito.");
				return SUCCESS;
	
			}
		}
		
		/*Adding a new Team*/
		
		if(teamNameAlreadyExists(teamName.trim())){
			addFieldError("teamName","El nombre del equipo ya existe. Por favor, elige otro.");
			return INPUT;
		}
		else{
			
			team = new Team(teamName.trim().substring(0,1).toUpperCase() + teamName.trim().substring(1),
					WordUtils.capitalize(teamLocation.trim()));
			service.SaveOrUpdateModelData(team);
			addActionMessage("Se ha sido añadido un nuevo equipo con exito.");
			return SUCCESS;
		}
	}
	
	@SkipValidation
	public String deleteTeam(){
		
		if (idTeam == null || idTeam.equals("")){
			
			addActionError("Por favor, introduce el id del equipo que quieres eliminar.");
			teams = service.GetModelDataList(Team.class, eqRestrictions, "teamName", true);
			context.setAttribute("teams", teams);
			return INPUT;
		}
		else{
			eqRestrictions.put("idTeam", new FieldCondition(idTeam));
			team = (Team) service.GetUniqueModelData(Team.class, eqRestrictions);			
			
			if(team != null){
				service.DeleteModelData(team);
				addActionMessage("El equipo ha sido eliminado con exito.");
				return SUCCESS;

			}
			else{
				addActionError("El equipo que quieres eliminar no existe o ya se ha eliminado.");
				eqRestrictions.clear();
				teams = service.GetModelDataList(Team.class, eqRestrictions, "teamName", true);
				context.setAttribute("teams", teams);
				return INPUT;
			}

		}
	}
	
	public Boolean teamNameAlreadyExists(String teamName){
		
		eqRestrictions.clear();
		eqRestrictions.put("teamName", new FieldCondition(teamName));
			
		return (Team) service.GetUniqueModelData(Team.class, eqRestrictions) != null;
		
	}
	
	public String getIdTeam() {
		return idTeam;
	}

	public void setIdTeam(String idTeam) {
		this.idTeam = idTeam;
	}
	
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public String getTeamLocation() {
		return teamLocation;
	}

	public void setTeamLocation(String teamLocation) {
		this.teamLocation = teamLocation;
	}

	public List<?> getTeams() {
		return teams;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
