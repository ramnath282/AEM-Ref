package com.mattel.ag.retail.core.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
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
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

/**
 * @author CTS
 *
 */
@Component(service = SearchEvents.class, immediate = true)
public class SearchEvents {

	private static final Logger LOGGER = LoggerFactory.getLogger(SearchEvents.class);
	@Reference
	ResourceResolverFactory resourceResolverFactory;

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

	@Reference
	QueryBuilder queryBuilder;
	@Reference
	private PropertyReaderUtils propertyReaderUtils;
	String eventId = "eventId";
	String eventTitle = "eventTitle";
	String storeName = "storeName";
	String keywords = "keywords";
	String jcrContent = "/jcr:content";
	String jcrContentNodeName = "jcr:content";
	String eventDate = "eventDate";
	String valueString = "_value";
	String jcrContentPredicateString = "@jcr:content/";
	String eventIdAtStores = "eventIdAtStore";
	String storeTag = "storeTag";
	String eventStartTimeConst = "eventStartTime";
	String eventEndTimeConst = "eventEndTime";
	SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

	/**
	 * @param searchFilters
	 * @return
	 */
	public List<DisplayEventPojo> getSearchResults(Map<String, String> searchFilters) {
		LOGGER.info("method getSearchResults start");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		ResourceResolver resolver = null;
		List<DisplayEventPojo> list = new ArrayList<>();
		try {
			if (null != resourceResolverFactory) {
				resolver = resourceResolverFactory.getServiceResourceResolver(map);
				Map<String, String> querymap = new HashMap<>();
				querymap.put("type", "cq:Page");
				querymap.put("path", propertyReaderUtils.getEventsPath());
				querymap.put("p.limit", "-1");
				if (!searchFilters.isEmpty()) {
					LOGGER.debug("Search Filter is not null");
					createQueryToGetEvents(querymap, searchFilters);
					Query query = queryBuilder.createQuery(PredicateGroup.create(querymap),
							resolver.adaptTo(Session.class));
					SearchResult result = query.getResult();
					if (null != result) {
						LOGGER.debug("Query results are not null");
						for (Hit hit : result.getHits()) {
							getEvents(searchFilters, resolver, hit, list);
						}
					}
					list.sort((DisplayEventPojo t1, DisplayEventPojo t2) -> Integer.parseInt(t1.getEventId())
							- Integer.parseInt(t2.getEventId()));
				}
				LOGGER.debug("Query map is {}", querymap);

			}

		} catch (LoginException | RepositoryException e) {
			LOGGER.error("Exception caused in Child page properties Service", e);
		} finally {
			LOGGER.info("start of finally in getSearchResults() Method");
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
			LOGGER.info("End of finally in getSearchResults() Method");
		}

		LOGGER.info("method getSearchResults end");
		return list;
	}

	/**
	 * This method get the resource out of the query results to further filter
	 * out the search results
	 * 
	 * @param searchFilters
	 * @param DisplayEventPojo
	 * @param resolver
	 * @param hit
	 * @param resultBasedOnMonth
	 * @param resultBasedOnStoreName
	 * @param resultBasedOnEventId
	 * @throws RepositoryException
	 * @throws ParseException
	 */
	private void getEvents(Map<String, String> searchFilters, ResourceResolver resolver, Hit hit,
			List<DisplayEventPojo> list) throws RepositoryException {
		LOGGER.info("method getEvents start");
		String nodePath = hit.getPath();
		String jcrContentPath = nodePath + jcrContent;
		Resource nodeResource = resolver.getResource(jcrContentPath);
		if (null != nodeResource) {
			filterResultOnTheBasisOfTitleAndKeywords(searchFilters, resolver, hit, list, nodeResource);
		}
		LOGGER.info("method getEvents end");
	}

	/**
	 * This method filters out the search results based on the keywords and
	 * Title
	 * 
	 * @param searchFilters
	 * @param resolver
	 * @param hit
	 * @param list
	 * @param nodeResource
	 * @throws RepositoryException
	 * @throws ParseException
	 */
	private void filterResultOnTheBasisOfTitleAndKeywords(Map<String, String> searchFilters, ResourceResolver resolver,
			Hit hit, List<DisplayEventPojo> list, Resource nodeResource) throws RepositoryException {
		LOGGER.info("method filterResultOnTheBasisOfTitleAndKeywords start");
		ValueMap vm = nodeResource.getValueMap();
		Boolean valueMapHasKeywords = false;
		Boolean valueMapHasEventID = false;
		Boolean valueMapHasEventTitle = false;
		String eventIdValue;
		if (null != searchFilters.get(eventId) && vm.containsKey(eventId)) {
			eventIdValue = vm.get(eventId, String.class);
			if (null != eventIdValue && !eventIdValue.isEmpty()) {
				valueMapHasEventID = vm.containsKey(eventId) && vm.get(eventId).equals(searchFilters.get(eventId));
			}
		}
		if (null != searchFilters.get(eventTitle) && vm.containsKey(eventTitle)) {
			valueMapHasEventTitle = checkIfSearchTitleIsPresent(searchFilters, vm);
		}

		if (null != searchFilters.get(keywords) && vm.containsKey(keywords)) {
			valueMapHasKeywords = searchKeywordsExistInNode(searchFilters, vm);
		}
		if (valueMapHasEventID || valueMapHasEventTitle || valueMapHasKeywords) {
			searchResultsIfEventIsFoundUsingEventLevelProperties(resolver, hit, list, vm);

		} else if (vm.containsKey(storeTag)) {
			getEventsBasedOnStoreTag(searchFilters, resolver, list, nodeResource, vm);
		}
		LOGGER.info("method filterResultOnTheBasisOfTitleAndKeywords end");
	}

	private Boolean checkIfSearchTitleIsPresent(Map<String, String> searchFilters, ValueMap vm) {
		String eventTitleValue;
		Boolean valueMapHasEventTitle = false;
		eventTitleValue = vm.get(eventTitle, String.class);
		if (null != searchFilters.get(eventTitle) && null != eventTitleValue && !eventTitleValue.isEmpty()) {
			valueMapHasEventTitle = vm.containsKey(eventTitle)
					&& eventTitleValue.contains(searchFilters.get(eventTitle));
		}
		return valueMapHasEventTitle;
	}

	private void getEventsBasedOnStoreTag(Map<String, String> searchFilters, ResourceResolver resolver,
			List<DisplayEventPojo> list, Resource nodeResource, ValueMap vm)
			throws RepositoryException {
		String storeTagValue;
		storeTagValue = vm.get(storeTag, String.class);
		if (null != storeTagValue && !storeTagValue.isEmpty() && storeTagValue.equals(searchFilters.get(storeTag))) {
			getEventUsingStoreNode(nodeResource, resolver, searchFilters, list);
		}
	}

	/**
	 * @param searchFilters
	 * @param vm
	 * @return
	 */
	private Boolean searchKeywordsExistInNode(Map<String, String> searchFilters, ValueMap vm) {
		LOGGER.info("method searchKeywordsExistInNode start");
		String keywordsStrings = searchFilters.get(keywords);
		String[] keywordsVal = vm.get(keywords, String[].class);
		Boolean valueMapHasKeywords = false;
		List<String> keywordsListInSearchFilter = Arrays.asList(keywordsStrings.split(","));
		if (null != keywordsVal) {
			LOGGER.debug("keywords in the Event Node: {} ", keywordsVal);
			List<String> keywordsListInEvent = Arrays.asList(keywordsVal);
			Collection<String> listOne = new ArrayList<>(keywordsListInSearchFilter);
			Collection<String> listTwo = new ArrayList<>(keywordsListInEvent);
			listTwo.retainAll(listOne);
			if (!listTwo.isEmpty()) {
				LOGGER.debug("keyword match found");
				valueMapHasKeywords = true;
			} else {
				LOGGER.debug("No keyword match found");
				valueMapHasKeywords = false;
			}
		}
		LOGGER.info("method searchKeywordsExistInNode end");
		return valueMapHasKeywords;
	}

	private void searchResultsIfEventIsFoundUsingEventLevelProperties(ResourceResolver resolver, Hit hit,
			List<DisplayEventPojo> list, ValueMap vm) throws RepositoryException {
		LOGGER.info("method searchResultsIfEventIsFoundUsingEventLevelProperties start");
		Node eventNode = hit.getNode();
		String eventIds;
		String eventTitles;
		String eventStoreName = StringUtils.EMPTY;
		NodeIterator eventNodeIterator = eventNode.getNodes();
		while (eventNodeIterator.hasNext()) {
			Node storeNode = eventNodeIterator.nextNode();
			if (!storeNode.getName().equalsIgnoreCase(jcrContentNodeName)) {
				LOGGER.debug("Store Nodes found in the Current Event Node");
				eventIds = vm.get("eventId").toString();
				eventTitles = vm.get("eventTitle").toString();
				String storeNodePath = storeNode.getPath();
				Resource storeNodeResource = resolver.getResource(storeNodePath + jcrContent);
				if (null != storeNodeResource) {
					ValueMap storeNodeResourceValuMap = storeNodeResource.getValueMap();
					eventStoreName = storeNodeResourceValuMap.get("storeName", String.class);
				}
				NodeIterator storeNodeIterator = storeNode.getNodes();
				while (storeNodeIterator.hasNext()) {
					getDateDetailPropertities(resolver, list, storeNodeIterator, eventIds, eventTitles, eventStoreName);
				}

			}

		}
		LOGGER.info("method searchResultsIfEventIsFoundUsingEventLevelProperties end");
	}

	private void getDateDetailPropertities(ResourceResolver resolver, List<DisplayEventPojo> list,
			NodeIterator storeNodeIterator, String eventId2, String eventTitle2, String eventStoreName)
			throws RepositoryException {
		LOGGER.info("method getDateDetailPropertities start");
		Node dateDetailsNode = storeNodeIterator.nextNode();
		DisplayEventPojo displayEventPojo = new DisplayEventPojo();
		if (!dateDetailsNode.getName().equalsIgnoreCase(jcrContentNodeName)) {
			LOGGER.debug("Date Details found at the Store Level");
			String dateDetailsNodePath = dateDetailsNode.getPath();
			Resource dateDetailsNodeResource = resolver.getResource(dateDetailsNodePath + jcrContent);
			if (null != dateDetailsNodeResource) {
				LOGGER.debug("dateDetailsNodeResource is not null");
				ValueMap dateDetailsNodeValueMap = dateDetailsNodeResource.getValueMap();
				String eventDateVal = dateDetailsNodeValueMap.get(eventDate, String.class);
				String eventStartTimeValue = dateDetailsNodeValueMap.get(eventStartTimeConst, String.class);
				String eventEndTimeValue = dateDetailsNodeValueMap.get(eventEndTimeConst, String.class);
				if (null != eventStartTimeValue && !eventStartTimeValue.isEmpty()) {
					LOGGER.debug("EventStartTime Value at the DateDetails level in a event : {}", eventStartTimeValue);
					String eventStartTime = convertTostandardDateFormat(eventStartTimeValue);
					displayEventPojo.setEventStartTime(eventStartTime);
				}
				if (null != eventEndTimeValue && !eventEndTimeValue.isEmpty()) {
					LOGGER.debug("eventEndTimeValue Value at the DateDetails level in a event : {}", eventEndTimeValue);
					String eventEndTime = convertTostandardDateFormat(eventEndTimeValue);
					displayEventPojo.setEventEndTime(eventEndTime);
				}
				String eventDateValInDispayFormat = getFormattedDate(eventDateVal);
				String eventDateDetailNodePath = dateDetailsNodeResource.getPath();
				displayEventPojo.setEventDate(eventDateValInDispayFormat);
				displayEventPojo.setEventDateDetailNodePath(eventDateDetailNodePath);
				displayEventPojo.setEventId(eventId2);
				displayEventPojo.setEventTitle(eventTitle2);
				displayEventPojo.setLocationName(eventStoreName);
				list.add(displayEventPojo);
			}
		}
		LOGGER.info("method getDateDetailPropertities end");
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
	 * @param eventNodeResource
	 * @param searchFilters
	 * @param storeName2
	 * @param list
	 * @param dateDetailsNodeResource
	 */
    private void getEventDetails(Resource eventNodeResource, Map<String, String> searchFilters, String storeName2,
            List<DisplayEventPojo> list, Resource dateDetailsNodeResource) {
        LOGGER.info("method getEventDetails start");
        ValueMap vm;
        vm = eventNodeResource.getValueMap();
        String eventIDVal = Optional.ofNullable(vm.get(eventId, String.class)).orElse(StringUtils.EMPTY);
        String eventTtileVal = Optional.ofNullable(vm.get(eventTitle, String.class)).orElse(StringUtils.EMPTY);
        String[] keywordsVal = Optional.ofNullable(vm.get(keywords, String[].class)).orElse(null);
        Boolean eventTitleInSearchFilter;
        Map<String, String> eventNodeData = new HashMap<>();
        eventNodeData.put(storeName, storeName2);
        eventNodeData.put("eventIDVal", eventIDVal);
        eventNodeData.put("eventTtileVal", eventTtileVal);

        if (searchFilters.containsKey(eventTitle)) {
            eventTitleInSearchFilter = searchFilters.containsKey(eventTitle) && Objects.nonNull(eventTtileVal)
                    && eventTtileVal.contains(searchFilters.get(eventTitle));
            if (eventTitleInSearchFilter) {
                if (searchFilters.containsKey(keywords)) {
                    getResultsIfKeywordFound(searchFilters, list, keywordsVal, dateDetailsNodeResource, eventNodeData);
                } else {
                    createSearchResultSet(list, dateDetailsNodeResource, eventNodeData);
                }

            }
        } else if (searchFilters.containsKey(keywords)) {
            getResultsIfKeywordFound(searchFilters, list, keywordsVal, dateDetailsNodeResource, eventNodeData);
        }

        else if (!searchFilters.containsKey(eventTitle) && !searchFilters.containsKey(keywords)) {
            createSearchResultSet(list, dateDetailsNodeResource, eventNodeData);
        }
        LOGGER.info("method getEventDetails end");
    }

    private void getResultsIfKeywordFound(Map<String, String> searchFilters, List<DisplayEventPojo> list,
            String[] keywordsVal, Resource dateDetailsNodeResource, Map<String, String> eventNodeData) {
		LOGGER.info("method getResultsIfKeywordFound start");
		String keywordsStrings = searchFilters.get(keywords);
		List<String> keywordsListInSearchFilter = Arrays.asList(keywordsStrings.split(","));
		if (null != keywordsVal && keywordsVal.length > 0) {
			List<String> keywordsListInEvent = Arrays.asList(keywordsVal);
			Collection<String> listOne = new ArrayList<>(keywordsListInSearchFilter);
			Collection<String> listTwo = new ArrayList<>(keywordsListInEvent);
			listTwo.retainAll(listOne);
			if (!listTwo.isEmpty()) {
				createSearchResultSet(list, dateDetailsNodeResource, eventNodeData);
			}
		}
		LOGGER.info("method getResultsIfKeywordFound end");
	}

	/**
	 * @param list
	 * @param dateDetailsNodeResource
	 * @param eventNodeData
	 */
    private void createSearchResultSet(List<DisplayEventPojo> list, Resource dateDetailsNodeResource,
            Map<String, String> eventNodeData) {
        LOGGER.info("method createSearchResultSet start");
        String eventDateValInDispayFormat = StringUtils.EMPTY;
        String eventDateDetailNodePath = StringUtils.EMPTY;
        String eventStartTime = StringUtils.EMPTY;
        String eventEndTime = StringUtils.EMPTY;
        String eventStartTimeVal;
        String eventEndTimeVal;
        if (null != dateDetailsNodeResource) {
            ValueMap dateDetailsNodeValueMap = dateDetailsNodeResource.getValueMap();
            String eventDateVal = dateDetailsNodeValueMap.get(eventDate, String.class);
            eventDateValInDispayFormat = getFormattedDate(eventDateVal);
            eventDateDetailNodePath = dateDetailsNodeResource.getPath();
            eventStartTimeVal = dateDetailsNodeValueMap.get(eventStartTimeConst, String.class);
            eventStartTime = getEventTimeValue(eventStartTimeVal);
            eventEndTimeVal = dateDetailsNodeValueMap.get(eventEndTimeConst, String.class);
            eventEndTime = getEventTimeValue(eventEndTimeVal);

        }
        DisplayEventPojo displayEventPojo = new DisplayEventPojo();
        displayEventPojo.setLocationName(eventNodeData.get(storeName));
        displayEventPojo.setEventDate(eventDateValInDispayFormat);
        displayEventPojo.setEventId(eventNodeData.get("eventIDVal"));
        displayEventPojo.setEventTitle(eventNodeData.get("eventTtileVal"));
        displayEventPojo.setEventDateDetailNodePath(eventDateDetailNodePath);
        displayEventPojo.setEventStartTime(eventStartTime);
        displayEventPojo.setEventEndTime(eventEndTime);
        list.add(displayEventPojo);
        LOGGER.info("method createSearchResultSet end");
    }

	/**
	 * @param nodeResource
	 * @param resolver
	 * @param searchFilters
	 * @param list
	 * @throws RepositoryException
	 * @throws ParseException
	 */
    private void getEventUsingStoreNode(Resource nodeResource, ResourceResolver resolver,
            Map<String, String> searchFilters, List<DisplayEventPojo> list) throws RepositoryException {
		LOGGER.info("method getEventUsingStoreNode start");
		Resource parentNode = nodeResource.getParent();
		if (null != parentNode) {
			getDateDetailNodeUsingStoreNode(resolver, searchFilters, list, parentNode);

		}
		LOGGER.info("method getEventUsingStoreNode end");
	}

    private void getDateDetailNodeUsingStoreNode(ResourceResolver resolver, Map<String, String> searchFilters,
            List<DisplayEventPojo> list, Resource parentNode) throws RepositoryException {
		LOGGER.info("method getDateDetailNodeUsingStoreNode start");
		String storeNode = parentNode.getPath() + jcrContent;
		Resource storeNodeResource = resolver.getResource(storeNode);
		String storeNameVal = StringUtils.EMPTY;
		if (null != storeNodeResource) {
			storeNameVal = getStoreNameValue(storeNodeResource);
		}
		Node store = parentNode.adaptTo(Node.class);
		if (null != store) {
			iteratingDateDetailsNode(resolver, searchFilters, list, parentNode, storeNameVal, store);
		}
		LOGGER.info("method getDateDetailNodeUsingStoreNode end");
	}

    private void iteratingDateDetailsNode(ResourceResolver resolver, Map<String, String> searchFilters,
            List<DisplayEventPojo> list, Resource parentNode, String storeNameVal, Node store)
            throws RepositoryException {
		LOGGER.info("method iteratingDateDetailsNode start");
		NodeIterator storeNodeIterator = store.getNodes();
		while (storeNodeIterator.hasNext()) {
			Node dateDetailsNode = storeNodeIterator.nextNode();
			if (!dateDetailsNode.getName().equalsIgnoreCase(jcrContentNodeName)) {
				String dateDetailsNodePath = dateDetailsNode.getPath();
				Resource dateDetailsNodeResource = resolver.getResource(dateDetailsNodePath + jcrContent);
				Resource superParentNode = parentNode.getParent();
				if (null != superParentNode) {
					createEventDetails(resolver, searchFilters, storeNameVal,
							 superParentNode, list,dateDetailsNodeResource);
				}
			}

		}
		LOGGER.info("method iteratingDateDetailsNode end");
	}

	private String getEventTimeValue(String eventStartTimeVal) {
		String eventStartTime = StringUtils.EMPTY;
		if (null != eventStartTimeVal && !eventStartTimeVal.isEmpty()) {
			eventStartTime = convertTostandardDateFormat(eventStartTimeVal);
		}
		return eventStartTime;
	}

	private String getFormattedDate(String eventDateVal) {
		LOGGER.info("method getEventDate start");
		String formattedEventDate = StringUtils.EMPTY;
		try {
			OffsetDateTime odt = OffsetDateTime.parse(eventDateVal);
			SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.NODE_DATE_FORMAT);
			Date date = dateFormat.parse(eventDateVal);
			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(date);
			currentDate.setTimeZone(TimeZone.getTimeZone(odt.getOffset()));
			Formatter fmt = new Formatter();
			formattedEventDate = fmt.format("%tB", currentDate).toString() + " " + currentDate.get(Calendar.DATE) + ", "
					+ currentDate.get(Calendar.YEAR);
			fmt.close();

		} catch (ParseException pe) {
			LOGGER.error("Parse Exception {}", pe);
		}
		LOGGER.info("method getEventDate end");
		return formattedEventDate;
	}

    private void createEventDetails(ResourceResolver resolver, Map<String, String> searchFilters, String storeNameVal,
            Resource superParentNode, List<DisplayEventPojo> list, Resource dateDetailsNodeResource) {
		LOGGER.info("method createEventDetails start");
		String eventNodePath = superParentNode.getPath() + jcrContent;
		Resource eventNodeResource = resolver.getResource(eventNodePath);
		if (null != eventNodeResource) {
			getEventDetails(eventNodeResource, searchFilters, storeNameVal, list, dateDetailsNodeResource);
		}
		LOGGER.info("method createEventDetails end");
	}

	private String getStoreNameValue(Resource storeNodeResource) {
		LOGGER.info("method getStoreNameValue start");
		ValueMap storeNodeValueMap = storeNodeResource.getValueMap();
		String storeNameVal = StringUtils.EMPTY;
		if (storeNodeValueMap.containsKey(storeName) && null != storeNodeValueMap.get(storeName).toString()) {
			storeNameVal = storeNodeValueMap.get(storeName).toString();
		}
		LOGGER.info("method getStoreNameValue start");
		return storeNameVal;
	}

	/**
	 * This method will create query based on the search parameters.
	 * 
	 * @param querymap
	 * @param searchFilters
	 */
	private void createQueryToGetEvents(Map<String, String> querymap, Map<String, String> searchFilters) {
		LOGGER.info("Start createQueryToGetEvents() Method ");
		if (searchFilters.containsKey(eventId) && !searchFilters.containsKey(storeTag)) {
			LOGGER.debug("searchFilters contains Key eventId ");
			querymap.put("1_property", jcrContentPredicateString + eventId);
			querymap.put("1_property.value", searchFilters.get(eventId));
			createQueryMapForTitleAndKeywords(querymap, searchFilters);
		}
		if (!searchFilters.containsKey(eventId) && searchFilters.containsKey(storeTag)) {
			LOGGER.debug("searchFilters contains Key storeTag ");
			querymap.put("4_property", jcrContentPredicateString + storeTag);
			querymap.put("4_property.value", searchFilters.get(storeTag));
		}
		if (!searchFilters.containsKey(eventId) && !searchFilters.containsKey(storeTag)) {
			createQueryMapForTitleAndKeywords(querymap, searchFilters);
		}
		if (searchFilters.containsKey(eventId) && searchFilters.containsKey(storeTag)) {
			LOGGER.debug("searchFilters contains Key eventId & storeTag ");
			querymap.put("5_property", jcrContentPredicateString + eventIdAtStores);
			querymap.put("5_property.value", searchFilters.get(eventId));
			querymap.put("6_property", jcrContentPredicateString + storeTag);
			querymap.put("6_property.value", searchFilters.get(storeTag));
		}

		LOGGER.debug("Query map is {}", querymap);
		LOGGER.info("End createQueryToGetEvents() Method ");
	}

	/**
	 * This method builds the query map for Title and keywords for querying
	 * purposes
	 * 
	 * @param querymap
	 * @param searchFilters
	 */
	private void createQueryMapForTitleAndKeywords(Map<String, String> querymap, Map<String, String> searchFilters) {
		LOGGER.info("Start createQueryMapForTitleAndKeywords() Method ");
		if (searchFilters.containsKey(eventTitle)) {
			LOGGER.debug("searchFilters contains Key eventTitle ");
			querymap.put("2_property", jcrContentPredicateString + eventTitle);
			querymap.put("2_property.value", "%" + searchFilters.get(eventTitle) + "%");
			querymap.put("2_property.operation", "like");
		}
		if (searchFilters.containsKey(keywords)) {
			LOGGER.debug("searchFilters contains Key keywords ");
			querymap.put("3_property", jcrContentPredicateString + keywords);
			String keywordsStrings = searchFilters.get(keywords);
			List<String> keywordsList = Arrays.asList(keywordsStrings.split(","));
			for (int index = 1; index <= keywordsList.size(); index++) {
				querymap.put("3_property." + index + valueString, keywordsList.get(index - 1));
			}
		}
		LOGGER.info("End createQueryMapForTitleAndKeywords() Method ");
	}

	public PropertyReaderUtils getPropertyReaderUtils() {
		return propertyReaderUtils;
	}

	public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
		this.propertyReaderUtils = propertyReaderUtils;
	}

}
