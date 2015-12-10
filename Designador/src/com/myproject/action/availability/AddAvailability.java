package com.myproject.action.availability;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.WordUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.myproject.model.RefereeAvailability;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class AddAvailability extends ActionSupport implements SessionAware, ServletContextAware  {

	private static final long serialVersionUID = 7158095113819658848L;

	private Map<String, Object> session;
    private ServletContext context;

    private String idUser;
	private String dateStr, startTime, endTime;
	
	private String refereeAvailabilityId;
	
	private User user;
	private Map<String, FieldCondition> eqRestrictions ;
	private GenericService service;


	@Override
	public String execute() {
		
		eqRestrictions = new HashMap<String, FieldCondition>();

		List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		context.setAttribute("users", users);
		
		if (idUser == null || idUser.equals("")){	
			addActionError("Por favor, introduce el id del usuario para añadir su disponibilidad.");
			return "not found";	
		}	
		else{
			
			user = (User)session.get("user");
			
			if (!idUser.equals(user.getIdUser()) && !user.isAdmin()){
				addActionError("No tienes permiso para ver esta página");
				return ERROR;
			}
			else if (!idUser.equals(user.getIdUser())){
				eqRestrictions.put("idUser", new FieldCondition(idUser));
				user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
				if(user == null){
					addActionError("El usuario que has introducido no existe o ya se ha eliminado");
					return SUCCESS;
				}
			}
		}
		
		
		Date startDate, endDate;
		Timestamp startTimestamp, endTimestamp; 

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d HH:mm");
		sdf.setLenient(false);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		if(startTime == null || startTime.trim().equals(""))
			startTime = "00:00";
		
		try {

			startDate = sdf.parse(dateStr + " " + startTime);
			startTimestamp =  new Timestamp(startDate.getTime());


			if(endTime == null || endTime.trim().equals("")){
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(startDate);
				cal2.set(Calendar.HOUR_OF_DAY, 23);
				cal2.set(Calendar.MINUTE, 59);
				cal2.set(Calendar.SECOND, 0);
				cal2.set(Calendar.MILLISECOND, 0);
				endDate = cal2.getTime();
			}
			else{
				endDate = sdf.parse(dateStr + " " + endTime);

				if(endDate.compareTo(startDate) < 0){
					setAttributeVariables(calendar);
					addActionError("La hora del fin de tu disponiblidad no puede ser menor que la hora del inicio.");
					return SUCCESS;
				}
			}
			
			endTimestamp = new Timestamp(endDate.getTime());
			
			if(calendar.getTime().compareTo(startDate) <= 0){
				
				eqRestrictions.clear();
				eqRestrictions.put("user", new FieldCondition(user));
				calendar.setTime(startTimestamp);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				
				
				Map<Integer,Object> btwDate = new HashMap<Integer, Object>();
				btwDate.put(1, new Timestamp(calendar.getTime().getTime()));
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				btwDate.put(-1, new Timestamp(calendar.getTime().getTime()));
				eqRestrictions.put("startDate", new FieldCondition(btwDate));
				
				List<?> refereeAvailabilityList = service.GetModelDataList(RefereeAvailability.class, eqRestrictions, "startDate", true);
				
				RefereeAvailability refereeAvailability = null;
				if(refereeAvailabilityList == null){

					refereeAvailability = new RefereeAvailability(user,startTimestamp,endTimestamp);
					service.SaveOrUpdateModelData(new RefereeAvailability(user,startTimestamp,endTimestamp));
				}
				else{
					 for(Object ra :  refereeAvailabilityList){
				
						 if(((RefereeAvailability)ra).getStartDate().compareTo(endTimestamp) <= 0 &&
								 ((RefereeAvailability)ra).getEndDate().compareTo(startTimestamp) >= 0 ){
							 
							if(((RefereeAvailability)ra).getStartDate().compareTo(startTimestamp) <= 0){
								
								startTimestamp = ((RefereeAvailability)ra).getStartDate();
								
								if(((RefereeAvailability)ra).getEndDate().compareTo(endTimestamp) < 0)
									service.DeleteModelData(ra);	
								else
									endTimestamp = ((RefereeAvailability)ra).getEndDate();
								
							}		
							else {
								
								if(((RefereeAvailability)ra).getEndDate().compareTo(endTimestamp) >= 0) {
									endTimestamp = ((RefereeAvailability)ra).getEndDate();
								}
								
								service.DeleteModelData(ra);	
							} 
						 }
					 }
					 
					 Timestamp ets = endTimestamp;
					 Timestamp sts = startTimestamp;

					 if(refereeAvailabilityList.stream().filter(ra -> ((RefereeAvailability)ra).getStartDate().equals(sts) &&
							 ((RefereeAvailability)ra).getEndDate().equals(ets)).count() == 0){

							refereeAvailability = new RefereeAvailability(user,startTimestamp,endTimestamp);
							service.SaveOrUpdateModelData(new RefereeAvailability(user,startTimestamp,endTimestamp));
					 }
						 
					 
				}
				
				
				if(user.getIdUser().equals(((User)session.get("user")).getIdUser())
						&& refereeAvailability != null){
					user.getRefereeAvailability().add(refereeAvailability);
					session.put("user", user);
				}
			}
			else{
				setAttributeVariables(calendar);
				addActionError("Por favor, introduce una fecha superior a la fecha de hoy.");
				return INPUT;
			}
			
		} catch (ParseException e) {
			setAttributeVariables(calendar);
			addActionError("Por favor, introduce la fecha exacta que quieres activar.");
			return SUCCESS;	

		} catch (NullPointerException e){
			
			setAttributeVariables(calendar);
			addActionError("Por favor, introduce la fecha exacta que quieres activar.");
			return SUCCESS;
		}
		
		setAttributeVariables(calendar);
		return SUCCESS;

	}
	
	
	
	void setAttributeVariables(Calendar calendar){
		
		SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMMM",new Locale("es","ES"));
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
		SimpleDateFormat yearMontFormat = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");

		context.setAttribute("userFullName", user.getUserFullName());
		context.setAttribute("selectedMonthName", WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
		context.setAttribute("selectedYear", yearFormat.format(calendar.getTime()));
		context.setAttribute("selectedMonth", monthFormat.format(calendar.getTime()));
		
		Map<String,String> monthsList = new LinkedHashMap<String,String>();
		
		for(int i = 0; i < 4; i++){
			monthsList.put(yearMontFormat.format(calendar.getTime()), WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
			calendar.add(Calendar.MONTH, 1);
		}

		context.setAttribute("monthsList", monthsList);

		calendar.add(Calendar.MONTH, -4);
		calendar.set(Calendar.DATE, 1);
		Timestamp fromDate = new Timestamp(calendar.getTime().getTime());
		calendar.add(Calendar.MONTH, 1);
		Timestamp toDate = new Timestamp(calendar.getTime().getTime());
		

		eqRestrictions.clear();
		eqRestrictions.put("startDate", new FieldCondition (fromDate,1));
		eqRestrictions.put("endDate", new FieldCondition (toDate, -1));
		eqRestrictions.put("user", new FieldCondition (user));
		
		List<?> availableDates = new LinkedList<String>();
		availableDates = service.GetModelDataList(RefereeAvailability.class, eqRestrictions, "startDate", true);
		context.setAttribute("availableDates",availableDates);

		List<String> availableStartDates = new LinkedList<String>();
		availableDates.forEach(ad ->  availableStartDates.add(dateFormat.format(((RefereeAvailability)ad).getStartDate())));
		context.setAttribute("availableStartDates",availableStartDates);

		context.setAttribute("dateStr", context.getAttribute("selectedYear") + "-" + context.getAttribute("selectedMonth"));			
				
	}

	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getDateStr() {
		return dateStr;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}



	public String getEndTime() {
		return endTime;
	}



	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}



	public String getStartTime() {
		return startTime;
	}



	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}



	public String getRefereeAvailabilityId() {
		return refereeAvailabilityId;
	}



	public void setRefereeAvailabilityId(String refereeAvailabilityId) {
		this.refereeAvailabilityId = refereeAvailabilityId;
	}
	

}
