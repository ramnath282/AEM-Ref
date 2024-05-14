package com.mattel.ag.retail.core.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.google.common.base.Strings;
import com.mattel.ag.explore.core.services.GetResourceResolver;
import com.mattel.ag.retail.core.constants.Constants;
import com.mattel.ag.retail.core.pojos.EventPojo;
import com.mattel.ag.retail.core.pojos.LocationDateDetailsPojo;
import com.mattel.ag.retail.core.pojos.LocationDetailsPojo;
import com.mattel.ag.retail.core.pojos.LocationPrepopulationPojo;
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

@Component(service = EventsToolService.class, immediate = true)
public class EventsToolService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventsToolService.class);

	@Reference
	private GetResourceResolver getResourceResolver;

	@Reference
	private Replicator replicator;

	@Reference
	private PropertyReaderUtils propertyReaderUtils;

	/**
	 * @param event
	 * 
	 *            Method contains logic to create/update new event.
	 */
	public String createEvent(EventPojo event, String createUpdateFlag) {
		LOGGER.info("Method createEvent start");
		LOGGER.debug("Create/Update Flag : {} ", createUpdateFlag);
		String eventId = null;
		Page newEventPage = null;
		ResourceResolver resolver = getResourceResolver.getResourceResolver();
		try {
			if (Objects.nonNull(resolver)) {
				if (createUpdateFlag.equals(Constants.EVENT_CREATE)) {
					eventId = Integer.toString(getMaxCount() + 1);
					setMaxCountToEvents(resolver, eventId);
				} else {
					eventId = event.getEventId();
				}
				LOGGER.debug("Creating/Updating Event{} page", eventId);
				newEventPage = createEventPage(resolver, eventId, createUpdateFlag);
				LOGGER.debug("Event_{} page created/Updated", eventId);
				if (Objects.nonNull(newEventPage)) {
					extractNewEventDetails(event, eventId, newEventPage, resolver);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Exception : {} ", e);
		} finally {
			if (Objects.nonNull(resolver) && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("Method createEvent end");
		return eventId;
	}

    private void extractNewEventDetails(EventPojo event, String eventId, Page newEventPage, ResourceResolver resolver)
            throws RepositoryException {
        Node newNode = newEventPage.adaptTo(Node.class);
        if (Objects.nonNull(newNode)) {
        	Node contentNode = newNode.getNode(Constants.JCR_CONTENT);
        	LOGGER.debug("Updating event with Event Level properties start");
        	contentNode.setProperty(Constants.EVENT_ID, eventId);
        	contentNode.setProperty(Constants.EVENT_TITLE, event.getEventTitle().trim());
        	contentNode.setProperty(Constants.EVENT_DESC, event.getEventDescription().trim());
        	setProperyIfNotNullOrEmpty(contentNode, Constants.EVENT_MINAGE, event.getMinAge());
        	contentNode.setProperty(Constants.EVENT_RESERVATION_REQUIRED, event.getReservationRequired());
        	if (Objects.nonNull(event.getKeywords()) && !Strings.isNullOrEmpty(event.getKeywords())) {
        		String[] keys = event.getKeywords().split(",");
        		for(int i = 0; i < keys.length; i++){
        			keys[i] = keys[i].trim();
        		}
        		contentNode.setProperty(Constants.EVENT_KEYWORDS, keys);
        	}
        	contentNode.getSession().save();
        	LOGGER.debug("Updating event with Event Level properties end");
        }
        List<LocationDetailsPojo> locationDetails = event.getLocationDetails();
        createLocationPages(locationDetails, newEventPage.getPath(), eventId, resolver);
        Session session = resolver.adaptTo(Session.class);
        if (Objects.nonNull(session)) {
            publishUnpublishEvents(session, newEventPage, ReplicationActionType.ACTIVATE);
        }
    }
	
	/**
	 * @param contentNode
	 * @param propertyName
	 * @param propertyValue
	 */
	private void setProperyIfNotNullOrEmpty(Node contentNode, String propertyName, String propertyValue){
		try {
			if(!Strings.isNullOrEmpty(propertyValue)){
				contentNode.setProperty(propertyName, propertyValue);
			}
		} catch (RepositoryException e) {
			LOGGER.error("Error ocured while adding properties {} ", e);
		}
	}

	/**
	 * @param resolver
	 * @param eventId
	 * @param createUpdateFlag
	 * @return
	 */
	private Page createEventPage(ResourceResolver resolver, String eventId, String createUpdateFlag) {
		LOGGER.info("method createEventPage start");
		PageManager pageManager = resolver.adaptTo(PageManager.class);
		Page newEventPage = null;
		Resource newEventResource = null;
		Resource resource = resolver.resolve(propertyReaderUtils.getEventsPath());
		Node eventsNode = resource.adaptTo(Node.class);
		try {

			if ("create".equals(createUpdateFlag)) {
				LOGGER.debug("Requested for Create Page - Start");
				newEventPage = createNewEvent(resolver, eventId, eventsNode);
				LOGGER.debug("Requested for Create Page - End");
			}
			if ("update".equals(createUpdateFlag)) {
				LOGGER.debug("Requested for Update Page - Start");
				Node newEventNode = null;
				Node eventNode = checkNodeExistsOrNot(propertyReaderUtils.getEventsPath(),
						Constants.EVENT_NAME_PREFIX + eventId, resolver);
				if (pageManager != null) {
					if (eventNode != null) {
						deletePage(eventNode, pageManager);
					}
					if (null != eventsNode) {
						newEventNode = eventsNode.addNode(Constants.EVENT_NAME_PREFIX + eventId, Constants.PAGE_TYPE);
					}
					if (null != newEventNode) {
						newEventNode.addNode(Constants.JCR_CONTENT, Constants.PAGE_CONTENT);
						newEventResource = resolver.resolve(newEventNode.getPath());
						newEventPage = newEventResource.adaptTo(Page.class);
					}
				}
				LOGGER.debug("Requested for Update Page - End");
			}
		} catch (RepositoryException e) {
			LOGGER.error("Exception Occured while creating/updating Event Page : {} ", e);
		}
		String newNodeStatusInfo = (null != newEventPage) ? newEventPage.getPath() : "No Page Created";
		LOGGER.debug("New Page : {} ", newNodeStatusInfo);
		LOGGER.info("method createEventPage end");
		return newEventPage;
	}

    /**
     * Create new event using event id under provided event node and returns newly
     * created event node
     * 
     * @param resolver
     *          resource resolver instance
     * @param eventId
     *          event id for new event
     * @param eventsNode
     *          event node under which new event will get created
     * @return
     * @throws RepositoryException
     */
    private Page createNewEvent(ResourceResolver resolver, String eventId, Node eventsNode)
            throws RepositoryException {
        Page newEventPage = null;
        Resource newEventResource;
        Node eventNode = null;
        if (Objects.nonNull(eventsNode)) {
        	eventNode = eventsNode.addNode(Constants.EVENT_NAME_PREFIX + eventId, Constants.PAGE_TYPE);
        }
        if (Objects.nonNull(eventNode)) {
        	eventNode.addNode(Constants.JCR_CONTENT, Constants.PAGE_CONTENT);
        	newEventResource = resolver.resolve(eventNode.getPath());
        	newEventPage = newEventResource.adaptTo(Page.class);
        }
        return newEventPage;
    }

	/**
	 * @param path
	 * @param eventId
	 * @param resolver
	 * @return
	 */
	private Node checkNodeExistsOrNot(String path, String eventId, ResourceResolver resolver) {
		LOGGER.info("method checkNodeExistsOrNot end");
		Resource eventsResource = resolver.resolve(path);
		Node eventNode = null;
		Node eventsNode = eventsResource.adaptTo(Node.class);
		try {
			eventNode = JcrUtils.getNodeIfExists(eventsNode, eventId);
			LOGGER.debug("Node : {} ", eventNode);
		} catch (RepositoryException e) {
			LOGGER.error("Exception caused while getting node : {} ", e);
		}
		LOGGER.info("method checkNodeExistsOrNot end");
		return eventNode;
	}

	/**
	 * @param locationDetails
	 * @param eventPagePath
	 * @param pageManager
	 * 
	 *            Method contains logic to create all location pages under the
	 *            event.
	 */
	private void createLocationPages(List<LocationDetailsPojo> locationDetails, String eventPagePath, String eventId,
			ResourceResolver resolver) {
		LOGGER.info("createLocationPages Start");
		if (!locationDetails.isEmpty() && eventPagePath != null) {
			locationDetails.forEach(location -> {
				Node locationNode = null;
				List<LocationDateDetailsPojo> locationDateDetails = location.getLocationDateDetails();
				String locationName = location.getStoreName();
				try {
					Resource resource = resolver.resolve(eventPagePath);
					Node eventPageNode = resource.adaptTo(Node.class);
					locationNode = createLocationNode(eventId, location, locationName, eventPageNode);
					LOGGER.debug("Location page created, path : {} ", locationNode.getPath());
					createLocationDatePages(locationDateDetails, locationNode.getPath(), resolver);
				} catch (RepositoryException e) {
					LOGGER.error("Exception caused : {} ", e);
				}
			});
		}
		LOGGER.info("createLocationPages End");
	}

    /**
     * Location node will be created under provided event node
     * 
     * @param eventId
     *          eventId for newly creaeted event
     * @param location
     *          location details pojo
     * @param locationName
     *          location name
     * @param eventPageNode
     *          event node under which new location node will be created
     * @return
     * @throws RepositoryException
     */
    private Node createLocationNode(String eventId, LocationDetailsPojo location, String locationName,
        Node eventPageNode) throws RepositoryException {
      Node locationNode = null;
      if (null != eventPageNode) {
        Node node = JcrUtils.getNodeIfExists(eventPageNode, locationName);
        if (null != node) {
          locationNode = node;
        } else {
          locationNode = eventPageNode.addNode(locationName, Constants.PAGE_TYPE);
          if (null != locationNode) {
            Node propertyNode = locationNode.addNode(Constants.JCR_CONTENT, Constants.PAGE_CONTENT);
            setProperyIfNotNullOrEmpty(propertyNode, Constants.EVENT_STORENAME,
                location.getStoreName());
            setProperyIfNotNullOrEmpty(propertyNode, Constants.EVENT_ZOMATOURL,
                location.getZomatoURL());
            setProperyIfNotNullOrEmpty(propertyNode, Constants.EVENT_STORETAG,
                location.getStoreTag());
            setProperyIfNotNullOrEmpty(propertyNode, Constants.EVENT_EVENTID_ATSTORE, eventId);
            setProperyIfNotNullOrEmpty(propertyNode, Constants.EVENT_GRATUITYREQUIRED,
                location.getGratuityRequired());
            setProperyIfNotNullOrEmpty(propertyNode, Constants.EVENT_PRICINGAMOUNT,
                location.getPricingAmount());
            setProperyIfNotNullOrEmpty(propertyNode, Constants.EVENT_PRICINGOPTION,
                location.getPricingOption());
            setProperyIfNotNullOrEmpty(propertyNode, Constants.EVENT_COSTINFO,
                location.getCostInfo());
  
            propertyNode.getSession().save();
            LOGGER.debug("Updating Location with Location Level properties end");
          }
        }
      }
      return locationNode;
    }

	/**
	 * @param locationDateDetails
	 * @param locactionPagePath
	 * @param pageManager
	 */
	private void createLocationDatePages(List<LocationDateDetailsPojo> locationDateDetails, String locactionPagePath,
			ResourceResolver resolver) {
		LOGGER.info("createLocationDatePages Start");
		if (locationDateDetails != null) {
			locationDateDetails.forEach(locationDate -> {
				String date = locationDate.getEventDate();
				String[] spitDate = date.split("-");
				String dateTobeSaved = spitDate[2] + "-" + spitDate[0] + "-" + spitDate[1];
				String eventStartTime = locationDate.getEventStartTime();
				String eventEndTime = locationDate.getEventEndTime();
				String pageNameDate = "";
				if (!Strings.isNullOrEmpty(date)) {
					pageNameDate = date.replace("-", "");
				}

				String pageName = pageNameDate + "-" + eventStartTime.replace(":", "") + "-"
						+ eventEndTime.replace(":", "");
				try {
					createLocationDatePageNode(locactionPagePath, resolver, locationDate, dateTobeSaved,
              eventStartTime, eventEndTime, pageName);
				} catch (RepositoryException e) {
					LOGGER.error("Exception caused while creating dates pages : {} ", e);
				}
			});
		}
		LOGGER.info("createLocationDatePages End");
	}

    /**
     * create date node under particular location
     * 
     * @param locactionPagePath
     *          location page path under which date node will be created
     * @param resolver
     *          resource resolver object
     * @param locationDate
     *          event date details for particular location
     * @param dateTobeSaved
     *          event date
     * @param eventStartTime
     *          event start time
     * @param eventEndTime
     *          event end time
     * @param pageName
     *          event page name
     * @throws RepositoryException
     */
    private void createLocationDatePageNode(String locactionPagePath, ResourceResolver resolver,
        LocationDateDetailsPojo locationDate, String dateTobeSaved, String eventStartTime,
        String eventEndTime, String pageName) throws RepositoryException {
      Node locationDatePageNode = null;
      Resource resource = resolver.resolve(locactionPagePath);
      Node locactionPageNode = resource.adaptTo(Node.class);
      if (null != locactionPageNode) {
        Node node = JcrUtils.getNodeIfExists(locactionPageNode, pageName);
        if (null == node) {
          locationDatePageNode = locactionPageNode.addNode(pageName, Constants.PAGE_TYPE);
        }
      }
      if (locationDatePageNode != null) {
        Node contentNode = locationDatePageNode.addNode(Constants.JCR_CONTENT,
            Constants.PAGE_CONTENT);
        LOGGER.debug("Updating LocationDate with LocationDate Level properties start");
        setProperyIfNotNullOrEmpty(contentNode, Constants.EVENT_DATE,
            dateTobeSaved + "T00:00:00.000Z");
        setProperyIfNotNullOrEmpty(contentNode, Constants.EVENT_STARTTIME, eventStartTime);
        setProperyIfNotNullOrEmpty(contentNode, Constants.EVENT_ENDTIME, eventEndTime);
        setProperyIfNotNullOrEmpty(contentNode, Constants.EVENT_SCHEDULEDESC,
            locationDate.getScheduleDescription());
        setProperyIfNotNullOrEmpty(contentNode, Constants.EVENT_ADDITIONALDATEINFO,
            locationDate.getAdditionalDateInfo());
        contentNode.getSession().save();
        LOGGER.debug("Updating LocationDate with LocationDate Level properties end");
      }
    }

	/**
	 * @param session
	 * @param newEventPage
	 * @param replicationType
	 */
	private void publishUnpublishEvents(Session session, Page newEventPage, ReplicationActionType replicationType) {
		LOGGER.info("Publish/Unpublish Event Page and all Child Pages Start, Replication Type : {} ", replicationType);
		if (session.isLive() && newEventPage != null) {
			publishUnpublishPage(session, newEventPage.getPath(), replicationType);
			Iterator<Page> locationPagesItr = newEventPage.listChildren();
			locationPagesItr.forEachRemaining(locationPage -> {
				publishUnpublishPage(session, locationPage.getPath(), replicationType);
				Iterator<Page> locationDatesItr = locationPage.listChildren();
				locationDatesItr.forEachRemaining(
						datePage -> publishUnpublishPage(session, datePage.getPath(), replicationType));
			});
		}
		LOGGER.info("Publish/Unpublish Event Page End");
	}

	/**
	 * @param session
	 * @param pagePath
	 * @param replicationType
	 */
	private void publishUnpublishPage(Session session, String pagePath, ReplicationActionType replicationType) {
		LOGGER.info("Publish/Unpublish Page {} ", pagePath);
		try {
			replicator.replicate(session, replicationType, pagePath);
		} catch (ReplicationException e) {
			LOGGER.error("Exception caused in Replication :{}", e);
		}
		LOGGER.info("Page Publish/Unpublish");
	}

	/**
	 * @param eventId
	 *            Method include logic to delete node by nodepath
	 */
	public void deleteEvent(String nodePath) {
		LOGGER.info("method deleteEvent start");
		LOGGER.debug("Event to be deleted : {}/event_{}", propertyReaderUtils.getEventsPath(), nodePath);
		if (!Strings.isNullOrEmpty(nodePath)) {
			String nodePathTobeDeleted = nodePath.replace("/jcr:content", "");
			ResourceResolver resolver = getResourceResolver.getResourceResolver();
			try {
				deleteEventNode(nodePathTobeDeleted, resolver);
			} catch (Exception e) {
				LOGGER.error("Exception caused : {} ", e);
			} finally {
				if (resolver != null && resolver.isLive()) {
					resolver.close();
				}
			}
		}
		LOGGER.info("method deleteEvent start");
	}

  /**
   * method contains logic to delete event node using node path
   * 
   * @param nodePathTobeDeleted
   *        node path which is to be deleted
   * @param resolver
   *        resource resolver instance
   * @throws ReplicationException
   * @throws WCMException
   */
  private void deleteEventNode(String nodePathTobeDeleted, ResourceResolver resolver)
      throws ReplicationException, WCMException {
    if (resolver != null) {
    	Session session = resolver.adaptTo(Session.class);
    	PageManager pageManager = resolver.adaptTo(PageManager.class);

    	if (null != pageManager) {
    		Page pageTobeDeactivated = resolver.resolve(nodePathTobeDeleted).adaptTo(Page.class);
    		if (null != pageTobeDeactivated) {
    			LOGGER.debug("Deleting Event Date For the Event");
    			replicator.replicate(session, ReplicationActionType.DEACTIVATE,
    					pageTobeDeactivated.getPath());
    			pageManager.delete(pageTobeDeactivated, false, true);
    			LOGGER.debug("Event node path : {} ", pageTobeDeactivated.getPath());
    			Iterator<Page> parentChildItr = pageTobeDeactivated.getParent().listChildren();
    			if (!parentChildItr.hasNext()) {
    				LOGGER.debug("Deleting Event parent as no dates available for the event");
    				replicator.replicate(session, ReplicationActionType.DEACTIVATE,
    						pageTobeDeactivated.getParent().getPath());
    				pageManager.delete(pageTobeDeactivated.getParent(), false, true);
    			}
    		}
    	}
    }
  }

	/**
	 * @param pageNode
	 * @param pageManager
	 * 
	 *            Delete page and respective child nodes.
	 */
	private void deletePage(Node pageNode, PageManager pageManager) {
		LOGGER.info("method deletePage start");
		try {
			Page pageTobeDeleted = pageManager.getPage(pageNode.getPath());
			pageManager.delete(pageTobeDeleted, false, true);
		} catch (WCMException | RepositoryException e) {
			LOGGER.error("Exception caused while deleting the page : {} ", e);
		}
		LOGGER.info("method deletePage end");
	}

	/**
	 * @param getsetFlag
	 * @return
	 * 
	 * 		This method includes logic to get the 'maxCount'attrbute at
	 *         'Events' page.
	 * 
	 */
	public int getMaxCount() {
		LOGGER.info("method getMaxCount -> genarateEventId start");
		ResourceResolver resolver = getResourceResolver.getResourceResolver();
		int eventId = 0;
		try {
			if (resolver != null) {
				Resource resource = resolver.getResource(propertyReaderUtils.getEventsPath());
				Page eventsPage;
				if (resource != null) {
					LOGGER.debug("Resource Path : {} ", resource.getPath());
					eventsPage = resource.adaptTo(Page.class);
					int currentEventId = 0;
					if (eventsPage != null) {
						ValueMap vm = eventsPage.getProperties();
						currentEventId = Integer.parseInt(vm.get("maxCount").toString());
						LOGGER.debug("Last Created Event Id : {} ", currentEventId);
					}
					eventId = currentEventId;
				}
			}
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.debug("Event Id : {} ", eventId);
		LOGGER.info("method getMaxCount -> genarateEventId end");
		return eventId;
	}

	/**
	 * This method includes logic to set the 'maxCount' attrbite at 'Events'
	 * page.
	 */
	private void setMaxCountToEvents(ResourceResolver resolver, String eventId) {
		LOGGER.info("method setMaxCountToEvents start");
		Resource eventsResource = resolver.resolve(propertyReaderUtils.getEventsPath());
		Node eventsNode = eventsResource.adaptTo(Node.class);
		LOGGER.debug("Max count to be saved : {} ", eventId);
		try {
			if (eventsNode != null) {
				Node contentNode = eventsNode.getNode(Constants.JCR_CONTENT);
				contentNode.setProperty("maxCount", eventId);
				contentNode.getSession().save();
			}
		} catch (RepositoryException e) {
			LOGGER.error("Exception caused while setting maxcount to events node : {} ", e);
		}
		LOGGER.info("method setMaxCountToEvents end");
	}

	/**
	 * @return
	 * 
	 * 		Method contains logic to read all AG retail store pages and
	 *         returns map of 'Page Name -> Store Tag'
	 */
	public List<LocationPrepopulationPojo> getAllAgStoreTags() {
		LOGGER.info("method getAllAgStoreTags start");
		List<LocationPrepopulationPojo> locationPrepopulationLst = new ArrayList<>();
		ResourceResolver resolver = getResourceResolver.getResourceResolver();
		try {
			if (resolver != null) {
				Resource resource = resolver.getResource(propertyReaderUtils.getAgStoreTagsPath());
				if (resource != null) {
					Tag parentTag = resource.adaptTo(Tag.class);
					if (parentTag != null) {
						LOGGER.debug("Parent Namespace : {} ", parentTag.getTagID());
						Iterator<Tag> tagItr = parentTag.listChildren();
						tagItr.forEachRemaining(tag -> {
							LocationPrepopulationPojo locationPrepopulationPojo = new LocationPrepopulationPojo();
							locationPrepopulationPojo.setStoreName(tag.getName());
							locationPrepopulationPojo.setStoreId(tag.getTagID());
							locationPrepopulationLst.add(locationPrepopulationPojo);
						});
					}
				}
			}
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.info("method getAllAgStoreTags end");
		return locationPrepopulationLst;
	}

	/**
	 * @param eventIdEventTobeUpdated
	 * @return
	 * 
	 * Method returns all data for the passes event id for updation
	 * 
	 */
	public EventPojo getEventTobeUpdated(String eventIdEventTobeUpdated) {
		LOGGER.info("GetEventsToUpdate start - Preparing Eventpojo for update prepopulation");
		EventPojo eventToBeUpdated = new EventPojo();
		ResourceResolver resolver = getResourceResolver.getResourceResolver();
		try {
			if (resolver != null) {
				Resource resource = resolver.getResource(propertyReaderUtils.getEventsPath() + "/"
						+ Constants.EVENT_NAME_PREFIX + eventIdEventTobeUpdated);
				if (resource != null) {
					LOGGER.debug("Resource Path : {} ", resource.getPath());
					Node eventNode = resource.adaptTo(Node.class);
					readEventNode(eventToBeUpdated, eventNode);
				}
			}
		} catch (RepositoryException e) {
			LOGGER.error("Exception caused :{}", e);
		} finally {
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
		}
		LOGGER.debug("Event Data to be updated : {} ", eventToBeUpdated);
		LOGGER.info("GetEventsToUpdate end");
		return eventToBeUpdated;
	}
	
	/**
     * @param eventToBeUpdated
     * @param eventNode
     * 
     * Method reads event node data
     */
    private void readEventNode(EventPojo eventToBeUpdated, Node eventNode) throws RepositoryException {
        LOGGER.info("EventsToolService readEventNode Start");
        List<LocationDetailsPojo> eventLocationLst = new ArrayList<>();
        if (eventNode != null) {
            Node eventPropNode = eventNode.getNode(Constants.JCR_CONTENT);
            getEventLevelData(eventPropNode, eventToBeUpdated);
            NodeIterator storeNodeItr = eventNode.getNodes();
            while (storeNodeItr.hasNext()) {
                Node storeNode = storeNodeItr.nextNode();
                if (!storeNode.getName().equals(Constants.JCR_CONTENT)) {
                    LocationDetailsPojo locationDetailsPojo = getEventStoreLevelData(storeNode);
                    eventLocationLst.add(locationDetailsPojo);
                }
            }
            eventToBeUpdated.setLocationDetails(eventLocationLst);
        }
        LOGGER.info("EventsToolService readEventNode End");
    }

	/**
	 * @param eventPropNode
	 * @param eventToBeUpdated
	 */
	private void getEventLevelData(Node eventPropNode, EventPojo eventToBeUpdated) {
		LOGGER.info("method getEventLevelData start");
		try {

			String eventId = eventPropNode.hasProperty("eventId")
					? eventPropNode.getProperty("eventId").getValue().getString() : "";
			eventToBeUpdated.setEventId(eventId);

			String eventTitle = eventPropNode.hasProperty("eventTitle")
					? eventPropNode.getProperty("eventTitle").getValue().getString() : "";
			eventToBeUpdated.setEventTitle(eventTitle);

			String eventDescription = eventPropNode.hasProperty("eventDescription")
					? eventPropNode.getProperty("eventDescription").getValue().getString() : "";
			eventToBeUpdated.setEventDescription(eventDescription);

			String minAge = eventPropNode.hasProperty("minAge")
					? eventPropNode.getProperty("minAge").getValue().getString() : "";
			eventToBeUpdated.setMinAge(minAge);

			String reservationRequired = eventPropNode.hasProperty("reservationRequired")
					? eventPropNode.getProperty("reservationRequired").getValue().getString() : "";
			eventToBeUpdated.setReservationRequired(reservationRequired);

			if (eventPropNode.hasProperty("keywords")) {
				Value[] keywords = eventPropNode.getProperty("keywords").getValues();
				String str = Arrays.toString(keywords);
				eventToBeUpdated.setKeywords(str.replace("[", "").replace("]", ""));
			}

		} catch (IllegalStateException | RepositoryException e) {
			LOGGER.error("Exception caused while reading setEventLevelData properties :{}", e);
		}
		LOGGER.info("method getEventLevelData end");
	}

	/**
	 * @param storeNode
	 * @return
	 */
	private LocationDetailsPojo getEventStoreLevelData(Node storeNode) {
		LOGGER.info("method getEventStoreLevelData start");
		List<LocationDateDetailsPojo> eventLocationDateLst = new ArrayList<>();
		LocationDetailsPojo locationDetailsPojo = new LocationDetailsPojo();
		try {
			Node storePropNode = storeNode.getNode(Constants.JCR_CONTENT);
			if (storePropNode != null) {
				setLocationDetail(locationDetailsPojo, storePropNode);
			}
			LOGGER.debug("method getEventStoreLevelData end");
			NodeIterator storeDatesNodeItr = storeNode.getNodes();
			while (storeDatesNodeItr.hasNext()) {
				Node storeDateNode = storeDatesNodeItr.nextNode();
				if (!storeDateNode.getName().equals(Constants.JCR_CONTENT)) {
					LocationDateDetailsPojo locationDateDetailsPojo = getEventStoreDateLevelData(storeDateNode);
					eventLocationDateLst.add(locationDateDetailsPojo);
				}
			}
			locationDetailsPojo.setLocationDateDetails(eventLocationDateLst);

		} catch (IllegalStateException | RepositoryException e) {
			LOGGER.error("Exception caused while reading properties :{}", e);
		}
		LOGGER.info("method getEventStoreLevelData end");
		return locationDetailsPojo;
	}

    /**
     * Get all location details from store node and prepare location detail pojo
     * 
     * @param locationDetailsPojo
     *          location details will be set to this object
     * @param storePropNode
     *          store node where all details are present for event location
     * @throws RepositoryException
     * @throws ValueFormatException
     * @throws PathNotFoundException
     */
    private void setLocationDetail(LocationDetailsPojo locationDetailsPojo, Node storePropNode)
        throws RepositoryException {
      String locationName = storePropNode.hasProperty("storeName")
          ? storePropNode.getProperty("storeName").getValue().getString() : "";
      locationDetailsPojo.setStoreName(locationName);
  
      String zomatoURL = storePropNode.hasProperty("zomatoURL")
          ? storePropNode.getProperty("zomatoURL").getValue().getString() : "";
      locationDetailsPojo.setZomatoURL(zomatoURL);
  
      String storeTag = storePropNode.hasProperty("storeTag")
          ? storePropNode.getProperty("storeTag").getValue().getString() : "";
      locationDetailsPojo.setStoreTag(storeTag);
  
      String gratuityRequired = storePropNode.hasProperty("gratuityRequired")
          ? storePropNode.getProperty("gratuityRequired").getValue().getString() : "";
      locationDetailsPojo.setGratuityRequired(gratuityRequired);
  
      String pricingAmount = storePropNode.hasProperty("pricingAmount")
          ? storePropNode.getProperty("pricingAmount").getValue().getString() : "";
      locationDetailsPojo.setPricingAmount(pricingAmount);
  
      String pricingTarget = storePropNode.hasProperty("pricingOption")
          ? storePropNode.getProperty("pricingOption").getValue().getString() : "";
      locationDetailsPojo.setPricingOption(pricingTarget);
  
      String costInfo = storePropNode.hasProperty("costInfo")
          ? storePropNode.getProperty("costInfo").getValue().getString() : "";
      locationDetailsPojo.setCostInfo(costInfo);
    }

	/**
	 * @param storeDateNode
	 * @return
	 */
	private LocationDateDetailsPojo getEventStoreDateLevelData(Node storeDateNode) {
		LOGGER.info("method getEventStoreDateLevelData start");
		LocationDateDetailsPojo dateDetailPojo = new LocationDateDetailsPojo();
		try {
			Node storeDatePropNode = storeDateNode.getNode(Constants.JCR_CONTENT);
			if (storeDatePropNode != null) {
				String scheduleDescription = storeDatePropNode.hasProperty("scheduleDescription")
						? storeDatePropNode.getProperty("scheduleDescription").getValue().getString() : "";
				dateDetailPojo.setScheduleDescription(scheduleDescription);

				String eventStartTime = storeDatePropNode.hasProperty("eventStartTime")
						? storeDatePropNode.getProperty("eventStartTime").getValue().getString() : "";
				dateDetailPojo.setEventStartTime(eventStartTime);

				String eventEndTime = storeDatePropNode.hasProperty("eventEndTime")
						? storeDatePropNode.getProperty("eventEndTime").getValue().getString() : "";
				dateDetailPojo.setEventEndTime(eventEndTime);

				String eventDate = storeDatePropNode.hasProperty("eventDate")
						? storeDatePropNode.getProperty("eventDate").getValue().getString() : "";
				String[] spitDate = eventDate.replace("T00:00:00.000Z", "").split("-");
				String dateTobeSaved = spitDate[1] + "-" + spitDate[2] + "-" + spitDate[0];

				dateDetailPojo.setEventDate(dateTobeSaved);

				String additionalDateInfo = storeDatePropNode.hasProperty("additionalDateInfo")
						? storeDatePropNode.getProperty("additionalDateInfo").getValue().getString() : "";

				LOGGER.debug("Location Date DetailsPojo : {} ", dateDetailPojo);
				dateDetailPojo.setAdditionalDateInfo(additionalDateInfo);
			}
		} catch (IllegalStateException | RepositoryException e) {
			LOGGER.error("Exception caused while reading properties :{}", e);
		}
		LOGGER.info("method getEventStoreDateLevelData end");
		return dateDetailPojo;
	}

	public void setGetResourceResolver(GetResourceResolver getResourceResolver) {
		this.getResourceResolver = getResourceResolver;
	}

	public void setReplicator(Replicator replicator) {
		this.replicator = replicator;
	}

	public void setPropertyReaderUtils(PropertyReaderUtils propertyReaderUtils) {
		this.propertyReaderUtils = propertyReaderUtils;
	}
	
}
