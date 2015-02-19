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

	Map<String, Object> session;

	private GenericService service;

	private User user;
	private UserCookie userCookie;
    private List<?> comments;
    
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

		Map<String, Object> eqRestrictions = new HashMap<String, Object>();

		if (user != null){
			return properResult(actionInvocation);
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
							String userFullName = user.getUserFullName();
							session.put("userFullName", userFullName);
							
							eqRestrictions.clear();
			    			comments = service.GetModelDataList(Comment.class, eqRestrictions);
			    			session.put("comments",comments);
			    			
			    			return properResult(actionInvocation);
			    			
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

	String properResult(ActionInvocation actionInvocation) throws Exception{
		
		List<String> actionsLoginNames = Arrays.asList("login", "homePage");

		List<String> actionsAdminNames = Arrays.asList("addComment", "deleteComment");
		

		if (actionsLoginNames.contains( actionInvocation.getInvocationContext().getName()) )
			return ActionSupport.SUCCESS;
		else if (actionsAdminNames.contains( actionInvocation.getInvocationContext().getName()) 
				&&  (!user.isAdmin()))
		{
			addActionError(actionInvocation ,"No tienes permiso para ver esta página");
			return ActionSupport.ERROR;
		}
		else
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
	
	public List<?> getComments() {
		return comments;
	}

	public void setComments(List<?> comments) {
		this.comments = comments;
	}
}
