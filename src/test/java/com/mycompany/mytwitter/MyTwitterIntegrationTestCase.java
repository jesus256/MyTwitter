package com.mycompany.mytwitter;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycompany.mytwitter.client.Command;
import com.mycompany.mytwitter.client.InputData;
import com.mycompany.mytwitter.client.MyConsole;
import com.mycompany.mytwitter.persistence.dao.MyTwitterHashStorage;
import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//We need to execute these tests in order, beware 1.6 JVM dependent
@FixMethodOrder(MethodSorters.JVM)
/**
 * This test case tests the provided Alice,Bob,Charlie example
 * @author Jesus
 *
 */
public class MyTwitterIntegrationTestCase {

	 
	@Autowired
	private MyConsole myConsole;
	
	@Autowired
	MyTwitterHashStorage storage;
	
	static Date now;
	static Date bobFisrtPost;
	static Date bobSecondPost;
	static Date aliceFisrtPost;
	static Date charlieFisrtPost;
	
	//We need to create an artificial timeline to meet example's expectations
	@BeforeClass
	public static void setup()
	{
		now=new Date();
		charlieFisrtPost=now;
		bobFisrtPost=new Date(now.getTime()-1000);
		bobSecondPost=new Date(now.getTime()-2000);
		aliceFisrtPost=new Date(now.getTime()-3000);
	}
	
	@Test
	public void testAliceHappyPosting()
	{
		InputData data= new InputData();
		data.setCommand(Command.POSTING);
		data.setUser("Alice");
		data.setMessage(new MyTwitterMessage("Alice","I love the weather today",aliceFisrtPost));
		myConsole.getBus().posting(data);
		//Checking if Alice's the message is stored
		List<MyTwitterMessage> list=storage.getMessageList().get("Alice");
		
		assertEquals("Alice",list.get(0).getUser());
		assertEquals("I love the weather today",list.get(0).getContent());
		assertEquals(aliceFisrtPost,list.get(0).getTimeStamp());
		 
	}
	
	@Test
	public void testBobHappyPosting()
	{
		InputData data= new InputData();
		data.setCommand(Command.POSTING);
		data.setUser("Bob");
		data.setMessage(new MyTwitterMessage("Bob","Oh, we lost!",bobFisrtPost));
		myConsole.getBus().posting(data);
		//Checking if Bob's first message is stored
		List<MyTwitterMessage> list=storage.getMessageList().get("Bob");
		
		assertEquals("Bob",list.get(0).getUser());
		assertEquals("Oh, we lost!",list.get(0).getContent());
		assertEquals(bobFisrtPost,list.get(0).getTimeStamp());
		 
	}
	
	@Test
	public void testBobHappyPosting2()
	{
		InputData data= new InputData();
		data.setCommand(Command.POSTING);
		data.setUser("Bob");
		data.setMessage(new MyTwitterMessage("Bob","at least it's sunny",bobSecondPost));
		myConsole.getBus().posting(data);
		//Checking if BOB's second message is stored
		List<MyTwitterMessage> list=storage.getMessageList().get("Bob");
		
		assertEquals("Bob",list.get(1).getUser());
		assertEquals("at least it's sunny",list.get(1).getContent());
		assertEquals(bobSecondPost,list.get(1).getTimeStamp());
		 
	}
	
	@Test
	public void testCharlieHappyPosting()
	{
		InputData data= new InputData();
		data.setCommand(Command.POSTING);
		data.setUser("Charlie");
		data.setMessage(new MyTwitterMessage("Charlie","I'm in New York today! Anyone wants to have a coffee?",charlieFisrtPost));
		myConsole.getBus().posting(data);
		//Checking if Bob's first message is stored
		List<MyTwitterMessage> list=storage.getMessageList().get("Charlie");
		
		assertEquals("Charlie",list.get(0).getUser());
		assertEquals("I'm in New York today! Anyone wants to have a coffee?",list.get(0).getContent());
		assertEquals(charlieFisrtPost,list.get(0).getTimeStamp());
		 
	}
	
	
	@Test
	public void testAliceHappyReading()
	{
		InputData data= new InputData();
		data.setCommand(Command.READING);
		data.setUser("Alice");
		 
		List<MyTwitterMessage> list=myConsole.getBus().reading(data);
		
		//Checking Alice's timeline
		assertEquals("Alice",list.get(0).getUser());
		assertEquals("I love the weather today",list.get(0).getContent());
		assertEquals(aliceFisrtPost,list.get(0).getTimeStamp());
		 
	}
	
	@Test
	public void testBobHappyReading()
	{
		InputData data= new InputData();
		data.setCommand(Command.READING);
		data.setUser("Bob");
		 
		List<MyTwitterMessage> list=myConsole.getBus().reading(data);
		
		//Checking Bob's timeline
		assertEquals("Bob",list.get(0).getUser());
		assertEquals("Oh, we lost!",list.get(0).getContent());
		assertEquals(bobFisrtPost,list.get(0).getTimeStamp());
		
		assertEquals("Bob",list.get(1).getUser());
		assertEquals("at least it's sunny",list.get(1).getContent());
		assertEquals(bobSecondPost,list.get(1).getTimeStamp());
		 
	}
	
	@Test
	public void testHappyCharlieFollowsAlice()
	{
		InputData data= new InputData();
		data.setCommand(Command.FOLLOWING);
		data.setUser("Charlie");
		data.setUserFollowed("Alice");
		 
		myConsole.getBus().following(data);
		
		List<String> list=storage.getFollowList().get("Charlie");
		
		//Checking Charlie's following list, Alice should be there
		assertEquals("Alice",list.get(0));
		 
		 
	}
	
	@Test
	public void testHappyCharlieFollowsBob()
	{
		InputData data= new InputData();
		data.setCommand(Command.FOLLOWING);
		data.setUser("Charlie");
		data.setUserFollowed("Bob");
		 
		myConsole.getBus().following(data);
		
		List<String> list=storage.getFollowList().get("Charlie");
		
		//Checking Charlie's following list, Bob should be there (second position)
		assertEquals("Bob",list.get(1)); 
		 
	}
	
	@Test
	public void testHappyCharlieWall()
	{
		InputData data= new InputData();
		data.setCommand(Command.WALL);
		data.setUser("Charlie");
		 
		List<MyTwitterMessage> list=myConsole.getBus().walling(data);
		
		//Checking Charlie's wall
		assertEquals("Charlie",list.get(0).getUser());
		assertEquals("I'm in New York today! Anyone wants to have a coffee?",list.get(0).getContent());
		assertEquals(charlieFisrtPost,list.get(0).getTimeStamp());
		
		assertEquals("Bob",list.get(1).getUser());
		assertEquals("Oh, we lost!",list.get(1).getContent());
		assertEquals(bobFisrtPost,list.get(1).getTimeStamp());
		
		assertEquals("Bob",list.get(2).getUser());
		assertEquals("at least it's sunny",list.get(2).getContent());
		assertEquals(bobSecondPost,list.get(2).getTimeStamp());
		
		assertEquals("Alice",list.get(3).getUser());
		assertEquals("I love the weather today",list.get(3).getContent());
		assertEquals(aliceFisrtPost,list.get(3).getTimeStamp());
		 
	}
	

	
	
}
