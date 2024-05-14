package com.mattel.ecomm.core.utils;

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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageFilter;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.pojos.CategoryColumnPojo;
import com.mattel.ecomm.core.pojos.PromoImagePojo;
import com.mattel.ecomm.core.pojos.SiteNavigationPojo;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EcommHelper.class)
public class NavigationUtilTest {
    @InjectMocks
    private NavigationUtil navigationUtil;
    @Mock
    private Resource resource;
    @Mock
    private ResourceResolver resolver;

    MultifieldReader multifieldReader;
    Page page;
    Node childNode;
    String title = "page";
    String path = "/content/fisher-price/language-masters/en/home/category/baby-gear";
    String categoryLink = "/content/fisher-price/language-masters/en/home/category";
    String text = path;
    String childPath = "/content/experience-fragments/fisher-price/en/header/master/jcr:content/root/siteheader/navLinks/navigationimage";
    ValueMap pageProperties;
    SiteNavigationPojo viewAllPojo;
    String viewAllText = "viewAll";
    String viewAllLink = "/content/fisher-price/language-masters/en/home/category/baby-gear";
    String linkTargetForViewAll = "new Window";
    String aeForViewAll = "Always English";
    ValueMap nodeValues, secondaryLinkValueMap;
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
    Page currentPage, childPage;
    Boolean defaultBoolean = false;
    String device = "mobile";
    List<SiteNavigationPojo> parentLinksList = new LinkedList<>();
    PageFilter pageFilter;
    ValueMap valueMap;
    Map<String, ValueMap> multifieldProperty;
    Map.Entry<String, ValueMap> entry;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws Exception {
        page = Mockito.mock(Page.class);
        categoryPage = Mockito.mock(Page.class);
        currentPage = Mockito.mock(Page.class);
        childPage = Mockito.mock(Page.class);
        childNode = Mockito.mock(Node.class);
        defaultNode = Mockito.mock(Node.class);
        viewAllPojo = new SiteNavigationPojo();
        pageProperties = Mockito.mock(ValueMap.class);
        multifieldReader = Mockito.mock(MultifieldReader.class);
        nodeValues = Mockito.mock(ValueMap.class);
        secondaryLinkValueMap = Mockito.mock(ValueMap.class);
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
        Mockito.when(EcommHelper.checkPropertyObject(page.getProperties().get("cq:redirectTarget"))).thenReturn(path);
        Mockito.when(EcommHelper.checkPropertyObject(page.getProperties().get("navThumbImage")))
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
        Mockito.when(EcommHelper.checkForProperty(defaultNode, "width")).thenReturn("6");
        Mockito.when(EcommHelper.checkForProperty(childNode, "promoButtonLink")).thenReturn(path);
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
        Mockito.when(EcommHelper.checkForProperty(headerNode, "shopByValue")).thenReturn("Shop By");
        Mockito.when(resolver.getResource(subCatLink)).thenReturn(categoryResource);
        Mockito.when(categoryResource.adaptTo(Page.class)).thenReturn(categoryPage);
        Mockito.when(categoryPage.getTitle()).thenReturn("Category");
        Mockito.when(categoryPage.getNavigationTitle()).thenReturn("Shop By Category");
        Mockito.when(categoryPage.getProperties()).thenReturn(nodeValues);
        Mockito.when(categoryPage.getProperties().get("cq:isNotLinkable", Boolean.class)).thenReturn(false);
        Mockito.when(EcommHelper.checkBooleanProperty(categoryPage, "cq:isNotLinkable", defaultBoolean))
                .thenReturn(false);
        Mockito.when(currentPage.getTitle()).thenReturn(title);
        Mockito.when(currentPage.getNavigationTitle()).thenReturn("");
        Mockito.when(currentPage.getPath()).thenReturn(path);
        Mockito.when(currentPage.getProperties()).thenReturn(pageProperties);
        Mockito.when(currentPage.getProperties().get("cq:redirectTarget"))
                .thenReturn("/content/fisher-price/language-masters/en/home/category/baby-gear");
        Mockito.when(EcommHelper.checkPropertyObject(currentPage.getProperties().get("cq:redirectTarget")))
                .thenReturn(path);
        Mockito.when(EcommHelper.checkPropertyObject(currentPage.getProperties().get("navThumbImage")))
                .thenReturn("/content/dam/fp-dam/fisher-price/navigation/brand/Brand_img1@2x.jpg");
        Mockito.when(resolver.map(text)).thenReturn(text);
        Mockito.when(resolver.getResource(categoryLink)).thenReturn(categoryResource);
        Mockito.when(resource.getChild("featuredSection")).thenReturn(featuredResource);
        Mockito.when(featuredResource.adaptTo(Node.class)).thenReturn(featuredNode);
        Mockito.when(multifieldReader.propertyReader(featuredNode)).thenReturn(multifieldProperty);
        Mockito.when(entry.getValue()).thenReturn(valueMap);
        Mockito.when(entry.getValue().get("featuredLinks", String.class)).thenReturn(path);
    }

    @Test
    public void fetchPageProperties() {
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
    }

    @Test
    public void checkForImages() throws RepositoryException {
        navigationUtil.checkForImages(childResource, childIterator, imageSectionList, limit);
    }

    @Test
    public void getShopByValue() throws RepositoryException {
        String shopByValue = navigationUtil.getShopByValue(resource);
        Assert.assertEquals("Shop By", shopByValue);
    }

    @Test
    public void fetchCategoryPageTitle() {
        navigationUtil.fetchCategoryPageTitle(columnPojo, subCatLink, subCatTitle, resolver);
    }

    @Test
    public void getPageDetails() {
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
    }

    @Test
    public void fetchColumnLinksWithChildLinks() {
        columnDetails.setColumnListFrom("children");
        columnDetails.setParentPage(path);
        columnDetails.setSubCatLink(path);
        columnDetails.setSubCatTitle("subCatTitle");
        String[] pageList = new String[] { "/content/fisher-price/language-masters/en/home/category/baby-gear" };
        columnDetails.setParentPageList(pageList);
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.getResource(path)).thenReturn(parentPageResource);
        Mockito.when(parentPageResource.adaptTo(Page.class)).thenReturn(currentPage);
        List<Page> childPageList = new ArrayList<>();
        childPageList.add(page);
        Mockito.when(currentPage.listChildren(Mockito.any(), Mockito.eq(false))).thenReturn(childPageList.iterator());
        List<SiteNavigationPojo> parentLinkList = navigationUtil.fetchColumnLinks(columnDetails, parentLinksList,
                resource, device);
        Assert.assertNotNull(parentLinkList);
    }

    @Test
    public void fetchColumnLinksForDesktop() {
        columnDetails.setColumnListFrom("children");
        columnDetails.setParentPage(path);
        columnDetails.setSubCatLink(path);
        columnDetails.setSubCatTitle("subCatTitle");
        String[] pageList = new String[] { "/content/fisher-price/language-masters/en/home/category/baby-gear" };
        columnDetails.setParentPageList(pageList);
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.getResource(path)).thenReturn(parentPageResource);
        Mockito.when(parentPageResource.adaptTo(Page.class)).thenReturn(currentPage);
        List<Page> childPageList = new ArrayList<>();
        childPageList.add(page);
        Mockito.when(currentPage.listChildren(Mockito.any(), Mockito.eq(false))).thenReturn(childPageList.iterator());
        List<SiteNavigationPojo> parentLinkList = navigationUtil.fetchColumnLinks(columnDetails, parentLinksList,
                resource, "desktop");
        Assert.assertNotNull(parentLinkList);
    }

    @Test
    public void getSecondaryCategoryTitle() {
        String[] navigationalLinks = { "/content/ag/testPage" };
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        secondaryLinkValueMap.put("viewALL", "viewAllText");
        Mockito.when(resource.getValueMap()).thenReturn(secondaryLinkValueMap);
        parentLinksList.add(new SiteNavigationPojo());
        Mockito.when(secondaryLinkValueMap.containsKey("viewALL")).thenReturn(true);
        Mockito.when(secondaryLinkValueMap.get("viewALL", String.class)).thenReturn("text");
        Mockito.when(secondaryLinkValueMap.containsKey("viewALLbuttonlink")).thenReturn(true);
        Mockito.when(secondaryLinkValueMap.get("viewALLbuttonlink", String.class)).thenReturn("buttonLink");
        navigationUtil.getSecondaryCategoryTitle(resource, navigationalLinks, device, "categoryTitle", parentLinksList,
                parentLinksList, Constant.FISHER_PRICE_BRAND_TEMPLATE);

    }

    @Test
    public void getSecondaryCategoryTitleForDesktop() {
        String[] navigationalLinks = { "/content/ag/testPage" };
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        secondaryLinkValueMap.put("viewALL", "viewAllText");
        Mockito.when(resource.getValueMap()).thenReturn(secondaryLinkValueMap);
        parentLinksList.add(new SiteNavigationPojo());
        Mockito.when(secondaryLinkValueMap.containsKey("viewALL")).thenReturn(true);
        Mockito.when(secondaryLinkValueMap.get("viewALL", String.class)).thenReturn("text");
        Mockito.when(secondaryLinkValueMap.containsKey("viewALLbuttonlink")).thenReturn(true);
        Mockito.when(secondaryLinkValueMap.get("viewALLbuttonlink", String.class)).thenReturn("buttonLink");
        Mockito.when(resolver.getResource("/content/ag/testPage")).thenReturn(resource);
        Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
        navigationUtil.getSecondaryCategoryTitle(resource, navigationalLinks, "desktop", "categoryTitle",
                parentLinksList, parentLinksList, Constant.FISHER_PRICE_AGE_TEMPLATE);

    }

    @Test
    public void getSecondaryCategoryTitleWithExploreTemplate() {
        String[] navigationalLinks = { "/content/ag/testPage" };
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        secondaryLinkValueMap.put("viewALL", "viewAllText");
        Mockito.when(resource.getValueMap()).thenReturn(secondaryLinkValueMap);
        parentLinksList.add(new SiteNavigationPojo());
        Mockito.when(secondaryLinkValueMap.containsKey("viewALL")).thenReturn(true);
        Mockito.when(secondaryLinkValueMap.get("viewALL", String.class)).thenReturn("text");
        Mockito.when(secondaryLinkValueMap.containsKey("viewALLbuttonlink")).thenReturn(true);
        Mockito.when(secondaryLinkValueMap.get("viewALLbuttonlink", String.class)).thenReturn("buttonLink");
        Mockito.when(resolver.getResource("/content/ag/testPage")).thenReturn(resource);
        Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
        navigationUtil.getSecondaryCategoryTitle(resource, navigationalLinks, device, "categoryTitle", parentLinksList,
                parentLinksList, Constant.FISHER_PRICE_EXPLORE_COLUMN_FOUR);

    }

    @Test
    public void getSecondaryCategoryTitleWithFPExploreTemplate() {
        String[] navigationalLinks = { "/content/ag/testPage" };
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        secondaryLinkValueMap.put("viewALL", "viewAllText");
        Mockito.when(resource.getValueMap()).thenReturn(secondaryLinkValueMap);
        parentLinksList.add(new SiteNavigationPojo());
        Mockito.when(secondaryLinkValueMap.containsKey("viewALL")).thenReturn(true);
        Mockito.when(secondaryLinkValueMap.get("viewALL", String.class)).thenReturn("text");
        Mockito.when(secondaryLinkValueMap.containsKey("viewALLbuttonlink")).thenReturn(true);
        Mockito.when(secondaryLinkValueMap.get("viewALLbuttonlink", String.class)).thenReturn("buttonLink");
        Mockito.when(resolver.getResource("/content/ag/testPage")).thenReturn(resource);
        Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
        navigationUtil.getSecondaryCategoryTitle(resource, navigationalLinks, device, "categoryTitle", parentLinksList,
                parentLinksList, Constant.FISHER_PRICE_EXPLORE_COLUMN_THREE);

    }

    @Test
    public void setCategoryDetails() throws RepositoryException {
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        navigationUtil.setCategoryDetails(columnPojo, resource, true, categoryLink, "Category", "same", device);
        navigationUtil.setCategoryDetails(columnPojo, resource, true, "", "Category", "same", "desktop");
    }

    @Test
    public void checkFeaturedNode() {
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.getResource(path)).thenReturn(featurePageResource);
        Mockito.when(featurePageResource.adaptTo(Page.class)).thenReturn(currentPage);
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
        viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constant.FISHER_PRICE_EXPLORE_COLUMN_TWO,
                "aeForPrimaryNavTitle", true, "mobile");
        Assert.assertEquals(Constant.SHOP_BY_AGE, viewAllPojo.getSubLinkClass());
        viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constant.FISHER_PRICE_EXPLORE_COLUMN_TWO,
                "aeForPrimaryNavTitle", true, "desktop");
        Assert.assertEquals(Constant.EXPLORE, viewAllPojo.getSubLinkClass());
        viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constant.FISHER_PRICE_BRAND_TEMPLATE,
                "aeForPrimaryNavTitle", true, "mobile");
        Assert.assertEquals(Constant.SHOP_BY_BRAND, viewAllPojo.getSubLinkClass());
        viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constant.FISHER_PRICE_EXPLORE_COLUMN_THREE,
                "aeForPrimaryNavTitle", false, "desktop");
        Assert.assertEquals(Constant.EXPLORE, viewAllPojo.getSubLinkClass());
        viewAllPojo = navigationUtil.getTemplateVariationType(viewAllPojo, Constant.FISHER_PRICE_EXPLORE_COLUMN_THREE,
                "aeForPrimaryNavTitle", false, "mobile");
        Assert.assertEquals(Constant.EXPLORE, viewAllPojo.getSubLinkClass());
    }
}
