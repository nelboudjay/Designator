package com.myproject.model;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;



@Entity
@Table(name = "REFEREE_AVAILABILITY")
public class RefereeAvailability implements Serializable{

	private static final long serialVersionUID = -3058890532896488659L;

	public RefereeAvailability(User user,
			Timestamp startDate, Timestamp endDate) {
		super();
		this.user = user;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public RefereeAvailability(){
		
	}
	
	@Id
	@Column(name = "idREFEREE_AVAILABILITY")
	@GeneratedValue
	private String refereeAvailabilityId;
	
	@ManyToOne
	@ForeignKey (name = "FK_REFEREE_AVAILABILITY__USER")
	@JoinColumn(name = "USER", nullable = false)
	private User user;

	@Column(name = "START_DATE", nullable = false)
	private Timestamp startDate;
	
	@Column(name = "END_DATE", nullable = false)
	private Timestamp endDate ;

	public String getRefereeAvailabilityId() {
		return refereeAvailabilityId;
	}

	public void setRefereeAvailabilityId(String refereeAvailabilityId) {
		this.refereeAvailabilityId = refereeAvailabilityId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
