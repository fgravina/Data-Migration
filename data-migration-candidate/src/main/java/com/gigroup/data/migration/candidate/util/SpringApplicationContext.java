package com.gigroup.data.migration.candidate.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public enum SpringApplicationContext {
	INSTANCE;
	
	private final ApplicationContext applicationContext;
	
	private SpringApplicationContext() {		
		applicationContext = new ClassPathXmlApplicationContext("dataAccessContext.xml");
	}
	
	public Object getBean(final String beanName){
		return this.applicationContext.getBean(beanName);
	}
}