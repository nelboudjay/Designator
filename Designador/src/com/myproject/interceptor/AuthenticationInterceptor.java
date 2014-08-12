package com.myproject.interceptor;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;

import com.myproject.action.DesEncrypter;
import com.myproject.model.Comment;
import com.myproject.model.User;
import com.myproject.model.UserCookie;
import com.myproject.service.GenericService;
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

		if (user == null) {
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
							return ActionSupport.SUCCESS;
						}
					}
				}
			}
			
			if(!actionInvocation.getInvocationContext().getName().equals("login"))
				addActionError(actionInvocation ,"Por favor, inicia sesión para continuar");
			return ActionSupport.LOGIN;
		}
		else{
			
			eqRestrictions.clear();
			comments = service.GetModelDataList(Comment.class, eqRestrictions);
			session.put("comments",comments);
			return ActionSupport.SUCCESS;
		}
	}

	void addActionError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if(action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}
	
	/*public void AddSessionVariables(Map<String, Object> session) {

		switch (getActionName()) {
		case "login":
			session.put("user", user);
			String userFullName = user.getUserFullName();
			session.put("userFullName", userFullName);
			//eqRestrictions.clear();
			//comments = service.GetModelDataList(Comment.class, eqRestrictions);
			//session.put("comments",comments);
			break;
		case "homePage":
			break;
		default:
			break;
		}

	}*/

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
