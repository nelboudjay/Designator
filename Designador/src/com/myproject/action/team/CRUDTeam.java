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
	private String teamShortName;
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
					setTeamShortName(team.getTeamShortName());
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
		
		teamName = teamName.trim().substring(0,1).toUpperCase() + teamName.trim().substring(1);
		if(teamShortName != null && teamShortName.trim().equals(""))
			teamShortName = null;
		else
			teamShortName = teamShortName.trim().toUpperCase().substring(0, 3);
		
		if(teamLocation != null && teamLocation.trim().equals(""))
			teamLocation = null;
		else
			teamLocation = WordUtils.capitalize(teamLocation.trim());
		
		/*Editing an existing Team*/
		if(idTeam != null && !idTeam.equals("")){
			eqRestrictions.put("idTeam", new FieldCondition(idTeam));
			team = (Team) service.GetUniqueModelData(Team.class, eqRestrictions);			
			
			if(team != null){
				if(!new Team(teamName,teamShortName,teamLocation).equals(team)){
					
					if (!teamName.equalsIgnoreCase(team.getTeamName()) && fieldAlreadyExists("teamName",teamName)){
						addFieldError("teamName","El nombre del equipo ya existe. Por favor, elige otro.");
						return INPUT;
					 }
					
					if (teamShortName != null && !teamShortName.equalsIgnoreCase(team.getTeamShortName()) 
							&& fieldAlreadyExists("teamShortName", teamShortName)){
						addFieldError("teamShortName","El nombre corto del equipo ya existe. Por favor, elige otro.");
						return INPUT;
					 }
				}							
				
				team.setTeamName(teamName);
				team.setTeamShortName(teamShortName);
				team.setTeamLocation(teamLocation);
				
				service.SaveOrUpdateModelData(team);
				
				addActionMessage("Se ha actualizado el nombre del equipo con exito.");
				return SUCCESS;
	
			}
		}
		
		/*Adding a new Team*/
		
		if(fieldAlreadyExists("teamName", teamName)){
			addFieldError("teamName","El nombre del equipo ya existe. Por favor, elige otro.");
			return INPUT;
		}
		
		if (teamShortName != null && fieldAlreadyExists("teamShortName", teamShortName)){
			addFieldError("teamShortName","El nombre corto del equipo ya existe. Por favor, elige otro.");
			return INPUT;
		}
		


		team = new Team(teamName, teamShortName, teamLocation);
		
		service.SaveOrUpdateModelData(team);
		addActionMessage("Se ha sido añadido un nuevo equipo con exito.");
		return SUCCESS;
		
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
	
	public Boolean fieldAlreadyExists(String fieldName, String fieldValue){
		
		eqRestrictions.clear();
		eqRestrictions.put(fieldName, new FieldCondition(fieldValue));
		
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

	public String getTeamShortName() {
		return teamShortName;
	}

	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}
}
