package com.mycompany.mytwitter.client;

import java.util.List;

import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * Convenient Output interface
 * for delegating the output implementation
 * Perhaps in the future we want to output to a REST webservice, raw socket etc... 
 * @author Jesus
 *
 */
public interface OutputEngine {

	void write(List<MyTwitterMessage> data);
	
}
