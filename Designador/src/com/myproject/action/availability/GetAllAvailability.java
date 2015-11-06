package com.myproject.action.availability;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.myproject.model.RefereeAvailability;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class GetAllAvailability extends ActionSupport {

	private static final long serialVersionUID = 7821048979321587609L;


    private Date date;
    private Date dateBefore;
    private Date dateAfter;

	private String dateStr;
	private Map<User,Boolean> availableReferees = new LinkedHashMap<User, Boolean>();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private GenericService service;

	@Override
	public String execute() {
		
		setSelectedDate();
		
		dateBefore = new Date(date.getTime() - (1000 * 60 * 60 * 24));
		dateAfter = new Date(date.getTime() + (1000 * 60 * 60 * 24));
		dateStr = sdf.format(date);

		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	
		eqRestrictions.put("userRole", new FieldCondition(User.REFEREE,1));
		List<?> referees = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		
		if(referees != null){
			Timestamp startDate = new Timestamp(date.getTime());
			eqRestrictions.clear();
			eqRestrictions.put("startDate", new FieldCondition(startDate));

			List<?> allRefereeAvailability = service.GetModelDataList(RefereeAvailability.class, eqRestrictions, "userName", true);

			
			if(allRefereeAvailability == null)
				referees.forEach(referee -> 
						availableReferees.put((User)referee,false));
			else{
				for(Object referee : referees){
					if(allRefereeAvailability.stream().filter(refereeAvailability -> 
							((RefereeAvailability)refereeAvailability).getUser().equals((User)referee)).count() == 1)
						availableReferees.put((User)referee, true);
					else
						availableReferees.put((User)referee, false);
				}
			}
		}

		return SUCCESS;

	}
	
	public void setSelectedDate(){
		
		sdf.setLenient(false);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

        try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			date = calendar.getTime();
		} catch (NullPointerException e){
			date = calendar.getTime();
		}
	}


	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDateBefore() {
		return dateBefore;
	}

	public void setDateBefore(Date dateBefore) {
		this.dateBefore = dateBefore;
	}

	public Date getDateAfter() {
		return dateAfter;
	}

	public void setDateAfter(Date dateAfter) {
		this.dateAfter = dateAfter;
	}

	public Map<User, Boolean> getAvailableReferees() {
		return availableReferees;
	}

	public void setAvailableReferees(Map<User, Boolean> availableReferees) {
		this.availableReferees = availableReferees;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	
}
	