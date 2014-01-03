package com.mycompany.mytwitter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
//import static org.mockito.Matchers.;

import com.mycompany.mytwitter.client.Command;
import com.mycompany.mytwitter.client.InputData;
import com.mycompany.mytwitter.persistence.dao.MyTwitterHashStorage;
import com.mycompany.mytwitter.persistence.dao.MyTwitterMessage;
import com.mycompany.mytwitter.persistence.impl.MyTwitterDataAccessHashImpl;

/**
 * This class tests our persistence layer implementation
 * @author Jesus
 *
 */
@SuppressWarnings( "unchecked" )
public class MyTwitterDataAccessHashImplTest {

	
	MyTwitterDataAccessHashImpl dataAccess;
	MyTwitterHashStorage storage=mock(MyTwitterHashStorage.class);
	List<MyTwitterMessage> messageListMocked=mock(ArrayList.class);
	
	@Before
	public void setup()
	{
		dataAccess=new MyTwitterDataAccessHashImpl();
	}
	
	@Test
	public void testPOSTING()
	{
		Date now=new Date();
		InputData data= new InputData();
		data.setCommand(Command.POSTING);
		data.setUser("Charlie");
		data.setMessage(new MyTwitterMessage("Charlie","I'm in New York today! Anyone wants to have a coffee?",now));
		
		dataAccess.setStorage(storage);
		
		dataAccess.posting(data);
		
		List<MyTwitterMessage> messageList=(List<MyTwitterMessage>)ReflectionTestUtils.getField(dataAccess, "messageList");
		
		
		assertEquals("Charlie", messageList.get(0).getUser());
		assertEquals("I'm in New York today! Anyone wants to have a coffee?", messageList.get(0).getContent());
		assertEquals(now,messageList.get(0).getTimeStamp());
		 
	}
	
	@Test
	public void testREADINGWithoutPosting()
	{
		InputData data= new InputData();
		data.setCommand(Command.READING);
		data.setUser("Charlie");
		
		dataAccess.setStorage(storage);
		
		//ReflectionTestUtils.setField(dataAccess, "messageList",messageListMocked);
		when(storage.getMessageList()).thenReturn(new HashMap<String,List<MyTwitterMessage>>());
		List<MyTwitterMessage> messageList=dataAccess.reading(data);
		
		//No posting, messageList is empty
		assertEquals(0, messageList.size());
		 
	}
	
	@Test
	public void testREADING()
	{
		Date now=new Date();
		InputData data= new InputData();
		data.setCommand(Command.READING);
		data.setUser("Charlie");
		
		dataAccess.setStorage(storage);
		
		Map<String,List<MyTwitterMessage>> messageListHash=new HashMap<String,List<MyTwitterMessage>>();
		List<MyTwitterMessage> messageList=new ArrayList<MyTwitterMessage>();
		messageList.add(new MyTwitterMessage("Charlie", "Hello.", now));
		messageListHash.put("Charlie", messageList);
		
		when(storage.getMessageList()).thenReturn(messageListHash);
		List<MyTwitterMessage> messageListReturned=dataAccess.reading(data);
		
		//No posting, messageList is empty
		assertEquals(1, messageListReturned.size());
		assertEquals("Charlie",messageListReturned.get(0).getUser());
		assertEquals("Hello.",messageListReturned.get(0).getContent());
		assertEquals(now,messageListReturned.get(0).getTimeStamp());
		 
	}
	
	@Test
	public void testWALLINGWithoutPosting()
	{
		InputData data= new InputData();
		data.setCommand(Command.WALL);
		data.setUser("Charlie");
		
		dataAccess.setStorage(storage);
		
		//ReflectionTestUtils.setField(dataAccess, "messageList",messageListMocked);
		when(storage.getMessageList()).thenReturn(new HashMap<String,List<MyTwitterMessage>>());
		List<MyTwitterMessage> messageList=dataAccess.walling(data);
		
		//No posting, messageList is empty
		assertEquals(0, messageList.size());
		 
	}
	
	@Test
	public void testWALLINGWithoutFollowing()
	{
		Date now=new Date();
		InputData data= new InputData();
		data.setCommand(Command.WALL);
		data.setUser("Charlie");
		
		dataAccess.setStorage(storage);
		
		Map<String,List<MyTwitterMessage>> messageListHash=new HashMap<String,List<MyTwitterMessage>>();
		List<MyTwitterMessage> messageList=new ArrayList<MyTwitterMessage>();
		messageList.add(new MyTwitterMessage("Charlie", "Hello.", now));
		messageListHash.put("Charlie", messageList);

		when(storage.getMessageList()).thenReturn(messageListHash);
		when(storage.getFollowList()).thenReturn(new HashMap<String,List<String>>());
		List<MyTwitterMessage> messageListReturned=dataAccess.walling(data);
		
		//No followers, messageList Charlie's timeline
		assertEquals(1, messageList.size());
		assertEquals("Charlie",messageListReturned.get(0).getUser());
		assertEquals("Hello.",messageListReturned.get(0).getContent());
		assertEquals(now,messageListReturned.get(0).getTimeStamp());
		 
	}
	
	@Test
	public void testWALLING()
	{
		Date now=new Date();
		InputData data= new InputData();
		data.setCommand(Command.WALL);
		data.setUser("Charlie");
		
		dataAccess.setStorage(storage);
		
		Map<String,List<MyTwitterMessage>> messageListHash=new HashMap<String,List<MyTwitterMessage>>();
		List<MyTwitterMessage> messageList=new ArrayList<MyTwitterMessage>();
		messageList.add(new MyTwitterMessage("Charlie", "Hello.", now));
		messageListHash.put("Charlie", messageList);
		
		messageList=new ArrayList<MyTwitterMessage>();
		messageList.add(new MyTwitterMessage("Alice", "Hello Charlie.", now));
		messageListHash.put("Alice", messageList);
		
		Map<String,List<String>> followListHash=new HashMap<String,List<String>>();
		List<String> followList=new ArrayList<String>();
		followList.add("Alice");
		followListHash.put("Charlie", followList);

		when(storage.getMessageList()).thenReturn(messageListHash);
		when(storage.getFollowList()).thenReturn(followListHash);
		List<MyTwitterMessage> messageListReturned=dataAccess.walling(data);
		
		//walling ok
		assertEquals(2, messageListReturned.size());
		assertEquals("Charlie",messageListReturned.get(0).getUser());
		assertEquals("Hello.",messageListReturned.get(0).getContent());
		assertEquals(now,messageListReturned.get(0).getTimeStamp());
		
		assertEquals("Alice",messageListReturned.get(1).getUser());
		assertEquals("Hello Charlie.",messageListReturned.get(1).getContent());
		assertEquals(now,messageListReturned.get(1).getTimeStamp());
		 
	}
	
	@Test
	public void testFOLLOWING()
	{
		Date now=new Date();
		InputData data= new InputData();
		data.setCommand(Command.FOLLOWING);
		data.setUser("Charlie");
		data.setUserFollowed("Alice");
		
		Map<String,List<MyTwitterMessage>> messageListHash=new HashMap<String,List<MyTwitterMessage>>();
		List<MyTwitterMessage> messageList=new ArrayList<MyTwitterMessage>();
		messageList.add(new MyTwitterMessage("Charlie", "Hello.", now));
		messageListHash.put("Charlie", messageList);
		
		messageList=new ArrayList<MyTwitterMessage>();
		messageList.add(new MyTwitterMessage("Alice", "Hello Charlie.", now));
		messageListHash.put("Alice", messageList);
		
		when(storage.getMessageList()).thenReturn(messageListHash);
		
		dataAccess.setStorage(storage);
		
		dataAccess.following(data);
		
		List<String> followList=(List<String>)ReflectionTestUtils.getField(dataAccess, "followList");
		
		
		assertEquals(1, followList.size());
		assertEquals("Alice", followList.get(0));
		
		 
	}
	
	@Test
	public void testFOLLOWINGUserNotFound()
	{
		InputData data= new InputData();
		data.setCommand(Command.FOLLOWING);
		data.setUser("Charlie");
		data.setUserFollowed("Alice");
		
		
		dataAccess.setStorage(storage);
		
		dataAccess.following(data);
		
		List<String> followList=(List<String>)ReflectionTestUtils.getField(dataAccess, "followList");
		
		//It does not matter that Charlie/Alice did not send any message yet, following should work
		assertEquals(1, followList.size());
		assertEquals("Alice", followList.get(0));
		
		 
	}
	
	@Test
	public void testFOLLOWINGFollowerNotFound()
	{
		Date now=new Date();
		InputData data= new InputData();
		data.setCommand(Command.FOLLOWING);
		data.setUser("Charlie");
		data.setUserFollowed("Alice");
		
		
		dataAccess.setStorage(storage);
		
		Map<String,List<MyTwitterMessage>> messageListHash=new HashMap<String,List<MyTwitterMessage>>();
		List<MyTwitterMessage> messageList=new ArrayList<MyTwitterMessage>();
		messageList.add(new MyTwitterMessage("Charlie", "Hello.", now));
		messageListHash.put("Charlie", messageList);
		
		when(storage.getMessageList()).thenReturn(messageListHash);
		
		dataAccess.following(data);
		
		List<String> followList=(List<String>)ReflectionTestUtils.getField(dataAccess, "followList");
		
		//Alice did not send any message yet, but she can follow
		assertEquals(1, followList.size());
		assertEquals("Alice", followList.get(0));
		
		 
	}
	
	
	
	
}
