package com.mattel.play.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
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

import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.CategoryFilterPojo;
import com.mattel.play.core.pojos.CategoryPojo;
import com.mattel.play.core.pojos.ProductTilePojo;
import com.mattel.play.core.utils.PlaySiteConfigurationUtils;


@RunWith(PowerMockRunner.class)
@PrepareForTest({PredicateGroup.class, PlaySiteConfigurationUtils.class, PlayHelper.class})
public class ProductGalleryAndLandingServiceTest {

	ProductGalleryAndLandingService productGalleryAndLandingService;
	SlingSettingsService slingSettingsService;
	ResourceResolverFactory resolverFactory;
	QueryBuilder queryBuilder;
	ResourceResolver resolver;
	Session session;
	MultifieldReader multifieldReader;
	LinkedList<ProductTilePojo> masterList = new LinkedList<>();
	List<CategoryPojo> categoryItemsList = new ArrayList<>();
	List<CategoryFilterPojo> catItemsList = new ArrayList<>();
	Map<String, Object> map = new HashMap<>();
	List<Hit> listHit = new ArrayList<>();
	List<String> productTag = new ArrayList<>();
	Iterator<CategoryFilterPojo> iterator;
	CategoryFilterPojo catFilterPojo = new CategoryFilterPojo();
	PageManager pageManager;
	Page page;
	CategoryPojo catPojo = new CategoryPojo();
	String[] productItems;
	Resource resource;
	ValueMap valMap;
	Query pageQuery;
	Map<String, String> querrymap = new HashMap<>();
	SearchResult result;
	Hit hit;
	ProductTilePojo productTilePojo = new ProductTilePojo();
	Node productNode;
	Map.Entry<String, ValueMap> entry;
	Tag tag;
	Tag tags[];
	Asset asset;
	Object obj;
	Object[] objtags;
	Map<String, ValueMap> multifieldProperty = new HashMap<>();
	TagManager tagManager;
	Iterator<Resource> resources;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws LoginException, RepositoryException {
		productGalleryAndLandingService = new ProductGalleryAndLandingService();
		slingSettingsService = Mockito.mock(SlingSettingsService.class);
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		queryBuilder = Mockito.mock(QueryBuilder.class);
		resolver = Mockito.mock(ResourceResolver.class);
		session = Mockito.mock(Session.class);
		iterator = Mockito.mock(Iterator.class);
		pageManager = Mockito.mock(PageManager.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		resource = Mockito.mock(Resource.class);
		page = Mockito.mock(Page.class);
		valMap = Mockito.mock(ValueMap.class);
		pageQuery = Mockito.mock(Query.class);
		result = Mockito.mock(SearchResult.class);
		resources = Mockito.mock(Iterator.class);
		PowerMockito.mockStatic(PredicateGroup.class);
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		PowerMockito.mockStatic(PlayHelper.class);
		hit = Mockito.mock(Hit.class);
		productNode = Mockito.mock(Node.class);
		entry = Mockito.mock(Map.Entry.class);
		tag = Mockito.mock(Tag.class);
		obj = Mockito.mock(Object.class);
		asset = Mockito.mock(Asset.class);
		tagManager = Mockito.mock(TagManager.class);
		tags = new Tag[5];
		tags[0] = tag;
		tags[1] = tag;
		tags[2] = tag;
		tags[3] = tag;
		tags[4] = tag;
		objtags = new Object[3];
		objtags[0] = obj;
		objtags[1] = obj;
		objtags[2] = obj;
		productTag.add("");
		productTilePojo.setage("");
		productTilePojo.setProductId("");
		productTilePojo.setProductTags(productTag);
		masterList.add(productTilePojo);
		productGalleryAndLandingService.setQueryBuilder(queryBuilder);
		productGalleryAndLandingService.setResolver(resolver);
		productGalleryAndLandingService.setResolverFactory(resolverFactory);
		productGalleryAndLandingService.setSession(session);
		productGalleryAndLandingService.setSlingSettingsService(slingSettingsService);
		productGalleryAndLandingService.setMultifieldReader(multifieldReader);
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		map.put(ResourceResolverFactory.USER, "playserviceuser");
		catItemsList.add(catFilterPojo);
		catPojo.setCatName(page.getTitle());
		catPojo.setCatUrl(page.getPath());
		categoryItemsList.add(catPojo);
		productItems = new String[5];
		productItems[0]="";
		listHit.add(hit);
		productNode.addNode("assets", "");
		multifieldProperty.put("", valMap);
		
		
		Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resolver);
		Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage(catFilterPojo.getCategoryPath())).thenReturn(page);
		Mockito.when(resolver.getResource("/jcr:content/root/productdetail")).thenReturn(resource);
		Mockito.when(resource.getValueMap()).thenReturn(valMap);
		Mockito.when(valMap.get("productPath", String.class)).thenReturn("productPath");
		Mockito.when(resolver.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querrymap), session)).thenReturn(pageQuery);
		Mockito.when(pageQuery.getResult()).thenReturn(result);
		Mockito.when(result.getHits()).thenReturn(listHit);
		Mockito.when(hit.getPath()).thenReturn("/content");
		Mockito.when(resolver.getResource(hit.getPath())).thenReturn(resource);
		Mockito.when(resolver.getResource("productPath")).thenReturn(resource);
		Mockito.when(resource.getValueMap()).thenReturn(valMap);
		Mockito.when(valMap.get("cq:tags", String[].class)).thenReturn(productItems);
		Mockito.when(resource.adaptTo(Node.class)).thenReturn(productNode);
		Mockito.when(productNode.getNode("assets")).thenReturn(productNode);
		Mockito.when(null != productNode && productNode.hasNode("assets")).thenReturn(true);
		Mockito.when(pageManager.getPage("")).thenReturn(page);
		Mockito.when(resource.getValueMap()).thenReturn(valMap);
		Mockito.when(page.getTags()).thenReturn(tags);
		Mockito.when(page.getTitle()).thenReturn("");
		Mockito.when(page.getName()).thenReturn("");
		Mockito.when(valMap.get("jcr:title", String.class)).thenReturn("");
		Mockito.when(valMap.get("imageReferenceAltText", String.class)).thenReturn("");
		Mockito.when(null != valMap.get("imageReferenceAltText", String.class)
				? valMap.get("imageReferenceAltText", String.class) : "").thenReturn("");
		Mockito.when(null != page.getTitle() ? page.getTitle()
						: page.getName()).thenReturn("");
		Mockito.when(resolver.getResource("productPath/assets/asset")).thenReturn(resource);
		Mockito.when(pageManager.getPage("/content")).thenReturn(page);
		Mockito.when(resolver.getResource("")).thenReturn(resource);
		Mockito.when(resource.adaptTo(Asset.class)).thenReturn(asset);
		Mockito.when(resolver.getResource("/jcr:content/productdetail")).thenReturn(resource);
		Mockito.when(multifieldReader.propertyReader(productNode)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(valMap);
		Mockito.when(entry.getValue().get("fileReference", String.class)).thenReturn("abc");
		Mockito.when(resolver.getResource("abc")).thenReturn(resource);
		Mockito.when(asset.getMetadata("cq:tags")).thenReturn(objtags);
		Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(tagManager);
		Mockito.when(obj.toString()).thenReturn("");
		Mockito.when(tagManager.resolve(obj.toString())).thenReturn(tag);
		Mockito.when(tag.getTagID()).thenReturn("");
		Mockito.when(PlaySiteConfigurationUtils.getProductPrimaryImageTag()).thenReturn("");
		if (null != resolver && resolver.isLive()) {
			resolver.close();
		}
		Mockito.when(result.getResources()).thenReturn(resources);
		Mockito.when(resources.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resources.next()).thenReturn(resource);
		Mockito.when(resources.next().getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.isLive()).thenReturn(true);
		Mockito.when(PlayHelper.checkLink("", resource)).thenReturn("");
		
	}
	@Test
	public void getProductTileDetails() {
		productGalleryAndLandingService.getProductTileDetails("/content", resolver, "", true, productTilePojo, true);
	}
	
	@Test 
	public void getAllTiles() {
		productGalleryAndLandingService.getAllTiles(productItems,true);
	}
	
	@Test
	public void getTilesByDate() {
		productGalleryAndLandingService.getTilesByDate("/content/",true);
	}
	@Test
	public void filterProductsByTag() {
		productGalleryAndLandingService.filterProductsByTag(masterList, "");
	}
	
	@Test
	public void testSetTileAltTextProp(){
	  productGalleryAndLandingService.setTileAltTextProp("tileAltTextProp");
	}
	
	@Test
	public void testgetCurrentBrandExpFragmentRootPath(){
	  String[] brandRootPath = {"polly-pocket:/content/experience-fragments/mattel-play/"};
	  Mockito.when(PlaySiteConfigurationUtils.getExpFragmentRootPathArray()).thenReturn(brandRootPath);
      productGalleryAndLandingService.getCurrentBrandExpFragmentRootPath("polly-pocket", "experience-fragments", "mattel-play");
	}
	
	@Test
    public void testgetCurrentBrandExpFragmentRootPath_1(){
      String[] brandRootPath = null;
      Mockito.when(PlaySiteConfigurationUtils.getExpFragmentRootPathArray()).thenReturn(brandRootPath);
      productGalleryAndLandingService.getCurrentBrandExpFragmentRootPath("polly-pocket", "test", "mattel-play");
    }
	
	
}
