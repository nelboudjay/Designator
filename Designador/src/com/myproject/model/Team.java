package com.myproject.model;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEAM")
public class Team {

	public Team(String idTeam, String teamName, String teamLocation) {
		super();
		this.idTeam = idTeam;
		this.teamName = teamName;
		this.teamLocation = teamLocation;
	}

	public Team(String teamName, String teamLocation) {
		super();
		this.teamName = teamName;
		this.teamLocation = teamLocation;

	}
	
	public Team(){
	}
	
	
	@Id
	@Column(name = "idTEAM")
	@GeneratedValue
	private String idTeam;
	
	@Column(name="TEAM_NAME", nullable = false, unique = true, length = 128)
	private String teamName;

	@Column(name="TEAM_LOCATION", length = 45)
	private String teamLocation;
	
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
	
	@Override
	public boolean equals(Object o){
		
		boolean result = false;
		if(o instanceof Team){
			Team t = (Team)o;
			result = t.getTeamName().equalsIgnoreCase(this.teamName)
					&& t.getTeamLocation().equalsIgnoreCase(this.teamLocation);
		}

		return result;
	}

}

