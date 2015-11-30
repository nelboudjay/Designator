package com.myproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

@Entity
@Table(name="REFEREE_GAME", uniqueConstraints = {@UniqueConstraint(columnNames={"GAME", "REFEREE_TYPE"})})
public class RefereeGame implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final boolean CONFIRMED = true;
	public static final boolean UNCONFIRMED = false;

	
	
	public RefereeGame(Game game, User user,
			int refereeType, boolean confirmed) {
		super();
		this.game = game;
		this.user = user;
		this.refereeType = refereeType;
		this.confirmed = confirmed;
	}

	public RefereeGame(Game game, User user,
			boolean confirmed) {
		super();
		this.game = game;
		this.user = user;
		this.confirmed = confirmed;
	}

	public RefereeGame(){
		super();
	}
	
	@Id
	@Column(name = "idREFEREE_GAME")
	@GeneratedValue
	private String idRefereeGame;
	
	@ManyToOne
	@ForeignKey (name = "FK_REFEREE_GAME__GAME")
	@JoinColumn(name = "GAME", nullable = false)
	private Game game;

	
	@ManyToOne
	@ForeignKey (name = "FK_REFEREE_GAME__USER")
	@JoinColumn(name = "USER")
	private User user;
	
	@Column(name="REFEREE_TYPE", nullable = false)
	private int refereeType;

	
	@Type(type = "org.hibernate.type.NumericBooleanType")
	@Column(name = "CONFIRMED", nullable = false)
	private boolean confirmed;


	public String getIdRefereeGame() {
		return idRefereeGame;
	}


	public void setIdRefereeGame(String idRefereeGame) {
		this.idRefereeGame = idRefereeGame;
	}


	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRefereeType() {
		return refereeType;
	}

	public void setRefereeType(int refereeType) {
		this.refereeType = refereeType;
	}

	public boolean isConfirmed() {
		return confirmed;
	}


	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	
}

