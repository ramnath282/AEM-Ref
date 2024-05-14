package com.mattel.play.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.settings.SlingSettingsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.CategoryFilterPojo;
import com.mattel.play.core.pojos.CategoryPojo;
import com.mattel.play.core.pojos.SiteNavigationPojo;
import com.mattel.play.core.pojos.TilePojo;
import com.mattel.play.core.utils.PropertyReaderUtils;


@RunWith(PowerMockRunner.class)
@PrepareForTest({PlayHelper.class, PropertyReaderUtils.class, PredicateGroup.class, Tag.class, StringUtils.class , Page.class})
public class TileGalleryAndLandingServiceTest {

	TileGalleryAndLandingService tileGalleryAndLandingService;
	SlingSettingsService slingSettingsService;
	ResourceResolverFactory resolverFactory;
	QueryBuilder queryBuilder;
	ResourceResolver resolver;
	Session session;
	Iterator<CategoryFilterPojo> iterator;
	Iterator<Page> rootPageIterator;
	LinkedList<TilePojo> masterList = new LinkedList<>();
	List<CategoryPojo> categoryItemsList = new ArrayList<>();
	Map<String, Object> map = new HashMap<>();
	Map<String, Object> map1 = new HashMap<>();
	Map<String, ValueMap> multifieldProperty = new HashMap<>();
	List<CategoryFilterPojo> catItemsList = new ArrayList<>();
	CategoryFilterPojo catPojo = new CategoryFilterPojo();
	List<TilePojo> nodeList1 = new ArrayList<>();
	List<TilePojo> nodeList2 = new ArrayList<>();
	List<TilePojo> nodeList3 = new ArrayList<>();
	List<Hit> listHit = new ArrayList<>();
	TilePojo tilePojo = new TilePojo();
	Map<String, String> querrymap = new HashMap<>();

	HashMap<String, String> childNavMap = new HashMap<>();

	Map.Entry<String, ValueMap> entry;
	PageManager pageManager;
	Page page;
	Node node;
	Resource resource;
	NodeIterator nodeIterator;
	Property property;
	Query query;
	SearchResult result;
	Hit hit;
	Tag tag;
	ValueMap valueMap;
	Iterator<Resource> resources;
	SiteNavigationPojo sitePojo = new SiteNavigationPojo();
	PageFilter pageFilter;
	Object obj;
	TagManager tagManager;


	@SuppressWarnings("unchecked")
	@Before

	public void setUp() throws Exception {
		tileGalleryAndLandingService = new TileGalleryAndLandingService();
		slingSettingsService = Mockito.mock(SlingSettingsService.class);
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		queryBuilder = Mockito.mock(QueryBuilder.class);
		resolver = Mockito.mock(ResourceResolver.class);
		session = Mockito.mock(Session.class);
		iterator = Mockito.mock(Iterator.class);
		rootPageIterator = Mockito.mock(Iterator.class);
		pageManager = Mockito.mock(PageManager.class);
		node = Mockito.mock(Node.class);
		resource = Mockito.mock(Resource.class);
		nodeIterator = Mockito.mock(NodeIterator.class);
		resources = Mockito.mock(Iterator.class);
		tagManager = Mockito.mock(TagManager.class);
		PowerMockito.mockStatic(PlayHelper.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		PowerMockito.mockStatic(PredicateGroup.class);
		PowerMockito.mockStatic(Tag.class);
		PowerMockito.mockStatic(Page.class);
		page = Mockito.mock(Page.class);
		PowerMockito.mockStatic(StringUtils.class);
		tag = Mockito.mock(Tag.class);
		property = Mockito.mock(Property.class);
		query = Mockito.mock(Query.class);
		result = Mockito.mock(SearchResult.class);
		hit = Mockito.mock(Hit.class);
		valueMap = Mockito.mock(ValueMap.class);
		entry = Mockito.mock(Map.Entry.class);
		pageFilter = Mockito.mock(PageFilter.class);
		obj = Mockito.mock(Object.class);

		//pageFilter = new PageFilter();
		tileGalleryAndLandingService.setQueryBuilder(queryBuilder);
		tileGalleryAndLandingService.setResolver(resolver);
		tileGalleryAndLandingService.setResolverFactory(resolverFactory);
		tileGalleryAndLandingService.setSession(session);
		tileGalleryAndLandingService.setSlingSettingsService(slingSettingsService);
		catItemsList.add(catPojo);
		listHit.add(hit);
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		map.put(ResourceResolverFactory.USER, "playserviceuser");
		map1.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		multifieldProperty.put("thumbnailImage", valueMap);
		multifieldProperty.put("thumbnailImage", valueMap);
		multifieldProperty.put("thumbnailImage", valueMap);
		sitePojo.setName("");
		childNavMap.put("redirectPath", "redirectPath");
		childNavMap.put("redirectTargetOption", "redirectTargetOption");
		childNavMap.put("path", "path");
		childNavMap.put("currentPath", "currentPath");
		Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
		Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage(catPojo.getCategoryPath())).thenReturn(page);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resolver.getResource("").adaptTo(Node.class)).thenReturn(node);
		Mockito.when(resolver.getResource("/columnOne")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
		Mockito.when(node.getNodes()).thenReturn(nodeIterator);
		Mockito.when(nodeIterator.nextNode()).thenReturn(node);
		Mockito.when(nodeIterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(PlayHelper.checkForProperty(node, "tileThumbnail")).thenReturn("");
		Mockito.when(PlayHelper.checkForProperty(node, "descLineOne")).thenReturn("");
		Mockito.when(PlayHelper.checkForProperty(node, "descLineTwo")).thenReturn("");
		Mockito.when(PlayHelper.checkForProperty(node, "detailpageMapping")).thenReturn("");
		Mockito.when(pageManager.getPage("")).thenReturn(page);
		Mockito.when(PlayHelper.checkForProperty(node, "tileAltTxt")).thenReturn("");
		Mockito.when(node.getProperty("tileThumbnail")).thenReturn(property);
		Mockito.when(node.getProperty("tileThumbnail").getString()).thenReturn("tileThumbnail");
		Mockito.when(node.getProperty("tileAltTxt")).thenReturn(property);
		Mockito.when(node.getProperty("tileAltTxt").getString()).thenReturn("tileThumbnail");
		Mockito.when(PropertyReaderUtils.getCharacterResourceType()).thenReturn("characters");
		Mockito.when(PropertyReaderUtils.getGameResourceType()).thenReturn("games");
		Mockito.when(PropertyReaderUtils.getProductResourceType()).thenReturn("products");
		Mockito.when(resolver.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querrymap), session)).thenReturn(query);
		Mockito.when(query.getResult()).thenReturn(result);
		Mockito.when(result.getHits()).thenReturn(listHit);
		Mockito.when(hit.getPath()).thenReturn("");
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resource.getParent()).thenReturn(resource);
		Mockito.when(resource.getPath()).thenReturn("");
		Mockito.when(tag.getName()).thenReturn("tagName");
		Tag [] tagArray = new Tag[2];
		tagArray[0] = tag;
		tagArray[1] = tag;
		Mockito.when(page.getTags()).thenReturn(tagArray);
		Mockito.when(StringUtils.isNoneEmpty(page.getPath())).thenReturn(true);
		Mockito.when(hit.getProperties()).thenReturn(valueMap);
		Mockito.when(tagArray[0].getTagID()).thenReturn("");
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(entry.getValue().get("retailerLogo", String.class)).thenReturn("retailerLogo");
		Mockito.when(page.listChildren(pageFilter, false)).thenReturn(rootPageIterator);
		//Mockito.when(rootPageIterator.hasNext()).thenReturn(true);
		if (null != resolver && resolver.isLive()) {
			resolver.close();
		}
		Mockito.when(result.getResources()).thenReturn(resources);
		Mockito.when(resources.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resources.next()).thenReturn(resource);
		Mockito.when(resources.next().getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.isLive()).thenReturn(true);
		Mockito.when(page.listChildren()).thenReturn(rootPageIterator);
		Mockito.when(rootPageIterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(rootPageIterator.next()).thenReturn(page);
		Mockito.when(page.listChildren()).thenReturn(rootPageIterator);
		//Mockito.when(rootPageIterator.hasNext()).thenReturn(true);
		Mockito.when(rootPageIterator.next()).thenReturn(page);
		Mockito.when(page.getProperties()).thenReturn(valueMap);

		Mockito.when(page.getProperties().get("navThumbImage")).thenReturn(obj);
		Mockito.when(page.getPath()).thenReturn("");
		Mockito.when(page.getTitle()).thenReturn("");
		Mockito.when(page.getNavigationTitle()).thenReturn("");
		Mockito.when(page.getProperties().get("navThumbImage")).thenReturn(obj);
		Mockito.when(resolverFactory.getServiceResourceResolver(map1)).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		Mockito.when(tagManager.resolve("Abc")).thenReturn(tag);
		Mockito.when(tag.getTitle()).thenReturn("Maxsteel");
		Mockito.when(tag.getLocalTagID()).thenReturn("111");
		Mockito.when(tag.getName()).thenReturn("Name");
		String str[] = new String[1];
		str[0] = "abc";
		Mockito.when(resource.getValueMap()).thenReturn(valueMap);
		Mockito.when(valueMap.get("primaryTags", String[].class)).thenReturn(str);


		//Mockito.when(rootPageIterator.hasNext()).thenReturn(true).thenReturn(false);

	}
	@Test
	public void getCategories() {
		tileGalleryAndLandingService.getCategories(catItemsList);
	}
	@Test
	public void getAllTiles() {
		tileGalleryAndLandingService.getAllTiles("", "", "landing",true);
		tileGalleryAndLandingService.getAllTiles("", "", "gallery",true);
		tileGalleryAndLandingService.getAllTiles("", "", "",true);
	}
	@Test
	public void getTilesByDate() {
		tileGalleryAndLandingService.getTilesByDate("", "characters", "", resolver,true);
		tileGalleryAndLandingService.getTilesByDate("", "games", "", resolver,true);
		tileGalleryAndLandingService.getTilesByDate("", "products", "", resolver,true);
		tileGalleryAndLandingService.getTilesByDate("", "products", null, resolver,true);

	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void getSiteNavigationDetails() {
		Page rootPage = Mockito.mock(Page.class);
		ValueMap mockedValueMap =  Mockito.mock(ValueMap.class);
		Iterator<Page> rootPageItr =  Mockito.mock(Iterator.class);
		Page childPage = Mockito.mock(Page.class);
		Iterator<Page> childPageItr = Mockito.mock(Iterator.class);
		Page l2ChildPage = Mockito.mock(Page.class);
		ValueMap l2ChildPagemockedValueMap =  Mockito.mock(ValueMap.class);
		ValueMap childPagemockedValueMap = Mockito.mock(ValueMap.class);

		Mockito.when(rootPage.getPath()).thenReturn("root-path");
		Mockito.when(rootPage.getTitle()).thenReturn("title");
		Mockito.when(rootPage.getNavigationTitle()).thenReturn("navigatioon-title");

		Mockito.when(rootPage.getProperties()).thenReturn(mockedValueMap);

		Object thumbnail = Mockito.mock(Object.class);
		Mockito.when(mockedValueMap.get("navThumbImage")).thenReturn(thumbnail);
		Mockito.when(thumbnail.toString()).thenReturn("navThumbImage");

		Object redirectTarget = Mockito.mock(Object.class);
		Mockito.when(mockedValueMap.get("cq:redirectTarget")).thenReturn(redirectTarget);
		Mockito.when(redirectTarget.toString()).thenReturn("redirectTarget");

		Object adobeTrackingNameForPage = Mockito.mock(Object.class);
		Mockito.when(mockedValueMap.get("adobeTrackingNameForPage")).thenReturn(adobeTrackingNameForPage);
		Mockito.when(adobeTrackingNameForPage.toString()).thenReturn("adobeTrackingNameForPage");

		Object redirectTargetOption = Mockito.mock(Object.class);
		Mockito.when(mockedValueMap.get("cq:redirectTargetOption")).thenReturn(redirectTargetOption);
		Mockito.when(redirectTargetOption.toString()).thenReturn("adobeTrackingNameForPage");

		Mockito.when(rootPage.listChildren(Mockito.any(), Mockito.anyBoolean())).thenReturn(rootPageItr);
		Mockito.when(rootPageItr.hasNext()).thenReturn(true, false);
		Mockito.when(rootPageItr.next()).thenReturn(childPage);

		Mockito.when(childPage.listChildren(Mockito.any(), Mockito.anyBoolean())).thenReturn(childPageItr);
		Mockito.when(childPageItr.hasNext()).thenReturn(true, false);
		Mockito.when(childPageItr.next()).thenReturn(l2ChildPage);
		Mockito.when(l2ChildPage.getPath()).thenReturn("l2childPage-path");
		Mockito.when(l2ChildPage.getTitle()).thenReturn("l2childPage-title");
		Mockito.when(l2ChildPage.getNavigationTitle()).thenReturn("l2childPage-navigation-title");
		Mockito.when(l2ChildPage.getProperties()).thenReturn(l2ChildPagemockedValueMap);

		Object thumbnail1 = Mockito.mock(Object.class);
		Mockito.when(l2ChildPagemockedValueMap.get("navThumbImage")).thenReturn(thumbnail1);
		Mockito.when(thumbnail1.toString()).thenReturn("navThumbImage");

		Object redirectTarget1 = Mockito.mock(Object.class);
		Mockito.when(l2ChildPagemockedValueMap.get("cq:redirectTarget")).thenReturn(redirectTarget1);
		Mockito.when(redirectTarget1.toString()).thenReturn("redirectTarget");

		Object adobeTrackingNameForPage1 = Mockito.mock(Object.class);
		Mockito.when(l2ChildPagemockedValueMap.get("adobeTrackingNameForPage")).thenReturn(adobeTrackingNameForPage1);
		Mockito.when(adobeTrackingNameForPage1.toString()).thenReturn("adobeTrackingNameForPage");

		Object redirectTargetOption1 = Mockito.mock(Object.class);
		Mockito.when(l2ChildPagemockedValueMap.get("cq:redirectTargetOption")).thenReturn(redirectTargetOption1);
		Mockito.when(redirectTargetOption1.toString()).thenReturn("adobeTrackingNameForPage");

		Mockito.when(childPage.getPath()).thenReturn("childpage-root-path");
		Mockito.when(childPage.getTitle()).thenReturn("childpage-title");
		Mockito.when(childPage.getNavigationTitle()).thenReturn("childpage-navigatioon-title");
		Mockito.when(childPage.getProperties()).thenReturn(childPagemockedValueMap);

		Object thumbnail2 = Mockito.mock(Object.class);
		Mockito.when(childPagemockedValueMap.get("navThumbImage")).thenReturn(thumbnail2);
		Mockito.when(thumbnail2.toString()).thenReturn("navThumbImage");

		Object redirectTarget2 = Mockito.mock(Object.class);
		Mockito.when(childPagemockedValueMap.get("cq:redirectTarget")).thenReturn(redirectTarget2);
		Mockito.when(redirectTarget2.toString()).thenReturn("redirectTarget");

		Object adobeTrackingNameForPage2 = Mockito.mock(Object.class);
		Mockito.when(childPagemockedValueMap.get("adobeTrackingNameForPage")).thenReturn(adobeTrackingNameForPage2);
		Mockito.when(adobeTrackingNameForPage2.toString()).thenReturn("adobeTrackingNameForPage");

		Object redirectTargetOption2 = Mockito.mock(Object.class);
		Mockito.when(childPagemockedValueMap.get("cq:redirectTargetOption")).thenReturn(redirectTargetOption2);
		Mockito.when(redirectTargetOption2.toString()).thenReturn("adobeTrackingNameForPage");

		tileGalleryAndLandingService.getSiteNavigationDetails(rootPage, true, "/currentPath", true);
	}

	@Test
	public void fetchRetailerDetails() {
		tileGalleryAndLandingService.fetchRetailerDetails(multifieldProperty,resource);
	}

	@Test
	public void checkNavigationDetails() {
		tileGalleryAndLandingService.checkNavigationDetails( sitePojo, true, "", "", resource, true,childNavMap);
	}
	@Test
	public void checkNavigationDetailss() {
		tileGalleryAndLandingService.checkNavigationDetails(sitePojo,true,"", "", resource, true,childNavMap);
	}
	@Test
	public void getArticleTilesByDate() {

		tileGalleryAndLandingService.getArticleTilesByDate("Abc", "Abc", resolver);
	}
	@Test
	public void getArticleTilesByDate1() {

		tileGalleryAndLandingService.getArticleTilesByDate("Abc", null, resolver);
	}
	@Test
	public void getManualAuthorArticleList() throws RepositoryException {
		tileGalleryAndLandingService.getManualAuthorArticleList("");
	}
}
