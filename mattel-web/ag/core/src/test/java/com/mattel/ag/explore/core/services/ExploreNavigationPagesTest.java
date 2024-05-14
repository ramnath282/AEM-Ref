package com.mattel.ag.explore.core.services;

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
import static org.mockito.ArgumentMatchers.any;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.mattel.ag.explore.core.pojos.ExploreNavigationPojo;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PredicateGroup.class)
public class ExploreNavigationPagesTest {

	ResourceResolverFactory resolverFactory;

	QueryBuilder queryBuilder;

	ResourceResolver resolver;

	Session session;

	Query query;

	SearchResult result;

	Hit hit;

	ValueMap valueMap;

	ExploreNavigationPages exploreNavigationPages;

	PredicateGroup predicateGroup;

	String pagePath = "/content/ag/en/explore/crafts";

	String templatePath = "/conf/ag/settings/wcm/templates/article-template-landing-page";

	ExploreNavigationPojo exploreNavigationPojo;

	Iterator<Resource> resourceIterator;
	
	Resource resource;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws LoginException, RepositoryException {
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		result = Mockito.mock(SearchResult.class);
		hit = Mockito.mock(Hit.class);
		List<Hit> hits = new ArrayList<Hit>();
		queryBuilder = Mockito.mock(QueryBuilder.class);
		session = Mockito.mock(Session.class);
		resource = Mockito.mock(Resource.class);
		predicateGroup = Mockito.mock(PredicateGroup.class);
		query = Mockito.mock(Query.class);
		resolver = Mockito.mock(ResourceResolver.class);
		resourceIterator = Mockito.mock(Iterator.class);
		
		exploreNavigationPojo = Mockito.mock(ExploreNavigationPojo.class);
		exploreNavigationPages = new ExploreNavigationPages();
		exploreNavigationPages.setQueryBuilder(queryBuilder);
		exploreNavigationPages.setTemplatePath(templatePath);
		valueMap = Mockito.mock(ValueMap.class);
		exploreNavigationPages.setResolverFactory(resolverFactory);
		Map<String, Object> map = new HashMap<>();
		Map<String, String> querymap = new HashMap<>();
		querymap.put("path", pagePath);
		querymap.put("type", "cq:Page");
		querymap.put("orderby", "@jcr:content/cq:lastModified");
		querymap.put("orderby.sort", "desc");
		querymap.put("property", "jcr:content/cq:template");
		querymap.put("property.value", templatePath);
		querymap.put("p.limit", "-1");
		hits.add(hit);
		PowerMockito.mockStatic(PredicateGroup.class);
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		Mockito.when(resolver.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
		Mockito.when(PredicateGroup.create(querymap)).thenReturn(predicateGroup);
		Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querymap), session)).thenReturn(query);
		Mockito.when(queryBuilder.createQuery(any(PredicateGroup.class), any(Session.class))).thenReturn(query);
		Mockito.when(query.getResult()).thenReturn(result);
		Mockito.when(result.getHits()).thenReturn(hits);
		Mockito.when(hit.getProperties()).thenReturn(valueMap);
		Mockito.when(hit.getProperties().get("jcr:title", String.class)).thenReturn("Title");
		Mockito.when(hit.getPath()).thenReturn("/content/ag/en/explore/crafts");
		exploreNavigationPojo.setPageUrl("/content/ag/en/explore/crafts");
		Mockito.when(result.getResources()).thenReturn(resourceIterator);
		Mockito.when(resourceIterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resourceIterator.next()).thenReturn(resource);
		Mockito.when(resourceIterator.next().getResourceResolver()).thenReturn(resolver);
		
	}

	@Test
	public void testGetpages() {

		exploreNavigationPages.getpages(pagePath);

	}
	@Test 
	public void getResolverFactory(){
		exploreNavigationPages.getResolverFactory();
	}
	
	@Test 
	public void getTemplatePath(){
		exploreNavigationPages.getTemplatePath();
	}

}
