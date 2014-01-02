package com.mycompany.mytwitter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.mycompany.mytwitter.client.OutputEngineConsoleImpl;
import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * This class tests our OutPut implementation class. 
 * @author Jesus
 *
 */
public class OutputEngineConsoleImplTest {

	
	
	@Test(expected = NullPointerException.class)  
	public void testWriteNullParameter()
	{
		
		OutputEngineConsoleImpl out= new OutputEngineConsoleImpl();
		out.write(null);
		
	}
	
	@Test
	public void testWriteHappy()
	{
		List<MyTwitterMessage> messageList=new ArrayList<MyTwitterMessage>();
		messageList.add(new MyTwitterMessage("Bob", "Hello world", new Date()));
		
		OutputEngineConsoleImpl out= new OutputEngineConsoleImpl();
		out.write(messageList);
		StringBuffer strb=(StringBuffer)ReflectionTestUtils.getField(out, "strb");
		
		assertEquals("Bob - Hello world (0 second/s)",strb.toString());
		
	}
	
}
