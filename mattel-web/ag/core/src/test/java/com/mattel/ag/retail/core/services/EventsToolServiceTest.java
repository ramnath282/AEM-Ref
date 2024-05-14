package com.mattel.ag.retail.core.services;

import com.day.cq.replication.Replicator;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ag.explore.core.services.GetResourceResolver;
import com.mattel.ag.retail.core.constants.Constants;
import com.mattel.ag.retail.core.pojos.EventPojo;
import com.mattel.ag.retail.core.pojos.LocationDateDetailsPojo;
import com.mattel.ag.retail.core.pojos.LocationDetailsPojo;
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyReaderUtils.class, JcrUtils.class })
public class EventsToolServiceTest {

  EventsToolService eventsToolService;

  GetResourceResolver getResourceResolver;

  Replicator replicator;

  PropertyReaderUtils propertyReaderUtils;

  ResourceResolver resourceResolver;

  Resource resource;

  PageManager pageManager;

  Node node;

  Page page;

  ValueMap vm;

  EventPojo eventPojo = new EventPojo();

  Iterator<Page> locationPagesItr;

  Node eventsNode;

  Property property;

  Value value;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws PathNotFoundException, RepositoryException {
    eventsToolService = new EventsToolService();
    getResourceResolver = Mockito.mock(GetResourceResolver.class);
    replicator = Mockito.mock(Replicator.class);
    propertyReaderUtils = Mockito.mock(PropertyReaderUtils.class);
    resourceResolver = Mockito.mock(ResourceResolver.class);
    eventsToolService.setGetResourceResolver(getResourceResolver);
    eventsToolService.setPropertyReaderUtils(propertyReaderUtils);
    eventsToolService.setReplicator(replicator);
    resource = Mockito.mock(Resource.class);
    pageManager = Mockito.mock(PageManager.class);
    node = Mockito.mock(Node.class);
    page = Mockito.mock(Page.class);
    vm = Mockito.mock(ValueMap.class);
    final NodeIterator nodeItr = Mockito.mock(NodeIterator.class);
    final Tag parentTag = Mockito.mock(Tag.class);
    final Iterator<Tag> tagItr = Mockito.mock(Iterator.class);
    eventsNode = Mockito.mock(Node.class);
    final Session session = Mockito.mock(Session.class);
    locationPagesItr = Mockito.mock(Iterator.class);
    property = Mockito.mock(Property.class);
    PowerMockito.mockStatic(JcrUtils.class);
    value = Mockito.mock(Value.class);
    final List<LocationDetailsPojo> locationDetailsLst = new ArrayList<>();
    final List<LocationDateDetailsPojo> locationDateDetailsLst = new ArrayList<>();
    final LocationDetailsPojo locationDetailsPojo = new LocationDetailsPojo();
    final LocationDateDetailsPojo locationDateDetailsPojo = new LocationDateDetailsPojo();
    eventPojo.setEventId("1");
    eventPojo.setEventTitle("Event Title");
    eventPojo.setEventDescription("Event Description");
    eventPojo.setMinAge("11");
    eventPojo.setReservationRequired("true");
    eventPojo.setKeywords("key1,key2");
    locationDetailsPojo.setStoreTag("ag:retail/storesList/atlanta");
    locationDetailsPojo.setZomatoURL("http://www.zomato.com");
    locationDetailsPojo.setPricingOption("couple");
    locationDetailsPojo.setPricingAmount("11");
    locationDetailsPojo.setStoreName("atlanta");
    locationDetailsPojo.setGratuityRequired("false");
    locationDateDetailsPojo.setScheduleDescription("");
    locationDateDetailsPojo.setEventStartTime("11:00:am");
    locationDateDetailsPojo.setEventEndTime("11:11:am");
    locationDateDetailsPojo.setEventDate("01-23-2019");
    locationDateDetailsPojo.setAdditionalDateInfo("Additional Info");
    locationDateDetailsLst.add(locationDateDetailsPojo);
    locationDetailsPojo.setLocationDateDetails(locationDateDetailsLst);
    locationDetailsLst.add(locationDetailsPojo);
    eventPojo.setLocationDetails(locationDetailsLst);
    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(propertyReaderUtils.getEventsPath()).thenReturn("/content/ag/en/retail/events");
    Mockito.when(resourceResolver.resolve(propertyReaderUtils.getEventsPath()))
        .thenReturn(resource);
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
    Mockito.when(node.getNode(Constants.JCR_CONTENT)).thenReturn(eventsNode);
    Mockito.when(eventsNode.getSession()).thenReturn(session);
    Mockito.when(propertyReaderUtils.getAgStoreTagsPath())
        .thenReturn("/content/cq:tags/ag/retail/storesList");
    Mockito.when(resourceResolver.getResource(propertyReaderUtils.getAgStoreTagsPath()))
        .thenReturn(resource);
    Mockito.when(resource.adaptTo(Tag.class)).thenReturn(parentTag);
    Mockito.when(parentTag.listChildren()).thenReturn(tagItr);
    Mockito.when(resourceResolver.getResource("/content/ag/en/retail/events/event_"))
        .thenReturn(resource);
    Mockito.when(node.getNodes()).thenReturn(nodeItr);
    Mockito.when(nodeItr.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
    Mockito.when(nodeItr.nextNode()).thenReturn(node);
    Mockito.when(nodeItr.nextNode().getName()).thenReturn("");
    Mockito.when(node.addNode("event_1", "cq:Page")).thenReturn(eventsNode);
    Mockito.when(JcrUtils.getNodeIfExists(node, "event_1")).thenReturn(node);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
    Mockito.when(resourceResolver.resolve(node.getPath())).thenReturn(resource);
    Mockito.when(page.adaptTo(Node.class)).thenReturn(node);
    Mockito.when(page.getPath()).thenReturn("/content/ag/en/retail/events");
    Mockito.when(resourceResolver.resolve("/content/ag/en/retail/events")).thenReturn(resource);

    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    Mockito.when(session.isLive()).thenReturn(true);
    Mockito.when(page.listChildren()).thenReturn(locationPagesItr);
    Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(resourceResolver.resolve("/content/ag/en/retail/events")).thenReturn(resource);
    Mockito.when(resourceResolver.resolve("/content/ag/en/retail/events").adaptTo(Page.class))
        .thenReturn(page);
    Mockito.when(page.getParent()).thenReturn(page);
    Mockito.when(page.getParent().listChildren()).thenReturn(locationPagesItr);
  }

  @Test
  public void testCreateEvent() throws PathNotFoundException, RepositoryException {
    Mockito.when(JcrUtils.getNodeIfExists(node, "atlanta")).thenReturn(node);
    eventsToolService.createEvent(eventPojo, "create");
    eventsToolService.getAllAgStoreTags();
  }

  @Test
  public void testCreateEvent2() throws PathNotFoundException, RepositoryException {
    final Node locationNode = Mockito.mock(Node.class);
    final Node propertyNode = Mockito.mock(Node.class);
    final Session session = Mockito.mock(Session.class);
    Mockito.when(JcrUtils.getNodeIfExists(node, "atlanta")).thenReturn(null);
    Mockito.when(node.addNode("atlanta", Constants.PAGE_TYPE)).thenReturn(locationNode);
    Mockito.when(locationNode.addNode(Constants.JCR_CONTENT, Constants.PAGE_CONTENT))
        .thenReturn(propertyNode);
    Mockito
        .when(propertyNode.setProperty(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .thenReturn(Mockito.mock(Property.class));
    Mockito.when(propertyNode.getSession()).thenReturn(session);
    Mockito.doNothing().when(session).save();
    eventsToolService.createEvent(eventPojo, "create");
    eventsToolService.getAllAgStoreTags();
  }

  @Test
  public void testCreateEvent3() throws PathNotFoundException, RepositoryException {
    final Node locationNode = Mockito.mock(Node.class);
    final Node propertyNode = Mockito.mock(Node.class);
    final Session session = Mockito.mock(Session.class);
    final Resource resource = Mockito.mock(Resource.class);
    final Node locationDatePageNode = Mockito.mock(Node.class);
    Mockito.when(JcrUtils.getNodeIfExists(node, "atlanta")).thenReturn(null);
    Mockito.when(node.addNode("atlanta", Constants.PAGE_TYPE)).thenReturn(locationNode);
    Mockito.when(locationNode.addNode(Constants.JCR_CONTENT, Constants.PAGE_CONTENT))
        .thenReturn(propertyNode);
    Mockito.when(locationNode.getPath()).thenReturn("/mockpath");
    Mockito.when(resourceResolver.resolve("/mockpath")).thenReturn(resource);
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(locationDatePageNode);
    Mockito.when(JcrUtils.getNodeIfExists(ArgumentMatchers.any(Node.class), ArgumentMatchers.any()))
        .thenReturn(null);
    Mockito.when(
        locationDatePageNode.addNode(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .thenReturn(locationDatePageNode);
    Mockito
        .when(propertyNode.setProperty(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
        .thenReturn(Mockito.mock(Property.class));
    Mockito.when(propertyNode.getSession()).thenReturn(session);
    Mockito.doNothing().when(session).save();
    eventsToolService.createEvent(eventPojo, "create");
    eventsToolService.getAllAgStoreTags();
  }

  @Test
  public void testCreateEvents() throws PathNotFoundException, RepositoryException {
    eventsToolService.createEvent(eventPojo, "update");
  }

  @Test
  public void testSomething()
      throws ValueFormatException, PathNotFoundException, RepositoryException {
    Mockito.when(node.getNode(Constants.JCR_CONTENT)).thenReturn(node);
    Mockito.when(node.hasProperty("eventDate")).thenReturn(true);
    Mockito.when(node.getProperty("eventDate")).thenReturn(property);
    Mockito.when(node.getProperty("eventDate").getValue()).thenReturn(value);
    Mockito.when(node.getProperty("eventDate").getValue().getString())
        .thenReturn("12-13-14-15-T00:00:00.000Z");
    eventsToolService.getEventTobeUpdated("");
  }

  @Test
  public void deleteEvent() {
    eventsToolService.deleteEvent("/content/ag/en/retail/events/jcr:content");
  }
}
