package com.myproject.action.game;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletContext;

import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.mail.MailService;
import com.myproject.model.Category;
import com.myproject.model.Game;
import com.myproject.model.League;
import com.myproject.model.RefereeGame;
import com.myproject.model.Team;
import com.myproject.model.User;
import com.myproject.model.UserRefereeType;
import com.myproject.model.Venue;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.myproject.tools.VelocityTemplate;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CRUDGame  extends ActionSupport implements SessionAware, ServletContextAware  {

	private static final long serialVersionUID = -4438495252365119204L;
	
	private String idUser;
	private String idGame;
	private String idHomeTeam, homeTeamName, idAwayTeam, awayTeamName;
	private String idCategory, categoryName;
	private int categoryGender;
	private String idLeague, leagueName;
	private String idVenue, venueName;
	private Boolean gameStatus;
	private Game game;
	private List<?> allGames;
	private List<Game> games;
	private Boolean[] refereeTypes = {false, false, false, false, false, false};
	private String[] idUsers = {"0", "0", "0", "0", "0", "0"};
	private int refereeType;
	private Boolean confirmed, requested;
	private List<?> teams, leagues, categories, venues, referees;
	
    private Date date, dateTime;
	private String dateStr, timeStr, is;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");
	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd H:mm");

	private Calendar calendar = Calendar.getInstance();

	
	
	private GenericService service;
	
	private Map<String, Object> session;
    private ServletContext context;

	private Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

	private Map<String, String> templateData = new HashMap<String, String>();
	private MailService mailService;

	@Override
	@SkipValidation
	public String execute() {
		
		if( ActionContext.getContext().getName().equalsIgnoreCase("addEditGame")){
			
			generateGameForm();
			
			if (idGame != null && !idGame.equals("")){
				eqRestrictions.clear();
				eqRestrictions.put("idGame", new FieldCondition(idGame));
				game = (Game) service.GetUniqueModelData(Game.class, eqRestrictions);
				if(game == null)
					setIdGame(null);
				else
				{
					setDateStr(sdf.format(game.getGameDate()));
					setTimeStr(timeFormat.format(game.getGameDate()));
					setIdHomeTeam(game.getHomeTeam().getIdTeam());
					setHomeTeamName(game.getHomeTeam().getTeamName());
					setIdAwayTeam(game.getAwayTeam().getIdTeam());
					setAwayTeamName(game.getAwayTeam().getTeamName());
					if(game.getGameCategory() != null)
						setIdCategory(game.getGameCategory().getIdCategory());
					if(game.getGameLeague() != null)
						setIdLeague(game.getGameLeague().getIdLeague());
					if(game.getGameVenue() != null)
						setIdVenue(game.getGameVenue().getIdVenue());
					setGameStatus(game.isGameStatus());
					
					for(RefereeGame refereeGame : game.getRefereesGame()){
						refereeTypes[refereeGame.getRefereeType() - 1] = true;
						if(refereeGame.getUser() != null)
							idUsers[refereeGame.getRefereeType() - 1] = refereeGame.getUser().getIdUser();
					}
					
				}
			}
			return NONE;
			
		}else{
	
			allGames = service.GetModelDataList(Game.class, eqRestrictions, "gameDate", false);
			
			if(allGames != null){
				
				if(!((User)session.get("user")).isAdmin())
					allGames.removeIf(game -> !((Game)game).isGameStatus());
				else{
					eqRestrictions.put("userRole", new FieldCondition(User.REFEREE,1));
					referees = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
				}

				if(idUser != null){
					
					if(idUser.equals(((User)session.get("user")).getIdUser())){
	
						if (((User)session.get("user")).getUserRole() == User.ADMIN){
							addActionError("No dispones de partidos designados para tí dado que no tienes perfil de Árbitro.");
							idUser = null;
						}else{
							Stream<?> stream;	
							stream = allGames.stream().filter(game -> ((Game)game).getRefereesGame().
										stream().filter(refereeGame -> refereeGame.getUser() != null &&
											refereeGame.getUser().getIdUser().equals(idUser)).count() > 0);
							allGames = stream.collect(Collectors.toList());
						}
					}
					else{
						eqRestrictions.clear();
						eqRestrictions.put("idUser", new FieldCondition(idUser));
						User user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
						
						if(user == null){
							addActionError("El usuario que has introducido para mostrar sus partidos no existe o ya se ha eliminado");
							idUser = null;
						}else if(user.getUserRole() == User.ADMIN){
							addActionError("Este usuario no dispone de partidos designados para él dado que no tiene perfil de Árbitro.");
							idUser = null;
						}else{
							Stream<?> stream;	
							stream = allGames.stream().filter(game -> ((Game)game).getRefereesGame().
										stream().filter(refereeGame ->  refereeGame.getUser() != null &&
										refereeGame.getUser().getIdUser().equals(idUser)).count() > 0);
							allGames = stream.collect(Collectors.toList());
						}	
					}	
				}

				if(is != null){
					
					if(is.equalsIgnoreCase("unassigned"))
						allGames.removeIf(game -> !((Game)game).isUnassigned());
					else if(is.equalsIgnoreCase("published"))
						allGames.removeIf(game -> !((Game)game).isGameStatus());
					else if(is.equalsIgnoreCase("unpublished"))
						allGames.removeIf(game -> ((Game)game).isGameStatus());
					else if(is.equalsIgnoreCase("conflict"))
						allGames.removeIf(game -> !((Game)game).hasConflict());
					else if(idUser != null){
						if(is.equalsIgnoreCase("confirmed"))
							allGames.removeIf(game -> !((Game)game).isConfirmedByReferee(idUser));
						else if(is.equalsIgnoreCase("unconfirmed"))
							allGames.removeIf(game -> !((Game)game).isUnConfirmedByReferee(idUser));
						else if(is.equalsIgnoreCase("declined"))
							allGames.removeIf(game -> !((Game)game).isDeclinedByReferee(idUser));
						else if(is.equalsIgnoreCase("requested"))
							allGames.removeIf(game -> !((Game)game).isRequestedByReferee(idUser));
					}
					else{
						if(is.equalsIgnoreCase("confirmed"))
							allGames.removeIf(game -> !((Game)game).isConfirmed());
						else if(is.equalsIgnoreCase("unconfirmed"))
							allGames.removeIf(game -> !((Game)game).isUnconfirmed());
						else if(is.equalsIgnoreCase("declined"))
							allGames.removeIf(game -> !((Game)game).isDeclined());
						else if(is.equalsIgnoreCase("requested"))
							allGames.removeIf(game -> !((Game)game).isRequested());
					}

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
	
	
	public String addEditGame(){
		
		setSelectedDate();
		if(date == null || date.equals(new Date(Long.MIN_VALUE))){
			generateGameForm();
			addFieldError("datepicker", "La fecha del partido no es correcta");
			return INPUT;
		}
		
		setSelectedTime();
		if(dateTime == null){
			generateGameForm();
			addFieldError("gameTime", "La hora del partido no es correcta");
			return INPUT;
		}
		
		Team homeTeam;
		eqRestrictions.clear();
		if(idHomeTeam.equals("0")){
			eqRestrictions.put("teamName", new FieldCondition(homeTeamName.trim()));
			homeTeam = (Team) service.GetUniqueModelData(Team.class, eqRestrictions);	
			if(homeTeam == null){
				homeTeam = new Team(homeTeamName.trim());
				service.SaveOrUpdateModelData(homeTeam);
			}
		}
		else{
			eqRestrictions.put("idTeam", new FieldCondition(idHomeTeam));
			homeTeam = (Team) service.GetUniqueModelData(Team.class, eqRestrictions);	
			if(homeTeam == null){
				generateGameForm();
				addFieldError("homeTeam", "El equipo local que has elegido no existe o ya se ha eliminado." +
						" Por favor, crea un nuevo equipo");
				return INPUT;
			}
		}
		
		Team awayTeam;
		eqRestrictions.clear();
		if(idAwayTeam.equals("0")){
			if(awayTeamName.trim().equalsIgnoreCase(homeTeam.getTeamName())){
				generateGameForm();
				addFieldError("awayTeamName", "Los dos equipos no deben ser iguales");
				return INPUT;
			}
			else{
				eqRestrictions.put("teamName", new FieldCondition(awayTeamName.trim()));
				awayTeam = (Team) service.GetUniqueModelData(Team.class, eqRestrictions);
				if(awayTeam == null){
					awayTeam = new Team(awayTeamName.trim());
					service.SaveOrUpdateModelData(awayTeam);
				}
			}
		}
		else{
			eqRestrictions.put("idTeam", new FieldCondition(idAwayTeam));
			awayTeam = (Team) service.GetUniqueModelData(Team.class, eqRestrictions);	
			if(awayTeam == null){
				generateGameForm();
				addFieldError("awayTeam", "El equipo visitante que has elegido no existe o ya se ha eliminado." +
						" Por favor, crea un nuevo equipo");
				return INPUT;
			}
			else if (awayTeam.getTeamName().equalsIgnoreCase(homeTeam.getTeamName())){
				generateGameForm();
				addFieldError("awayTeam", "Los dos equipos no deben ser iguales");
				return INPUT;
			}
		}
		
		Category category = null;
		eqRestrictions.clear();

		if(categoryGender < Category.MALE || categoryGender > Category.MIXED)
			categoryGender = Category.MALE;
		
		if(idCategory.equals("0") && categoryName != null && !categoryName.trim().equals("")){
			eqRestrictions.put("categoryName", new FieldCondition(categoryName.trim()));
			eqRestrictions.put("categoryGender", new FieldCondition(categoryGender));

			category = (Category) service.GetUniqueModelData(Category.class, eqRestrictions);
			if(category == null){
				category = new Category(categoryName.trim(), categoryGender);
				service.SaveOrUpdateModelData(category);
			}
		}
		else{
			eqRestrictions.put("idCategory", new FieldCondition(idCategory));
			category = (Category) service.GetUniqueModelData(Category.class, eqRestrictions);	
		}
		
		League league = null;
		eqRestrictions.clear();
		
		if(idLeague.equals("0") && leagueName != null && !leagueName.trim().equals("")){
			eqRestrictions.put("leagueName", new FieldCondition(leagueName.trim()));
			league = (League) service.GetUniqueModelData(League.class, eqRestrictions);
			if(league == null){
				league = new League(leagueName.trim());
				service.SaveOrUpdateModelData(league);
			}
		}
		else{
			eqRestrictions.put("idLeague", new FieldCondition(idLeague));
			league = (League) service.GetUniqueModelData(League.class, eqRestrictions);	
		}
		
		Venue venue = null;
		eqRestrictions.clear();
		
		if(idVenue.equals("0") && venueName != null && !venueName.trim().equals("")){
			eqRestrictions.put("venueName", new FieldCondition(venueName.trim()));
			venue = (Venue) service.GetUniqueModelData(Venue.class, eqRestrictions);
			if(venue == null){
				venue = new Venue(venueName.trim());
				service.SaveOrUpdateModelData(venue);
			}
		}
		else{
			eqRestrictions.put("idVenue", new FieldCondition(idVenue));
			venue = (Venue) service.GetUniqueModelData(Venue.class, eqRestrictions);	
		}
		
		gameStatus = gameStatus != null;
		
		eqRestrictions.clear();
		eqRestrictions.put("userRole", new FieldCondition(User.REFEREE,1));
		referees = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		
		/*Editing an existing Game*/
		if(idGame != null && !idGame.equals("")){
			eqRestrictions.clear();
			eqRestrictions.put("idGame", new FieldCondition(idGame));
			game = (Game) service.GetUniqueModelData(Game.class, eqRestrictions);			
			
			if(game != null){
				
				game.setGameDate(new Timestamp(dateTime.getTime()));
			
				game.setHomeTeam(homeTeam);
				
				game.setAwayTeam(awayTeam);

				game.setGameCategory(category);
				
				game.setGameLeague(league);
				
				game.setGameVenue(venue);
				
				Game oldGame = (Game)SerializationUtils.clone(game);
				oldGame.setRefereesGame(new LinkedList<RefereeGame>());
				Game currentGame = (Game)SerializationUtils.clone(game);
				Game newGame = (Game)SerializationUtils.clone(game);
				newGame.setRefereesGame(new LinkedList<RefereeGame>());
				
				for(int i = 0; i < UserRefereeType.REFEREETYPES; i++){
					
					if(game.getRefereeGame(i + 1) != null){
						if(!refereeTypes[i]){
							
							service.DeleteModelData(game.getRefereeGame(i + 1));
							if(game.getRefereeGame(i + 1).getUser() != null)
								oldGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
							game.getRefereesGame().remove(game.getRefereeGame(i + 1));

						}else{
							if(game.getRefereeGame(i + 1).getUser() != null){
								if(idUsers[i].equals("0")){
									oldGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
									game.getRefereeGame(i + 1).setUser(null);
								}else if(!idUsers[i].equals(game.getRefereeGame(i + 1).getUser().getIdUser())){
									String idUser = idUsers[i];
									Optional<?> opt = referees.stream().
											filter(referee -> ((User)referee).getIdUser().equals(idUser)).findFirst();
									if(opt.isPresent()){
										oldGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
										game.getRefereeGame(i + 1).setUser((User)opt.get());
										newGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
									}
								}
							}
							else{
								if(!idUsers[i].equals("0")){
									String idUser = idUsers[i];
									Optional<?> opt = referees.stream().
											filter(referee -> ((User)referee).getIdUser().equals(idUser)).findFirst();
									if(opt.isPresent()){
										game.getRefereeGame(i + 1).setUser((User)opt.get());
										newGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
									}
								}
							}						
						}
					}
					else{
						if(refereeTypes[i]){
							RefereeGame refereeGame = new RefereeGame(game, null, i + 1, RefereeGame.UNCONFIRMED);

							if(!idUsers[i].equals("0")){
								String idUser = idUsers[i];
								Optional<?> opt = referees.stream().
										filter(referee -> ((User)referee).getIdUser().equals(idUser)).findFirst();
								if(opt.isPresent()){
									refereeGame.setUser((User)opt.get());
									newGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(refereeGame));
								}
							}
							service.SaveOrUpdateModelData(refereeGame);
							game.getRefereesGame().add(refereeGame);
						}
					}
				}
			
				
				User user = (User)session.get("user");
				game.setLastUpdaterUser(user);
		
				date = new Date();
				game.setLastUpdatedDate(new Timestamp(date.getTime()));
				
				if(gameStatus && game.isGameStatus()){
					notifyReferees(oldGame,false);
					notifyReferees(newGame,true);
				}
				else if(gameStatus && !game.isGameStatus())
					notifyReferees(game,true);
				else if(!gameStatus && game.isGameStatus())
					notifyReferees(currentGame,false);
				
				game.setGameStatus(gameStatus);
				
				service.SaveOrUpdateModelData(game);
				addActionMessage("Se ha actualizado el partido con exito.");
				return SUCCESS;
			}
		}
		
		
		/*Adding a new Game*/
		User user = (User)session.get("user");
		date = new Date();
		
		game = new Game(homeTeam, awayTeam, new Timestamp(dateTime.getTime()), venue, league, category, 
				gameStatus, user, new Timestamp(date.getTime()));
		
		for(int i = 0; i < UserRefereeType.REFEREETYPES; i++){
			
			if(refereeTypes[i]){
				RefereeGame refereeGame = new RefereeGame(game, null, i + 1, RefereeGame.UNCONFIRMED);

				if(!idUsers[i].equals("0")){
					String idUser = idUsers[i];
					Optional<?> opt = referees.stream().
							filter(referee -> ((User)referee).getIdUser().equals(idUser)).findFirst();
					if(opt.isPresent())
						refereeGame.setUser((User)opt.get());	
	
				}
				
				game.getRefereesGame().add(refereeGame);
			}
		}
		
		if(gameStatus)
			notifyReferees(game,true);
		
		service.SaveOrUpdateModelData(game);
		addActionMessage("Se ha añadido el partido con exito.");
		setIdGame(game.getIdGame());
		return SUCCESS;
	}

	@SkipValidation
	public String getSelectedGame(){
		
		if (idGame == null || idGame.equals("")){
			
			addActionError("Por favor, introduce el id del partido que quieres mostrar.");
			setContextGames();
			return INPUT;
			
			
		}
		else{
			
			eqRestrictions.put("idGame", new FieldCondition(idGame));

			game = (Game)service.GetUniqueModelData(Game.class, eqRestrictions);
			
			if(game == null || (!((User)session.get("user")).isAdmin() && !game.isGameStatus())){
				addActionError("El partido que quieres mostrar no existe o ya se ha eliminado.");
				setContextGames();
				return INPUT;
			}
			else{
				eqRestrictions.clear();
				eqRestrictions.put("userRole", new FieldCondition(User.REFEREE,1));
				referees = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
				return SUCCESS;

			}
		}		
	}
	
	@SkipValidation
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
				
				game.setGameStatus(Game.PUBLISHED);
				
				service.SaveOrUpdateModelData(game);
				
				notifyReferees(game,true);
				
				addActionMessage("El partido ha sido publicado con exito.");
				return SUCCESS;
			
			}
		}
	}
	
	
	@SkipValidation
	public String confirmGame(){
		
		User user = (User)session.get("user");

		if(user.getUserRole() == User.ADMIN){
			addActionError("No tienes perfil de árbitro para confirmar o rechazar este partido.");
			setContextGames();
			return INPUT;
		}
		else if (idGame == null || idGame.equals("")){
			
			addActionError("Por favor, introduce el id del partido que quieres confirmar o rechazar.");
			setContextGames();
			return INPUT;
		}
		else{
			
			eqRestrictions.put("idGame", new FieldCondition(idGame));
			game = (Game) service.GetUniqueModelData(Game.class, eqRestrictions);			
			
			if(game == null || (user.getUserRole() == User.REFEREE && !game.isGameStatus())){
				
				addActionError("El partido que quieres confirmar o rechazar no existe o ya se ha eliminado.");
				setContextGames();
				return INPUT;	
			}
			else{
				
				if( refereeType >= 1 && refereeType <= 6 && game.getRefereeGame(refereeType) != null 
						 && game.getRefereeGame(refereeType).getUser().getIdUser().equals(user.getIdUser())){
					
					if(confirmed == null)
						game.getRefereeGame(refereeType).setConfirmed(RefereeGame.UNCONFIRMED);
					else
					{	
						if(confirmed){
							game.getRefereeGame(refereeType).setConfirmed(RefereeGame.CONFIRMED);
							addActionMessage("Tu designación para este partido ha sido confirmada con exito.");
						}else{
							game.getRefereeGame(refereeType).setConfirmed(RefereeGame.DECLINED);
							addActionMessage("Tu designación para este partido ha sido rechzada con exito.");
						}
						
						notifyAdmins(game, refereeType, confirmed);
					}

					service.SaveOrUpdateModelData(game);
				}
				
				return SUCCESS;
			
			}
		}
	}
	
	@SkipValidation
	public String requestGame(){
		
		User user = (User)session.get("user");

		if(user.getUserRole() == User.ADMIN){
			addActionError("No tienes perfil de árbitro para solicitar o cancelar la solicitud de este partido.");
			setContextGames();
			return INPUT;
		}
		else if (idGame == null || idGame.equals("")){
			
			addActionError("Por favor, introduce el id del partido que quieres solicitar o cancelar la solicitud.");
			setContextGames();
			return INPUT;
		}
		else{
			
			eqRestrictions.put("idGame", new FieldCondition(idGame));
			game = (Game) service.GetUniqueModelData(Game.class, eqRestrictions);			
			
			if(game == null || (user.getUserRole() == User.REFEREE && !game.isGameStatus())){
				
				addActionError("El partido que quieres solicitar o cancelar la solicitudno no existe o ya se ha eliminado.");
				setContextGames();
				return INPUT;	
			}
			else{

				if( refereeType >= 1 && refereeType <= 6 && game.getRefereeGame(refereeType) != null 
						&& game.getRefereeGame(refereeType).getUser() == null
						&& game.isAvailable(user) && user.getUserRefereeType(refereeType)
								&& !user.hasOtherGame(game) ){
					if(requested != null){
						
						if(requested){
							
							if(game.getRefereeGame(refereeType).getUsers().stream().filter(
									usr -> usr.equals(user)).count() == 0){
								game.getRefereeGame(refereeType).getUsers().add(user);
								service.SaveOrUpdateModelData(game);
							}		
						}
						else{

							if(game.getRefereeGame(refereeType).getUsers().stream().filter(
									usr -> usr.equals(user)).count() == 1){
			
								game.getRefereeGame(refereeType).getUsers().removeIf(usr -> usr.equals(user));
								service.SaveOrUpdateModelData(game);
							}
						}
					}
				}

				addActionMessage("Tu solicitud para este partido ha sido confirmada con exito.");
				return SUCCESS;
			
			}
		}	
	}
	
	@SkipValidation
	public String assignGame(){
		

		if (idGame == null || idGame.equals("")){
			
			addActionError("Por favor, introduce el id del partido que quieres publicar.");
			setContextGames();
			return INPUT;
		}
		else{
			
			eqRestrictions.put("idGame", new FieldCondition(idGame));
			game = (Game) service.GetUniqueModelData(Game.class, eqRestrictions);			
			
			if(game == null){
				
				addActionError("El partido que quieres designar no existe o ya se ha eliminado.");
				setContextGames();
				return INPUT;	
			}
			else{
				

				Game oldGame = (Game)SerializationUtils.clone(game);
				oldGame.setRefereesGame(new LinkedList<RefereeGame>());
				Game newGame = (Game)SerializationUtils.clone(game);
				newGame.setRefereesGame(new LinkedList<RefereeGame>());
				
				eqRestrictions.clear();
				eqRestrictions.put("userRole", new FieldCondition(User.REFEREE,1));
				referees = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
				
				for(int i = 0; i < UserRefereeType.REFEREETYPES; i++){
					
					if(game.getRefereeGame(i + 1) != null){
						
						if(game.getRefereeGame(i + 1).getUser() != null){
							if(idUsers[i].equals("0")){
								oldGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
								game.getRefereeGame(i + 1).setUser(null);
							}
							else if(!idUsers[i].equals(game.getRefereeGame(i + 1).getUser().getIdUser())){
								String idUser = idUsers[i];
								Optional<?> opt = referees.stream().
										filter(referee -> ((User)referee).getIdUser().equals(idUser)).findFirst();
								if(opt.isPresent()){
										oldGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
										game.getRefereeGame(i + 1).setUser((User)opt.get());
										newGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
								}
							}
						}
						else{
							if(!idUsers[i].equals("0")){
								String idUser = idUsers[i];
								Optional<?> opt = referees.stream().
										filter(referee -> ((User)referee).getIdUser().equals(idUser)).findFirst();
								if(opt.isPresent()){
									game.getRefereeGame(i + 1).setUser((User)opt.get());
									newGame.getRefereesGame().add((RefereeGame)SerializationUtils.clone(game.getRefereeGame(i + 1)));
								}
							}
						}			
					}
				}
				
				if(game.isGameStatus()){
					notifyReferees(oldGame, false);
					notifyReferees(newGame, true);
				}
				
				setDateStr(dateStr);
				User user = (User)session.get("user");
				game.setLastUpdaterUser(user);
		
				Date date = new Date();
				game.setLastUpdatedDate(new Timestamp(date.getTime()));
				
				game.setGameStatus(true);
				
				service.SaveOrUpdateModelData(game);
				
				addActionMessage("El partido ha sido designado con exito.");
				return SUCCESS;
			
			}
		}
	}
	
	
	@SkipValidation
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
				if(game.isGameStatus())
					notifyReferees(game, false);
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
	
	public void setSelectedTime(){
		
		dateTimeFormat.setLenient(false);

        try {
			dateTime = dateTimeFormat.parse(dateStr + " " + timeStr);
		} catch (ParseException e) {
			dateTime = null;
		} catch (NullPointerException e){
			dateTime = null;
		}
	}

	public void setContextGames(){
		
		eqRestrictions.clear();
		allGames = service.GetModelDataList(Game.class, eqRestrictions, "gameDate", false);
		
		if(!((User)session.get("user")).isAdmin())
			allGames.removeIf(game -> !((Game)game).isGameStatus());

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

	public void generateGameForm(){
		
		eqRestrictions.clear();
		teams = service.GetModelDataList(Team.class, eqRestrictions, "teamName", true);
		leagues = service.GetModelDataList(League.class, eqRestrictions, "leagueName", true);
		categories = service.GetModelDataList(Category.class, eqRestrictions, "categoryName", true);
		venues = service.GetModelDataList(Venue.class, eqRestrictions, "venueName", true);
		
		eqRestrictions.put("userRole", new FieldCondition(User.REFEREE,1));
		referees = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
	}
	
	
	public void notifyAdmins(Game game, int refereeType, boolean confirmed){
		setTemplateGameInfo(game);
		
		templateData.put("fullName", ((User)session.get("user")).getUserFullName());
		
		templateData.put("action", confirmed ? "confirmado" : "rechazado");

		templateData.put("refereeType", UserRefereeType.getRefereeTypeName(refereeType));

		eqRestrictions.clear();
		List<?> users =  service.GetModelDataList(User.class, eqRestrictions, null, null);

		List<String> emailAddressesList = new  ArrayList<String>();
		users.stream().filter(user -> ((User)user).isAdmin()).
			forEach(user ->   emailAddressesList.add(((User)user).getEmail()));
		
		if(emailAddressesList.size() > 0){
			
			String[] emailAddressesArray = new String[ emailAddressesList.size() ];
			emailAddressesList.toArray( emailAddressesArray );
			
			VelocityTemplate  velocityTemplate = new VelocityTemplate("confirmNotification.vm", templateData);
			mailService.sendMail(emailAddressesArray,
					((User)session.get("user")).getUserFullName() + " ha "  + (confirmed ? "Confirmado" : "Rechazado") +
					 " su Designación", velocityTemplate.getTemplate());
		}

	}
	
	public void notifyReferees(Game game, boolean assign){
		
		setTemplateGameInfo(game);

		templateData.put("adminFirstName", ((User)session.get("user")).getUserProfile().getFirstName());

		
		Map<String,List<RefereeGame>> usersInfo = new  HashMap<String,List<RefereeGame>>();
		game.getRefereesGame().stream().filter(refereeGame -> refereeGame.getUser() != null)
					.forEach(refereeGame -> 
						usersInfo.put(refereeGame.getUser().getEmail(), game.getRefereesGameByUser(refereeGame.getUser()))
					);
		
		usersInfo.forEach((emailAddress, refereesGame) -> {
			templateData.put("firstName", refereesGame.get(0).getUser().getUserProfile().getFirstName());					
			String refereeTypes = "";
			for(RefereeGame refereeGame : refereesGame)
				refereeTypes += UserRefereeType.getRefereeTypeName(refereeGame.getRefereeType()) + ", ";
				
			refereeTypes = refereeTypes.substring(0, refereeTypes.length() - 2) + ".";
			templateData.put("refereeTypes",refereeTypes);
			
			if(assign){
				VelocityTemplate  velocityTemplate = new VelocityTemplate("assignmentNotification.vm", templateData);
				mailService.sendMail(new String[]{emailAddress}, "Tienes una Nueva Designación", velocityTemplate.getTemplate());
			}
			else{
				VelocityTemplate  velocityTemplate = new VelocityTemplate("unassignmentNotification.vm", templateData);
				mailService.sendMail(new String[]{emailAddress}, "Designación Eliminada", velocityTemplate.getTemplate());
			}
			
		});
	}
	
	public void setTemplateGameInfo(Game game){

		SimpleDateFormat dayNameFormat = new SimpleDateFormat("EEE",new Locale("es","ES"));
		SimpleDateFormat dateFormat = new SimpleDateFormat("d");
		SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMM",new Locale("es","ES"));
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

		templateData.put("gameInfo", game.getHomeTeam().getTeamName() + " vs " + game.getAwayTeam().getTeamName() +
				", " + WordUtils.capitalize(dayNameFormat.format(game.getGameDate())) + " " + dateFormat.format(game.getGameDate())
				+ " de " + monthNameFormat.format(game.getGameDate()) + " de " + yearFormat.format(game.getGameDate())
				+ " a las " + timeFormat.format(game.getGameDate()));
		templateData.put("gameLeague", game.getGameLeague() != null ? game.getGameLeague().getLeagueName() : "");
		templateData.put("gameCategory", game.getGameCategory() != null ? 
			game.getGameCategory().getCategoryName() + " " + game.getGameCategory().getCategoryGenderName() : "");
		templateData.put("gameCity", game.getGameVenue() != null && game.getGameVenue().getVenueAddress() != null ? 
			game.getGameVenue().getVenueAddress().getCity() : "");
		templateData.put("gameVenue", game.getGameVenue() != null ? game.getGameVenue().getVenueName() : "");
		
	}
	
	public String getIdUser() {
		return idUser;
	}


	public void setIdUser(String idUser) {
		this.idUser = idUser;
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

	public void setService(GenericService service) {
		this.service = service;
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


	public String getTimeStr() {
		return timeStr;
	}


	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}


	public List<?> getTeams() {
		return teams;
	}


	public void setTeams(List<?> teams) {
		this.teams = teams;
	}


	public List<?> getLeagues() {
		return leagues;
	}


	public void setLeagues(List<?> leagues) {
		this.leagues = leagues;
	}


	public List<?> getVenues() {
		return venues;
	}


	public void setVenues(List<?> venues) {
		this.venues = venues;
	}


	public List<?> getCategories() {
		return categories;
	}


	public void setCategories(List<?> categories) {
		this.categories = categories;
	}


	public List<?> getReferees() {
		return referees;
	}


	public void setReferees(List<?> referees) {
		this.referees = referees;
	}


	public String getIdHomeTeam() {
		return idHomeTeam;
	}


	public void setIdHomeTeam(String idHomeTeam) {
		this.idHomeTeam = idHomeTeam;
	}


	public String getIdAwayTeam() {
		return idAwayTeam;
	}


	public void setIdAwayTeam(String idAwayTeam) {
		this.idAwayTeam = idAwayTeam;
	}


	public String getHomeTeamName() {
		return homeTeamName;
	}


	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}


	public String getAwayTeamName() {
		return awayTeamName;
	}


	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}


	public String getIdCategory() {
		return idCategory;
	}


	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}


	public int getCategoryGender() {
		return categoryGender;
	}


	public void setCategoryGender(int categoryGender) {
		this.categoryGender = categoryGender;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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


	public String getVenueName() {
		return venueName;
	}


	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}


	public String getIdVenue() {
		return idVenue;
	}


	public void setIdVenue(String idVenue) {
		this.idVenue = idVenue;
	}


	public Boolean isGameStatus() {
		return gameStatus;
	}


	public void setGameStatus(Boolean gameStatus) {
		this.gameStatus = gameStatus;
	}


	public Boolean[] getRefereeTypes() {
		return refereeTypes;
	}


	public void setRefereeTypes(Boolean[] refereeTypes) {
		this.refereeTypes = refereeTypes;
	}


	public String[] getIdUsers() {
		return idUsers;
	}


	public void setIdUsers(String[] idUsers) {
		this.idUsers = idUsers;
	}


	public int getRefereeType() {
		return refereeType;
	}


	public void setRefereeType(int refereeType) {
		this.refereeType = refereeType;
	}


	public Boolean getConfirmed() {
		return confirmed;
	}


	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}


	public Boolean getRequested() {
		return requested;
	}


	public void setRequested(Boolean requested) {
		this.requested = requested;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

}
