package com.mycompany.mytwitter.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTwitter {

	/**
	 * Just Main standalone class
	 * that loads the spring context.
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
       
		MyConsole console = context.getBean(MyConsole.class);
		console.run();
		
	}

}
