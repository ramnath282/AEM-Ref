package com.mattel.ag.retail.core.pojos;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class DisplayEventPojoTest {

	DisplayEventPojo displayEventPojo;
	Calendar myCal = Calendar.getInstance();
	Date theDate = myCal.getTime();

	@Before
	public void setUp() {
		displayEventPojo = new DisplayEventPojo();
		displayEventPojo.setAdditionalDateInfo("additionalDateInfo");
		displayEventPojo.setDate(theDate);
		displayEventPojo.setEventDate(theDate.toString());
		displayEventPojo.setEventDescription("eventDescription");
		displayEventPojo.setEventEndTime("eventEndTime");
		displayEventPojo.setEventId("eventId");
		displayEventPojo.setEventStartTime("eventStartTime");
		displayEventPojo.setEventTitle("eventTitle");
		displayEventPojo.setEventZomatoUrl("eventZomatoUrl");
		displayEventPojo.setGratuityRequired("gratuityRequired");
		displayEventPojo.setLocationName("locationName");
		displayEventPojo.setMinAge("minAge");
		displayEventPojo.setPricingDetails("displayEventPojo");
		displayEventPojo.setReservationRequired("reservationRequired");
		displayEventPojo.setScheduleDescription("scheduleDescription");
		displayEventPojo.setTime(theDate);
		displayEventPojo.setCostInfo("costInfo");
		displayEventPojo.setEventDateForSearchResult("eventDateForSearchResult");
		displayEventPojo.setDateDetailNodePath("dateDetailNodePath");
		displayEventPojo.setReservationDetails("reservationDate");
		displayEventPojo.setEventDateDetailNodePath("eventDateNodePath");

	}

	@Test
	public void getAdditionalDateInfoTest() {
		displayEventPojo.getAdditionalDateInfo();
	}

	@Test
	public void getDateTest() {
		displayEventPojo.getDate();
	}

	@Test
	public void getEventDateTest() {
		displayEventPojo.getEventDate();
	}

	@Test
	public void getEventDescriptionTest() {
		displayEventPojo.getEventDescription();
	}

	@Test
	public void getEventEndTimeTest() {
		displayEventPojo.getEventEndTime();
	}

	@Test
	public void getEventIdTest() {
		displayEventPojo.getEventId();
	}

	@Test
	public void getEventStartTimeTest() {
		displayEventPojo.getEventStartTime();
	}

	@Test
	public void getEventTitleTest() {
		displayEventPojo.getEventTitle();
	}

	@Test
	public void getEventZomatoUrlTest() {
		displayEventPojo.getEventZomatoUrl();
	}

	@Test
	public void getGratuityRequiredTest() {
		displayEventPojo.getGratuityRequired();
	}

	@Test
	public void getLocationNameTest() {
		displayEventPojo.getLocationName();
	}

	@Test
	public void getMinAgeTest() {
		displayEventPojo.getMinAge();
	}

	@Test
	public void getPricingDetailsTest() {
		displayEventPojo.getPricingDetails();
	}

	@Test
	public void getReservationRequiredTest() {
		displayEventPojo.getReservationRequired();
	}

	@Test
	public void getScheduleDescriptionTest() {
		displayEventPojo.getScheduleDescription();
	}

	@Test
	public void getTimeTest() {
		displayEventPojo.getTime();
	}

	@Test
	public void toStringTest() {
		displayEventPojo.toString();
	}
	
	@Test
    public void getCostInfo() {
        displayEventPojo.getCostInfo();
    }
	
	@Test
    public void getEventDateForSearchResult() {
        displayEventPojo.getEventDateForSearchResult();
    }
	
	@Test
    public void getDateDetailNodePath() {
        displayEventPojo.getDateDetailNodePath();
    }

	@Test
    public void getReservationDetails() {
        displayEventPojo.getReservationDetails();
    }
	
	@Test
    public void getEventDateDetailNodePath() {
        displayEventPojo.getEventDateDetailNodePath();
    }
	
}
