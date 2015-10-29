package com.myproject.action;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.opensymphony.xwork2.ActionSupport;

public class GetAvailability extends ActionSupport implements SessionAware, ServletContextAware {

	private static final long serialVersionUID = 7821048979321587609L;

	private Map<String, Object> session;
    private ServletContext context;


	private String idUser;
	private String userFullName;
	private Map<String,String> monthsList = new LinkedHashMap<String,String>();
	private String dateStr;
	private String selectedMonthName, selectedYear;
	private int selectedMonth;
	
	Map<String, Object> eqRestrictions = new HashMap<String, Object>();	
	
	private GenericService service;

	@Override
	public String execute() {
		
		User user;
		
		List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		context.setAttribute("users", users);
		
		if (idUser == null || idUser.equals("")){		
			addActionError("Por favor, introduce el id del usuario para ver su disponibilidad.");
			return "not found";	
		}	
		else{
			user = (User)session.get("user");
	
			if (idUser.equals(user.getIdUser())){
			

				setSelectedDate(dateStr);
				setUserFullName(user.getUserFullName());
				
				eqRestrictions.put("user", user);
				List<?> availableDates = service.GetModelDataList(RefereeAvailability.class, eqRestrictions, "startDate", true);
				
				for(Object  ra : availableDates)
					System.out.println(((RefereeAvailability)ra).getStartDate());
				
				System.out.println(selectedYear);
				System.out.println(selectedMonth);
				return NONE;
			}
			else{
				
				Map<String, Object> eqRestrictions = new HashMap<String, Object>();	
		
				eqRestrictions.put("idUser", idUser);
				user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
				
				if(user != null){
					setSelectedDate(dateStr);
					setUserFullName(user.getUserFullName());

					return NONE;
				}else{
					addActionError("El usuario que has introducido no existe o ya se ha eliminado");
					return "not found";
				}
			}
		}
	}
	
	
	public void setSelectedDate(String dateStr){
	
		SimpleDateFormat monthNameFormat = new SimpleDateFormat("MMMM",new Locale("es","ES"));
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
			
			Date date = sdf.parse(dateStr);
		
			
			if(calendar.getTime().compareTo(date) >= 0){
				selectedYear =  yearFormat.format(calendar.getTime());
				selectedMonthName = WordUtils.capitalize(monthNameFormat.format(calendar.getTime()));
				selectedMonth =  calendar.get(Calendar.MONTH) + 1;
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
					selectedMonth =  calendar.get(Calendar.MONTH) + 1;
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
						selectedMonth =  calendar.get(Calendar.MONTH) + 1;
						calendar.add(Calendar.MONTH, -2);
						for(int i = 0; i < 6; i++){
							monthsList.put(sdf.format(calendar.getTime()), WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
							calendar.add(Calendar.MONTH, 1);
						}
					}
				}
				
			}
		
		} catch (ParseException e) {
			selectedYear =  yearFormat.format(calendar.getTime());
			selectedMonthName = WordUtils.capitalize(monthNameFormat.format(calendar.getTime()));
			selectedMonth =  calendar.get(Calendar.MONTH) + 1;
			for(int i = 0; i < 4; i++){
				monthsList.put(sdf.format(calendar.getTime()), WordUtils.capitalize(monthNameFormat.format(calendar.getTime())));
				calendar.add(Calendar.MONTH, 1);
			}
		} catch (NullPointerException e){
			selectedYear =  yearFormat.format(calendar.getTime());
			selectedMonthName = WordUtils.capitalize(monthNameFormat.format(calendar.getTime()));
			selectedMonth =  calendar.get(Calendar.MONTH) + 1;
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

	
	public int getSelectedMonth() {
		return selectedMonth;
	}


	public String getSelectedYear() {
		return selectedYear;
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
