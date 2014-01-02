package com.mycompany.mytwitter.client;

import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * Structure that holds all data needed for the business + patterns for identifying
 * the business call
 * @author Jesus
 *
 */
public class InputData {

	public static final String POST_PATTERN=" -> ";
	public static final String FOLLOWS_PATTERN=" follows ";
	public static final String WALL_PATTERN=" wall";
	
	private String user;
	private String userFollowed;
	private MyTwitterMessage message;
	private Command command;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getUserFollowed() {
		return userFollowed;
	}
	public void setUserFollowed(String userFollowed) {
		this.userFollowed = userFollowed;
	}
	
	public MyTwitterMessage getMessage() {
		return message;
	}
	public void setMessage(MyTwitterMessage message) {
		this.message = message;
	}
	public Command getCommand() {
		return command;
	}
	public void setCommand(Command command) {
		this.command = command;
	}
	
	
	
}
