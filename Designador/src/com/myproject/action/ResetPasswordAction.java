package com.myproject.action;

import java.io.IOException;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.interceptor.SessionAware;

import com.myproject.mail.MailService;
import com.myproject.model.PasswordChangeRequest;
import com.myproject.model.User;
import com.myproject.user.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordAction extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 818537998167655005L;

	private Map<String, Object> session;

	private User user;
	private PasswordChangeRequest passwordChangeRequest;
	private String resetPasswordLoginField;

	private UserService userService;

	private MailService mailService;

	@Override
	public String execute() throws IOException, URISyntaxException {

		clearFieldErrors();

		user = (User) session.get("user");

		if (user != null)
			return SUCCESS;
		else {

			Map<String, Object> eqRestrictions = new HashMap<String, Object>();
			eqRestrictions.put("userName", getResetPasswordLoginField());

			user = (User) userService.GetUniqueModelData(User.class,
					eqRestrictions);

			if (user == null) {
				eqRestrictions.remove("userName");
				eqRestrictions.put("email", getResetPasswordLoginField());
				user = (User) userService.GetUniqueModelData(User.class,
						eqRestrictions);
			}

			if (user == null) {
				addActionError("El nombre de usuario o el E-mail introducido no se corresopnde a ningún usuario registrado.");
				return INPUT;
			} else {


				eqRestrictions.clear();
				eqRestrictions.put("user", user);
				passwordChangeRequest = (PasswordChangeRequest) userService
						.GetUniqueModelData(PasswordChangeRequest.class,
								eqRestrictions);
				
				if (passwordChangeRequest != null) {
					userService.DeleteModelData(passwordChangeRequest);
					userService.DropEvent("DELETE_PASSWORD_CHANGE_REQUEST_"
							+ passwordChangeRequest.getIdPasswordChangeRequest());
				}

				String idPasswordChangeRequest;
				eqRestrictions.clear();

				do {
					idPasswordChangeRequest = UUID.randomUUID().toString()
							.replaceAll("-", "");
					eqRestrictions.put("idPasswordChangeRequest",
							idPasswordChangeRequest);
					passwordChangeRequest = (PasswordChangeRequest) userService
							.GetUniqueModelData(PasswordChangeRequest.class,
									eqRestrictions);
				} while (passwordChangeRequest != null);

				passwordChangeRequest = new PasswordChangeRequest(
						idPasswordChangeRequest, user);
				userService.SaveOrUpdateModelData(passwordChangeRequest);
				userService.CreateEvent("DELETE_PASSWORD_CHANGE_REQUEST_"
						+ idPasswordChangeRequest, "PASSWORD_CHANGE_REQUEST",
						"idPASSWORD_CHANGE_REQUEST", idPasswordChangeRequest,
						"DAY");

				Map<String, String> templateData = new HashMap<String, String>();
				templateData.put("idPasswordChangeRequest",
						idPasswordChangeRequest);

				mailService.sendMail(user.getEmail(),
						"Instrucciones para restablecer tu contraseña",
						"passwordResetInstructions.vm", templateData);
				addActionMessage("Se te ha enviado un E-mail con las instrucciones para restablecer tu contraseña");
				return SUCCESS;
			}
		}
	}

	public String getResetPasswordLoginField() {
		return resetPasswordLoginField;
	}

	public void setResetPasswordLoginField(String resetPasswordLoginField) {
		this.resetPasswordLoginField = resetPasswordLoginField;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
