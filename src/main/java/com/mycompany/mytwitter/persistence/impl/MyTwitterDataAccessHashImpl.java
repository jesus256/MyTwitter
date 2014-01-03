package com.mycompany.mytwitter.persistence.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.mytwitter.client.InputData;
import com.mycompany.mytwitter.persistence.MyTwitterDataAccess;
import com.mycompany.mytwitter.persistence.dao.MyTwitterHashStorage;
import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * Persistence/repository Implementation
 * @author Jesus
 *
 */
@Component
public class MyTwitterDataAccessHashImpl implements MyTwitterDataAccess {

	@Autowired
	private MyTwitterHashStorage storage;
	
	private List<MyTwitterMessage> messageList;
	private List<String> followList;
	
	
	@Override
	public void posting(InputData inputData) {
		
		messageList=storage.getMessageList().get(inputData.getUser());
		if (messageList==null)
		{
		 //First user message
		 messageList=new ArrayList<MyTwitterMessage>();
		}

		messageList.add(new MyTwitterMessage(inputData.getUser(),inputData.getMessage().getContent(), inputData.getMessage().getTimeStamp()));
		storage.getMessageList().put(inputData.getUser(), messageList);
		
	}

	@Override
	public List<MyTwitterMessage> reading(InputData inputData) {
		
		messageList=storage.getMessageList().get(inputData.getUser());
		if (messageList==null)
		{
			//The user cannot be found, so empty List returned
			return new ArrayList<MyTwitterMessage>();
		}
		
		Collections.sort(messageList);
				
		return messageList;
	}

	@Override
	public void following(InputData inputData) {
		
		followList=storage.getFollowList().get(inputData.getUser());
		if (followList==null)
		{
		 //First time user follows
		 followList=new ArrayList<String>();
		}

		followList.add(inputData.getUserFollowed());
		storage.getFollowList().put(inputData.getUser(), followList);
		
	}
	
	private List<MyTwitterMessage> cloneMessageList(List<MyTwitterMessage> content)
	{
		List<MyTwitterMessage> clonedList=new ArrayList<MyTwitterMessage>();
		
		for(MyTwitterMessage mes:content)
		{
			clonedList.add(mes);
		}
		  
		return clonedList;
	}

	@Override
	public List<MyTwitterMessage> walling(InputData inputData) {
		
		messageList=storage.getMessageList().get(inputData.getUser());
		
		if (messageList!=null)
		{
		 messageList=this.cloneMessageList(messageList);	
		}
		else
		{
		 messageList=new ArrayList<MyTwitterMessage>();
		}
	
		
		followList=storage.getFollowList().get(inputData.getUser());
		
		if (followList==null)
		{
			return reading(inputData);
		}
		List<MyTwitterMessage> messageTemp;
		for(String user:followList)
		{		
			messageTemp=storage.getMessageList().get(user);
			if (messageTemp!=null)
			{
				messageList.addAll(messageTemp);
			}
		}
		
		Collections.sort(messageList);
		
		return messageList;
	}

	public MyTwitterHashStorage getStorage() {
		return storage;
	}

	public void setStorage(MyTwitterHashStorage storage) {
		this.storage = storage;
	}
	
	

}
