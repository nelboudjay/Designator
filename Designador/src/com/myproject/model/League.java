package com.myproject.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LEAGUE")
public class League implements Serializable{

	private static final long serialVersionUID = 9210813917132442031L;

	public League(String idLeague, String leagueName) {
		super();
		this.idLeague = idLeague;
		this.leagueName = leagueName;
	}

	public League(String leagueName) {
		super();
		this.leagueName = leagueName;
	}
	
	public League(){
	}
	
	
	@Id
	@Column(name = "idLEAGUE")
	@GeneratedValue
	private String idLeague;
	
	@Column(name="LEAGUE_NAME", nullable = false, unique = true, length = 128)
	private String leagueName;

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
	

}

