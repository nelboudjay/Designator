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

public class GetAvailability extends ActionSupport implements SessionAware, ServletContextAware {

	private static final long serialVersionUID = 7821048979321587609L;

	private Map<String, Object> session;
    private ServletContext context;

    Date date;
    
	private String idUser;
	private String userFullName;
	private Map<String,String> monthsList; 
	private String dateStr;
	private String selectedMonthName, selectedMonth, selectedYear;
	private List<?> availableDates;
	private List<String> availableStartDates; 
	
	
	private GenericService service;

	@Override
	public String execute() {
		
		User user;
		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	

		List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		context.setAttribute("users", users);

		if (idUser == null || idUser.equals("")){	
			addActionError("Por favor, introduce el id del usuario para ver su disponibilidad.");
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
					return "not found";
				}
			}
			
			if(user.getUserRole() == User.ADMIN){ /*User is only Admin*/
				if(user.equals((User)session.get("user")))
					addActionError("No dispones de perfil de árbitro para consultar tu disponibilidad");
				else
					addActionError("Este usuario no tiene perfil de árbitro para consultar su disponibilidad");

				return "not found";

			}
			
			setSelectedDate(dateStr);
			setUserFullName(user.getUserFullName());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Timestamp fromDate = new Timestamp(date.getTime());
			calendar.add(Calendar.MONTH, 1);
			Timestamp toDate = new Timestamp(calendar.getTime().getTime());

			eqRestrictions.clear();
			eqRestrictions.put("startDate", new FieldCondition (fromDate,1));
			eqRestrictions.put("endDate", new FieldCondition (toDate, -1));
			eqRestrictions.put("user", new FieldCondition (user));

			availableDates = service.GetModelDataList(RefereeAvailability.class, eqRestrictions, "startDate", true);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
			availableStartDates = new LinkedList<String>();
			availableDates.forEach(ad ->  availableStartDates.add(dateFormat.format(((RefereeAvailability)ad).getStartDate())));
			
			setDateStr(selectedYear + "-" + selectedMonth);

			/*Display only users with Referee profile role*/
			users.removeIf(usr -> (((User)usr).getUserRole() == User.ADMIN));

			return SUCCESS;

		}
	}
	
	public void setSelectedDate(String dateStr){
	
		monthsList =  new LinkedHashMap<String,String>();
		SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMMM",new Locale("es","ES"));
		SimpleDateFormat monthFormat = new SimpleDateFormat("MM",new Locale("es","ES"));
		SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		sdf.setLenient(false);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

        try {
			
			date = sdf.parse(dateStr);
			if(calendar.getTime().compareTo(date) >= 0){
				date = calendar.getTime();
				selectedYear =  yearFormat.format(calendar.getTime());
				selectedMonthName = WordUtils.capitalize(monthNameFormat.format(calendar.getTime()));
				selectedMonth =  monthFormat.format(calendar.getTime());
				for(int i = 0; i < 4; i++){
					monthsList.put(sdf.format(calendar.getTime()), WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
					calendar.add(Calendar.MONTH, 1);
				}
				
			}
			else{ 
				selectedYear =  yearFormat.format(date);
				selectedMonthName = WordUtils.capitalize(monthNameFormat.format(date));

				calendar.add(Calendar.MONTH, 1);
				if(calendar.getTime().compareTo(date) == 0){
					selectedMonth =  monthFormat.format(calendar.getTime());
					calendar.add(Calendar.MONTH, -1);
					for(int i = 0; i < 5; i++){
						monthsList.put(sdf.format(calendar.getTime()), WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
						calendar.add(Calendar.MONTH, 1);
					}
				}

				else{
					calendar.add(Calendar.MONTH, 1);
					if(calendar.getTime().compareTo(date) <= 0){
						calendar.setTime(date);
						selectedMonth =  monthFormat.format(calendar.getTime());
						calendar.add(Calendar.MONTH, -2);
						for(int i = 0; i < 6; i++){
							monthsList.put(sdf.format(calendar.getTime()), WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
							calendar.add(Calendar.MONTH, 1);
						}
					}
				}
				
			}
		
		} catch (ParseException e) {
			date = calendar.getTime();
			selectedYear =  yearFormat.format(calendar.getTime());
			selectedMonthName = WordUtils.capitalize(monthNameFormat.format(calendar.getTime()));
			selectedMonth =  monthFormat.format(calendar.getTime());
			for(int i = 0; i < 4; i++){
				monthsList.put(sdf.format(calendar.getTime()), WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
				calendar.add(Calendar.MONTH, 1);
			}

		} catch (NullPointerException e){
			date = calendar.getTime();
			selectedYear =  yearFormat.format(calendar.getTime());
			selectedMonthName = WordUtils.capitalize(monthNameFormat.format(calendar.getTime()));
			selectedMonth =  monthFormat.format(calendar.getTime());
			for(int i = 0; i < 4; i++){
				monthsList.put(sdf.format(calendar.getTime()), WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
				calendar.add(Calendar.MONTH, 1);
			}

		}
	}
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}


	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}

	public Map<String,String> getMonthsList() {
		return monthsList;
	}
	
	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getSelectedMonthName() {
		return selectedMonthName;
	}

	
	public String getSelectedMonth() {
		return selectedMonth;
	}


	public String getSelectedYear() {
		return selectedYear;
	}
	
	public List<?> getAvailableDates() {
		return availableDates;
	}


	public List<String> getAvailableStartDates() {
		return availableStartDates;
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

	
}
	