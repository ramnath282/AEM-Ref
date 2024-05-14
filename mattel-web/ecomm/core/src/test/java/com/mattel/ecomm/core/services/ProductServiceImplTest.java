package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.pojos.ProductFeaturePojo;
import com.mattel.ecomm.core.utils.LanguageMastersMapping;
import com.mattel.global.core.utils.NavigationUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ NavigationUtil.class, EcommHelper.class, LanguageMastersMapping.class })
public class ProductServiceImplTest {
	
	@InjectMocks
	ProductServiceImpl productServiceImpl;
	@Mock
	private Resource resource;
	ResourceResolverFactory resolverFactory;
	QueryBuilder queryBuilder;
	ResourceResolver resolver;
	Session session;
	ValueMap prodProperties;
	ResourceResolver resourceResolver;
	PageManager pageManager;
	Page page;
	Page currentPage;
	Query pageQuery;
	SearchResult result;
	Map<String, Object> map = new HashMap<>();
	Hit hit;
	List<Hit> listHit = new ArrayList<>();
	Map<String, String> querrymap = new HashMap<>();
	@Mock
	SlingHttpServletRequest request;
	List<ProductFeaturePojo> prodFeaturePojoList;
	String plpTemplate = "/conf/fisher-price/settings/wcm/templates/fisher-price-plp-template";
	ValueMap valueMap;
	Iterator<Resource> resources;
	String[] languagemapings;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		resolverFactory = Mockito.mock(ResourceResolverFactory.class);
		queryBuilder = Mockito.mock(QueryBuilder.class);
		resolver = Mockito.mock(ResourceResolver.class);
		session = Mockito.mock(Session.class);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);
		currentPage = Mockito.mock(Page.class);
		pageQuery = Mockito.mock(Query.class);
		result = Mockito.mock(SearchResult.class);
		hit = Mockito.mock(Hit.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		prodProperties = Mockito.mock(ValueMap.class);
		valueMap = Mockito.mock(ValueMap.class);
		resources = Mockito.mock(Iterator.class);
		PowerMockito.mockStatic(LanguageMastersMapping.class);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("/var/commerce/products/fisher-price/en-us/product_DRF13")).thenReturn(resource);
		Mockito.when(resource.getValueMap()).thenReturn(prodProperties);
		Mockito.when(resource.getPath()).thenReturn("/content/fisher-price/us/en-us/home/product/on-the-go-baby-dome-drf13");
		Mockito.when(request.getPathInfo()).thenReturn("/content/fisher-price/us/en-us/home/product/on-the-go-baby-dome-drf13");
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource("/content/fisher-price/us/en-us/home/product/on-the-go-baby-dome-drf13")).thenReturn(resource);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		JSONObject prodDescp = new JSONObject();
		prodDescp.put("title", "title");
		prodDescp.put("description", "desc");
		String[] bulletPoints = new String[] { "Gnanendra","Ponnala"};
		prodDescp.put("bulletItems", bulletPoints);
		JSONArray prodDescpArray = new JSONArray();
		prodDescpArray.put(prodDescp);
		JSONObject productDesc = new JSONObject();
		productDesc.put("productDesc", prodDescpArray);
		JSONArray productDescp = productDesc.getJSONArray("productDesc");
		String[] bulletItems = new String[productDescp.length()];
		for (int i = 0; i < productDescp.length(); i++) {
			bulletItems[i] = productDescp.getJSONObject(i).toString();
		}
		Mockito.when(prodProperties.get("productAccordTitle", String[].class)).thenReturn(bulletItems);
		Mockito.when(page.getPath()).thenReturn("/content/fisher-price/us/en-us/home/product/the-go-baby-dome-drf13");
		prodFeaturePojoList = new LinkedList<>();
		map.put(ResourceResolverFactory.SUBSERVICE, "readwriteservice");
		Mockito.when(resolverFactory.getServiceResourceResolver(map)).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		Mockito.when(resource.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(4)).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(4).getPath()).thenReturn("/content/fisher-price/us/en-us/home");
		querrymap.put("path", "/content/fisher-price/us/en-us/home");
		querrymap.put("nodename", "gnanendra");
		querrymap.put("type", "cq:page");
		querrymap.put("p.limit", "-1");
		Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querrymap), session)).thenReturn(pageQuery);
		Mockito.when(pageQuery.getResult()).thenReturn(result);
		Mockito.when(result.getHits()).thenReturn(listHit);
		Mockito.when(hit.getPath()).thenReturn("");
		Mockito.when(hit.getProperties()).thenReturn(valueMap);
		Mockito.when(hit.getProperties().get("cq:template", String.class)).thenReturn(plpTemplate);
		if (null != resourceResolver && resourceResolver.isLive()) {
			resourceResolver.close();
		}
		Mockito.when(result.getResources()).thenReturn(resources);
		Mockito.when(resources.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(resources.next()).thenReturn(resource);
		Mockito.when(resources.next().getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.isLive()).thenReturn(true);
		languagemapings = new String[2];
		languagemapings[0] = "en:en-us";
		languagemapings[1] = "es:es-es";
		Mockito.when(LanguageMastersMapping.getLanguageMapping()).thenReturn(languagemapings);
	
	}
	
    @Test
	public void productProperties() {
		productServiceImpl.productProperties("/var/commerce/products/fisher-price/en-us/product_DRF13" ,resource);
	}
    
    @Test
    public void getCurrentPagePath() {
    	productServiceImpl.getCurrentPagePath(pageManager, resource, request);
    }
    
    @Test
    public void getCurrentPagePathElseCondition() {
    	Mockito.when(resource.getPath()).thenReturn("/content/experience-fragments/fisher-price/en/header/master");
    	Mockito.when(resolver.getResource("/content/fisher-price/us/en-us/home/product")).thenReturn(resource);
    	productServiceImpl.getCurrentPagePath(pageManager, resource, request);
    }

    @Test
    public void getPageLocaleFromMappings() {
     productServiceImpl.getPageLocaleFromMappings("/content/fisher-price/langauge-masters/en/home/product/on-the-go-baby-dome-drf13", "");
    }


    @Test
    public void getProductFeatures() {
    	productServiceImpl.getProductFeatures(prodProperties, prodFeaturePojoList, "productAccordTitle");
    }

}
