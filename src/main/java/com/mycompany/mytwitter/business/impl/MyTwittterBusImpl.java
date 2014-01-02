package com.mycompany.mytwitter.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.mytwitter.business.MyTwittterBus;
import com.mycompany.mytwitter.client.InputData;
import com.mycompany.mytwitter.persistence.MyTwitterDataAccess;
import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * Business interface.
 * We don't have hard/strict business rules.
 * From my point of view there are only access rules that select or check data so I decided to put everything
 * int the persistence layer
 * Anyways, I think it is important to declare the business interface for not to couple the client
 * with the business implementation
 * @author Jesus
 *
 */
@Component
public class MyTwittterBusImpl implements MyTwittterBus{

	@Autowired
	private MyTwitterDataAccess data;
	
	@Override
	public void posting(InputData inputData) {
		
		data.posting(inputData);
		
	}

	@Override
	public List<MyTwitterMessage> reading(InputData inputData) {
		
		return data.reading(inputData);
	}

	@Override
	public void following(InputData inputData) {
		 
		data.following(inputData);
		
	}

	@Override
	public List<MyTwitterMessage> walling(InputData inputData) {
		 
		return data.walling(inputData);
	}
	

	public MyTwitterDataAccess getData() {
		return data;
	}

	public void setData(MyTwitterDataAccess data) {
		this.data = data;
	}
	
	

	
	
}
