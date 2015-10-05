package com.myproject.interceptor;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.myproject.model.Comment;
import com.myproject.model.User;
import com.myproject.model.UserCookie;
import com.myproject.service.GenericService;
import com.myproject.tools.DesEncrypter;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor implements Interceptor {

	private static final long serialVersionUID = 5686161301032864561L;

	private GenericService service;

	private User user;
	private UserCookie userCookie;
    
	private Map<String, Object> eqRestrictions = new HashMap<String, Object>();


	@Override
	public void destroy() {
	}

	@Override
	public void init() {
		
	}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		Map<String, Object> session = actionInvocation.getInvocationContext()
				.getSession();
		HttpServletRequest request = (HttpServletRequest) actionInvocation
				.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		
		user = (User) session.get("user");

		if (user != null){
			return properResult(actionInvocation, session);
		}else {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("MYJSESSIONID")) {
						String encryptedCookieValue = cookie.getValue();

						eqRestrictions
								.put("idUserCookie", encryptedCookieValue);

						userCookie = (UserCookie) service
								.GetUniqueModelData(UserCookie.class,
										eqRestrictions);

						if (userCookie != null) {
							user = userCookie.getUser();
							DesEncrypter decrypter = new DesEncrypter(
									((ActionSupport) actionInvocation
											.getAction()).getText("cookiePass"));
							String cookieValue = decrypter.decrypt(URLDecoder
									.decode(encryptedCookieValue, "UTF-8"));
							service.AlterEvent("DELETE_USER_COOKIE_"
									+ cookieValue, "WEEK");
							session.put("user", user);
							
			    			return properResult(actionInvocation, session);
			    			
						}
					}
				}
			}
			
			/*Return LOGIN except for 'login', 'passwordForgot' and 'changePassword' actions*/
			List<String> excludedActionsNames = Arrays.asList("login", "passwordForgot", "changePassword");
			
			if(excludedActionsNames.contains(actionInvocation.getInvocationContext().getName()))
				return actionInvocation.invoke();

			else{
				addActionError(actionInvocation ,"Por favor, inicia sesión para continuar");
				return ActionSupport.LOGIN;
			}
		}
		
	}

	String properResult(ActionInvocation actionInvocation, Map<String, Object> session) throws Exception{
		
		List<String> actionsLoginNames = Arrays.asList("login", "homePage");
		List<String> actionsAdminNames = Arrays.asList("addComment", "deleteComment","addMember","members");
		
		if (actionsLoginNames.contains( actionInvocation.getInvocationContext().getName()) ){

			if (actionInvocation.getInvocationContext().getName().equals("homePage")  ){
				eqRestrictions.clear();
				
				List<?> comments = service.GetModelDataList(Comment.class, eqRestrictions, "commentDate", true);
				
				session.put("comments",comments);
			}
			return ActionSupport.SUCCESS;
		}else if (actionsAdminNames.contains( actionInvocation.getInvocationContext().getName()) 
				&&  (!user.isAdmin()))
		{
			addActionError(actionInvocation ,"No tienes permiso para ver esta página");
			return ActionSupport.ERROR;
		}
			return actionInvocation.invoke();
		
	}
	void addActionError(ActionInvocation actionInvocation, String message) {
		Object action = actionInvocation.getAction();
		if(action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}

	public User getUser() {
		return user;
	}
	public GenericService getService() {
		return service;
	}

	public void setService(GenericService service) {
		this.service = service;
	}
	
}
