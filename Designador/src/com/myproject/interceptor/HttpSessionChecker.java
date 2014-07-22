package com.myproject.interceptor;

import java.util.Date;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;



public class HttpSessionChecker  implements HttpSessionAttributeListener {

	
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		
		String attributeName = event.getName();
		Object attributeValue = event.getValue();
		System.out.println("Attribute added : " + attributeName + 
				" : " + attributeValue + " at " + new Date());
	
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		String attributeName = event.getName();
		Object attributeValue = event.getValue();
		System.out.println("Attribute removed : " + attributeName + 
				" : " + attributeValue + " at " + new Date());
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		String attributeName = event.getName();
		Object attributeValue = event.getValue();
		System.out.println("Attribute replaced : " + attributeName + 
				" : " + attributeValue + " at " + new Date());	
	}	

}
