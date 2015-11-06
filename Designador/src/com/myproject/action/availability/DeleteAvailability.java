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

public class DeleteAvailability extends ActionSupport implements SessionAware, ServletContextAware  {

	private static final long serialVersionUID = 7158095113819658848L;

	private Map<String, Object> session;
    private ServletContext context;

    private String idUser;
	private String dateStr;
	
	private User user;
	private Map<String, FieldCondition> eqRestrictions ;
	private GenericService service;


	@Override
	public String execute() {
		
		eqRestrictions = new HashMap<String, FieldCondition>();

		List<?> users = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		context.setAttribute("users", users);
		
		if (idUser == null || idUser.equals("")){	
			addActionError("Por favor, introduce el id del usuario para eliminar su disponibilidad.");
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
		}
		
		
		RefereeAvailability refereeAvailability;

		Date date;
		Timestamp startDate; 

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d");
		sdf.setLenient(false);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		try {
			
			date = sdf.parse(dateStr);
			
			if(calendar.getTime().compareTo(date) <= 0){
				startDate =  new Timestamp(date.getTime());
				eqRestrictions.clear();
				eqRestrictions.put("user", new FieldCondition(user));
				eqRestrictions.put("startDate", new FieldCondition(startDate));
				refereeAvailability = (RefereeAvailability)service.GetUniqueModelData(RefereeAvailability.class, eqRestrictions);
				if(refereeAvailability != null)
					service.DeleteModelData(refereeAvailability);	
			}
			else
				addActionError("Por favor, la fecha que quieres desactivar debe ser superior a la fecha de hoy");
			
			
		} catch (ParseException e) {
			
			setAttributeVariables(calendar);
			addActionError("Por favor, introduce la fecha exacta que quieres desactivar.");
			return SUCCESS;	

		} catch (NullPointerException e){
			
			setAttributeVariables(calendar);
			addActionError("Por favor, introduce la fecha exacta que quieres desactivar.");
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
