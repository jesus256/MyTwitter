package com.mycompany.mytwitter.business;

import java.util.List;

import com.mycompany.mytwitter.client.InputData;
import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * Business Interface
 * @author Jesus
 *
 */
public interface MyTwittterBus {

	
	void posting(InputData inputData);
	List<MyTwitterMessage> reading(InputData inputData);
	void following(InputData inputData);
	List<MyTwitterMessage> walling(InputData inputData);
	
	
}
