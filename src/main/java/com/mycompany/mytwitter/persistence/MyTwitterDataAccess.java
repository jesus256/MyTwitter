package com.mycompany.mytwitter.persistence;

import java.util.List;

import com.mycompany.mytwitter.client.InputData;
import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * Persistance/repository layer interface
 * @author Jesus
 *
 */
public interface MyTwitterDataAccess {

	void posting(InputData inputData);
	List<MyTwitterMessage> reading(InputData inputData);
	void following(InputData inputData);
	List<MyTwitterMessage> walling(InputData inputData);
	
	
}
