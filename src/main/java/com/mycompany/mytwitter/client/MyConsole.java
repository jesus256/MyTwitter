package com.mycompany.mytwitter.client;

import java.io.Console;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.mytwitter.business.MyTwittterBus;
import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;

/**
 * This class behaves as the client console.
 * commands from the user are parsed and delegated to the business
 * @author Jesus
 *
 */
@Component
public class MyConsole {

	public static final String CONSOLE_PROMPT="> ";
	@Autowired
	private MyTwittterBus bus;
	@Autowired
	private OutputEngine out;
	
	/**
	 * Infinite console loop.
	 */
	public void run()
	{
		Console console=System.console();
		String line;
		while(true)
		{
			System.out.print(CONSOLE_PROMPT);
			line=console.readLine();
			System.out.println();
			executeCommand(getCommandWithData(line));
			
		}
	}
	
	/**
	 * business delegate
	 * @param inputData user input data from the command line
	 */
	public void executeCommand(InputData inputData)
	{
		switch(inputData.getCommand())
		{
			case POSTING:
							bus.posting(inputData);
							break;
			case FOLLOWING:
							bus.following(inputData);
							break;
			case READING:							
							out.write(bus.reading(inputData));
							break;
			case WALL:
							out.write(bus.walling(inputData));
							break;
		}
	}
	
	/**
	 * Parses command line
	 * @param line user command line
	 * @return
	 */
	public InputData getCommandWithData(String line)
	{
		InputData inputData=new InputData();
		
		inputData.setUser(line);
		inputData.setCommand(Command.READING);
		
		if (line.contains(InputData.POST_PATTERN))
		{		
			inputData.setUser(line.split(InputData.POST_PATTERN)[0]);
			inputData.setMessage(new MyTwitterMessage(line.split(InputData.POST_PATTERN)[0],line.split(InputData.POST_PATTERN)[1],new Date()));
			inputData.setCommand(Command.POSTING);
		}
		
		if (line.contains(InputData.FOLLOWS_PATTERN))
		{		
			inputData.setUser(line.split(InputData.FOLLOWS_PATTERN)[0]);
			inputData.setUserFollowed(line.split(InputData.FOLLOWS_PATTERN)[1]);
			inputData.setCommand(Command.FOLLOWING);
		}
		
		if (line.contains(InputData.WALL_PATTERN))
		{		
			inputData.setUser(line.split(InputData.WALL_PATTERN)[0]);
			inputData.setCommand(Command.WALL);
		}
		
		return inputData;
	}

	public MyTwittterBus getBus() {
		return bus;
	}

	public void setBus(MyTwittterBus bus) {
		this.bus = bus;
	}

	public OutputEngine getOut() {
		return out;
	}

	public void setOut(OutputEngine out) {
		this.out = out;
	}
	
	
	
	

}
