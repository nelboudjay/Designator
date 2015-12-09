package com.myproject.action.availability;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
    private Date dayBefore;
    private Date dayAfter;

	private String dateStr;
	private Map<User,List<AvailabilityPlot>> availableReferees = new LinkedHashMap<User, List<AvailabilityPlot>>();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");


	private GenericService service;

	@Override
	public String execute() {
		
		setSelectedDate();
		
		dayBefore = new Date(date.getTime() - (1000 * 60 * 60 * 24));
		dayAfter = new Date(date.getTime() + (1000 * 60 * 60 * 24));
		dateStr = sdf.format(date);

		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();	
		eqRestrictions.put("userRole", new FieldCondition(User.REFEREE,1));
		List<?> referees = service.GetModelDataList(User.class, eqRestrictions, "firstName", true);
		
		if(referees != null){
			Timestamp startDate = new Timestamp(date.getTime());
			Timestamp endDate = new Timestamp(dayAfter.getTime() - 6000);

			eqRestrictions.clear();
			Map<Integer,Object> btwDate = new HashMap<Integer, Object>();
			btwDate.put(1, startDate);
			btwDate.put(-1, endDate);
			eqRestrictions.put("startDate", new FieldCondition(btwDate));

			List<?> allRefereeAvailability = service.GetModelDataList(RefereeAvailability.class, eqRestrictions, "userName", true);

			Calendar calendar = Calendar.getInstance();
			
			allRefereeAvailability.forEach(ra -> {
				
				calendar.setTime(((RefereeAvailability)ra).getStartDate());
				double marginLeftPer = ((double)calendar.get(Calendar.HOUR_OF_DAY) + (double)calendar.get(Calendar.MINUTE)/60.0)*100/24.0;
				double widthPer = (double)(((RefereeAvailability)ra).getEndDate().getTime() -
						((RefereeAvailability)ra).getStartDate().getTime())/(10 * 60 * 60 * 24);
				
				AvailabilityPlot avPlot = new AvailabilityPlot(marginLeftPer, widthPer);
				
				
				if(availableReferees.get(((RefereeAvailability)ra).getUser()) == null)
					availableReferees.put(((RefereeAvailability)ra).getUser(), new LinkedList<AvailabilityPlot>(Arrays.asList(avPlot)));
				else{ 
					List<AvailabilityPlot> userAvailabilityPlot = availableReferees.get(((RefereeAvailability)ra).getUser());
					userAvailabilityPlot.add(avPlot);
					availableReferees.put(((RefereeAvailability)ra).getUser(), userAvailabilityPlot);
				}
	
			});
			
			availableReferees.forEach((usr,plotList) ->
			{
				System.out.println(usr.getUserFullName() + plotList);
			});
			
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

	public Date getDayBefore() {
		return dayBefore;
	}

	public void setDayBefore(Date dayBefore) {
		this.dayBefore = dayBefore;
	}

	public Date getDayAfter() {
		return dayAfter;
	}

	public void setDayAfter(Date dayAfter) {
		this.dayAfter = dayAfter;
	}

	public Map<User, List<AvailabilityPlot>> getAvailableReferees() {
		return availableReferees;
	}

	public void setAvailableReferees(Map<User, List<AvailabilityPlot>> availableReferees) {
		this.availableReferees = availableReferees;
	}

	public void setService(GenericService service) {
		this.service = service;
	}

	
}

class AvailabilityPlot {
	
	private double marginLeftPer;
	private double widthPer;
	
	
	public AvailabilityPlot(double marginLeftPer, double widthPer) {
		super();
		this.marginLeftPer = marginLeftPer;
		this.widthPer = widthPer;
	}
	
	public double getMarginLeftPer() {
		return marginLeftPer;
	}
	public void setMarginLeftPer(double marginLeftPer) {
		this.marginLeftPer = marginLeftPer;
	}
	public double getWidthPer() {
		return widthPer;
	}
	public void setWidthPer(double widthPer) {
		this.widthPer = widthPer;
	}
}