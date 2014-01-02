package com.mycompany.mytwitter.client;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * Console OutputEngine Implementation
 * @author Jesus
 *
 */
@Component
public class OutputEngineConsoleImpl implements OutputEngine {

	private StringBuffer strb;
	
	@Override
	public void write(List<MyTwitterMessage> data) {
		
		for(MyTwitterMessage message:data)
		{
			strb=new StringBuffer("");
			long seconds=(new Date().getTime() - message.getTimeStamp().getTime())/1000;
			String timeString=" second/s";
			if (seconds>59)
			{
				seconds = seconds/60;
				timeString=" minute/s";
			}
			//We are inside a loop concatenating values, better to use a StringBuffer
			strb.append(message.getUser());
			strb.append(" - ");
			strb.append(message.getContent());
			strb.append(" (");
			strb.append(seconds);
			strb.append(timeString);
			strb.append(")");
			System.out.println(strb.toString());
		}
		
	}
	
	

}
