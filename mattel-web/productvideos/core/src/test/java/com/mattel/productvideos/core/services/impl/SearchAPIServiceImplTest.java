package com.mattel.productvideos.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.facets.Facet;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mattel.productvideos.core.constants.Constants;
import com.mattel.productvideos.core.utils.ProductVideosPropertyUtils;
import com.mattel.productvideos.core.utils.ProductVideosUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ProductVideosPropertyUtils.class,ProductVideosUtil.class })
public class SearchAPIServiceImplTest {
    @InjectMocks
    SearchAPIServiceImpl searchAPIServiceImpl;

    SlingHttpServletRequest request;
    ResourceResolver resourceResolver;
    Map<String, Object> requestDetailsMap = new HashMap<>();
    String parametersCount = "7";
    String parametersCountOtherThanSeven = "6";
    Session session;

    @Mock
    QueryBuilder queryBuilder;
    @Mock
    ProductVideosPropertyUtils productVideosPropertyUtils;
    @Mock
    Query query;
    @Mock
    SearchResult result;
    @Mock
    Map<String, Facet> facet;
    @Mock
    Set<Entry<String, Facet>> iterabble;
    @Mock
    Hit hit;
    Node root;
    Node dataPathNode;
    Property fragmentPropty;
    Value fragmentValue;
    Node fragmentDataNode;

    @Before
    public void setUp() throws RepositoryException {
        List<Hit> hits = new ArrayList<>();
        root = Mockito.mock(Node.class);
        dataPathNode = Mockito.mock(Node.class);
        fragmentDataNode = Mockito.mock(Node.class);
        session = Mockito.mock(Session.class);
        request = Mockito.mock(SlingHttpServletRequest.class);
        resourceResolver = Mockito.mock(ResourceResolver.class);
        fragmentPropty = Mockito.mock(Property.class);
        fragmentValue = Mockito.mock(Value.class);
        PowerMockito.mockStatic(ProductVideosPropertyUtils.class);
        PowerMockito.mockStatic(ProductVideosUtil.class);
        Mockito.when(session.getRootNode()).thenReturn(root);
        Mockito.when(root.hasNode(Mockito.anyString())).thenReturn(true);
        Mockito.when(root.hasNode(Mockito.anyString())).thenReturn(true);
        Mockito.when(root.getNode(Mockito.anyString())).thenReturn(dataPathNode);
        Mockito.when(dataPathNode.hasProperty("fragmentPath")).thenReturn(true);
        Mockito.when(dataPathNode.getProperty("fragmentPath")).thenReturn(fragmentPropty);
        Mockito.when(fragmentPropty.getValue()).thenReturn(fragmentValue);
        Mockito.when(fragmentValue.toString()).thenReturn("/content/dam/digital-fragments/jurassic-world/dinosaur-cards/jurassic-video--ankylosaurus/jurassic-video--ankylosaurus");
        Mockito.when(root.getNode(Mockito.anyString())).thenReturn(fragmentDataNode);
        Mockito.when(ProductVideosUtil.getPropertyValue(fragmentDataNode, "Constants.DESC_FIELD")).thenReturn("description");
        requestDetailsMap.put("limit", 20);
        requestDetailsMap.put("offset", 2);
        requestDetailsMap.put("keyword", "jurrasic-world");
        hits.add(hit);
        hits.add(hit);
        Mockito.when(result.getHits()).thenReturn(hits);
        Mockito.when(hit.getPath()).thenReturn("/content/mobile-apps/JurassicWorldFacts/language_masters/en/archive/jurassic-world-dinosaur-battles-ep1-blue-vs-indoraptor/jcr:content");
    }

    @Test
    public void testWithEmptyPath() throws RepositoryException {
        requestDetailsMap.put("path", "");
        requestDetailsMap.put("filter", "jurrasic-world-filter,test");
        requestDetailsMap.put("sort", "N");
        requestDetailsMap.put("sortorder", "desc");
        Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
        Mockito.when(productVideosPropertyUtils.getSitePath()).thenReturn("/content/mobile-apps");
        Mockito.when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
        Mockito.when(query.getResult()).thenReturn(result);
        Mockito.when(result.getFacets()).thenReturn(facet);
        Mockito.when(facet.entrySet()).thenReturn(iterabble);
        Assert.assertNotNull((searchAPIServiceImpl.getData(requestDetailsMap, parametersCount, request)));
    }

    @Test
    public void testWithNonEmptyPath() {
        requestDetailsMap.put("filter", "jurrasic-world-filter");
        requestDetailsMap.put("path", "/JurassicWorldFacts/language_masters/en/gallery");
        requestDetailsMap.put("sort", "R");
        requestDetailsMap.put("sortorder", "desc");
        Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
        Mockito.when(productVideosPropertyUtils.getSitePath()).thenReturn("/content/mobile-apps");
        Mockito.when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
        Mockito.when(query.getResult()).thenReturn(result);
        Assert.assertNotNull((searchAPIServiceImpl.getData(requestDetailsMap, parametersCountOtherThanSeven, request)));
    }

    @Test
    public void testWithSortC() {
        requestDetailsMap.put("filter", "jurrasic-world-filter");
        requestDetailsMap.put("path", "/JurassicWorldFacts/language_masters/en/gallery");
        requestDetailsMap.put("sort", "C");
        requestDetailsMap.put("sortorder", "desc");
        Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
        Mockito.when(productVideosPropertyUtils.getSitePath()).thenReturn("/content/mobile-apps");
        Mockito.when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
        Mockito.when(query.getResult()).thenReturn(result);
        Assert.assertNotNull((searchAPIServiceImpl.getData(requestDetailsMap, parametersCount, request)));
    }

    @Test
    public void testWithoutK() {
        requestDetailsMap.put("filter", "jurrasic-world-filter");
        requestDetailsMap.put("path", "/JurassicWorldFacts/language_masters/en/gallery");
        requestDetailsMap.put("sortorder", "desc");
        Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        Mockito.when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
        Mockito.when(productVideosPropertyUtils.getSitePath()).thenReturn("/content/mobile-apps");
        Mockito.when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
        Mockito.when(query.getResult()).thenReturn(result);
        Assert.assertNotNull((searchAPIServiceImpl.getData(requestDetailsMap, parametersCount, request)));
    }

}
