package com.myproject.model;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="VENUE")
public class Venue implements Serializable{

	private static final long serialVersionUID = -1685008510554148755L;

	public Venue(String venueName, String venueContactName,
				String venueContactPhone, Address venueAddress) {
		super();
		this.venueName = venueName;
		this.venueContactName = venueContactName;
		this.venueContactPhone = venueContactPhone;
		this.venueAddress = venueAddress;
	}

	public Venue(String venueName) {
		super();
		this.venueName = venueName;
	}
	
	public Venue(){
		super();
	}
	
	@Id
	@Column(name = "idVENUE")
	@GeneratedValue
	private String idVenue;
	
	@Column(name="VENUE_NAME", nullable = false, unique = true, length = 45)
	private String venueName;

	@Column(name="VENUE_CONTACT_NAME", length = 45)
	private String venueContactName;
	
	@Column(name="VENUE_CONTACT_PHONE", length = 45)
	private String venueContactPhone;
	
	@OneToOne(cascade = CascadeType.ALL)
	@ForeignKey (name = "FK_VENUE__ADDRESS")
	@JoinColumn(name = "VENUE_ADDRESS", unique = true)
	private Address venueAddress;
	
	public String getIdVenue() {
		return idVenue;
	}

	public void setIdVenue(String idVenue) {
		this.idVenue = idVenue;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	
	public String getVenueContactName() {
		return venueContactName;
	}

	public void setVenueContactName(String venueContactName) {
		this.venueContactName = venueContactName;
	}

	public String getVenueContactPhone() {
		return venueContactPhone;
	}

	public void setVenueContactPhone(String venueContactPhone) {
		this.venueContactPhone = venueContactPhone;
	}

	public Address getVenueAddress() {
		return venueAddress;
	}

	public void setVenueAddress(Address venueAddress) {
		this.venueAddress = venueAddress;
	}
	
	@Override
	public boolean equals(Object o){
		
		boolean result = false;
		if(o instanceof Venue){
			Venue v = (Venue)o;
			result = v.getVenueName().equalsIgnoreCase(this.venueName)
					&& ((v.getVenueContactName() == null && this.venueContactName == null)
							|| (v.getVenueContactName() != null && this.venueContactName != null
								&& v.getVenueContactName().equalsIgnoreCase(this.venueContactName)))
					&& ((v.getVenueContactPhone() == null && this.venueContactPhone == null)
						|| (v.getVenueContactPhone() != null && this.venueContactPhone != null
							&& v.getVenueContactPhone().equalsIgnoreCase(this.venueContactPhone)))
					&& ((v.getVenueAddress() == null && this.venueAddress == null)
					||  (v.getVenueAddress() != null && this.venueAddress != null && v.getVenueAddress().equals(this.venueAddress)));
		}

		return result;
	}


}

