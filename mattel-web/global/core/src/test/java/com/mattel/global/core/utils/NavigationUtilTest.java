package com.mattel.global.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.core.pojo.CategoryColumnPojo;
import com.mattel.global.core.pojo.GlobalNavigationPojo;
import com.mattel.global.core.pojo.PromoImagePojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.services.MultifieldReader;
import com.mattel.global.master.core.constants.Constants;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class NavigationUtilTest {
	@InjectMocks
	private NavigationUtil navigationUtil;
	@Mock
	private Resource resource;
	@Mock
	private ResourceResolver resolver;
	@Mock
	private Iterator<Page> rootPageIterator;
	MultifieldReader multifieldReader;
	Page page;
	Node childNode;
	String title = "page";
	String path = "/content/fisher-price/language-masters/en/home/category/baby-gear";
	String categoryLink = "/content/fisher-price/language-masters/en/home/category";
	String text = path;
	String childPath = "/content/experience-fragments/fisher-price/en/header/master/jcr:content/root/siteheader/navLinks/navigationimage";
	ValueMap pageProperties;
	GlobalNavigationPojo globalNavigationPojo;
	SiteNavigationPojo viewAllPojo;
	String viewAllText = "viewAll";
	String viewAllLink = "/content/fisher-price/language-masters/en/home/category/baby-gear";
	String linkTargetForViewAll = "new Window";
	String aeForViewAll = "Always English";
	ValueMap nodeValues;
	Resource childResource;
	Property property;
	Node defaultNode;
	Node featuredNode;
	Iterator<Resource> childIterator;
	List<PromoImagePojo> imageSectionList;
	int limit = 4;
	Resource parentResource;
	Resource headerResource;
	Resource parentPageResource;
	Resource featuredResource;
	Resource featurePageResource;
	Node headerNode;
	SiteNavigationPojo columnPojo = new SiteNavigationPojo();
	CategoryColumnPojo columnDetailsPojo = new CategoryColumnPojo();
	CategoryColumnPojo columnDetails = new CategoryColumnPojo();
	String subCatLink = "/content/fisher-price/us/en-us/home";
	String subCatTitle = "";
	Resource categoryResource;
	Page categoryPage;
	Page currentPage;
	Boolean defaultBoolean = false;
	String device = "mobile";
	List<SiteNavigationPojo> parentLinksList = new LinkedList<>();
	boolean confettiEffect = false;
	PageFilter pageFilter;
	ValueMap valueMap;
	Map<String, ValueMap> multifieldProperty;
	Map.Entry<String, ValueMap> entry;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws Exception {
		PowerMockito.mockStatic(GlobalUtils.class);
		page = Mockito.mock(Page.class);
		categoryPage = Mockito.mock(Page.class);
		currentPage = Mockito.mock(Page.class);
		childNode = Mockito.mock(Node.class);
		defaultNode = Mockito.mock(Node.class);
		viewAllPojo = new SiteNavigationPojo();
		globalNavigationPojo = new GlobalNavigationPojo();
		pageProperties = Mockito.mock(ValueMap.class);
		multifieldReader = Mockito.mock(MultifieldReader.class);
		nodeValues = Mockito.mock(ValueMap.class);
		property = Mockito.mock(Property.class);
		childResource = resource;
		categoryResource = Mockito.mock(Resource.class);
		parentResource = Mockito.mock(Resource.class);
		headerResource = Mockito.mock(Resource.class);
		parentPageResource = Mockito.mock(Resource.class);
		featuredResource = Mockito.mock(Resource.class);
		featurePageResource = Mockito.mock(Resource.class);
		childIterator = Mockito.mock(Iterator.class);
		imageSectionList = new LinkedList<>();
		headerNode = Mockito.mock(Node.class);
		featuredNode = Mockito.mock(Node.class);
		pageFilter = Mockito.mock(PageFilter.class);
		valueMap = Mockito.mock(ValueMap.class);
		entry = Mockito.mock(Entry.class);
		navigationUtil.setMultifieldReader(multifieldReader);
		nodeValues.put("fileReference", "/content/dam/fp-dam/fisher-price/navigation/brand/Brand_img1@2x.jpg");
		childNode.setProperty("fileReference", "/content/dam/fp-dam/fisher-price/navigation/brand/Brand_img1@2x.jpg");
		multifieldProperty = new HashMap<>();
		multifieldProperty.put("document", valueMap);
		multifieldProperty.put("document", valueMap);
		multifieldProperty.put("document", valueMap);
		Mockito.when(page.getTitle()).thenReturn(title);
		Mockito.when(page.getNavigationTitle()).thenReturn("");
		Mockito.when(page.getPath()).thenReturn(path);
		Mockito.when(page.getProperties()).thenReturn(pageProperties);
		Mockito.when(page.getProperties().get("cq:redirectTarget"))
				.thenReturn("/content/fisher-price/language-masters/en/home/category/baby-gear");
		Mockito.when(GlobalUtils.checkPropertyObject(page.getProperties().get("cq:redirectTarget"))).thenReturn(path);
		Mockito.when(GlobalUtils.checkPropertyObject(page.getProperties().get("navThumbImage")))
				.thenReturn("/content/dam/fp-dam/fisher-price/navigation/brand/Brand_img1@2x.jpg");
		Mockito.when(childNode.hasProperty("fileReference")).thenReturn(true);
		Mockito.when(childResource.adaptTo(ValueMap.class)).thenReturn(nodeValues);
		Mockito.when(childNode.getProperty("fileReference")).thenReturn(property);
		Mockito.when(childNode.getProperty("fileReference").getString())
				.thenReturn("/content/dam/fp-dam/fisher-price/navigation/brand/Brand_img1@2x.jpg");
		Mockito.when(childNode.hasProperty("promoButtonLink")).thenReturn(true);
		Mockito.when(childNode.getProperty("promoButtonLink")).thenReturn(property);
		Mockito.when(childNode.getProperty("promoButtonLink").getString()).thenReturn(path);
		Mockito.when(childNode.hasNode("cq:responsive/default")).thenReturn(true);
		Mockito.when(childNode.getNode("cq:responsive/default")).thenReturn(defaultNode);
		Mockito.when(defaultNode.hasProperty("width")).thenReturn(true);
		Mockito.when(defaultNode.getProperty("width")).thenReturn(property);
		Mockito.when(defaultNode.getProperty("width").getString()).thenReturn("6");
		Mockito.when(GlobalUtils.checkForProperty(defaultNode, "width")).thenReturn("6");
		Mockito.when(GlobalUtils.checkForProperty(childNode, "promoButtonLink")).thenReturn(path);
		Mockito.when(childIterator.hasNext()).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(childIterator.next()).thenReturn(childResource);
		Mockito.when(childResource.adaptTo(Node.class)).thenReturn(childNode);
		Mockito.when(childNode.getPath()).thenReturn(childPath);
		Mockito.when(resource.getPath()).thenReturn(childPath).thenReturn("");
		Mockito.when(childResource.getResourceType())
				.thenReturn("mattel/ecomm/shared/components/content/navigationImage");
		Mockito.when(resource.getParent()).thenReturn(parentResource);
		Mockito.when(parentResource.getParent()).thenReturn(headerResource);
		Mockito.when(headerResource.adaptTo(Node.class)).thenReturn(headerNode);
		Mockito.when(headerResource.getResourceType())
				.thenReturn("mattel/ecomm/fisher-price/components/content/siteHeader");
		Mockito.when(headerNode.hasProperty("shopByValue")).thenReturn(true);
		Mockito.when(headerNode.getProperty("shopByValue")).thenReturn(property);
		Mockito.when(headerNode.getProperty("shopByValue").getString()).thenReturn("Shop By");
		Mockito.when(GlobalUtils.checkForProperty(headerNode, "shopByValue")).thenReturn("Shop By");
		Mockito.when(resolver.getResource(subCatLink)).thenReturn(categoryResource);
		Mockito.when(categoryResource.adaptTo(Page.class)).thenReturn(categoryPage);
		Mockito.when(categoryPage.getTitle()).thenReturn("Category");
		Mockito.when(categoryPage.getNavigationTitle()).thenReturn("Shop By Category");
		Mockito.when(categoryPage.getProperties()).thenReturn(nodeValues);
		Mockito.when(categoryPage.getProperties().get("cq:isNotLinkable", Boolean.class)).thenReturn(false);
		Mockito.when(GlobalUtils.checkBooleanProperty(categoryPage, "cq:isNotLinkable", defaultBoolean))
				.thenReturn(false);
		Mockito.when(currentPage.getTitle()).thenReturn(title);
		Mockito.when(currentPage.getNavigationTitle()).thenReturn("");
		Mockito.when(currentPage.getPath()).thenReturn(path);
		Mockito.when(currentPage.getProperties()).thenReturn(pageProperties);
		Mockito.when(currentPage.getProperties().get("cq:redirectTarget"))
				.thenReturn("/content/fisher-price/language-masters/en/home/category/baby-gear");
		Mockito.when(GlobalUtils.checkPropertyObject(currentPage.getProperties().get("cq:redirectTarget")))
				.thenReturn(path);
		Mockito.when(GlobalUtils.checkPropertyObject(currentPage.getProperties().get("navThumbImage")))
				.thenReturn("/content/dam/fp-dam/fisher-price/navigation/brand/Brand_img1@2x.jpg");
		Mockito.when(resolver.map(text)).thenReturn(text);
		Mockito.when(resolver.getResource(categoryLink)).thenReturn(categoryResource);
		Mockito.when(resource.getChild("featuredSection")).thenReturn(featuredResource);
		Mockito.when(featuredResource.adaptTo(Node.class)).thenReturn(featuredNode);
		Mockito.when(multifieldReader.propertyReader(featuredNode)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(entry.getValue().get("featuredLinks", String.class)).thenReturn(path);
		Mockito.when(entry.getValue().get("confettiEffect", Boolean.class)).thenReturn(true);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
	}

	@Test
	public void fetchPageProperties() {
		navigationUtil.fetchPageProperties(page);
		Mockito.when(GlobalUtils.checkBooleanProperty(page, "cq:isSpecialLink", defaultBoolean))
		.thenReturn(true);
		Mockito.when(GlobalUtils.checkPropertyObject(page.getProperties().get("navThumbImage"))).thenReturn(StringUtils.EMPTY);
		//Mockito.when(page.getProperties().get("cq:redirectTarget")).thenReturn(StringUtils.EMPTY);
		navigationUtil.fetchPageProperties(page);
	}

	@Test
	public void setViewAllDetails() {
		navigationUtil.setViewAllDetails(viewAllPojo, viewAllText, viewAllLink, linkTargetForViewAll, aeForViewAll,
				resource);
		columnDetailsPojo.setViewAllLink(viewAllLink);
		columnDetailsPojo.setViewAllText(viewAllText);
		columnDetailsPojo.setLinkTargetForViewAll(linkTargetForViewAll);
		columnDetailsPojo.setAeForViewAll(aeForViewAll);
		navigationUtil.setViewAllDetails(viewAllPojo, columnDetailsPojo, categoryResource);
	}

	@Test
	public void getImageProperties() throws RepositoryException {
		navigationUtil.getImageProperties(childNode, resource);
		Mockito.when(nodeValues.containsKey("checkBoxLink")).thenReturn(true);
		Mockito.when(nodeValues.get("checkBoxLink", Boolean.class)).thenReturn(true);
		Mockito.when(childNode.hasProperty("promoButtonLink")).thenReturn(false);
		Mockito.when(childNode.hasNode("cq:responsive/default")).thenReturn(false);
		navigationUtil.getImageProperties(childNode, resource);
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(null);
		navigationUtil.getImageProperties(childNode, resource);
	}

	@Test
	public void checkForImages() throws RepositoryException {
		navigationUtil.checkForImages(childResource, childIterator, imageSectionList, limit);
		Mockito.when(childIterator.next()).thenReturn(childResource);
		Mockito.when(childResource.adaptTo(Node.class)).thenReturn(null);
		navigationUtil.checkForImages(childResource, childIterator, imageSectionList, limit);
	}

	@Test
	public void getShopByValue() throws RepositoryException {
		String shopByValue = navigationUtil.getShopByValue(resource);
		Assert.assertEquals("Shop By", shopByValue);
		Mockito.when(headerResource.getResourceType()).thenReturn("mattel/ecomm/fisher-price/components/content/otherHeader");
		navigationUtil.getShopByValue(resource);
		Mockito.when(headerResource.adaptTo(Node.class)).thenReturn(null);
		navigationUtil.getShopByValue(resource);
		Mockito.when(parentResource.getParent()).thenReturn(null);
		navigationUtil.getShopByValue(resource);
		Mockito.when(resource.getParent()).thenReturn(null);
		navigationUtil.getShopByValue(resource);
	}

	@Test
	public void fetchCategoryPageTitle() {
		navigationUtil.fetchCategoryPageTitle(columnPojo, subCatLink, subCatTitle, resolver);
		navigationUtil.fetchCategoryPageTitle(columnPojo, subCatLink, "subCatTitle", resolver);
		Mockito.when(categoryPage.getNavigationTitle()).thenReturn(null);
		Mockito.when(GlobalUtils.checkBooleanProperty(categoryPage, "cq:isSpecialLink",
	            defaultBoolean)).thenReturn(true);
		navigationUtil.fetchCategoryPageTitle(columnPojo, subCatLink, subCatTitle, resolver);
		Mockito.when(categoryResource.adaptTo(Page.class)).thenReturn(null);
		navigationUtil.fetchCategoryPageTitle(columnPojo, subCatLink, subCatTitle, resolver);
		Mockito.when(resolver.getResource(subCatLink)).thenReturn(null);
		navigationUtil.fetchCategoryPageTitle(columnPojo, subCatLink, subCatTitle, resolver);
	}

	@Test
	public void getPageDetails() {
		navigationUtil.getPageDetails(subCatLink, columnPojo, resolver);
		Mockito.when(categoryResource.adaptTo(Page.class)).thenReturn(null);
		navigationUtil.getPageDetails(subCatLink, columnPojo, resolver);
		Mockito.when(resolver.getResource(subCatLink)).thenReturn(null);
		navigationUtil.getPageDetails(subCatLink, columnPojo, resolver);
	}

	@Test
	public void fetchColumnDetails() {
		navigationUtil.fetchColumnDetails(nodeValues, "One");
	}

	@Test
	public void fetchColumnLinks() {
		columnDetails.setColumnListFrom("static");
		columnDetails.setParentPage(path);
		columnDetails.setSubCatLink(path);
		columnDetails.setSubCatTitle("subCatTitle");
		String[] pageList = new String[] { "/content/fisher-price/language-masters/en/home/category/baby-gear" };
		columnDetails.setParentPageList(pageList);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource(path)).thenReturn(parentPageResource);
		Mockito.when(parentPageResource.adaptTo(Page.class)).thenReturn(currentPage);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);		
		columnDetails.setColumnListFrom("nonstatic");
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);			
		columnDetails.setColumnListFrom("children");	
		Mockito.when(parentPageResource.adaptTo(Page.class)).thenReturn(null);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);
		Mockito.when(resolver.getResource(path)).thenReturn(null);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);		
		columnDetails.setParentPage(StringUtils.EMPTY);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);	
		columnDetails.setColumnListFrom(null);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);		
		columnDetails.setSubCatTitle(null);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);
		columnDetails.setSubCatLink(null);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);
	}

	@Test
	public void fetchFixedList() {
		columnDetails.setColumnListFrom("static");
		columnDetails.setParentPage(path);
		columnDetails.setSubCatLink(path);
		columnDetails.setSubCatTitle("subCatTitle");
		String[] pageList = new String[] { "/content/fisher-price/language-masters/en/home/category/baby-gear" };
		columnDetails.setParentPageList(pageList);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource(path)).thenReturn(parentPageResource);
		Mockito.when(parentPageResource.adaptTo(Page.class)).thenReturn(currentPage);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);	
		
		Mockito.when(parentPageResource.adaptTo(Page.class)).thenReturn(null);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, "desktop");		
		
		Mockito.when(resolver.getResource(path)).thenReturn(null);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, "desktop");	
	}

	@Test
	public void testColumnListChildren() {
		columnDetails.setColumnListFrom("children");
		columnDetails.setParentPage(path);
		columnDetails.setSubCatLink(path);
		columnDetails.setSubCatTitle("subCatTitle");
		String[] pageList = new String[] { "/content/fisher-price/language-masters/en/home/category/baby-gear" };
		columnDetails.setParentPageList(pageList);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource(path)).thenReturn(parentPageResource);
		Mockito.when(parentPageResource.adaptTo(Page.class)).thenReturn(currentPage);
		Mockito.when(currentPage.listChildren(Mockito.any(), Mockito.anyBoolean())).thenReturn(rootPageIterator);
		Mockito.when(rootPageIterator.hasNext()).thenReturn(false);
		Mockito.when(rootPageIterator.next()).thenReturn(page);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, device);
		navigationUtil.fetchColumnLinks(columnDetails, parentLinksList, resource, "desktop");
	}

	@Test
	public void setCategoryDetails() throws RepositoryException {
		viewAllPojo.setPageName("home");
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		//Mockito.when(GlobalUtils.checkLink("same", resource)).thenReturn("same");
		navigationUtil.setCategoryDetails(viewAllPojo, resource, true, categoryLink, "Category", "same", device);
		navigationUtil.setCategoryDetails(viewAllPojo, resource, true, categoryLink, "Category", "same", "desktop");
		navigationUtil.setCategoryDetails(viewAllPojo, resource, true, "", "Category", "same", "desktop");
		navigationUtil.setCategoryDetails(viewAllPojo, resource, true, "/conf/template-path", "Category", "same", "desktop");
		navigationUtil.setCategoryDetails(viewAllPojo, resource, false, "", "Category", "same", "desktop");
		Mockito.when(parentResource.getParent()).thenReturn(null);
		navigationUtil.setCategoryDetails(viewAllPojo, resource, true, categoryLink, "Category", "same", "desktop");
	}

	@Test
	public void setTemplateVariationType() throws RepositoryException {
		globalNavigationPojo.setAeForPrimaryNavTitle("primaryNavTitle");
		globalNavigationPojo.setTemplateType(Constants.FISHER_PRICE_BRAND_TEMPLATE);
		globalNavigationPojo.setDisplayShopByValue(false);
		navigationUtil.getTemplateVariationType(viewAllPojo, globalNavigationPojo);
		globalNavigationPojo.setDisplayShopByValue(true);
		globalNavigationPojo.setDevice("desktop");
		navigationUtil.getTemplateVariationType(viewAllPojo, globalNavigationPojo);
		globalNavigationPojo.setDevice(device);
		navigationUtil.getTemplateVariationType(viewAllPojo, globalNavigationPojo);
		globalNavigationPojo.setTemplateType(Constants.FISHER_PRICE_AGE_TEMPLATE);
		navigationUtil.getTemplateVariationType(viewAllPojo, globalNavigationPojo);
		
	}

	@Test
	public void setCategoryDetailsForPage() throws RepositoryException {
		globalNavigationPojo.setAeForPrimaryNavTitle("primaryNavTitle");
		globalNavigationPojo.setTemplateType(Constants.FISHER_PRICE_BRAND_TEMPLATE);
		globalNavigationPojo.setDisplayShopByValue(true);
		globalNavigationPojo.setCategoryTitleLink("/content/mattel/mattel-corp");
		globalNavigationPojo.setDevice("desktop");
		globalNavigationPojo.setLinkTargetCategory("linkTargetCategory");
		navigationUtil.setCategoryDetailsForPage(viewAllPojo ,resource, globalNavigationPojo);
		globalNavigationPojo.setCategoryTitleLink("/conf/fisher-price/settings/wcm/templates/fp-homepage-template");
		Mockito.when(GlobalUtils.checkLink("/conf/fisher-price/settings/wcm/templates/fp-homepage-template", resource)).thenReturn("/conf/fisher-price/settings/wcm/templates/fp-homepage-template");
		navigationUtil.setCategoryDetailsForPage(viewAllPojo ,resource, globalNavigationPojo);	
		globalNavigationPojo.setCategoryTitleLink("");
		navigationUtil.setCategoryDetailsForPage(viewAllPojo ,resource, globalNavigationPojo);	
		globalNavigationPojo.setDevice("mobile");
		navigationUtil.setCategoryDetailsForPage(viewAllPojo ,resource, globalNavigationPojo);	
		globalNavigationPojo.setDisplayShopByValue(false);
		Mockito.when(parentResource.getParent()).thenReturn(null);
		navigationUtil.setCategoryDetailsForPage(viewAllPojo ,resource, globalNavigationPojo);	
		globalNavigationPojo.setDevice("desktop");
		navigationUtil.setCategoryDetailsForPage(viewAllPojo ,resource, globalNavigationPojo);
	}

	@Test
	public void setCategoryDetailsForContent() throws RepositoryException {		
		viewAllPojo.setPageName("pageName");
		globalNavigationPojo.setDisplayShopByValue(false);
		globalNavigationPojo.setPrimaryNavTitleLink("/content/mattel/mattel-corp");
		globalNavigationPojo.setDevice("desktop");
		navigationUtil.setCategoryDetailsForContent(viewAllPojo, resource, globalNavigationPojo);
		globalNavigationPojo.setDisplayShopByValue(true);
		navigationUtil.setCategoryDetailsForContent(viewAllPojo, resource, globalNavigationPojo);
		globalNavigationPojo.setPrimaryNavTitleLink("/mattel-corp");
		navigationUtil.setCategoryDetailsForContent(viewAllPojo, resource, globalNavigationPojo);
		globalNavigationPojo.setPrimaryNavTitleLink("");
		navigationUtil.setCategoryDetailsForContent(viewAllPojo, resource, globalNavigationPojo);
		globalNavigationPojo.setDevice("mobile");
		navigationUtil.setCategoryDetailsForContent(viewAllPojo, resource, globalNavigationPojo);
	}

	@Test
	public void getSecondaryCategoryTitle() throws RepositoryException {	
		globalNavigationPojo.setDisplayShopByValue(true);
		globalNavigationPojo.setPrimaryNavTitleLink("/content/mattel/mattel-corp");
		navigationUtil.setCategoryDetailsForContent(viewAllPojo, resource, globalNavigationPojo);
		globalNavigationPojo.setPrimaryNavTitleLink("/mattel-corp");
		String[] pageList = new String[] { "/content/fisher-price/language-masters/en/home/category/baby-gear" };
		globalNavigationPojo.setNavigationalLinks(pageList);
		globalNavigationPojo.setCategorySectionNavLinks(parentLinksList);
		globalNavigationPojo.setDevice("desktop");
		globalNavigationPojo.setCategoryTitle("categoryTitle");
		navigationUtil.getSecondaryCategoryTitle( resource, globalNavigationPojo);
		globalNavigationPojo.setDevice("mobile");
		navigationUtil.getSecondaryCategoryTitle( resource, globalNavigationPojo);
		globalNavigationPojo.setDevice("tab");
		navigationUtil.getSecondaryCategoryTitle( resource, globalNavigationPojo);
	}

	@Test
	public void setSecondaryCategoryTitle() throws RepositoryException {
		globalNavigationPojo.setAeForPrimaryNavTitle("primaryNavTitle");
		globalNavigationPojo.setDisplayShopByValue(true);
		globalNavigationPojo.setCategoryTitleLink("/content/mattel/mattel-corp");
		String[] pageList = new String[] { "/content/fisher-price/language-masters/en/home/category/baby-gear" };
		globalNavigationPojo.setNavigationalLinks(pageList);
		globalNavigationPojo.setTemplateType(Constants.FISHER_PRICE_BRAND_TEMPLATE);
		globalNavigationPojo.setDevice("desktop");
		globalNavigationPojo.setCategorySectionNavLinks(parentLinksList);
		Mockito.when(resource.getValueMap()).thenReturn(pageProperties);
		Mockito.when(GlobalUtils.getValueMapNodeValue(pageProperties, "viewALL")).thenReturn("viewALL");
		Mockito.when(GlobalUtils.getValueMapNodeValue(pageProperties, "viewALLbuttonlink")).thenReturn("viewALLbuttonlink");
		Mockito.when(GlobalUtils.getValueMapNodeValue(pageProperties, "viewALLtargetURL")).thenReturn("viewALLtargetURL");
		Mockito.when(GlobalUtils.getValueMapNodeValue(pageProperties, "viewALLAlwaysText")).thenReturn("viewALLAlwaysText");
		navigationUtil.getSecondaryCategoryTitle(resource, pageList, "desktop", "categoryTitle", parentLinksList, parentLinksList, Constants.FISHER_PRICE_BRAND_TEMPLATE,"promoHoverImgPath");
		navigationUtil.getSecondaryCategoryTitle(resource, pageList , "mobile", "categoryTitle", parentLinksList, parentLinksList, Constants.FISHER_PRICE_BRAND_TEMPLATE,"promoHoverImgPath");
		navigationUtil.getSecondaryCategoryTitle(resource, pageList , "tab", "categoryTitle", parentLinksList, parentLinksList, Constants.FISHER_PRICE_BRAND_TEMPLATE,"promoHoverImgPath");
		navigationUtil.getSecondaryCategoryTitle(resource, pageList, "desktop", "categoryTitle", parentLinksList, parentLinksList, Constants.FISHER_PRICE_AGE_TEMPLATE,"promoHoverImgPath");
		navigationUtil.getSecondaryCategoryTitle(resource, pageList, "desktop", "categoryTitle", parentLinksList, parentLinksList, Constants.FISHER_PRICE_EXPLORE_COLUMN_TWO,"promoHoverImgPath");		
		navigationUtil.getSecondaryCategoryTitle(resource, pageList, "desktop", "categoryTitle", parentLinksList, parentLinksList, Constants.FISHER_PRICE_EXPLORE_COLUMN_THREE,"promoHoverImgPath");
		navigationUtil.getSecondaryCategoryTitle(resource, pageList, "desktop", "categoryTitle", parentLinksList, parentLinksList, Constants.FISHER_PRICE_EXPLORE_COLUMN_FOUR,"promoHoverImgPath");
		List<SiteNavigationPojo> emptySiteNavigationPojo = new ArrayList<>();
		navigationUtil.getSecondaryCategoryTitle(resource, pageList, "desktop", "categoryTitle", emptySiteNavigationPojo, parentLinksList, Constants.FISHER_PRICE_BRAND_TEMPLATE,"promoHoverImgPath");

		Mockito.when(GlobalUtils.getValueMapNodeValue(pageProperties, "viewALLbuttonlink")).thenReturn(StringUtils.EMPTY);
		navigationUtil.getSecondaryCategoryTitle(resource, pageList, "desktop", "categoryTitle", parentLinksList, parentLinksList, Constants.FISHER_PRICE_BRAND_TEMPLATE,"promoHoverImgPath");
	}

	@Test
	public void setViewAllDetailsBlank() throws RepositoryException {
		navigationUtil.setViewAllDetails( viewAllPojo, "",null, "", "", resource);
	}



	@Test
	public void checkFeaturedNode() {
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.getResource(path)).thenReturn(featurePageResource);
		Mockito.when(featurePageResource.adaptTo(Page.class)).thenReturn(currentPage);
		navigationUtil.checkFeaturedNode(resource, parentLinksList);
		Mockito.when(featurePageResource.adaptTo(Page.class)).thenReturn(null);
		navigationUtil.checkFeaturedNode(resource, parentLinksList);
		Mockito.when(resolver.getResource(path)).thenReturn(null);
		navigationUtil.checkFeaturedNode(resource, parentLinksList);
		Mockito.when(multifieldReader.propertyReader(featuredNode)).thenReturn(null);
		navigationUtil.checkFeaturedNode(resource, parentLinksList);
		Mockito.when(resource.getChild("featuredSection")).thenReturn(null);
		navigationUtil.checkFeaturedNode(resource, parentLinksList);
	}

	@Test
	public void getImageSection() throws RepositoryException {
		Mockito.when(childResource.getParent()).thenReturn(parentResource);
		Mockito.when(parentResource.listChildren()).thenReturn(childIterator);
		navigationUtil.getImageSection(childResource, imageSectionList, limit);
	}
	
	@Test
	public void testGetTemplateVariationType() {
		viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constants.FISHER_PRICE_EXPLORE_COLUMN_TWO, "aeForPrimaryNavTitle", true, "mobile");
		Assert.assertEquals(Constants.SHOP_BY_AGE, viewAllPojo.getSubLinkClass());	
		viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constants.FISHER_PRICE_EXPLORE_COLUMN_TWO, "aeForPrimaryNavTitle", true, "desktop");
		Assert.assertEquals(Constants.EXPLORE, viewAllPojo.getSubLinkClass());	
		viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constants.FISHER_PRICE_BRAND_TEMPLATE, "aeForPrimaryNavTitle", true, "mobile");
		Assert.assertEquals(Constants.SHOP_BY_BRAND, viewAllPojo.getSubLinkClass());
		viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constants.FISHER_PRICE_EXPLORE_COLUMN_THREE, "aeForPrimaryNavTitle", false, "desktop");
		Assert.assertEquals(Constants.EXPLORE, viewAllPojo.getSubLinkClass());
		viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constants.FISHER_PRICE_EXPLORE_COLUMN_THREE, "aeForPrimaryNavTitle", false, "mobile");
		Assert.assertEquals(Constants.EXPLORE, viewAllPojo.getSubLinkClass());
	}
	
  @SuppressWarnings("unchecked")
  @Test
  public void testGetSecondaryCategoryTitle() {
    String[] navigationalLinks = {"navlink1"};
    List<SiteNavigationPojo> categorySectionNavLinks = Mockito.mock(List.class);
    List<SiteNavigationPojo> secondaryNavLinks = Mockito.mock(List.class);
    
    navigationUtil.getSecondaryCategoryTitle(categoryResource, navigationalLinks, device,
        "category1", categorySectionNavLinks, secondaryNavLinks, "templateType", "promoHoverImgPath");
  }
  
  @SuppressWarnings("unchecked")
  @Test
  public void testGetPageProperties(){
    String[] navigationalLinks = {"navlink1"};
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    List<SiteNavigationPojo> categorySectionNavLinks = Mockito.mock(List.class);
    Mockito.when(resourceResolver.getResource("navlink1")).thenReturn(resource);
    Mockito.when(resource.adaptTo(Page.class)).thenReturn(currentPage);
    navigationUtil.getPageProperties(navigationalLinks, resourceResolver , limit, categorySectionNavLinks , "promoHoverImagePath");
  }
}
