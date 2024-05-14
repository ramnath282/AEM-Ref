package com.mattel.ag.retail.core.services;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mattel.ag.retail.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
public class DisplayStoreEventsTest {

  @InjectMocks
  DisplayStoreEvents displayStoreEvents;

  @Mock
  ResourceResolverFactory resourceResolverFactory;

  @Mock
  QueryBuilder queryBuilder;

  @Mock
  PropertyReaderUtils propertyReaderUtils;

  @Mock
  ResourceResolver resourceResolver;

  @Mock
  Session session;

  @Mock
  Query query;

  @Before
  public void setUp() throws Exception {
    Node node = Mockito.mock(Node.class);
    Node parentNode = Mockito.mock(Node.class);
    Node node2 = Mockito.mock(Node.class);
    Node node3 = Mockito.mock(Node.class);
    NodeIterator nodeItr = Mockito.mock(NodeIterator.class);
    NodeIterator nodeItr1 = Mockito.mock(NodeIterator.class);
    NodeIterator nodeItr2 = Mockito.mock(NodeIterator.class);
    SearchResult result = Mockito.mock(SearchResult.class);
    Hit hit = Mockito.mock(Hit.class);

    MemberModifier.field(DisplayStoreEvents.class, "resourceResolverFactory")
        .set(displayStoreEvents, resourceResolverFactory);
    MemberModifier.field(DisplayStoreEvents.class, "queryBuilder").set(displayStoreEvents,
        queryBuilder);
    MemberModifier.field(DisplayStoreEvents.class, "propertyReaderUtils").set(displayStoreEvents,
        propertyReaderUtils);
    MemberModifier.field(DisplayStoreEvents.class, "propertyReaderUtils").set(displayStoreEvents,
        propertyReaderUtils);
    Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.anyMap()))
        .thenReturn(resourceResolver);
    Mockito.when(propertyReaderUtils.getEventsPath()).thenReturn("/content/ag/en/retail/events");
    Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
    Mockito
        .when(
            queryBuilder.createQuery(Mockito.any(PredicateGroup.class), Mockito.any(Session.class)))
        .thenReturn(query);

    Mockito.when(query.getResult()).thenReturn(result);
    List<Hit> hits = new ArrayList<Hit>();
    hits.add(hit);
    Mockito.when(result.getHits()).thenReturn(hits);
    Mockito.when(hit.getNode()).thenReturn(node);
    Mockito.when(node.getParent()).thenReturn(parentNode);
    Mockito.when(parentNode.getNodes()).thenReturn(nodeItr);
    Mockito.when(nodeItr.hasNext()).thenReturn(true, false);
    Mockito.when(nodeItr.nextNode()).thenReturn(node);
    Mockito.when(node.getName()).thenReturn("jcr:content");
    Mockito.when(node.getNodes()).thenReturn(nodeItr1);
    Mockito.when(nodeItr1.hasNext()).thenReturn(true, true, false, true, false);
    Mockito.when(nodeItr1.nextNode()).thenReturn(node2);
    Mockito.when(node2.getName()).thenReturn("02222040-0500pm-0700pm");
    Mockito.when(node2.getNodes()).thenReturn(nodeItr2);
    Mockito.when(nodeItr2.hasNext()).thenReturn(true, false);
    Mockito.when(nodeItr2.nextNode()).thenReturn(node3);
    Mockito.when(node3.getName()).thenReturn("jcr:content");
    Mockito.when(node3.hasProperty("eventDate")).thenReturn(true);
    Mockito.when(node3.hasProperty("eventEndTime")).thenReturn(true);
    Mockito.when(node3.hasProperty("eventStartTime")).thenReturn(true);
    Mockito.when(node3.hasProperty("scheduleDescription")).thenReturn(true);
    Mockito.when(node3.hasProperty("additionalDateInfo")).thenReturn(false);

    Property prop1 = Mockito.mock(Property.class);
    Value value1 = Mockito.mock(Value.class);
    Mockito.when(node3.getProperty("eventDate")).thenReturn(prop1);
    Mockito.when(prop1.getValue()).thenReturn(value1);
    Mockito.when(value1.getString()).thenReturn("2040-02-22T00:00:00.000Z");

    Property prop2 = Mockito.mock(Property.class);
    Value value2 = Mockito.mock(Value.class);
    Mockito.when(node3.getProperty("eventEndTime")).thenReturn(prop2);
    Mockito.when(prop2.getValue()).thenReturn(value2);
    Mockito.when(value2.getString()).thenReturn("07:00:pm");

    Property prop3 = Mockito.mock(Property.class);
    Value value3 = Mockito.mock(Value.class);
    Mockito.when(node3.getProperty("eventStartTime")).thenReturn(prop3);
    Mockito.when(prop3.getValue()).thenReturn(value3);
    Mockito.when(value3.getString()).thenReturn("05:00:pm");

    Property prop4 = Mockito.mock(Property.class);
    Value value4 = Mockito.mock(Value.class);
    Mockito.when(node3.getProperty("scheduleDescription")).thenReturn(prop4);
    Mockito.when(prop4.getValue()).thenReturn(value4);
    Mockito.when(value4.getString())
        .thenReturn("Please book a reservation in our restaurant between 5pm and 7pm.");

    // Mockito.when(node3.getProperty("eventDate").getValue().toString()).thenReturn("2019-02-22T00:00:00.000Z");
  }

  @Test
  public void testGetResourceResolverFactory() {
    Assert.assertEquals(resourceResolverFactory, displayStoreEvents.getResourceResolverFactory());
  }

  @Test
  public void testQueryBuilder() {
    Assert.assertEquals(queryBuilder, displayStoreEvents.getQueryBuilder());
  }

  @Test
  public void testPropertyReaderUtils() {
    Assert.assertEquals(propertyReaderUtils, displayStoreEvents.getPropertyReaderUtils());
  }

  @Test
  public void testGetEventData() {
    displayStoreEvents.getEventsData("ag:retail/storesList/boston");
  }

}
