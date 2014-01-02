package com.mycompany.mytwitter.persistence.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Core storage hash structures
 * for managing messages and following list 
 * @author Jesus
 *
 */
@Component
public class MyTwitterHashStorage {
	
	//No need to override equals and hashCode, they key is of type String (default equals/hashCode used)
	private Map<String,List<String>> followList=new HashMap<String,List<String>>();
	private Map<String,List<MyTwitterMessage>> messageList=new HashMap<String,List<MyTwitterMessage>>();
	
	public Map<String, List<String>> getFollowList() {
		return followList;
	}
	public void setFollowList(Map<String, List<String>> followList) {
		this.followList = followList;
	}
	public Map<String, List<MyTwitterMessage>> getMessageList() {
		return messageList;
	}
	public void setMessageList(Map<String, List<MyTwitterMessage>> messageList) {
		this.messageList = messageList;
	}
	
	
	
	
	
	

}
