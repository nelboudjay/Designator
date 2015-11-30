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

@Entity
@Table(name="USER_REFEREE_TYPE", uniqueConstraints = {@UniqueConstraint(columnNames={"USER", "REFEREE_TYPE"})})
public class UserRefereeType implements Serializable{
	
	private static final long serialVersionUID = 7141061081920779112L;
	
	public static final int PRINCIPAL = 1;
	public static final int AUXILIAR = 2;
	public static final int ANOTADOR = 3;
	public static final int CRONOMETRADOR = 4;
	public static final int OPERADOR30 = 5;
	public static final int COCHE = 6;
	
	public static final int REFEREETYPES = 6;

	public UserRefereeType(User user, int refereeType) {
		super();
		this.user = user;
		this.refereeType = refereeType;
	}

	public UserRefereeType(){
	}
	

	@Id
	@Column(name = "idUSER_REFEREE_TYPE")
	@GeneratedValue
	private String idUserRefereeType;
	
	@ManyToOne
	@ForeignKey (name = "FK_USER_REFEREE_TYPE__USER")
	@JoinColumn(name = "USER", nullable = false)
	private User user;
	
	@Column(name="REFEREE_TYPE", nullable = false)
	private int refereeType;

	
	public String getIdUserRefereeType() {
		return idUserRefereeType;
	}

	public void setIdUserRefereeType(String idUserRefereeType) {
		this.idUserRefereeType = idUserRefereeType;
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
	
	public String getRefereeTypeName(){
		
		switch (refereeType){
		
			case AUXILIAR:		return "Auxiliar";
			case ANOTADOR: 		return "Anotador";
			case CRONOMETRADOR:	return "Cronometrador";
			case OPERADOR30:	return "Operador 30\"";
			case COCHE:			return "Coche";
			default:			return "Principal";
				
		}
	}

}

