package com.myproject.action.game;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
	private List<?> allGames;
	private List<Game> games;
	private List<?> refereesGame;
	
    private Date date;
	private String dateStr, is;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Calendar calendar = Calendar.getInstance();

	
	
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
			
			allGames = service.GetModelDataList(Game.class, eqRestrictions, "gameDate", false);

			if(allGames != null){
				
				if(is != null){
					
					if(is.equalsIgnoreCase("unassigned"))
						allGames.removeIf(game -> !((Game)game).isUnassigned());
					else if(is.equalsIgnoreCase("published"))
						allGames.removeIf(game -> !((Game)game).isGameStatus());
					else if(is.equalsIgnoreCase("unpublished"))
						allGames.removeIf(game -> ((Game)game).isGameStatus());
					else if(is.equalsIgnoreCase("confirmed"))
						allGames.removeIf(game -> !((Game)game).isConfirmed());
					else if(is.equalsIgnoreCase("unconfirmed"))
						allGames.removeIf(game -> !((Game)game).isUnconfirmed());

				}
			
				games = new LinkedList<Game>();
	
				setSelectedDate();

		
				if(date == null || date.equals(new Date(Long.MIN_VALUE))){
					
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					
					allGames.stream().filter(game -> ((Game)game).getGameDate().after(calendar.getTime())).
					forEach(game -> games.add((Game)game));
	
				}else 
					allGames.stream().filter(game -> ((Game)game).getGameDate().after(date) 
								&& ((Game)game).getGameDate().before(new Timestamp(date.getTime() + (1000 * 60 * 60 * 24)))).
								forEach(game -> games.add((Game)game));
			}
			
			return SUCCESS;
		}
	}


	public String getSelectedGame(){
		
		if (idGame == null || idGame.equals("")){
			
			User user = (User)session.get("user");

			if(user.isAdmin()){
				addActionError("Por favor, introduce el id del partido que quieres mostrar.");
				setContextGames();
				return INPUT;
			}
			else{
				addActionError("No tienes permiso para ver esta página");
				return ERROR;	
			}
			
		}
		else{
			
			eqRestrictions.put("idGame", new FieldCondition(idGame));

			game = (Game)service.GetUniqueModelData(Game.class, eqRestrictions);
			
			if(game == null){
				addActionError("El partido que quieres mostrar no existe o ya se ha eliminado.");
				setContextGames();
				return INPUT;
			}
			else
				return SUCCESS;
		}		
	}
	
	public String publishGame(){
		
		if (idGame == null || idGame.equals("")){
			
			addActionError("Por favor, introduce el id del partido que quieres publicar.");
			setContextGames();
			return INPUT;
		}
		else{
			
			eqRestrictions.put("idGame", new FieldCondition(idGame));
			game = (Game) service.GetUniqueModelData(Game.class, eqRestrictions);			
			
			if(game == null){
				
				addActionError("El partido que quieres publicar no existe o ya se ha eliminado.");
				setContextGames();
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
	
	public String deleteGame(){
		
		if (idGame == null || idGame.equals("")){
			
			addActionError("Por favor, introduce el id del partido que quieres eliminar.");
			setContextGames();
			return INPUT;
		}
		else{
			eqRestrictions.put("idGame", new FieldCondition(idGame));
			game = (Game) service.GetUniqueModelData(Game.class, eqRestrictions);			
			
			if(game == null){
				
				addActionError("El partido que quieres eliminar no existe o ya se ha eliminado.");
				setContextGames();
				return INPUT;	
			}
			else{
				service.DeleteModelData(game);
				addActionMessage("El partido ha sido eliminado con exito.");
				return SUCCESS;
			
			}
		}
	}
	
	public void setSelectedDate(){
			
		sdf.setLenient(false);

        try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			if(dateStr.trim().equalsIgnoreCase("all"))
				date = new Date(Long.MIN_VALUE);
			else
				date = null;
		} catch (NullPointerException e){
			date = null;
		}
	}

	public void setContextGames(){
		
		eqRestrictions.clear();
		allGames = service.GetModelDataList(Game.class, eqRestrictions, "gameDate", false);
		
		context.setAttribute("allGames", allGames);

		if(allGames != null){
			games = new LinkedList<Game>();

			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			allGames.stream().filter(game -> ((Game)game).getGameDate().after(calendar.getTime())).
			forEach(game -> games.add((Game)game));
			
			context.setAttribute("games", games);
		}
		
	}
	
	public String getIdGame() {
		return idGame;
	}

	public void setIdGame(String idGame) {
		this.idGame = idGame;
	}
	
	public Game getGame() {
		return game;
	}

	public List<Game> getGames() {
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateStr() {
		return dateStr;
	}

	public String getIs() {
		return is;
	}

	public void setIs(String is) {
		this.is = is;
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

	public List<?> getAllGames() {
		return allGames;
	}

	public void setAllGames(List<?> allGames) {
		this.allGames = allGames;
	}
}
