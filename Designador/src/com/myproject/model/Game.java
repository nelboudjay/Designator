package com.myproject.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

@Entity
@Table(name="GAME", uniqueConstraints = {@UniqueConstraint(columnNames={"HOME_TEAM", "AWAY_TEAM","GAME_DATE"})})
public class Game {
	
	public static final boolean PUBLISHED = true;
	public static final boolean UNPUBLISHED = false;
	
	public Game(Team homeTeam, Team awayTeam, Timestamp gameDate,
			Venue gameVenue, boolean gameStatus, League gameLeague) {
		super();
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.gameDate = gameDate;
		this.gameVenue = gameVenue;
		this.gameStatus = gameStatus;
		this.gameLeague = gameLeague;
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
	@JoinColumn(name = "GAME_VENUE", nullable = false)
	private Venue gameVenue;
	
	@OneToOne
	@ForeignKey (name = "FK_GAME__LEAGUE")
	@JoinColumn(name = "GAME_LEAGUE", nullable = false)
	private League gameLeague;
	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "GAME_STATUS", nullable = false)
	private boolean gameStatus;

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

}

