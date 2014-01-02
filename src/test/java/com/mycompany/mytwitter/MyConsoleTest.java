package com.mycompany.mytwitter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.mycompany.mytwitter.business.MyTwittterBus;
import com.mycompany.mytwitter.client.Command;
import com.mycompany.mytwitter.client.InputData;
import com.mycompany.mytwitter.client.MyConsole;
import com.mycompany.mytwitter.client.OutputEngine;
import com.mycompany.mytwitter.client.OutputEngineConsoleImpl;

/**
 * MyConsole Unitary test
 * @author Jesus
 *
 */
public class MyConsoleTest {

	
	private MyTwittterBus bus=mock(MyTwittterBus.class);
	private OutputEngine out=mock(OutputEngineConsoleImpl.class);
	
	@Test(expected = NullPointerException.class)  
	public void testExecuteCommandNullParameter()
	{
		MyConsole myconsole=new MyConsole();
		myconsole.executeCommand(null);
	}
	
	@Test
	public void testExecuteCommandPOSTING()
	{
		MyConsole myconsole=new MyConsole();
		InputData inputData=new InputData();
		inputData.setCommand(Command.POSTING);
		myconsole.setBus(bus);
		
		myconsole.executeCommand(inputData);
		//Is posting executed?
		verify(bus).posting(inputData);
	}
	
	@Test
	public void testExecuteCommandREADING()
	{
		MyConsole myconsole=new MyConsole();
		InputData inputData=new InputData();
		inputData.setCommand(Command.READING);
		myconsole.setBus(bus);
		myconsole.setOut(out);
		
		myconsole.executeCommand(inputData);
		//Is reading executed?
		verify(bus).reading(inputData);
	}
	
	@Test
	public void testExecuteCommandFOLLOWING()
	{
		MyConsole myconsole=new MyConsole();
		InputData inputData=new InputData();
		inputData.setCommand(Command.FOLLOWING);
		myconsole.setBus(bus);
		
		myconsole.executeCommand(inputData);
		//Is posting executed?
		verify(bus).following(inputData);
	}
	
	@Test
	public void testExecuteCommandWALLING()
	{
		MyConsole myconsole=new MyConsole();
		InputData inputData=new InputData();
		inputData.setCommand(Command.WALL);
		myconsole.setBus(bus);
		myconsole.setOut(out);
		
		myconsole.executeCommand(inputData);
		//Is posting executed?
		verify(bus).walling(inputData);
	}
	
	@Test(expected = NullPointerException.class) 
	public void testGetCommandWithDataNullParameter()
	{
		MyConsole myconsole=new MyConsole();
		myconsole.getCommandWithData(null);
		 
	}
	
	@Test
	public void testGetCommandWithDataPOSTING()
	{
		MyConsole myconsole=new MyConsole();
		InputData inputData=myconsole.getCommandWithData("userName -> hello");
		
		assertEquals("userName", inputData.getUser());
		assertEquals("userName", inputData.getMessage().getUser());
		assertEquals("hello", inputData.getMessage().getContent());
		assertNotNull(inputData.getMessage().getTimeStamp());
		assertEquals(Command.POSTING, inputData.getCommand());
		 
	}
	
	@Test
	public void testGetCommandWithDataREADING()
	{
		MyConsole myconsole=new MyConsole();
		InputData inputData=myconsole.getCommandWithData("userName");
		
		assertEquals("userName", inputData.getUser());
		assertEquals(Command.READING, inputData.getCommand());
		 
	}
	
	@Test
	public void testGetCommandWithDataWALLING()
	{
		MyConsole myconsole=new MyConsole();
		InputData inputData=myconsole.getCommandWithData("userName wall");
		
		assertEquals("userName", inputData.getUser());
		assertEquals(Command.WALL, inputData.getCommand());
		 
	}
	
	@Test
	public void testGetCommandWithDataFOLLOWING()
	{
		MyConsole myconsole=new MyConsole();
		InputData inputData=myconsole.getCommandWithData("userName follows userName2");
		
		assertEquals("userName", inputData.getUser());
		assertEquals("userName2", inputData.getUserFollowed());
		assertEquals(Command.FOLLOWING, inputData.getCommand());
		 
	}
	
}
