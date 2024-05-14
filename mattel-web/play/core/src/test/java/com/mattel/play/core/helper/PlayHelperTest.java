package com.mattel.play.core.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.utils.PlaySiteConfigurationUtils;
import com.mattel.play.core.utils.PropertyReaderUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ PropertyReaderUtils.class, PlaySiteConfigurationUtils.class, PredicateGroup.class,
		StringUtils.class })
public class PlayHelperTest {
	PlayHelper playHelper;
	Session session;
	QueryBuilder queryBuilder;
	Map<String, String> querrymap = new HashMap<>();
	PredicateGroup predicateGroup;
	Query query;
	ResourceResolver resolver;
	Resource currPageRootRes;
	Node currPageRootNode;
	NodeIterator iter;
	Property property;
	Value value;
	Page page;
	Asset asset;
	Resource resource;
	PageManager pageManager;

	@Before
	public void setUp() throws RepositoryException {
		playHelper = new PlayHelper();
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		PowerMockito.mockStatic(PredicateGroup.class);
		PowerMockito.mockStatic(StringUtils.class);
		page = Mockito.mock(Page.class);
		session = Mockito.mock(Session.class);
		predicateGroup = Mockito.mock(PredicateGroup.class);
		queryBuilder = Mockito.mock(QueryBuilder.class);
		resolver = Mockito.mock(ResourceResolver.class);
		currPageRootRes = Mockito.mock(Resource.class);
		currPageRootNode = Mockito.mock(Node.class);
		resource = Mockito.mock(Resource.class);
		iter = Mockito.mock(NodeIterator.class);
		query = Mockito.mock(Query.class);
		property = Mockito.mock(Property.class);
		value = Mockito.mock(Value.class);
		asset = Mockito.mock(Asset.class);
		pageManager = Mockito.mock(PageManager.class);
		querrymap.put("path", "/content/");
		querrymap.put("type", "cq:PageContent");
		querrymap.put("property", "jcr:language");
		querrymap.put("property.operation", "exists");
		querrymap.put("p.limit", "-1");
		Mockito.when(PropertyReaderUtils.getPlayPath()).thenReturn("/content/mattel-play/");
		Mockito.when(PlaySiteConfigurationUtils.getExpFragmentRootPath()).thenReturn("");
		Mockito.when(PlaySiteConfigurationUtils.getRootErrorPageName()).thenReturn("/error/");
		Mockito.when(PredicateGroup.create(querrymap)).thenReturn(predicateGroup);
		Mockito.when(queryBuilder.createQuery(PredicateGroup.create(querrymap), session)).thenReturn(query);
		Mockito.when(currPageRootRes.adaptTo(Node.class)).thenReturn(currPageRootNode);
		Mockito.when(currPageRootNode.getNodes()).thenReturn(iter);
		Mockito.when(iter.hasNext()).thenReturn(true).thenReturn(false);
		Mockito.when(iter.nextNode()).thenReturn(currPageRootNode);
		Mockito.when(currPageRootNode.hasProperty("sling:resourceType")).thenReturn(true);
		Mockito.when(currPageRootNode.getProperty("sling:resourceType")).thenReturn(property);
		Mockito.when(currPageRootNode.getProperty("sling:resourceType").getValue()).thenReturn(value);
		Mockito.when(currPageRootNode.getProperty("sling:resourceType").getValue().toString())
				.thenReturn("cq/experience-fragments/editor/components/experiencefragment");
		Mockito.when(StringUtils.isNotBlank("")).thenReturn(true);
		Mockito.when(resolver.getResource("" + "/jcr:content/root")).thenReturn(currPageRootRes);
		Mockito.when(currPageRootNode.hasNode("productthumbnailgrid")).thenReturn(true);
		Mockito.when(currPageRootNode.getNode("productthumbnailgrid")).thenReturn(currPageRootNode);
		Mockito.when(currPageRootNode.getNode("productthumbnailgrid").getPath()).thenReturn("");
		Mockito.when(resolver.getResource("")).thenReturn(currPageRootRes);
		Mockito.when(currPageRootRes.adaptTo(Asset.class)).thenReturn(asset);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5).getName()).thenReturn("");
	}

	@Test
	public void getBrandNameForPlay() {
		PlayHelper.getBrandName("/content/mattel-play/polly-pocket/us/en-us/home");
	}
	
	@Test
	public void getBrandNameWithPlayFrgment() {
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		Mockito.when(PlaySiteConfigurationUtils.getRescueRootPath()).thenReturn("RescueRootPath");
		Mockito.when(PlaySiteConfigurationUtils.getFpRootPath()).thenReturn("FpRootPath");
		Mockito.when(PlaySiteConfigurationUtils.getExpFragmentRootPath()).thenReturn("/content/experience-fragments/mattel-play/");
		Mockito.when(PlaySiteConfigurationUtils.getFpExpFragmentRootPath()).thenReturn("/content/experience-fragments/fisher-price/");
		Mockito.when(PlaySiteConfigurationUtils.getRescueExpFgmtRootPath()).thenReturn("/content/experience-fragments/fisher-price/rescue-heroes");
		PlayHelper.getBrandName("/content/experience-fragments/mattel-play/polly-pocket/us/en-us/home");
	}
	
	@Test
	public void getBrandNameWithFpFrgment() {
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		Mockito.when(PlaySiteConfigurationUtils.getRescueRootPath()).thenReturn("RescueRootPath");
		Mockito.when(PlaySiteConfigurationUtils.getFpRootPath()).thenReturn("FpRootPath");
		Mockito.when(PlaySiteConfigurationUtils.getExpFragmentRootPath()).thenReturn("/content/experience-fragments/mattel-play/");
		Mockito.when(PlaySiteConfigurationUtils.getFpExpFragmentRootPath()).thenReturn("/content/experience-fragments/fisher-price/");
		Mockito.when(PlaySiteConfigurationUtils.getRescueExpFgmtRootPath()).thenReturn("/content/experience-fragments/fisher-price/rescue-heroes");
		PlayHelper.getBrandName("/content/experience-fragments/fisher-price/us/en-us/home");
	}
	
	@Test
	public void getBrandNameWithRescueFrgment() {
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		Mockito.when(PlaySiteConfigurationUtils.getRescueRootPath()).thenReturn("RescueRootPath");
		Mockito.when(PropertyReaderUtils.getRescuePath()).thenReturn("ResuePath");
		Mockito.when(PlaySiteConfigurationUtils.getFpRootPath()).thenReturn("FpRootPath");
		Mockito.when(PlaySiteConfigurationUtils.getExpFragmentRootPath()).thenReturn("/content/experience-fragments/mattel-play/");
		Mockito.when(PlaySiteConfigurationUtils.getFpExpFragmentRootPath()).thenReturn("/content/experience-fragments/fisher-price/");
		Mockito.when(PlaySiteConfigurationUtils.getRescueExpFgmtRootPath()).thenReturn("/content/experience-fragments/fisher-price/rescue-heroes");
		PlayHelper.getBrandName("/content/experience-fragments/fisher-price/rescue-heroes/us/en-us/home");
	}

	@Test
	public void getRelativePath() {
		Resource mockedResource = Mockito.mock(Resource.class);
		ResourceResolver mockedResourceResolver =  Mockito.mock(ResourceResolver.class);
		PageManager mockedPageManager = Mockito.mock(PageManager.class);
		Page mockedPage = Mockito.mock(Page.class);
		Page mockedParentPage = Mockito.mock(Page.class);
		Page mockedAbcParentPage = Mockito.mock(Page.class);
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		Mockito.when(PlaySiteConfigurationUtils.getRescueRootPath()).thenReturn("RescueRootPath");
		Mockito.when(PropertyReaderUtils.getRescuePath()).thenReturn("ResuePath");
		Mockito.when(PlaySiteConfigurationUtils.getExpFragmentRootPath()).thenReturn("/content/experience-fragments/mattel-play/");
		Mockito.when(mockedResource.getPath()).thenReturn("/test/home/ResuePath/jcr:content");
		Mockito.when(mockedResource.getResourceResolver()).thenReturn(mockedResourceResolver);
		Mockito.when(mockedResourceResolver.adaptTo(PageManager.class)).thenReturn(mockedPageManager);
		Mockito.when(mockedPageManager.getPage("/test/home/ResuePath")).thenReturn(mockedPage);
		Mockito.when(mockedPageManager.getContainingPage(mockedResource)).thenReturn(mockedParentPage);
		Mockito.when(mockedParentPage.getAbsoluteParent(Mockito.anyInt())).thenReturn(mockedAbcParentPage);
		Mockito.when(mockedPage.getAbsoluteParent(2)).thenReturn(mockedParentPage);
		Mockito.when(mockedParentPage.hasChild("language-masters")).thenReturn(false);
		Mockito.when(mockedPage.getAbsoluteParent(1)).thenReturn(mockedParentPage);
		Mockito.when(mockedParentPage.hasChild("language-masters")).thenReturn(false);
		Mockito.when(mockedParentPage.getAbsoluteParent(5)).thenReturn(mockedAbcParentPage);
		Mockito.when(mockedAbcParentPage.getName()).thenReturn("jcr:content");
		PlayHelper.getRelativePath("/test/home/ResuePath/jcr:content", mockedResource);
	}
	
	@Test
	public void testGetSlideCount(){
		String[] str = {"test:test1:test2"};
		PlayHelper.getSlideCount("2", str, "test");
	}
	
	@Test
	public void testGetExpFrLocaleRH(){
		PlayHelper.getExpFrLocaleRH("/content/rescue-heroes/experience-fragments");
	}
	
	/*@Test
	public void testFetchLocaleFromDam(){
		PowerMockito.mockStatic(PlaySiteConfigurationUtils.class);
		Mockito.when(PropertyReaderUtils.getPlayPath()).thenReturn("/content/mattel-play/");
		Mockito.when(PlaySiteConfigurationUtils.getExpFragmentRootPath()).thenReturn("/content/experience-fragments/mattel-play/");
		Resource mockedResource = Mockito.mock(Resource.class);
		Mockito.when(mockedResource.getPath()).thenReturn("/content/experience-fragments/fisher-price/rescue-heroes/us/en-us/home");
		PlayHelper.fetchLocaleFromDam("/content/mattel-play/fp", mockedResource);
	}*/
	
	@Test
	public void testGetHomePagePath(){
		Resource mockedResource = Mockito.mock(Resource.class);
		ResourceResolver mockedResourceResolver =  Mockito.mock(ResourceResolver.class);
		PageManager mockedPageManager = Mockito.mock(PageManager.class);
		Page mockedPage = Mockito.mock(Page.class);
		Page mockedParentPage = Mockito.mock(Page.class);
		
		Mockito.when(mockedResource.getResourceResolver()).thenReturn(mockedResourceResolver);
		Mockito.when(mockedResourceResolver.adaptTo(PageManager.class)).thenReturn(mockedPageManager);
		Mockito.when(mockedPageManager.getContainingPage(mockedResource)).thenReturn(mockedPage);
		Mockito.when(mockedPage.getAbsoluteParent(Mockito.anyInt())).thenReturn(mockedParentPage);
		
		Mockito.when(mockedParentPage.getPath()).thenReturn("mockedParentPage");
		Mockito.when(mockedResource.getPath()).thenReturn("/test/home/ResuePath/experience-fragments/jcr:content");
		
		PlayHelper.getHomePagePath(mockedResource);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetLeakingResResolver(){
		SearchResult result = Mockito.mock(SearchResult.class);
		Iterator<Resource> itr = Mockito.mock(Iterator.class);
		Resource mockedResource = Mockito.mock(Resource.class);
		ResourceResolver mockedResourceResolver =  Mockito.mock(ResourceResolver.class);
		Mockito.when(result.getResources()).thenReturn(itr);
		Mockito.when(itr.hasNext()).thenReturn(true);
		Mockito.when(itr.next()).thenReturn(mockedResource);
		Mockito.when(mockedResource.getResourceResolver()).thenReturn(mockedResourceResolver);
		Mockito.when(mockedResourceResolver.isLive()).thenReturn(true);
		PlayHelper.getLeakingResResolver(result);
	}
	
	@Test
	public void testCheckPropertyObject() throws RepositoryException{
		PlayHelper.checkPropertyObject("value");
	}

	@Test
	public void getPageLocale() {
		PlayHelper.getPageLocale("ab-c/ab-c/ab-c");
	}

	@Test
	public void getCountryNodesByLanguage() {
		PlayHelper.getCountryNodesByLanguage("/content/", session, queryBuilder);
	}

	@Test
	public void getPathURL() {
		playHelper.getPathURL();
	}

	@Test
	public void checkProductThumbnailExpFragemnt() throws RepositoryException {
		PlayHelper.checkProductThumbnailExpFragemnt(resolver, currPageRootRes);
	}

	@Test
	public void forNodeProperty() throws RepositoryException {
		PlayHelper.checkProductThumbnailExpFragemnt(resolver, currPageRootRes);
		Mockito.when(currPageRootNode.hasProperty("fragmentPath")).thenReturn(true);
		Mockito.when(currPageRootNode.getProperty("fragmentPath")).thenReturn(property);
		Mockito.when(currPageRootNode.getProperty("fragmentPath").getValue()).thenReturn(value);
		Mockito.when(currPageRootNode.getProperty("fragmentPath").getValue().toString()).thenReturn("fragmentPath");
	}

	@Test
	public void checkForProperty() throws RepositoryException {
		PlayHelper.checkForProperty(currPageRootNode, "");
	}

	@Test
	public void checkForPropertyies() {
		PlayHelper.checkForProperty(page, "");
	}

	@Test
	public void checkLink() {
		PlayHelper.checkLink("/content/#/content/#", currPageRootRes);
	}

	@Test
	public void getAssetMetadataValue() {
		PlayHelper.getAssetMetadataValue("", resolver, "");
	}
	
    @SuppressWarnings("static-access")
    @Test
    public void testCheckForProperty() throws RepositoryException {
      Node node = Mockito.mock(Node.class);
      Mockito.when(node.hasProperty("prop")).thenReturn(true);
      Property prop = Mockito.mock(Property.class);
      Mockito.when(node.getProperty("prop")).thenReturn(prop);
      Mockito.when(prop.getString()).thenReturn("prop-value");
      playHelper.checkForProperty(node, "prop");
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testCheckForPropertyAsset() throws RepositoryException {
      Asset assetNode = Mockito.mock(Asset.class);
      Mockito.when(assetNode.getMetadata("prop")).thenReturn(true);
      Mockito.when(assetNode.getMetadataValue("prop")).thenReturn("prop");
      playHelper.checkForProperty(assetNode, "prop");
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testCheckPropertyObject_1(){
      String val = "value";
      playHelper.checkPropertyObject(val);
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testCheckBooleanProperty(){
      Page testPage = Mockito.mock(Page.class);
      ValueMap properties = Mockito.mock(ValueMap.class);
      Mockito.when(properties.get("prop", Boolean.class)).thenReturn(true);
      Mockito.when(testPage.getProperties()).thenReturn(properties);
      playHelper.checkBooleanProperty(testPage, "prop", true);
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testCheckBooleanProperty_1(){
      Page testPage = Mockito.mock(Page.class);
      ValueMap properties = Mockito.mock(ValueMap.class);
      Mockito.when(properties.get("prop", Boolean.class)).thenReturn(null);
      Mockito.when(testPage.getProperties()).thenReturn(properties);
      playHelper.checkBooleanProperty(testPage, "prop", true);
    }    
    
    @SuppressWarnings("static-access")
    @Test
    public void testGetValueMapNodeVale(){
      ValueMap nodeValues = Mockito.mock(ValueMap.class);
      Mockito.when(nodeValues.containsKey("prop")).thenReturn(true);
      Mockito.when(nodeValues.get("prop", String.class)).thenReturn("prop-value");
      playHelper.getValueMapNodeVale(nodeValues, "prop");
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testGetValueMapNodeVale_1(){
      ValueMap nodeValues = Mockito.mock(ValueMap.class);
      Mockito.when(nodeValues.containsKey("prop")).thenReturn(false);
      playHelper.getValueMapNodeVale(nodeValues, "prop");
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testIsNullOrEmpty(){
      String[] navigationalLinks = new String[] {"link1"};
      playHelper.isNullOrEmpty(navigationalLinks);
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testIsNullOrEmpty_WithNullValue(){
      String[] navigationalLinks = null;
      playHelper.isNullOrEmpty(navigationalLinks);
    }
}
