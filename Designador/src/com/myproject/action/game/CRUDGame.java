package com.myproject.action.game;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.Game;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CRUDGame  extends ActionSupport implements SessionAware, ServletContextAware  {

	private static final long serialVersionUID = -4438495252365119204L;
	
	private String idGame;
	private Game game;
	private List<?> games;
	private List<?> refereesGame;
	
	private String dateStr;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	
	private GenericService service;
	
	private Map<String, Object> session;
    private ServletContext context;

	private Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

	@Override
	@SkipValidation
	public String execute() {
		
		if( ActionContext.getContext().getName().equalsIgnoreCase("addEditGame")){
			
			return NONE;
			
		}else{
			games = service.GetModelDataList(Game.class, eqRestrictions, "gameDate", true);
			
			return SUCCESS;
		}
	}


	
	public String publishGame(){
		
		if (idGame == null || idGame.equals("")){
			
			addActionError("Por favor, introduce el id del partido que quieres publicar.");

			List<?> games = service.GetModelDataList(Game.class, eqRestrictions, "gameDate", true);
			context.setAttribute("games", games);
			
			return INPUT;
		}
		else{
			
			eqRestrictions.put("idGame", new FieldCondition(idGame));
			game = (Game) service.GetUniqueModelData(Game.class, eqRestrictions);			
			
			if(game == null){
				
				addActionError("El partido que quieres eliminar no existe o ya se ha eliminado.");
				
				eqRestrictions.clear();
				List<?> games = service.GetModelDataList(Game.class, eqRestrictions, "gameDate", true);
				context.setAttribute("games", games);
				
				return INPUT;	
			}
			else{
				User user = (User)session.get("user");
				game.setLastUpdaterUser(user);
		
				Date date = new Date();
				game.setLastUpdatedDate(new Timestamp(date.getTime()));
				
				game.setGameStatus(true);
				
				service.SaveOrUpdateModelData(game);
				
				addActionMessage("El partido ha sido publicado con exito.");
				return SUCCESS;
			
			}
		}
	}
	
	public String getIdGame() {
		return idGame;
	}

	public void setIdGame(String idGame) {
		this.idGame = idGame;
	}
	
	public List<?> getGames() {
		return games;
	}

	public List<?> getRefereesGame() {
		return refereesGame;
	}

	public void setRefereesGame(List<?> refereesGame) {
		this.refereesGame = refereesGame;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
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
