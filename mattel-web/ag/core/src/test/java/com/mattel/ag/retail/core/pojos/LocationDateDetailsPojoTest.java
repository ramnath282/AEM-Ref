package com.mattel.ag.retail.core.pojos;

import org.junit.Before;
import org.junit.Test;

public class LocationDateDetailsPojoTest {

	LocationDateDetailsPojo locationDateDetailsPojo;

	@Before
	public void setUp() {

		locationDateDetailsPojo = new LocationDateDetailsPojo();
		locationDateDetailsPojo.setAdditionalDateInfo("additionalDateInfo");
		locationDateDetailsPojo.setEventDate("eventDate");
		locationDateDetailsPojo.setEventEndTime("eventEndTime");
		locationDateDetailsPojo.setEventStartTime("eventStartTime");
		locationDateDetailsPojo.setScheduleDescription("scheduleDescription");

	}

	@Test
	public void toStringTest() {
		locationDateDetailsPojo.toString();
	}

	@Test
	public void getAdditionalDateInfo() {
		locationDateDetailsPojo.getAdditionalDateInfo();
	}

	@Test
	public void getEventDate() {
		locationDateDetailsPojo.getEventDate();
	}

	@Test
	public void getEventEndTime() {
		locationDateDetailsPojo.getEventEndTime();
	}

	@Test
	public void getEventStartTime() {
		locationDateDetailsPojo.getEventStartTime();
	}

	@Test
	public void getScheduleDescription() {
		locationDateDetailsPojo.getScheduleDescription();
	}
}
