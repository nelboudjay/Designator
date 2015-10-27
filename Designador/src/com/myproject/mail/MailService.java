package com.myproject.mail;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendMail(String[] to, String subject, String path, Map<String,String> templateData) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					true, "UTF-8");
			
			/*  first, get and initialize an engine  */
	        VelocityEngine ve = new VelocityEngine();
	        ve.setProperty("resource.loader", "class");
	        ve.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
	        ve.init();
	        
	        /*  next, get the Template  */	
	        Template t = ve.getTemplate("templates/" + path,"utf-8");
			
	        /*  create a context and add data */
	        VelocityContext context = new VelocityContext();

	        setTemplateData(context,templateData);
	        
	        /* now render the template into a StringWriter */
	        StringWriter writer = new StringWriter();

	        t.merge( context, writer );
	        
		//	mimeMessage.setContent(writer.toString(), "text/html");
			helper.setText(writer.toString(),true);
			helper.addInline("logoImage", new ClassPathResource("/Logo.png"));
	

			helper.setTo(to);
			helper.setSubject(subject);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		mailSender.send(mimeMessage);
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return encoding.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public void setTemplateData(VelocityContext context,
			Map<String, String> templateData) {

		for (Entry<String, String> entry : templateData.entrySet()) {
			// get key
			String key = entry.getKey();
			// get value
			String value = entry.getValue();
			
			context.put(key, value);
		}

	}

}
