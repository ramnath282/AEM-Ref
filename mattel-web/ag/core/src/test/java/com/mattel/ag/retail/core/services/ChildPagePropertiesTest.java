package com.mattel.ag.retail.core.services;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.eval.JcrPropertyPredicateEvaluator;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.NameConstants;
import com.mattel.ag.retail.core.constants.Constants;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PredicateGroup.class)
public class ChildPagePropertiesTest {

	ResourceResolverFactory resolverFactory;
	ResourceResolver resourceResolver;
	ChildPageProperties childPageProperties;
	Resource resource;
	Session session;
	Query query;
	PredicateGroup predicateGroup;
	SearchResult result;
	Hit hit;
	ValueMap valueMap;

	QueryBuilder queryBuilder;
	Iterator<Resource> resourceIterator;

	String rootPagePath = "/content/ag/en/retail";

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		childPageProperties = new ChildPageProperties();
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		resource = Mockito.mock(Resource.class);
		session = Mockito.mock(Session.class);
		query = Mockito.mock(Query.class);
		predicateGroup = Mockito.mock(PredicateGroup.class);
		result = Mockito.mock(SearchResult.class);
		valueMap = Mockito.mock(ValueMap.class);
		resourceIterator = Mockito.mock(Iterator.class);
		hit = Mockito.mock(Hit.class);
		List<Hit> hits = new ArrayList<Hit>();
		hits.add(hit);
		
		queryBuilder = Mockito.mock(QueryBuilder.class);
		childPageProperties.setRootPagePath(rootPagePath);
		childPageProperties.setResolverFactory(resolverFactory);
		childPageProperties.setQueryBuilder(queryBuilder);
		try {
			Mockito.when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resourceResolver);
			Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
			Map<String, String> querrymap = new HashMap<>();
			querrymap.put("path", rootPagePath);
			querrymap.put("1_property", NameConstants.NN_TEMPLATE);
			querrymap.put("1_property.value", Constants.STOREPAGETEMPLATEPATH);
			querrymap.put("1_property.operation", JcrPropertyPredicateEvaluator.OP_LIKE);
			querrymap.put("p.limit", "-1");
			PowerMockito.mockStatic(PredicateGroup.class);
			Mockito.when(PredicateGroup.create(querrymap)).thenReturn(predicateGroup);
			Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querrymap), session)).thenReturn(query);
			Mockito.when(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
			Mockito.when(query.getResult()).thenReturn(result);
			Mockito.when(result.getHits()).thenReturn(hits);
			Mockito.when(result.getHits()).thenReturn(hits);
			Mockito.when(hit.getProperties()).thenReturn(valueMap);
			Mockito.when(hit.getProperties().get("storeLocation", String.class)).thenReturn("storelocation");
			Mockito.when(hit.getProperties().get("isInternational", Boolean.class)).thenReturn(true);
			Mockito.when(hit.getPath()).thenReturn("anystring");
			Mockito.when(hit.getPath().replaceAll(Constants.SLASH + JcrConstants.JCR_CONTENT, ".html")).thenReturn("anystring");
		} catch (RepositoryException | LoginException e) {
			e.printStackTrace();
		}
		Mockito.when(result.getResources()).thenReturn(resourceIterator);
		Mockito.when(resourceIterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resourceIterator.next()).thenReturn(resource);
		Mockito.when(resourceIterator.next().getResourceResolver()).thenReturn(resourceResolver);
	}

	@Test
	public void getPagesTest() {
		childPageProperties.getpages();
	}

}
