package com.myproject.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.myproject.model.User;
import com.myproject.model.UserCookie;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class UserLogoutAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware{

	private static final long serialVersionUID = 4462259177202729230L;
	
	private Map<String, Object> session;
    private HttpServletRequest request;
    private HttpServletResponse response;

	private GenericService service;
	
	@Override
	public String execute() throws UnsupportedEncodingException{
		
		User user = ((User)session.get("user"));
		
		if(user != null){	
			addActionMessage("Gracias " + user.getUserProfile().getUserFirstName() + ", se ha cerrado tu sesión correctamente");
			session.clear();
		}
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("MYJSESSIONID")){
					String encryptedCookieValue = cookie.getValue();
					
					Map<String, Object> eqRestrictions = new HashMap<String, Object>();
					eqRestrictions.put("idUserCookie", encryptedCookieValue);
					
					UserCookie userCookie = (UserCookie)service.GetUniqueModelData(UserCookie.class, eqRestrictions);          	
					
					service.DeleteModelData(userCookie);
					
                    DesEncrypter decrypter = new DesEncrypter(getText("cookiePass"));
                    String cookieValue = decrypter.decrypt(URLDecoder.decode(encryptedCookieValue,"UTF-8"));
					
                    service.DropEvent("DELETE_USER_COOKIE_" + cookieValue);
					cookie.setValue(null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}	
		
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;		
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
	
	public void setService(GenericService service) {
		this.service = service;
	}
	
}


