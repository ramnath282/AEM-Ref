package com.mattel.play.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.VideoTile;
import com.mattel.play.core.utils.PropertyReaderUtils;
import com.mattel.play.core.utils.VideosDamLanguageMapping;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PredicateGroup.class, PlayHelper.class, PropertyReaderUtils.class, VideosDamLanguageMapping.class })
public class VideoGalleryServiceTest {
	VideoGalleryService videoGalleryService;
	ResourceResolver resolver;
	QueryBuilder queryBuilder;
	ResourceResolverFactory resolverFactory;
	Resource resource;
	List<VideoTile> masterList = new ArrayList<>();
	Map<String, String> querrymap = new HashMap<>();
	List<Hit> listHit = new ArrayList<>();
	Query pageQuery;
	SearchResult result;
	Session session;
	Hit hit;
	NodeIterator videoGalleryColumnChildren = null;
	Node node;
	Property property;
	Asset videoAsset = null;
	PageManager pageManager;
	Page page;
	List<String> tags = new ArrayList<>();
	Tag[] categoryTag;
	Tag tag;
	String[] languagemapings;
	Iterator<Resource> resources;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws LoginException, RepositoryException {
		videoGalleryService = new VideoGalleryService();
		resolver = Mockito.mock(ResourceResolver.class);
		queryBuilder = Mockito.mock(QueryBuilder.class);
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		resource = Mockito.mock(Resource.class);
		pageQuery = Mockito.mock(Query.class);
		result = Mockito.mock(SearchResult.class);
		session = Mockito.mock(Session.class);
		hit = Mockito.mock(Hit.class);
		videoGalleryColumnChildren = Mockito.mock(NodeIterator.class);
		node = Mockito.mock(Node.class);
		property = Mockito.mock(Property.class);
		videoAsset = Mockito.mock(Asset.class);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);
		tag = Mockito.mock(Tag.class);
		resources = Mockito.mock(Iterator.class);
		PowerMockito.mockStatic(PredicateGroup.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		PowerMockito.mockStatic(PlayHelper.class);
		PowerMockito.mockStatic(VideosDamLanguageMapping.class);
		videoGalleryService.setQueryBuilder(queryBuilder);
		videoGalleryService.setResolver(resolver);
		videoGalleryService.setResolverFactory(resolverFactory);
		tags.add("abc");
		tags.add("abc");
		querrymap.put("path", "");
		querrymap.put("type", "dam:Asset");
		querrymap.put("orderby", "@jcr:content/jcr:lastModified");
		querrymap.put("orderby.sort", "desc");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		map.put(ResourceResolverFactory.USER, "playserviceuser");
		listHit.add(hit);
		listHit.add(hit);
		categoryTag = new Tag[2];
		categoryTag[0] = tag;
		categoryTag[1] = tag;
		languagemapings = new String[2];
		languagemapings[0] = "abc:abc:abc";
		languagemapings[1] = "abc:abc:abc";
		Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resolver.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querrymap), session)).thenReturn(pageQuery);
		Mockito.when(pageQuery.getResult()).thenReturn(result);
		Mockito.when(result.getHits()).thenReturn(listHit);
		Mockito.when(hit.getPath()).thenReturn("");
		Mockito.when(resolver.getResource(hit.getPath())).thenReturn(resource);
		Mockito.when(resolver.getResource("/videoGalleryColumn1")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.getNodes()).thenReturn(videoGalleryColumnChildren);
		Mockito.when(videoGalleryColumnChildren.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(videoGalleryColumnChildren.nextNode()).thenReturn(node);
		Mockito.when(node.getProperty("videoThumbnail")).thenReturn(property);
		Mockito.when(node.getProperty("videoThumbnail").getString()).thenReturn("");
		Mockito.when(resolver.resolve("category")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Asset.class)).thenReturn(videoAsset);
		Mockito.when(node.getProperty("videoTitle")).thenReturn(property);
		Mockito.when(node.getProperty("videoTitle").getString()).thenReturn("");
		Mockito.when(node.getProperty("videoAltTxt")).thenReturn(property);
		Mockito.when(node.getProperty("videoAltTxt").getString()).thenReturn("");
		Mockito.when(videoAsset.getMetadataValue("dc:tags")).thenReturn("ta/g,ta/g,ta/g");
		Mockito.when(PropertyReaderUtils.getPlayDamPath()).thenReturn("");
		Mockito.when(PlayHelper.getBrandName("")).thenReturn("");
		Mockito.when(PropertyReaderUtils.getDamGlobalPath()).thenReturn("");
		Mockito.when(PlayHelper.getPageLocale("")).thenReturn("");
		Mockito.when(PropertyReaderUtils.getVideoPath()).thenReturn("");
		Mockito.when(PropertyReaderUtils.getPlayDamPath() + PlayHelper.getBrandName("")
				+ PropertyReaderUtils.getDamGlobalPath() + PlayHelper.getPageLocale("")
				+ PropertyReaderUtils.getVideoPath()).thenReturn("");
		Mockito.when(node.hasNode("categoryDetail")).thenReturn(true);
		Mockito.when(node.getNode("categoryDetail")).thenReturn(node);
		Mockito.when(node.getNodes()).thenReturn(videoGalleryColumnChildren);
		Mockito.when(node.getProperty("category")).thenReturn(property);
		Mockito.when(node.getProperty("category").getString()).thenReturn("category");
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(resolver.getResource("category/jcr:content")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(PlayHelper.checkForProperty(node, "seoTitle")).thenReturn("");
		Mockito.when(node.getProperty("category")).thenReturn(property);
		Mockito.when(pageManager.getPage("category")).thenReturn(page);
		Mockito.when(page.getTags()).thenReturn(categoryTag);
		Mockito.when(node.hasNode("metaKeywords")).thenReturn(true);
		Mockito.when(node.getNode("metaKeywords")).thenReturn(node);
		Mockito.when(node.getNodes()).thenReturn(videoGalleryColumnChildren);
		Mockito.when(videoGalleryColumnChildren.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(PlayHelper.checkForProperty(node, "videoThumbnail")).thenReturn("");
		Mockito.when(resolver.resolve("")).thenReturn(resource);
		Mockito.when(PlayHelper.checkForProperty(node, "videoTitle")).thenReturn("");
		Mockito.when(PlayHelper.checkForProperty(node, "videoAltTxt")).thenReturn("");
		Mockito.when(VideosDamLanguageMapping.getLanguageMapping()).thenReturn(languagemapings);
		// Mockito.when(StringUtils.isNotBlank("")).thenReturn(true);
		if (null != resolver && resolver.isLive()) {
			resolver.close();
		}
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.getAncestor(6)).thenReturn(node);
		Mockito.when(node.getAncestor(6).getPath()).thenReturn("");
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource(resource,"jcr:content/metadata")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.hasProperty("dc:name")).thenReturn(true);
		Mockito.when(node.getProperty("dc:name")).thenReturn(property);
		Mockito.when(node.getProperty("dc:name").isMultiple()).thenReturn(true);
		Mockito.when(node.getProperty("")).thenReturn(property);
		Mockito.when(node.getProperty("").getString()).thenReturn("");
		Mockito.when(result.getResources()).thenReturn(resources);
		Mockito.when(resources.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resources.next()).thenReturn(resource);
		Mockito.when(resources.next().getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.isLive()).thenReturn(true);
		Mockito.when(node.getProperty("category")).thenReturn(property);
		Mockito.when(node.getProperty("category").getString()).thenReturn("hello");
		Mockito.when(resolver.getResource("hello/jcr:content")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(pageManager.getPage("hello")).thenReturn(page);
	}

	@Test
	public void getVideosForManual() {
		videoGalleryService.getVideosForManual("");
	}

	@Test
	public void getVideosByDate() {
		videoGalleryService.getVideosByDate("", "", true);
	}

	@Test
	public void getVideoList() throws AccessDeniedException, ItemNotFoundException, RepositoryException, LoginException {
		videoGalleryService.getVideoList("manual", "","");
		videoGalleryService.getVideoList("automatic", "","");
	}

	@Test
	public void getVideoCategoryList() throws RepositoryException {
		videoGalleryService.getVideoCategoryList(node, "allCatLabel","allCatAnalyticsLabel", resolver);
	}

	@Test
	public void getPageLocaleFromMappings() {
		videoGalleryService.getPageLocaleFromMappings("", "");
	}
	
	@Test
	public void testGetPLPVideosManual(){
	  String[] plpVideos = {""};
      videoGalleryService.getPLPVideosManual(plpVideos);
	}
	
}
