package com.myproject.action;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.myproject.model.Comment;
import com.myproject.model.User;
import com.myproject.model.UserCookie;
import com.myproject.user.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserLoginAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 9149826260758390091L;

	private Map<String, Object> session;
	private HttpServletRequest request;
	private HttpServletResponse response;

	private String loginField;
	private String password;
	private boolean rememberMe;
	private User user;
	private UserCookie userCookie;
	private List<?> comments;

	private UserService userService;

	@Override
	public String execute() {

		clearFieldErrors();
		
		user = ((User) session.get("user"));
		
		if(user != null)
			return SUCCESS;
		else{

			DesEncrypter encrypter = new DesEncrypter(getText("loginPass"));
	
			Map<String, Object> eqRestrictions = new HashMap<String, Object>();
			eqRestrictions.put("userName", getLoginField());
			eqRestrictions.put("password", encrypter.encrypt(getPassword()));
	
			user = (User) userService
					.GetUniqueModelData(User.class, eqRestrictions);
			if (user == null) {
				eqRestrictions.remove("userName");
				eqRestrictions.put("email", getLoginField());
				user = (User) userService.GetUniqueModelData(User.class,
						eqRestrictions);
			}
	
			if (user != null) {
				session.put("user", user);
				String userFullName = user.getUserFullName();
				session.put("userFullName", userFullName);
				if (isRememberMe()) {
	
					String cookieValue = user.getUserName()
							+ request.getSession().getId();
					try {
						encrypter = new DesEncrypter(getText("cookiePass"));
						String encryptedCookieValue = URLEncoder.encode(
								encrypter.encrypt(cookieValue), "UTF-8");
						Cookie cookie = new Cookie("MYJSESSIONID",
								encryptedCookieValue);
						cookie.setMaxAge(60 * 60 * 24 * 7);
						response.addCookie(cookie);
	
						eqRestrictions.clear();
						eqRestrictions.put("idUserCookie", encryptedCookieValue);
	
						userCookie = (UserCookie) userService.GetUniqueModelData(
								UserCookie.class, eqRestrictions);
	
						if (userCookie == null) {
							userCookie = new UserCookie(encryptedCookieValue, user);
							userService.SaveOrUpdateModelData(userCookie);
							userService.CreateEvent("DELETE_USER_COOKIE_"
									+ cookieValue, "USER_COOKIE", "idUSER_COOKIE",
									encryptedCookieValue, "WEEK");
						}
					} catch (Exception e) {
					}
				}
	
				eqRestrictions.clear();
				comments = userService.GetModelDataList(Comment.class,
						eqRestrictions);			
				session.put("comments", comments);
				
				return SUCCESS;
			} else {
				addActionError("El nombre de usuario o la contraseña introducidos no son correctos.");
				return INPUT;
			}
		}
	}

	public String getLoginField() {
		return loginField;
	}

	public void setLoginField(String loginField) {
		this.loginField = loginField;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public HttpServletResponse getServletResponse() {
		return response;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<?> getComments() {
		return comments;
	}

	public void setComments(List<?> comments) {
		this.comments = comments;
	}



}
