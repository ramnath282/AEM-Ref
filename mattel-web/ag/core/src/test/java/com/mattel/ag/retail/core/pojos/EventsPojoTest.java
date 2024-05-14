package com.mattel.ag.retail.core.pojos;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class EventsPojoTest {

	EventsPojo eventsPojo;

	@Before
	public void setUp() {
		eventsPojo = new EventsPojo();
	}

	@Test
	public void setDate() {
		eventsPojo.setDate(new Date());
	}

	@Test
	public void setEventDate() {
		eventsPojo.setEventDate(new Date().toString());
	}

	@Test
	public void setStartTime() {
		eventsPojo.setStartTime(new Date().toString());
	}

	@Test
	public void setEndTime() {
		eventsPojo.setEndTime(new Date().toString());
	}

	@Test
	public void setEventDescription() {
		eventsPojo.setEventDescription("EventDescription");
	}

	@Test
	public void setEventTitle() {
		eventsPojo.setEventTitle("EventTitle");
	}

	@Test
	public void setEventInfo() {
		eventsPojo.setEventInfo("EventInfo");
	}

	@Test
	public void getDate() {
		eventsPojo.getDate();
	}

	@Test
	public void getEventDate() {
		eventsPojo.getEventDate();
	}

	@Test
	public void getStartTime() {
		eventsPojo.getStartTime();
	}

	@Test
	public void getEndTime() {
		eventsPojo.getEndTime();
	}

	@Test
	public void getEventDescription() {
		eventsPojo.getEventDescription();
	}

	@Test
	public void getEventTitle() {
		eventsPojo.getEventTitle();
	}

	@Test
	public void getEventInfo() {
		eventsPojo.getEventInfo();
	}

	@Test
	public void toStringTest() {
		eventsPojo.toString();
	}
	@Test 
	public void getZomatoUrl(){
		eventsPojo.getZomatoUrl();
	}
	@Test 
	public void setZomatoUrl(){
		eventsPojo.setZomatoUrl("zomatoUrl");
	}
	@Test 
	public void getTagTitle(){
		eventsPojo.getTagTitle();
	}
	@Test 
	public void getTagNameSpace(){
		eventsPojo.getTagNameSpace();
	}
	@Test 
	public void getTime()
	{eventsPojo.getTime();
		
	}
	@Test 
	public void setTagTitle(){
		eventsPojo.setTagTitle("tagTitle");
	}
	
	@Test 
	public void setTagNameSpace(){
		eventsPojo.setTagNameSpace("tagNameSpace");
	}
	
}