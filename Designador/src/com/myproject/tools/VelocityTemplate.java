package com.myproject.tools;

import java.io.StringWriter;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;



public class VelocityTemplate {

	private String template;
	
	public VelocityTemplate(String path, Map<String,String> templateData) {
		
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
        
        
        template = writer.toString();
        
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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}



}
