package com.mattel.global.core.services;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.global.master.core.services.VideoGalleryService;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ PredicateGroup.class, GetResourceResolver.class})
public class VideoGalleryServiceTest {
	@InjectMocks
	private VideoGalleryService videoGalleryService;

	@Mock GetResourceResolver getResourceResolver;
	ResourceResolver resolver;
	QueryBuilder queryBuilder;
	ResourceResolverFactory resolverFactory;
	Resource resource;
	Query pageQuery;
	SearchResult result;
	Session session;
	Hit hit;
	@Mock
	Iterator<Resource> resources;
	@Mock
	Node node;
	@Mock
	TagManager manager;
	@Mock
	Tag tag;

	String [] plpVideos = {"test", "test-1"};



	Map<String, String> querrymap = new HashMap<>();
	List<Hit> listHit = new ArrayList<>();


	public void setUp() throws RepositoryException, LoginException {

	}

	@Test
	public void testTrasform1() throws RepositoryException {
		queryBuilder = Mockito.mock(QueryBuilder.class);
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		resource = Mockito.mock(Resource.class);
		pageQuery = Mockito.mock(Query.class);
		result = Mockito.mock(SearchResult.class);
		session = Mockito.mock(Session.class);
		hit = Mockito.mock(Hit.class);
		resolver = Mockito.mock(ResourceResolver.class);
		pageQuery = Mockito.mock(Query.class);
		videoGalleryService.setQueryBuilder(queryBuilder);
		querrymap.put("path", "/content/dam/fisher-price/videos");
		querrymap.put("type", "dam:Asset");
		querrymap.put("orderby", "@jcr:content/jcr:lastModified"); 
		querrymap.put("orderby.sort", "desc");
		querrymap.put("p.limit", "5000"); 
		querrymap.put("1_property", "jcr:content/metadata/dc:tags");
		querrymap.put("1_property.value", "fp-videos");
		listHit.add(hit);
		listHit.add(hit);
		Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querrymap), session)).thenReturn(pageQuery);
		Mockito.when(pageQuery.getResult()).thenReturn(result);
		Mockito.when(result.getHits()).thenReturn(listHit);
		Mockito.when(hit.getPath()).thenReturn("");
		Mockito.when(resolver.getResource(hit.getPath())).thenReturn(resource);
		Mockito.when(result.getResources()).thenReturn(resources);
		Mockito.when(resources.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resources.next()).thenReturn(resource);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(resolver.isLive()).thenReturn(true);

		videoGalleryService.getVideosByDateCategory("/content/dam/fisher-price/videos", "fp-videos");
	}

	@Test
	public void testGetCategoryId() {
		resolver = Mockito.mock(ResourceResolver.class);
		Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(manager);
		Mockito.when(manager.resolve("category")).thenReturn(tag);
		videoGalleryService.getCategoryId("category");
	}

	@Test
	public void testGetManualVideos() {
		resolver = Mockito.mock(ResourceResolver.class);
		Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resolver);
		videoGalleryService.getManualVideos(plpVideos);
	}
}
