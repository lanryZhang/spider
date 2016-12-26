package com.ifeng.pgc.core;

import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanLoader {
	private static GenericXmlApplicationContext context;
	
	public static GenericXmlApplicationContext getContext() {
		if (context == null){
			initContext();
		}
		return context;
	}

	private static void closeContext(){
		if (context == null){
			return;
		}
		context.close();
	}

	public static void refresh(){
		if (context == null){
			return;
		}
		closeContext();
		initContext();
	}
	private static void initContext(){
		context = new GenericXmlApplicationContext();
        context.setValidating(false);
        context.load("classpath*:spring*.xml");
        context.refresh();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> classz) {
		return (T) getContext().getBean(classz);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) getContext().getBean(name);
	}
}
