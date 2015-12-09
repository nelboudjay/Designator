package com.myproject.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Type;

@Entity
@Table(name="REFEREE_GAME", uniqueConstraints = {@UniqueConstraint(columnNames={"GAME", "REFEREE_TYPE"})})
public class RefereeGame implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final Boolean CONFIRMED = true;
	public static final Boolean UNCONFIRMED = null;
	public static final Boolean DECLINED = false;
	
	public RefereeGame(Game game, User user,
			int refereeType, Boolean confirmed) {
		super();
		this.game = game;
		this.user = user;
		this.refereeType = refereeType;
		this.confirmed = confirmed;
	}

	public RefereeGame(Game game, User user,
			Boolean confirmed) {
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
	@Column(name = "CONFIRMED")
	private Boolean confirmed;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "REFEREE_GAME_REQUEST", 
				joinColumns = { @JoinColumn( name = "idREFEREE_GAME")},
				inverseJoinColumns = { @JoinColumn(name = "idUSER") })
	
	private Set<User> users = new HashSet<User>();

	
	public RefereeGame(Set<User> users, Game game, User user, int refereeType, Boolean confirmed) {
		
		super();
		this.game = game;
		this.user = user;
		this.refereeType = refereeType;
		this.confirmed = confirmed;
		this.users = users;

	}
	
	
	
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

	public Boolean isConfirmed() {
		return confirmed;
	}


	public void setConfirmed(Boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
}

