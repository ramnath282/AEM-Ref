package com.mattel.ag.retail.core.pojos;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EventPojoTest {

	EventPojo eventPojo;
	List<LocationDetailsPojo> locationDetails;

	@Before
	public void setUp() {

		eventPojo = new EventPojo();
		eventPojo.setEventDescription("eventDescription");
		eventPojo.setEventId("eventId");
		eventPojo.setEventTitle("eventTitle");
		eventPojo.setLocationDetails(locationDetails);
		eventPojo.setMinAge("minAge");
		eventPojo.setReservationRequired("reservationRequired");

	}

	@Test
	public void getEventDescriptionTest() {

		eventPojo.getEventDescription();

	}

	@Test
	public void getEventIdTest() {

		eventPojo.getEventId();

	}

	@Test
	public void getEventTitleTest() {

		eventPojo.getEventTitle();

	}

	@Test
	public void getLocationDetailsTest() {

		eventPojo.getLocationDetails();

	}

	@Test
	public void getMinAgeTest() {

		eventPojo.getMinAge();

	}

	@Test
	public void getReservationRequiredTest() {
		eventPojo.getReservationRequired();
	}

	@Test
	public void toStringTest() {

		eventPojo.toString();

	}
}
