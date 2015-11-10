package com.myproject.action.venue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.WordUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.Address;
import com.myproject.model.Venue;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CRUDVenue  extends ActionSupport implements ServletContextAware{

	private static final long serialVersionUID = -4438495252365119204L;
	
	private String idVenue;
	private String venueName;
	private String venueContactName, venueContactPhone;
	private String address1, address2, province, city, zipcode;
	private List<?> venues;
	private Venue venue;

	private GenericService service;

    private ServletContext context;

	private Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

	@Override
	@SkipValidation
	public String execute() {
		
		if( ActionContext.getContext().getName().equalsIgnoreCase("addEditVenue")){
			
			if (idVenue != null && !idVenue.equals("")){
				eqRestrictions.put("idVenue", new FieldCondition(idVenue));
				venue = (Venue) service.GetUniqueModelData(Venue.class, eqRestrictions);
				if(venue != null){
					setIdVenue(venue.getIdVenue());
					setVenueName(venue.getVenueName());
					setVenueContactName(venue.getVenueContactName());
					setVenueContactPhone(venue.getVenueContactPhone());
					if(venue.getVenueAddress() != null){
						setAddress1(venue.getVenueAddress().getAddress1());
						setAddress2(venue.getVenueAddress().getAddress2());
						setProvince(venue.getVenueAddress().getProvince());
						setCity(venue.getVenueAddress().getCity());
						setZipcode(venue.getVenueAddress().getZipcode());
					}
				}
			}
			
			return NONE;
			
		}else{
			venues = service.GetModelDataList(Venue.class, eqRestrictions, "venueName", true);
			return SUCCESS;
		}
	}

	public String addEditVenue(){
		
		Address address = null;
		if(!address1.trim().equals("")  || !address2.trim().equals("") || !province.trim().equals("") 
				|| !city.trim().equals("") || !zipcode.equals(""))
			address = new Address(address1.trim(), address2.trim(), province.trim(), city.trim(), zipcode);
		
		/*Editing an existing Venue*/
		if(idVenue != null && !idVenue.equals("")){
			eqRestrictions.put("idVenue", new FieldCondition(idVenue));
			venue = (Venue) service.GetUniqueModelData(Venue.class, eqRestrictions);			
			
			if(venue != null){
				if(!new Venue(venueName.trim(),venueContactName.trim(),venueContactPhone.trim(), address).equals(venue)){
					if (!venueName.trim().equalsIgnoreCase(venue.getVenueName()) && venueNameAlreadyExists(venueName.trim())){
						addFieldError("venueName","El nombre del equipo ya existe. Por favor, elige otro.");
						return INPUT;
					 }
											
					venue.setVenueName(venueName.trim().substring(0,1).toUpperCase() + venueName.trim().substring(1));
					venue.setVenueContactName(WordUtils.capitalize(venueContactName.trim()));
					venue.setVenueContactPhone(venueContactPhone.trim());
					
					if(venue.getVenueAddress() != null){
					
						address = venue.getVenueAddress();
						address.setAddress1(address1.trim());
						address.setAddress2(address2.trim());
						address.setCity(city.trim());
						address.setProvince(province.trim());
						address.setZipcode(zipcode.trim());
					}
					
					venue.setVenueAddress(address);
					
					service.SaveOrUpdateModelData(venue);
					
				}
				
				addActionMessage("Se ha actualizado el nombre de la pista con exito.");
				return SUCCESS;
	
			}
		}
		
		/*Adding a new Venue*/
		
		if(venueNameAlreadyExists(venueName.trim())){
			addFieldError("venueName","El nombre de la pista ya existe. Por favor, elige otro.");
			return INPUT;
		}
		else{
			
			venue = new Venue(venueName.trim().substring(0,1).toUpperCase() + venueName.trim().substring(1),
					WordUtils.capitalize(venueContactName.trim()),venueContactPhone, address);
			service.SaveOrUpdateModelData(venue);
			addActionMessage("Se ha sido añadido una nueva pista con exito.");
			return SUCCESS;
		}
	}
	
	@SkipValidation
	public String deleteVenue(){
		
		if (idVenue == null || idVenue.equals("")){
			
			addActionError("Por favor, introduce el id de la pista que quieres eliminar.");
			venues = service.GetModelDataList(Venue.class, eqRestrictions, "venueName", true);
			context.setAttribute("venues", venues);
			return INPUT;
		}
		else{
			eqRestrictions.put("idVenue", new FieldCondition(idVenue));
			venue = (Venue) service.GetUniqueModelData(Venue.class, eqRestrictions);			
			
			if(venue != null){
				service.DeleteModelData(venue);
				addActionMessage("La pista ha sido eliminado con exito.");
				return SUCCESS;

			}
			else{
				addActionError("La pista que quieres eliminar no existe o ya se ha eliminado.");
				eqRestrictions.clear();
				venues = service.GetModelDataList(Venue.class, eqRestrictions, "venueName", true);
				context.setAttribute("venues", venues);
				return INPUT;
			}

		}
	}
	
	public Boolean venueNameAlreadyExists(String venueName){
		
		eqRestrictions.clear();
		eqRestrictions.put("venueName", new FieldCondition(venueName));
			
		return (Venue) service.GetUniqueModelData(Venue.class, eqRestrictions) != null;
		
	}
	
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public List<?> getVenues() {
		return venues;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
