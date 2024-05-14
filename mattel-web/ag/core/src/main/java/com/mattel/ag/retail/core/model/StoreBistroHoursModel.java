package com.mattel.ag.retail.core.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mattel.ag.retail.core.constants.Constants;
import com.mattel.ag.retail.core.pojos.StoreBistroHoursPojo;
import com.mattel.ag.retail.core.services.MultifieldReader;

/**
 * @author CTS. A Model class for Store Bistro Hours
 */

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class StoreBistroHoursModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(StoreBistroHoursModel.class);
  List<StoreBistroHoursPojo> storeHoursDetails = new ArrayList<>();
  List<StoreBistroHoursPojo> bistroHoursDetails = new ArrayList<>();
  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
  @Inject
  private Node storeHourDetails;
  @Inject
  private Node bistroHourDetails;
  @Inject
  private MultifieldReader multifieldReader;

  @PostConstruct
  protected void init() {
    LOGGER.info("Init method in StoreBistroHoursModel start");
    try {

      if (storeHourDetails != null) {
        getStoreDetails(storeHourDetails);
      }
      if (bistroHourDetails != null) {
        getBistroDetails(bistroHourDetails);
      }
    } catch (ParseException e) {
      LOGGER.error("Exception caused while getting start and end time in StoreBistroHoursModel: ",
          e);
    }
    LOGGER.info("Init method in StoreBistroHoursModel end");
  }

  /**
   * @return This method return list of StoreHours
   */
  public List<StoreBistroHoursPojo> getStoreHoursDetails() {
    return storeHoursDetails;
  }

  /**
   * @return This method return list of BistroHours
   */
  public List<StoreBistroHoursPojo> getBistroHoursDetails() {
    return bistroHoursDetails;
  }

  /**
   * @param storeHourDetails
   * @throws JSONException
   * @throws ParseException
   */
  public void getStoreDetails(Node storeHourDetails) throws ParseException {
    LOGGER.info("Start of getStoreDetails method");
     Map<String, ValueMap> multifieldStoreProperty = multifieldReader.propertyReader(storeHourDetails);
    for (Map.Entry<String, ValueMap> entry : multifieldStoreProperty.entrySet()) {
      if (entry.getValue().containsKey("storeStartTime") && entry.getValue().containsKey("storeEndTime")) {
          String startTime = entry.getValue().get("storeStartTime", String.class);
          String endTime = entry.getValue().get("storeEndTime", String.class);
          LOGGER.debug("Store Start Time {} End Time {}", startTime, endTime);
          Calendar startDateCal = getParsedDate(startTime);
          Calendar endDateCal = getParsedDate(endTime);
          StoreBistroHoursPojo storeHoursPojo = new StoreBistroHoursPojo();
          storeHoursPojo.setDate(startDateCal.get(Calendar.DATE));
          setStoreStartTimeEndTime(startDateCal,storeHoursPojo,endDateCal);
          String storeStoreClosed =  entry.getValue().get("storeStoreClosed", String.class);
          boolean storeClosed = false;
          if("true".equals(storeStoreClosed)){
        	  storeClosed = true;
          }
          storeHoursPojo.setStoreStoreClosed(storeClosed);
          Calendar calcurrentDate = getDateWithTime(format.parse(endTime));
          Calendar currentDateCal = getDateWithTime(format.parse(startTime));
          if (calcurrentDate.getTime().after(currentDateCal.getTime())) {
            storeHoursPojo.setStoreEndDate(endDateCal.get(Calendar.DATE));
            if (currentDateCal.get(Calendar.MONTH) != endDateCal.get(Calendar.MONTH)) {
              Formatter monthFormatter = new Formatter();
              storeHoursPojo.setStoreEndMonth(monthFormatter.format("%tB", endDateCal).toString());
              monthFormatter.close();
            }
          }
          LOGGER.debug("storeHours{}", storeHoursPojo);
          storeHoursDetails.add(storeHoursPojo);
          
        
      }
    }
    LOGGER.info("End of getStoreDetails method");
  }

	/**
	 * @param startDateCal
	 * @param storeHoursPojo
	 * @param endDateCal
	 */
	private void setStoreStartTimeEndTime(Calendar startDateCal, StoreBistroHoursPojo storeHoursPojo,
			Calendar endDateCal) {
		Formatter fmt = new Formatter();
		try {
			storeHoursPojo.setMonth(fmt.format("%tB", startDateCal).toString());
			int startingTime = startDateCal.get(Calendar.HOUR_OF_DAY);
			if (startDateCal.get(Calendar.HOUR_OF_DAY) > 12) {
				startingTime = startDateCal.get(Calendar.HOUR_OF_DAY) - 12;
			}
			int endingTime = endDateCal.get(Calendar.HOUR_OF_DAY);
			if (endDateCal.get(Calendar.HOUR_OF_DAY) > 12) {
				endingTime = endDateCal.get(Calendar.HOUR_OF_DAY) - 12;
			}
			// Set starting time for store and bistro hours
			setStartingTime(startDateCal, startingTime, storeHoursPojo);
			// Set ending time for store and bistro hours
			setEndingTime(endDateCal, endingTime, storeHoursPojo);
		} finally {
			fmt.close();
		}
	}
  /**
 * @param endDateCal
 * @param endingTime
 * @param storeHoursPojo
 */
private void setEndingTime(Calendar endDateCal, int endingTime, StoreBistroHoursPojo storeHoursPojo) {
	  String calendarEndTime = String.valueOf(endDateCal.get(Calendar.MINUTE));
	  if(endDateCal.get(Calendar.MINUTE) >= 0 && endDateCal.get(Calendar.MINUTE) <= 9) {
		  calendarEndTime = "0" + endDateCal.get(Calendar.MINUTE);
	  }
	  String endTimeInTwoHrFormat = String.valueOf(endingTime);
      if (endDateCal.get(Calendar.HOUR_OF_DAY) < 12 || endDateCal.get(Calendar.HOUR_OF_DAY) == 0) {
    	 storeHoursPojo
            .setStoreEndTime(endTimeInTwoHrFormat + ":" + calendarEndTime + " " + Constants.AM);
        storeHoursPojo
        .setBistroEndTime(endTimeInTwoHrFormat + ":" + calendarEndTime + " " + Constants.AM);
      } else {
        storeHoursPojo
            .setStoreEndTime(endTimeInTwoHrFormat + ":" + calendarEndTime + " " + Constants.PM);
        storeHoursPojo
        .setBistroEndTime(endTimeInTwoHrFormat + ":" + calendarEndTime + " " + Constants.PM);
      }
}

/**
 * @param startDateCal
 * @param startingTime
 * @param storeHoursPojo
 */
private void setStartingTime(Calendar startDateCal, int startingTime, StoreBistroHoursPojo storeHoursPojo) {
	String calendarStartTime = String.valueOf(startDateCal.get(Calendar.MINUTE));
	if(startDateCal.get(Calendar.MINUTE) >= 0 && startDateCal.get(Calendar.MINUTE) <= 9) {
		calendarStartTime = "0" + startDateCal.get(Calendar.MINUTE);
	  }
	  String startTimeInTwoHrFormat = String.valueOf(startingTime);

      if (startDateCal.get(Calendar.HOUR_OF_DAY) < 12 || startDateCal.get(Calendar.HOUR_OF_DAY) == 0 ) {
    	  
        storeHoursPojo.setStoreStartTime(
        		startTimeInTwoHrFormat + ":" + calendarStartTime + " " + Constants.AM);
        storeHoursPojo.setBistroStartTime(
        		startTimeInTwoHrFormat + ":" + calendarStartTime + " " + Constants.AM);
        
      } else {
    	  
        storeHoursPojo.setStoreStartTime(
        		startTimeInTwoHrFormat + ":" + calendarStartTime + " " + Constants.PM);
        storeHoursPojo.setBistroStartTime(
        		startTimeInTwoHrFormat + ":" + calendarStartTime + " " + Constants.PM);
      }
	
}

/**
 * @param endDate
 * @return
 */
private Calendar getDateWithTime(Date endDate){
	  Calendar calcurrentDate = Calendar.getInstance();
      calcurrentDate.setTime(endDate);
      calcurrentDate.set(Calendar.HOUR_OF_DAY, 0);
      calcurrentDate.set(Calendar.MINUTE, 0);
      calcurrentDate.set(Calendar.SECOND, 0);
      calcurrentDate.set(Calendar.MILLISECOND, 0);
	return calcurrentDate;
  }
/**
 * @param startTime
 * @return
 * @throws ParseException
 */
private Calendar getParsedDate(String startTime) throws ParseException{
	LOGGER.info("Start of getParsedDate method");
	OffsetDateTime odt = OffsetDateTime.parse(startTime);
	Date date = format.parse(startTime);
    Calendar startDateCal = Calendar.getInstance();
    startDateCal.setTime(date);
    startDateCal.setTimeZone(TimeZone.getTimeZone(odt.getOffset()));
    LOGGER.info("End of getParsedDate method");
	return startDateCal;
}
  /**
   * @param bistroHourDetails
   * @throws JSONException
   * @throws ParseException
   */
  public void getBistroDetails(Node bistroHourDetails) throws ParseException {
    LOGGER.info("Start of getBistroDetails method");
     Map<String, ValueMap> multifieldBistroProperty = multifieldReader.propertyReader(bistroHourDetails);
    for (Map.Entry<String, ValueMap> entry : multifieldBistroProperty.entrySet()) {
      if (entry.getValue().containsKey("bistroStartTime") && entry.getValue().containsKey("bistroEndTime")) {
        String startTime = entry.getValue().get("bistroStartTime", String.class);
          String endTime = entry.getValue().get("bistroEndTime", String.class);
          LOGGER.debug("Bistro Start Time {} End Time {}", startTime, endTime);
          Calendar startDateCal = getParsedDate(startTime);
          Calendar endDateCal = getParsedDate(endTime);
          StoreBistroHoursPojo bistroHoursPojo = new StoreBistroHoursPojo();
          bistroHoursPojo.setDate(startDateCal.get(Calendar.DATE));
          setStoreStartTimeEndTime(startDateCal,bistroHoursPojo,endDateCal);
          String bistroStoreClosed =  entry.getValue().get("bistroStoreClosed", String.class);
          boolean bistroClosed = false;
          if("true".equals(bistroStoreClosed)){
        	  bistroClosed = true;
          }
          bistroHoursPojo.setBistroStoreClosed(bistroClosed);
          Calendar calcurrentDate = getDateWithTime(format.parse(endTime));
          Calendar currentDateCal = getDateWithTime(format.parse(startTime));
          if (calcurrentDate.getTime().after(currentDateCal.getTime())) {
            bistroHoursPojo.setBistroEndDate(endDateCal.get(Calendar.DATE));
            if (currentDateCal.get(Calendar.MONTH) != endDateCal.get(Calendar.MONTH)) {
              Formatter monthFormatter = new Formatter();
              bistroHoursPojo
                  .setBistroEndMonth(monthFormatter.format("%tB", endDateCal).toString());
              monthFormatter.close();
            }
          }


          LOGGER.debug("bistroHoursPojo{}", bistroHoursPojo);
          
          bistroHoursDetails.add(bistroHoursPojo);
        
      }

    }
    LOGGER.info("End of getBistroDetails method");
  }

public void setStoreHourDetails(Node storeHourDetails) {
	this.storeHourDetails = storeHourDetails;
}

public void setBistroHourDetails(Node bistroHourDetails) {
	this.bistroHourDetails = bistroHourDetails;
}

public void setMultifieldReader(MultifieldReader multifieldReader) {
	this.multifieldReader = multifieldReader;
}

}
