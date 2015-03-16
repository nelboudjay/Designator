package com.myproject.action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.myproject.mail.MailService;
import com.myproject.model.PasswordChangeRequest;
import com.myproject.model.User;
import com.myproject.service.GenericService;
import com.opensymphony.xwork2.ActionSupport;

public class PasswordForgot extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6102575198527216129L;
	
	private String resetPasswordLoginField;

	private GenericService service;

	private MailService mailService;

	@Override
	@SkipValidation
	public String execute() {
		
		return NONE;
	}
	
	public String resetPassword(){
		
		clearFieldErrors();

		Map<String, Object> eqRestrictions = new HashMap<String, Object>();
		eqRestrictions.put("userName", getResetPasswordLoginField());

		User user = (User) service.GetUniqueModelData(User.class, eqRestrictions);
		
		if (user == null) {
			eqRestrictions.remove("userName");
			eqRestrictions.put("email", getResetPasswordLoginField());
			user = (User) service
					.GetUniqueModelData(User.class, eqRestrictions);
		}
		
		if (user == null) {
			addActionError("El nombre de usuario o el E-mail introducido no se corresopnde a ningún usuario registrado.");
			return INPUT;
		} else {
			
			/*Generate a new token */
			String token;
			eqRestrictions.clear();

			PasswordChangeRequest passwordChangeRequest;

			do {
				token = UUID.randomUUID().toString()
						.replaceAll("-", "");
				eqRestrictions.put("token",
						token);
				passwordChangeRequest = (PasswordChangeRequest) service
						.GetUniqueModelData(PasswordChangeRequest.class,
								eqRestrictions);
			} while (passwordChangeRequest != null);
			
			/*Save the password request in the database and create a 24H event for it*/
			passwordChangeRequest = new PasswordChangeRequest(user, token);
			service.SaveOrUpdateModelData(passwordChangeRequest);

			service.DropEvent("DELETE_PASSWORD_CHANGE_REQUEST_"
					+ user.getIdUser());
			service.CreateEvent("DELETE_PASSWORD_CHANGE_REQUEST_"
					+ user.getIdUser(), "PASSWORD_CHANGE_REQUEST",
					"idPASSWORD_CHANGE_REQUEST", ""+user.getIdUser(), "DAY");
			
			/*add a value to the template email*/
			Map<String, String> templateData = new HashMap<String, String>();
			templateData
					.put("token", token);
			
			/*Send the email*/
			mailService.sendMail(user.getEmail(),
					"Instrucciones para restablecer tu contraseña",
					"passwordResetInstructions.vm", templateData);
			addActionMessage("Se te ha enviado un E-mail con las instrucciones para restablecer tu contraseña");
			
			return SUCCESS;
		}
		
	}

	public String getResetPasswordLoginField() {
		return resetPasswordLoginField;
	}
	
	public void setResetPasswordLoginField(String resetPasswordLoginField) {
		this.resetPasswordLoginField = resetPasswordLoginField;
	}
	
	public void setService(GenericService service) {
		this.service = service;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

}
