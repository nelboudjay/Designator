package com.myproject.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LEAGUE")
public class League {

	public League(String idLeague, String leagueName) {
		super();
		this.idLeague = idLeague;
		this.leagueName = leagueName;
	}

	public League(){
	}
	
	
	@Id
	@Column(name = "idLEAGUE")
	@GeneratedValue
	private String idLeague;
	
	@Column(name="LEAGUE_NAME", nullable = false, unique = true, length = 45)
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

