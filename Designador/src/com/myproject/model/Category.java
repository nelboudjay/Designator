package com.myproject.model;



import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="CATEGORY", uniqueConstraints = { @UniqueConstraint( columnNames = { "CATEGORY_NAME", "CATEGORY_GENDER" } ) } )
public class Category implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	public static final int MALE = 1;
	public static final int FEMALE = 2;
	public static final int MIXED = 3;
	
	@Id
	@Column(name = "idCATEGORY")
	@GeneratedValue
	private String idCategory;
	
	@Column(name="CATEGORY_NAME", nullable = false, length = 128)
	private String categoryName;

	@Column(name="CATEGORY_GENDER", nullable = false)
	private int categoryGender;
	
	/*@ManyToMany(mappedBy="categories")
	private Set<Team> teams = new HashSet<Team>();*/
	
	
	public Category(String idCategory, String categoryName, int categoryGender) {
		super();
		this.idCategory = idCategory;
		this.categoryName = categoryName;
		this.categoryGender = categoryGender;
	}

	public Category(String categoryName, int categoryGender) {
		super();
		this.categoryName = categoryName;
		this.categoryGender = categoryGender;
	}
	
	public Category(){
	}
	
	/*public Category(Set<Team> teams, String categoryName, int categoryGender) {
		super();
		this.teams = teams;
		this.categoryName = categoryName;
		this.categoryGender = categoryGender;
	}*/
	
	public String getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(String idCategory) {
		this.idCategory = idCategory;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryGender() {
		return categoryGender;
	}

	public void setategoryGender(int categoryGender) {
		this.categoryGender = categoryGender;
	}
	
	/*@ManyToMany( targetEntity=Team.class, mappedBy = "categories")
	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}*/

	public String getCategoryGenderShortName(){
		
		switch (categoryGender){
		
		case FEMALE: 	return "F";
		case MIXED:		return "MIX";
		default:		return "M";
			
		}
	}
	
	public String getCategoryGenderName(){
		
		switch (categoryGender){
		
		case FEMALE: 	return "Femenino";
		case MIXED:		return "Mixto";
		default:		return "Masculino";
			
		}
	}
	
	@Override
	public boolean equals(Object o){
		
		boolean result = false;
		if(o instanceof Category){
			Category cat = (Category)o;
			result = cat.getCategoryName().equalsIgnoreCase(this.categoryName)
					&& cat.getCategoryGender() == this.categoryGender;
		}

		return result;
	}

}

