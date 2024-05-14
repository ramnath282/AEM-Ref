package com.mattel.ag.retail.core.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mattel.ag.retail.core.constants.Constants;
import com.mattel.ag.retail.core.pojos.DisplayEventPojo;
import com.mattel.ag.retail.core.pojos.EventPojo;
import com.mattel.ag.retail.core.pojos.LocationDetailsPojo;
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

/**
 * @author CTS
 *
 */
@Component(service = DisplayStoreEvents.class, immediate = true)
public class DisplayStoreEvents {

	private static final Logger LOGGER = LoggerFactory.getLogger(DisplayStoreEvents.class);
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	@Reference
	QueryBuilder queryBuilder;
	@Reference
	private PropertyReaderUtils propertyReaderUtils;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

	/**
	 * This method will finally get all the events & the event details
	 * associated to a particular store.
	 * 
	 * @param currentStoreTag
	 *            This is the store tag corresponding to which the events need
	 *            to be fetched.
	 * @return List<DisplayEventPojo> This method will return a complete list of
	 *         Events associated to a particular Store
	 **/
	public List<DisplayEventPojo> getEventsData(String currentStoreTag) {
		ResourceResolver resolver = null;
		LOGGER.info("method getEventsData start");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		List<DisplayEventPojo> eventsDetailsList = new ArrayList<>();
		try {
			resolver = resourceResolverFactory.getServiceResourceResolver(map);
			LOGGER.debug("Resolver is not null");
			eventsDetailsList = getStoreEvents(currentStoreTag);
			eventsDetailsList.sort((DisplayEventPojo t1, DisplayEventPojo t2) -> {
				LOGGER.debug("Sorting Events according to Event Date & Time");
				int cmp = t1.getDate().compareTo(t2.getDate());
				if (cmp == 0) {
					cmp = t1.getTime().compareTo(t2.getTime());
				}
				return cmp;
			});
		} catch (LoginException e) {
			LOGGER.error("Exception caused in getEventsData:{}", e);
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("method getEventsData end");
		return eventsDetailsList;

	}

	public ResourceResolverFactory getResourceResolverFactory() {
		return resourceResolverFactory;
	}

	public void setResourceResolverFactory(ResourceResolverFactory resourceResolverFactory) {
		this.resourceResolverFactory = resourceResolverFactory;
	}

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	/**
	 * @param currentStoreTag
	 *            This is the store tag corresponding to which the events need
	 *            to be fetched.
	 * @return displayEvents
	 */
	private List<DisplayEventPojo> getStoreEvents(String currentStoreTag) {
		LOGGER.info("method getEvents start");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, Constants.READWRITESERVICE);
		ResourceResolver resolver = null;
		List<DisplayEventPojo> displayEvents = new ArrayList<>();

		try {
			resolver = resourceResolverFactory.getServiceResourceResolver(map);
			LOGGER.debug("Resolver is not null");
			Map<String, String> querymap = new HashMap<>();
			querymap.put("type", "cq:Page");
			querymap.put("path", propertyReaderUtils.getEventsPath());
			querymap.put("property", "@jcr:content/storeTag");
			querymap.put("property.value", currentStoreTag);
			querymap.put("p.limit", "-1");
			LOGGER.debug("querymap: {}", querymap);
			Query query = queryBuilder.createQuery(PredicateGroup.create(querymap), resolver.adaptTo(Session.class));
			SearchResult result = query.getResult();
			if (null != result) {
				getEvents(displayEvents, result);

			}

		} catch (LoginException | RepositoryException | ParseException e) {
			LOGGER.error("Exception caused in getStoreEvents:{}", e);
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("method getEvents end");
		return displayEvents;
	}

	/**
	 * @param displayEvents
	 * @param result
	 * @throws RepositoryException
	 * @throws ParseException
	 */
	private void getEvents(List<DisplayEventPojo> displayEvents, SearchResult result)
			throws RepositoryException, ParseException {
		LOGGER.info("method getEvents start");
		for (Hit hit : result.getHits()) {
			createEventPojo(displayEvents, hit);

		}
		LOGGER.info("method getEvents end");
	}

	private void createEventPojo(List<DisplayEventPojo> displayEvents, Hit hit)
			throws RepositoryException, ParseException {
		EventPojo eventPojo = new EventPojo();
		String eventID = StringUtils.EMPTY;
		String eventDescription = StringUtils.EMPTY;
		String eventTitle = StringUtils.EMPTY;
		String minAge = StringUtils.EMPTY;
		String reservationRequired = StringUtils.EMPTY;
		Node storeNode = hit.getNode();
		Node storeParentEventNode = storeNode.getParent();
		NodeIterator storeParentEventNodeIterator = storeParentEventNode.getNodes();

		while (storeParentEventNodeIterator.hasNext()) {
			Node storeParentNodeJcrContent = storeParentEventNodeIterator.nextNode();
			if (storeParentNodeJcrContent.getName().equalsIgnoreCase(Constants.JCR_CONTENT)) {
				eventID = extractEventDetailsFromJCR("eventID", storeParentNodeJcrContent);
				eventDescription = extractEventDetailsFromJCR("eventDescription", storeParentNodeJcrContent);
				eventTitle = extractEventDetailsFromJCR("eventTitle", storeParentNodeJcrContent);
				reservationRequired = extractEventDetailsFromJCR("reservationRequired", storeParentNodeJcrContent);
				minAge = extractEventDetailsFromJCR("minAge", storeParentNodeJcrContent);
			}
		}

		eventPojo.setEventId(eventID);
		eventPojo.setEventDescription(eventDescription);
		eventPojo.setEventTitle(eventTitle);
		eventPojo.setMinAge(minAge);
		eventPojo.setReservationRequired(reservationRequired);
		NodeIterator dateTimeNodeIterator1 = storeNode.getNodes();
		NodeIterator dateTimeNodeIterator2 = storeNode.getNodes();
		if (dateTimeNodeIterator1.hasNext()) {
			createDisplayEventList(displayEvents, eventPojo, dateTimeNodeIterator1, dateTimeNodeIterator2);
		}
	}

    /**
     * @param propertyName
     * @param nodeJcrContent
     * @return
     * @throws RepositoryException
     */
    private String extractEventDetailsFromJCR(String propertyName, Node nodeJcrContent)
            throws RepositoryException {
        String property = StringUtils.EMPTY;
            if (nodeJcrContent.hasProperty(propertyName)) {
                property = nodeJcrContent.getProperty(propertyName).getValue().getString();
            }
        return property;
    }

	/**
	 * @param displayEventPojo
	 * @param childrenNodes
	 * @throws RepositoryException
	 * @throws ParseException
	 */
	private void createDisplayEventList(List<DisplayEventPojo> displayEvents, EventPojo eventPojo,
			NodeIterator dateTimeNodeIterator1, NodeIterator dateTimeNodeIterator2)
			throws RepositoryException, ParseException {
		LOGGER.info("method createDisplayEventList start");
		LocationDetailsPojo locationDetailsPojo = new LocationDetailsPojo();
		String zomatoURL;
		String storeTag;
		String storeName;
		String gratuityRequired;
		String costInfo;
		while (dateTimeNodeIterator1.hasNext()) {
			Node next = dateTimeNodeIterator1.nextNode();
			if (next.getName().equalsIgnoreCase(Constants.JCR_CONTENT)) {
				zomatoURL = extractEventDetailsFromJCR("zomatoURL", next);
				storeTag = extractEventDetailsFromJCR("storeTag", next);
				storeName = extractEventDetailsFromJCR("storeName", next);
				gratuityRequired = extractEventDetailsFromJCR("gratuityRequired", next);
				costInfo = extractEventDetailsFromJCR("costInfo", next);
				createPricingDetails(locationDetailsPojo, next);
				locationDetailsPojo.setZomatoURL(zomatoURL);
				locationDetailsPojo.setStoreTag(storeTag);
				locationDetailsPojo.setStoreName(storeName);
				locationDetailsPojo.setCostInfo(costInfo);
				locationDetailsPojo.setGratuityRequired(gratuityRequired);

			}
		}
		while (dateTimeNodeIterator2.hasNext()) {
			Node next = dateTimeNodeIterator2.nextNode();
			if (!next.getName().equalsIgnoreCase(Constants.JCR_CONTENT)) {
				Node dateTimeNode = next;
				NodeIterator dateTimeNodeChildrenIterartor = dateTimeNode.getNodes();
				if (dateTimeNodeChildrenIterartor.hasNext()) {
					DisplayEventPojo displayEventPojo = new DisplayEventPojo();
					Node dateTimeNodeChildren = dateTimeNodeChildrenIterartor.nextNode();
					if (dateTimeNodeChildren.getName().equalsIgnoreCase(Constants.JCR_CONTENT)) {
						createDisplayEventPojo(displayEvents, eventPojo, locationDetailsPojo, displayEventPojo,
								dateTimeNodeChildren);
					}
				}

			}
		}
		LOGGER.info("method createDisplayEventList end");
	}

	private void createPricingDetails(LocationDetailsPojo locationDetailsPojo, Node next) throws RepositoryException {

		String pricingAmount = StringUtils.EMPTY;
		String pricingOption = StringUtils.EMPTY;

		if (next.hasProperty("pricingAmount")) {
			pricingAmount = next.getProperty("pricingAmount").getValue().getString();
		}
		if (next.hasProperty("pricingOption")) {
			pricingOption = next.getProperty("pricingOption").getValue().getString();
		}
		locationDetailsPojo.setPricingAmount(pricingAmount);
		locationDetailsPojo.setPricingOption(pricingOption);
	}

	/**
	 * @param displayEvents
	 * @param eventID
	 * @param zomatoURL
	 * @param displayEventPojo
	 * @param dateTimeNodeChildren
	 * @throws RepositoryException
	 * @throws ParseException
	 */
	private void createDisplayEventPojo(List<DisplayEventPojo> displayEvents, EventPojo eventPojo,
			LocationDetailsPojo locationDetailsPojo, DisplayEventPojo displayEventPojo, Node dateTimeNodeChildren)
			throws RepositoryException, ParseException {
		LOGGER.info("method createDisplayEventPojo start");
		Date date = null;
		String eventDate = StringUtils.EMPTY;
		String eventEndTime = StringUtils.EMPTY;
		String eventStartTime = StringUtils.EMPTY;
		String scheduleDescription = StringUtils.EMPTY;
		String additionalDateInfo = StringUtils.EMPTY;

		Date currentDate = getCurrentDate();
		if (dateTimeNodeChildren.hasProperty("eventDate")) {
			String eventDateVal = dateTimeNodeChildren.getProperty("eventDate").getValue().getString();
			date = format.parse(eventDateVal);
			eventDate = getEventDate(date, eventDateVal);
		} else {
			displayEventPojo.setEventDate("");
		}
		if (dateTimeNodeChildren.hasProperty("eventEndTime")) {
			String eventEndTimeVal = dateTimeNodeChildren.getProperty("eventEndTime").getValue().getString();
			eventEndTime = convertTostandardDateFormat(eventEndTimeVal);
		}
		if (dateTimeNodeChildren.hasProperty("eventStartTime")) {
			String eventStartTimeVal = dateTimeNodeChildren.getProperty("eventStartTime").getValue().getString();
			eventStartTime = convertTostandardDateFormat(eventStartTimeVal);
		}
		if (dateTimeNodeChildren.hasProperty("scheduleDescription")) {
			scheduleDescription = dateTimeNodeChildren.getProperty("scheduleDescription").getValue().getString();

		}
		if (dateTimeNodeChildren.hasProperty("additionalDateInfo")) {
			additionalDateInfo = dateTimeNodeChildren.getProperty("additionalDateInfo").getValue().getString();

		}
		/*
		 * We will set the event data to the DisplayEventPojo only if the
		 * EventDate is greater than the current Date
		 */
		if (null != date && date.compareTo(currentDate) >= 0) {

			displayEventPojo.setEventId(eventPojo.getEventId());
			displayEventPojo.setEventDescription(eventPojo.getEventDescription());
			displayEventPojo.setEventTitle(eventPojo.getEventTitle());
			displayEventPojo.setReservationRequired(eventPojo.getReservationRequired());
			displayEventPojo.setMinAge(eventPojo.getMinAge());
			displayEventPojo.setLocationName(locationDetailsPojo.getStoreName());
			displayEventPojo.setCostInfo(locationDetailsPojo.getCostInfo());
			displayEventPojo.setEventZomatoUrl(locationDetailsPojo.getZomatoURL());
			createPricingDetails(locationDetailsPojo, displayEventPojo);
			displayEventPojo.setGratuityRequired(locationDetailsPojo.getGratuityRequired());
			displayEventPojo.setEventDate(eventDate);
			displayEventPojo.setEventEndTime(removeZero(eventEndTime));
			displayEventPojo.setEventStartTime(removeZero(eventStartTime));
			displayEventPojo.setScheduleDescription(scheduleDescription + " ");
			displayEventPojo.setAdditionalDateInfo(additionalDateInfo);
			displayEventPojo.setDate(date);
			String reservationRequiredText;
			String gratuityText;
			String minAgeText;
			String reservationDetails;
			reservationRequiredText = getReservationText(displayEventPojo);
			gratuityText = getGratutiyText(displayEventPojo);
			minAgeText = getMinAgeText(displayEventPojo);
			reservationDetails = createReservationDetails(displayEventPojo, reservationRequiredText, gratuityText,
					minAgeText);

			displayEventPojo.setReservationDetails(reservationDetails);
			if (StringUtils.isNotEmpty(eventStartTime)) {
				displayEventPojo.setTime(returnTimeInDateFormat(eventStartTime));
			}
			displayEvents.add(displayEventPojo);

		}
		LOGGER.info("method createDisplayEventPojo end");
	}

	private String createReservationDetails(DisplayEventPojo displayEventPojo, String reservationRequiredText,
			String gratuityText, String minAgeText) {
		String reservationDetails = StringUtils.EMPTY;
		if (null != displayEventPojo.getPricingDetails() || null != displayEventPojo.getAdditionalDateInfo()) {
			reservationDetails = displayEventPojo.getPricingDetails() + gratuityText + reservationRequiredText
					+ minAgeText + displayEventPojo.getAdditionalDateInfo();
		}
		return reservationDetails;
	}

	private void createPricingDetails(LocationDetailsPojo locationDetailsPojo, DisplayEventPojo displayEventPojo) {
		if (null != locationDetailsPojo.getPricingAmount() && !locationDetailsPojo.getPricingAmount().isEmpty()
				&& null != locationDetailsPojo.getPricingOption() && null != locationDetailsPojo.getCostInfo()) {
			displayEventPojo.setPricingDetails("$" + locationDetailsPojo.getPricingAmount() + " per "
					+ locationDetailsPojo.getPricingOption() + ". " + locationDetailsPojo.getCostInfo());
		} else {
			displayEventPojo.setPricingDetails("");
		}
	}

	private String getMinAgeText(DisplayEventPojo displayEventPojo) {
		String minAgeText;
		if (displayEventPojo.getMinAge() != null && !"0".equalsIgnoreCase(displayEventPojo.getMinAge())
				&& !displayEventPojo.getMinAge().isEmpty()) {
			minAgeText = "For girls ages" + " " + displayEventPojo.getMinAge() + " " + "and up. ";
		} else {
			minAgeText = "";
		}
		return minAgeText;
	}

	private String getGratutiyText(DisplayEventPojo displayEventPojo) {
		String gratuityText = "";
		if ("true".equalsIgnoreCase(displayEventPojo.getGratuityRequired())
				&& !displayEventPojo.getGratuityRequired().isEmpty()) {
			gratuityText = "(gratuity not included). ";
		}
		return gratuityText;
	}

	private String getReservationText(DisplayEventPojo displayEventPojo) {
		String reservationRequiredText;
		if ("true".equalsIgnoreCase(displayEventPojo.getReservationRequired())
				&& !displayEventPojo.getReservationRequired().isEmpty()) {
			reservationRequiredText = "Reservations required. ";
		} else {
			reservationRequiredText = "";
		}
		return reservationRequiredText;
	}

	/**
	 * @return calcurrentDate
	 */
	private Date getCurrentDate() {
		LOGGER.info("method getCurrentDate start");
		Date currentDate = Calendar.getInstance().getTime();
		OffsetDateTime odt = OffsetDateTime.parse(format.format(currentDate));
		Calendar calcurrentDate = Calendar.getInstance();
		calcurrentDate.setTime(currentDate);
		calcurrentDate.setTimeZone(TimeZone.getTimeZone(odt.getOffset()));
		calcurrentDate.set(Calendar.HOUR_OF_DAY, 0);
		calcurrentDate.set(Calendar.MINUTE, 0);
		calcurrentDate.set(Calendar.SECOND, 0);
		calcurrentDate.set(Calendar.MILLISECOND, 0);
		LOGGER.info("method getCurrentDate end");
		LOGGER.debug("CurrentDate: {} ", calcurrentDate.getTime());
		return calcurrentDate.getTime();
	}

	private String convertTostandardDateFormat(String eventEndTimeVal) {
		LOGGER.info("method convertTostandardDateFormat start");
		StringBuilder sb = new StringBuilder(eventEndTimeVal);
		int i = eventEndTimeVal.lastIndexOf(':');
		sb.setCharAt(i, ' ');
		LOGGER.info("method convertTostandardDateFormat end");
		return sb.toString();

	}

	/**
	 * This method will return time in Date Format
	 * 
	 * @param time
	 * @return date
	 */
	private Date returnTimeInDateFormat(String time) {
		LOGGER.info("method returnTimeInDateFormat start");
		String parseableTime;
		String[] timeSplit = time.split(" ");
		String timeValue = timeSplit[0];
		if (timeSplit[1].equals(Constants.AM)) {
			parseableTime = timeValue + " AM";
		} else {
			parseableTime = timeValue + " PM";
		}
		Date date = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
			date = simpleDateFormat.parse(parseableTime);
		} catch (ParseException pe) {
			LOGGER.error("Parse Exception Occured {}", pe);
		}
		LOGGER.info("method returnTimeInDateFormat end");
		return date;
	}

	public static String removeZero(String str) {
		// Count leading zeros
		int i = 0;
		while (str.charAt(i) == '0')
			i++;
		StringBuilder sb = new StringBuilder(str);
		// The StringBuffer replace function removes
		// i characters from given index (0 here)
		sb.replace(0, i, "");
		String[] timeSplit = sb.toString().split(" ");
		String timeValue = timeSplit[0];
		String meridianValue = timeSplit[1];
		String meridianValueWithChange = meridianValue.substring(0,1) +"."+ meridianValue.substring(1)+".";
		return timeValue + " " + meridianValueWithChange;
	}

	/**
	 * This method will return date in (Month Date,Year) Format
	 * 
	 * @param date
	 * @param eventsDate
	 * @return
	 */
	private String getEventDate(Date date, String eventsDate) {
		LOGGER.info("method getEventDate start");
		OffsetDateTime odt = OffsetDateTime.parse(eventsDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTimeZone(TimeZone.getTimeZone(odt.getOffset()));
		Formatter fmt = new Formatter();
		String eventDate = fmt.format("%tB", cal).toString() + " " + cal.get(Calendar.DATE) + ", "
				+ cal.get(Calendar.YEAR);
		fmt.close();
		LOGGER.info("method getEventDate end");
		return eventDate;
	}

	public PropertyReaderUtils getPropertyReaderUtils() {
		return propertyReaderUtils;
	}

	public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
		this.propertyReaderUtils = propertyReaderUtils;
	}

}
