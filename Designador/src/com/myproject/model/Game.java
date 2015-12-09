package com.myproject.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

@Entity
@Table(name="GAME", uniqueConstraints = {@UniqueConstraint(columnNames={"HOME_TEAM", "AWAY_TEAM","GAME_DATE"})})
public class Game implements Serializable{
	
	private static final long serialVersionUID = 1889018466788086979L;
	
	public static final boolean PUBLISHED = true;
	public static final boolean UNPUBLISHED = false;


	public Game(Team homeTeam, Team awayTeam, Timestamp gameDate,
			Venue gameVenue, League gameLeague, Category gameCategory,
			boolean gameStatus, User lastUpdaterUser, Timestamp lastUpdatedDate) {
		super();
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.gameDate = gameDate;
		this.gameVenue = gameVenue;
		this.gameLeague = gameLeague;
		this.gameCategory = gameCategory;
		this.gameStatus = gameStatus;
		this.lastUpdaterUser = lastUpdaterUser;
		this.lastUpdatedDate = lastUpdatedDate;
		this.refereesGame = new LinkedList<RefereeGame>();
	}

	public Game(){
		super();
	}
	
	@Id
	@Column(name = "idGAME")
	@GeneratedValue
	private String idGame;
	
	@OneToOne
	@ForeignKey (name = "FK_GAME__TEAM")
	@JoinColumn(name = "HOME_TEAM", nullable = false)
	private Team homeTeam;
	
	@OneToOne
	@ForeignKey (name = "FK_GAME__TEAM")
	@JoinColumn(name = "AWAY_TEAM", nullable = false)
	private Team awayTeam;
	
	@Column(name = "GAME_DATE", nullable = false)
	private Timestamp gameDate;

	@OneToOne
	@ForeignKey (name = "FK_GAME__VENUE")
	@JoinColumn(name = "GAME_VENUE")
	private Venue gameVenue;
	
	@OneToOne
	@ForeignKey (name = "FK_GAME__LEAGUE")
	@JoinColumn(name = "GAME_LEAGUE")
	private League gameLeague;
	
	@OneToOne
	@ForeignKey (name = "FK_GAME__CATEGORY")
	@JoinColumn(name = "GAME_CATEGORY")
	private Category gameCategory;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "GAME_STATUS", nullable = false)
	private boolean gameStatus;

	@OneToOne
	@ForeignKey (name = "FK_GAME__USER")
	@JoinColumn(name = "LAST_UPDATER_USER", nullable = false)
	private User lastUpdaterUser;
	
	@Column(name = "LAST_UPDATED_DATE", nullable = false)
	private Timestamp lastUpdatedDate;
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="game")
    private List<RefereeGame> refereesGame;
	
	public String getIdGame() {
		return idGame;
	}

	public void setIdGame(String idGame) {
		this.idGame = idGame;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Timestamp getGameDate() {
		return gameDate;
	}

	public void setGameDate(Timestamp gameDate) {
		this.gameDate = gameDate;
	}

	public Venue getGameVenue() {
		return gameVenue;
	}

	public void setGameVenue(Venue gameVenue) {
		this.gameVenue = gameVenue;
	}

	public boolean isGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(boolean gameStatus) {
		this.gameStatus = gameStatus;
	}

	public League getGameLeague() {
		return gameLeague;
	}

	public void setGameLeague(League gameLeague) {
		this.gameLeague = gameLeague;
	}

	public Category getGameCategory() {
		return gameCategory;
	}

	public void setGameCategory(Category gameCategory) {
		this.gameCategory = gameCategory;
	}

	public User getLastUpdaterUser() {
		return lastUpdaterUser;
	}

	public void setLastUpdaterUser(User lastUpdaterUser) {
		this.lastUpdaterUser = lastUpdaterUser;
	}

	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public List<RefereeGame> getRefereesGame() {
		return refereesGame;
	}

	public void setRefereesGame(List<RefereeGame> refereesGame) {
		this.refereesGame = refereesGame;
	}
	
	public List<RefereeGame> getRefereesGameByUser(User user) {
				
		return refereesGame.stream().filter(refereeGame 
				-> refereeGame.getUser() != null && 
				refereeGame.getUser().getIdUser().equals(user.getIdUser())).collect(Collectors.toList());		
	}
	
	public Timestamp getGameEndDate(){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.gameDate);
		cal.add(Calendar.HOUR, 2);
		return new Timestamp(cal.getTime().getTime());
		
	}
	
	public RefereeGame getRefereeGame(int refereeType){
		
		RefereeGame refereeGame = null;
		
		if(refereesGame != null){
			
			for(RefereeGame rg : refereesGame){
				if(rg.getRefereeType() == refereeType){
					refereeGame = rg;
					break;
				}
			}
		}
		
		return refereeGame;
	}
	
	public boolean isUnassigned(){
		
		boolean result = false;
		
		if(refereesGame != null && refereesGame.size() > 0){
			
			for(RefereeGame refereeGame : refereesGame){
				if(refereeGame.getUser() == null){
					result = true;
					break;
				}
			}
		}
		else
			result = true;

		return result;
	}
	
	
	public boolean isUnconfirmed(){
		
		boolean result = false;
		
		for(RefereeGame refereeGame : refereesGame){
			if(refereeGame.getUser() != null && refereeGame.isConfirmed() == null){
				result = true;
				break;
			}
		}

		return result;
	}
	
	public boolean isDeclined(){
		
		boolean result = false;
		
		for(RefereeGame refereeGame : refereesGame){
			if(refereeGame.getUser() != null && 
					 refereeGame.isConfirmed() != null && !refereeGame.isConfirmed()){
				result = true;
				break;
			}
		}

		return result;
	}
	
	public boolean isConfirmed(){
		
		boolean result = true;

		if(!isUnassigned()){
			for(RefereeGame refereeGame : refereesGame){
				if(refereeGame.getUser() != null && 
						(refereeGame.isConfirmed() == null || !refereeGame.isConfirmed())){
					result = false;
					break;
				}
			}
		}
		else
			result = false;
		
		return result;
	}
	
	public boolean isRequested(){
		
		boolean result = false;

			for(RefereeGame refereeGame : refereesGame){
				if(refereeGame.getUser() == null && refereeGame.getUsers().size() > 0){ 
					result = true;
					break;
				}
			}
		
		return result;
	}
	
	public boolean isConfirmedByReferee(String idUser){
		
		boolean result = true;

			for(RefereeGame refereeGame : refereesGame){
				if(refereeGame.getUser() != null && refereeGame.getUser().getIdUser().equals(idUser)){
					if(refereeGame.isConfirmed() == null ||  !refereeGame.isConfirmed()){
						result = false;
						break;
					}
				}
			}
		
		return result;
	}
	
	public boolean isUnConfirmedByReferee(String idUser){
		
		boolean result = false;

			for(RefereeGame refereeGame : refereesGame){
				if(refereeGame.getUser() != null && refereeGame.getUser().getIdUser().equals(idUser)
						&& refereeGame.isConfirmed() == null){
			
					result = true;
					break;
					
				}
			}
		
		return result;
	}
	
	public boolean isDeclinedByReferee(String idUser){
		
		boolean result = false;

			for(RefereeGame refereeGame : refereesGame){
				if(refereeGame.getUser() != null && refereeGame.getUser().getIdUser().equals(idUser)
						&& refereeGame.isConfirmed() != null && !refereeGame.isConfirmed()){
			
					result = true;
					break;
					
				}
			}
		
		return result;
	}
	
	
	public boolean isRequestedByReferee(String idUser){
		
		boolean result = false;

			for(RefereeGame refereeGame : refereesGame){
				if(refereeGame.getUser() == null && 
						refereeGame.getUsers().stream().filter(user -> user.getIdUser().equals(idUser)).count() == 1){ 
					result = true;
					break;
				}
			}
		
		return result;
	}
	
	public void deleteRefereeGame(RefereeGame refereeGame){
		refereesGame.removeIf(rg -> rg.getIdRefereeGame().equals(refereeGame.getIdRefereeGame()));
	}
	
	public boolean isAvailable(User user){
	
		
		if(user.getUserRole() == User.ADMIN)
			return false; /*This user is not a referee*/
		else
			return (user.getRefereeAvailability().stream().filter(refereeAvailability 
				-> refereeAvailability.getStartDate().compareTo(this.gameDate) <= 0 &&
				refereeAvailability.getEndDate().compareTo(this.gameDate) >= 0).count() > 0);

	}
	

	public boolean hasConflict(){
		
		boolean result = false;
		
		for(RefereeGame refereeGame : refereesGame){
			
			if(refereeGame.getUser() != null){
				if(!this.isAvailable(refereeGame.getUser()) 
					|| !refereeGame.getUser().getUserRefereeType(refereeGame.getRefereeType())
					|| refereeGame.getUser().hasOtherGame(this)){
					result = true;
					break;
				}
			}
		}
		
		return result; 
	}
	
}

