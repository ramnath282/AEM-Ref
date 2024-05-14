package com.mattel.ecomm.core.helper;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@RunWith(MockitoJUnitRunner.class)
public class EcommHelperTest {

    @InjectMocks
    EcommHelper ecommHelper;

    @Mock
    Resource resource, pageRes, pageContentRes;
    @Mock
    ResourceResolver resolver;
    @Mock
    Asset asset;
    @Mock
    Node node;
    @Mock
    Page page, parentPage;
    @Mock
    Property nodeProperties;
    @Mock
    QueryBuilder queryBuilder;
    @Mock
    Session session;
    @Mock
    PredicateGroup predicateGroup;
    @Mock
    Query productQuery;
    @Mock
    SearchResult searchResult;
    @Mock
    PageManager pageManager;
    @Mock
    Hit hit;
    @Mock
    SlingHttpServletRequest request;
    @Mock
    private RequestPathInfo pathInfo;

    private static final String PRODUCT_IDENTIFIER = "blender-milkshake-set-drg53";
    private static final String PRODUCT_LINK_PROPERTY = "pdpLink";
    private static final String AG_SHOP_PAGE_PATH = "/content/ag/en/shop/home";
    private static final String PRODUCT_PROPERTY = "isPdpLink";
    private static final String IMAGE_ASSET_PROPERTY = "image";
    private static final String PDP_PAGE_PATH = "/shop/ag/dolls/girl-of-the-year/simply-spring-dress-for-girls-gbm03.html";
    private static final String PAGE_PATH = "/content/ag/en/shop/categories/dolls";

    @Test
    public void testCheckLink() {
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.map(AG_SHOP_PAGE_PATH)).thenReturn(AG_SHOP_PAGE_PATH);
        String modifiedtext = EcommHelper.checkLink(AG_SHOP_PAGE_PATH, resource);
        Assert.assertEquals(AG_SHOP_PAGE_PATH + ".html", modifiedtext);
    }

    @Test
    public void testCheckLinkwithNull() {
        String modifiedtext = EcommHelper.checkLink(null, resource);
        Assert.assertNull(modifiedtext);
    }

    @Test
    public void testCheckLinkwithEmpty() {
        String modifiedLink = EcommHelper.checkLink("", resource);
        Assert.assertEquals("", modifiedLink);
    }

    @Test
    public void testCheckLinkwithParam() {
        String pageLink = AG_SHOP_PAGE_PATH + "#param=value";
        String modifiedLink = EcommHelper.checkLink(pageLink, resource);
        Assert.assertEquals(AG_SHOP_PAGE_PATH + ".html#param=value", modifiedLink);
    }

    @Test
    public void testGetValueMapNodeValue() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "Home");
        ValueMap vm = new ValueMapDecorator(map);
        String value = EcommHelper.getValueMapNodeValue(vm, "key");
        Assert.assertEquals("", value);
    }

    @Test
    public void testIsNullOrEmpty() {
        String[] navigationlink = { "/content/ag/en/home.html", "/content/ag/en/shot.html" };
        Boolean value = EcommHelper.isNullOrEmpty(navigationlink);
        Assert.assertFalse(value);
    }

    @Test
    public void testIsNullOrEmptywithZero() {
        String[] navigationlink = {};
        Boolean value = EcommHelper.isNullOrEmpty(navigationlink);
        Assert.assertTrue(value);
    }

    @Test
    public void testIsNullOrEmptywithEmpty() {
        String[] navigationlink = null;
        Boolean value = EcommHelper.isNullOrEmpty(navigationlink);
        Assert.assertTrue(value);
    }

    @Test
    public void testCheckPropertyObject() {
        Object value = null;
        String val = EcommHelper.checkPropertyObject(value);
        Assert.assertEquals("", val);
    }

    @Test
    public void testCheckPropertyObjectWithValue() {
        Object value = new Object();
        String val = EcommHelper.checkPropertyObject(value);
        Assert.assertNotNull(val);
    }

    @Test
    public void testCheckForPropertyAssetString() {
        Mockito.when(asset.getMetadata(IMAGE_ASSET_PROPERTY)).thenReturn("ProductImage");
        Mockito.when(asset.getMetadataValue(IMAGE_ASSET_PROPERTY)).thenReturn("assetMetadata");
        String value = EcommHelper.checkForProperty(asset, IMAGE_ASSET_PROPERTY);
        Assert.assertNotNull(value);
    }

    @Test
    public void testCheckForPropertyAssetStringWithNull() {
        String value = EcommHelper.checkForProperty(asset, IMAGE_ASSET_PROPERTY);
        Assert.assertEquals("", value);
    }

    @Test
    public void testCheckForPropertyNodeString() throws RepositoryException {
        Mockito.when(node.hasProperty((Mockito.any(String.class)))).thenReturn(true);
        Mockito.when(node.getProperty(Mockito.any(String.class))).thenReturn(nodeProperties);
        Mockito.when(nodeProperties.getString()).thenReturn("EcommPage");
        String propertyValue = EcommHelper.checkForProperty(node, "jcr:title");
        Assert.assertNotNull(propertyValue);
    }

    @Test
    public void testCheckForPropertyNodeStringWithNull() throws RepositoryException {
        Mockito.when(node.hasProperty((Mockito.any(String.class)))).thenReturn(false);
        String propertyValue = EcommHelper.checkForProperty(node, "jcr:title");
        Assert.assertEquals("", propertyValue);
    }

    @Test
    public void testCheckBooleanProperty() {
        ValueMap currentPageProperties = Mockito.mock(ValueMap.class);
        Mockito.when(page.getProperties()).thenReturn(currentPageProperties);
        Mockito.when(currentPageProperties.get(PRODUCT_PROPERTY, Boolean.class)).thenReturn(true);
        Boolean val = EcommHelper.checkBooleanProperty(page, PRODUCT_PROPERTY, true);
        Assert.assertTrue(val);
    }

    @Test
    public void testCheckBooleanPropertyWithNull() {
        ValueMap currentPageProperties = Mockito.mock(ValueMap.class);
        Mockito.when(page.getProperties()).thenReturn(currentPageProperties);
        Boolean val = EcommHelper.checkBooleanProperty(page, "isVideoLink", true);
        Assert.assertTrue(val);
    }

    @Test
    public void testGetLeakingResResolver() {
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(resource);
        Mockito.when(searchResult.getResources()).thenReturn(resourceList.iterator());
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.isLive()).thenReturn(true);
        EcommHelper.getLeakingResResolver(searchResult);
    }

    @Test
    public void testFetchProductSKUId() {
        Mockito.when(request.getRequestURI()).thenReturn(PRODUCT_IDENTIFIER + ".html");
        String productSKUId = EcommHelper.fetchProductSKUId(request);
        Assert.assertEquals("DRG53", productSKUId);
    }

    @Test
    public void testFetchProductSKUIdWithResourcePath() {
        Mockito.when(request.getRequestURI()).thenReturn(PDP_PAGE_PATH);
        String productSKUId = EcommHelper.fetchProductSKUId(request);
        Assert.assertEquals("GBM03", productSKUId);

    }

    @Test
    public void testFetchProdCommerceNodeProperties() throws RepositoryException {
        ValueMap nodeProperties = Mockito.mock(ValueMap.class);
        nodeProperties.put(PRODUCT_LINK_PROPERTY, PRODUCT_IDENTIFIER);
        Mockito.when(hit.getProperties()).thenReturn(nodeProperties);
        Mockito.when(nodeProperties.get(PRODUCT_LINK_PROPERTY, String.class)).thenReturn(PRODUCT_IDENTIFIER);
        String productProperty = EcommHelper.fetchProdCommerceNodeProperties(hit, PRODUCT_LINK_PROPERTY);
        Assert.assertEquals(PRODUCT_IDENTIFIER, productProperty);
    }

    @Test
    public void testFetchProdCommerceNodePropertiesWithNull() throws RepositoryException {
        ValueMap nodeProperties = Mockito.mock(ValueMap.class);
        nodeProperties.put(PRODUCT_LINK_PROPERTY, PRODUCT_IDENTIFIER);
        Mockito.when(hit.getProperties()).thenReturn(nodeProperties);
        String productProperty = EcommHelper.fetchProdCommerceNodeProperties(hit, PRODUCT_LINK_PROPERTY);
        Assert.assertEquals("", productProperty);
    }

    @Test
    public void testGetBrandName() {
        Mockito.when(resource.getPath()).thenReturn("/content/ag/en/explore/jcr:content/root/richtextexplore");
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        Mockito.when(pageManager.getPage(Mockito.any(String.class))).thenReturn(page);
        Mockito.when(page.getAbsoluteParent(2)).thenReturn(parentPage);
        Mockito.when(parentPage.hasChild("language-masters")).thenReturn(true);
        Mockito.when(parentPage.getName()).thenReturn("AG");
        String brandName = EcommHelper.getBrandName(resource);
        Assert.assertEquals("AG", brandName);

    }

    @Test
    public void testProductPageProperties() {
        ValueMap currentPageProperties = Mockito.mock(ValueMap.class);
        Mockito.when(page.getProperties()).thenReturn(currentPageProperties);
        Mockito.when(currentPageProperties.get("domainKey", String.class)).thenReturn("AG");
        String domainKey = EcommHelper.productPageProperties(page, "domainKey");
        Assert.assertEquals("AG", domainKey);
    }

    @Test
    public void testProductPagePropertiesWithNullProperty() {
        ValueMap currentPageProperties = Mockito.mock(ValueMap.class);
        Mockito.when(page.getProperties()).thenReturn(currentPageProperties);
        String domainKey = EcommHelper.productPageProperties(page, "domainKey");
        Assert.assertEquals("", domainKey);
    }

    @Test
    public void testGetBooleanValuefromValueMap() {
        ValueMap currentPageProperties = Mockito.mock(ValueMap.class);
        Mockito.when(currentPageProperties.get(PRODUCT_PROPERTY, Boolean.class)).thenReturn(true);
        Boolean val = EcommHelper.getBooleanValuefromValueMap(currentPageProperties, PRODUCT_PROPERTY, true);
        Assert.assertTrue(val);
    }

    @Test
    public void testGetBooleanValuefromValueMapWithNullProperty() {
        ValueMap currentPageProperties = Mockito.mock(ValueMap.class);
        currentPageProperties.put("isPdpLink", "true");
        Boolean val = EcommHelper.getBooleanValuefromValueMap(currentPageProperties, PRODUCT_PROPERTY, false);
        Assert.assertFalse(val);
    }

    @Test
    public void testConvertSpecialCharacters() throws UnsupportedEncodingException {
        String src = null;
        src = EcommHelper.convertSpecialCharacters(null);
        assertEquals(StringUtils.EMPTY, src);
        src = EcommHelper.convertSpecialCharacters(StringUtils.EMPTY);
        assertEquals(StringUtils.EMPTY, src);
        src = EcommHelper.convertSpecialCharacters("testString");
        assertEquals("testString", src);
        src = EcommHelper.convertSpecialCharacters("testStringWithTrademark \u2122");
        assertEquals("testStringWithTrademark <sup>\u2122</sup>", src);
    }

    @Test
    public void testCheckIfRootShopPage() throws UnsupportedEncodingException {
        ValueMap vmap = Mockito.mock(ValueMap.class);
        vmap.put("isRootShopPage", "true");
        Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
        Mockito.when(resolver.resolve(PAGE_PATH)).thenReturn(pageRes);
        Mockito.when(pageRes.getPath()).thenReturn(PAGE_PATH);
        Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
        Mockito.when(pageManager.getPage(pageRes.getPath())).thenReturn(page);
        Mockito.when(page.getContentResource()).thenReturn(pageContentRes);
        Mockito.when(pageContentRes.getValueMap()).thenReturn(vmap);
        Mockito.when(vmap.get("isRootShopPage", String.class)).thenReturn("true");
        boolean flag = EcommHelper.checkIfRootShopPage(PAGE_PATH, resource);
        
        Assert.assertTrue(flag);
    }

}
