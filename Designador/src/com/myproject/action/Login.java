package com.myproject.action;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.myproject.model.User;
import com.myproject.model.UserCookie;
import com.myproject.service.GenericService;
import com.myproject.tools.DesEncrypter;
import com.myproject.tools.FieldCondition;
import com.opensymphony.xwork2.ActionSupport;

public class Login extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 9149826260758390091L;

	private Map<String, Object> session;
	private HttpServletRequest request;
	private HttpServletResponse response;

	private String loginField;
	private String password;
	private boolean rememberMe;

	private GenericService service;

	@Override
	@SkipValidation
	public String execute() {
		return LOGIN;
	}

	public String login() {
		DesEncrypter encrypter = new DesEncrypter(getText("loginPass"));

		Map<String, FieldCondition> eqRestrictions = new HashMap<String, FieldCondition>();
		eqRestrictions.put("userName", new FieldCondition(loginField.trim()));
		eqRestrictions.put("password", new FieldCondition(encrypter.encrypt(getPassword())));

		User user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
		if (user == null) {
			eqRestrictions.remove("userName");
			eqRestrictions.put("email", new FieldCondition(loginField.trim()));
			user = (User) service
					.GetUniqueModelData(User.class, eqRestrictions);
		}

		if (user != null) {
			session.put("user", user);
			if (rememberMe) {

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
					eqRestrictions.put("idUserCookie", new FieldCondition(encryptedCookieValue));

					UserCookie userCookie = (UserCookie) service
							.GetUniqueModelData(UserCookie.class,
									eqRestrictions);

					if (userCookie == null) {
						userCookie = new UserCookie(encryptedCookieValue, user);
						service.SaveOrUpdateModelData(userCookie);
						service.CreateEvent(
								"DELETE_USER_COOKIE_" + cookieValue,"USER_COOKIE",
								"USER_COOKIE", "idUSER_COOKIE",
								encryptedCookieValue, "WEEK");
					}
				} catch (Exception e) {
				}
			}

			return SUCCESS;
		} else {
			addActionError("El nombre de usuario o la contraseña introducidos no son correctos.");
			return INPUT;
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

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	public void setService(GenericService service) {
		this.service = service;
	}
}
