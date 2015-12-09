package com.myproject.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TEAM")
public class Team implements Serializable{

	private static final long serialVersionUID = 2607356364780984918L;
	
	@Id
	@Column(name = "idTEAM")
	@GeneratedValue
	private String idTeam;
	
	@Column(name="TEAM_NAME", nullable = false, unique = true, length = 128)
	private String teamName;

	@Column(name="TEAM_SHORT_NAME", unique = true, length = 128)
	private String teamShortName;
	
	@Column(name="TEAM_LOCATION", length = 45)
	private String teamLocation;
	
	
	/*@ManyToMany
	@JoinTable(name = "TEAM_CATEGORY", 
				joinColumns = { @JoinColumn( name = "idTEAM")},
				inverseJoinColumns = { @JoinColumn(name = "idCATEGORY") })
	
	private Set<Category> categories = new HashSet<Category>();

	
	public Team(Set<Category> categories, String teamName, String teamLocation) {
		super();
		this.categories = categories;
		this.teamName = teamName;
		this.teamLocation = teamLocation;
	}*/
	
	public Team(String idTeam, String teamName, String teamLocation) {
		super();
		this.idTeam = idTeam;
		this.teamName = teamName;
		this.teamLocation = teamLocation;
	}

	public Team(String idTeam, String teamName, String teamShortName,
			String teamLocation) {
		super();
		this.idTeam = idTeam;
		this.teamName = teamName;
		this.teamShortName = teamShortName;
		this.teamLocation = teamLocation;
	}

	public Team(String teamName, String teamLocation) {
		super();
		this.teamName = teamName;
		this.teamLocation = teamLocation;

	}
	
	public Team(String teamName) {
		super();
		this.teamName = teamName;
	}

	public Team(){
	}
	
	
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
	
	public String getTeamShortName() {
		if(teamShortName == null || teamShortName.equals(""))
			return this.teamName.toUpperCase().substring(0, 3);
		else
			return teamShortName;
	}

	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}

	public String getTeamLocation() {
		return teamLocation;
	}

	public void setTeamLocation(String teamLocation) {
		this.teamLocation = teamLocation;
	}

	/*public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	*/
	
	@Override
	public boolean equals(Object o){
		
		boolean result = false;
		if(o instanceof Team){
			Team t = (Team)o;
			result = t.getTeamName().equalsIgnoreCase(this.teamName)
					&& ((t.getTeamShortName() == null && this.teamShortName == null ) 
						|| (t.getTeamShortName() != null && this.teamShortName != null 
							&& t.getTeamShortName().equalsIgnoreCase(this.teamShortName)))
					&& ((t.getTeamLocation() == null && this.teamLocation == null ) 
							|| (t.getTeamLocation() != null && this.teamLocation != null 
								&& t.getTeamLocation().equalsIgnoreCase(this.teamLocation)));
		}

		return result;
	}

}

