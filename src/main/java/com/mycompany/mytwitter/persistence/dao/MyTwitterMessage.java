package com.mycompany.mytwitter.persistence.dao;

import java.util.Date;

/**
 * Application Message structure
 * we store the user, the message and the timestamp
 * @author Jesus
 *
 */
public class MyTwitterMessage implements Comparable<MyTwitterMessage> {

	private String user;
	private String content;
	private Date timeStamp;
	
	public MyTwitterMessage(String user, String content, Date timeStamp) {
		super();
		this.user = user;
		this.content = content;
		this.timeStamp = timeStamp;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public int compareTo(MyTwitterMessage other) {

		
		if (this.getTimeStamp().getTime()>other.getTimeStamp().getTime())
		{
			return -1;	
		}
		
		if (this.getTimeStamp().getTime()<other.getTimeStamp().getTime())
		{
			return 1;	
		}
		
		return 0;
		
	}
	
    
	
	
	
}
