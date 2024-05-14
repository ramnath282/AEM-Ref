package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.pojo.CategoryColumnPojo;
import com.mattel.global.core.pojo.SiteNavigationPojo;
import com.mattel.global.core.utils.NavigationUtil;

@RunWith(PowerMockRunner.class)
public class CategoryNavigationServiceImplTest {
    @InjectMocks
    CategoryNavigationServiceImpl categoryNavigationServiceImpl;
    @Mock
    NavigationUtil navigationUtil;
    @Mock
    SlingHttpServletRequest request;
    @Mock
    Resource currentresource;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    ValueMap propertyMap;

    private static final String DISPLAY_SHOP_VALUE = "displayShopByValue";
    private static final String DEVICE_TYPE = "mobile";

    @Test
    public void testCategoryNavLinks() throws RepositoryException {
        Mockito.when(currentresource.getResourceResolver()).thenReturn(resourceResolver);

        Mockito.when(currentresource.adaptTo(ValueMap.class)).thenReturn(propertyMap);
        Mockito.when(propertyMap.containsKey(DISPLAY_SHOP_VALUE)).thenReturn(true);
        Mockito.when(propertyMap.get(DISPLAY_SHOP_VALUE, Boolean.class)).thenReturn(true);
        Mockito.when(propertyMap.get("categoryTitle", String.class)).thenReturn("doll");
        Mockito.when(propertyMap.get("linkTargetCategory", String.class)).thenReturn("bitty-baby");
        Mockito.when(propertyMap.get("aeForCategoryTitle", String.class)).thenReturn("aeCategoryTitle");
        CategoryColumnPojo columnDetails = new CategoryColumnPojo();
        Mockito.when(navigationUtil.fetchColumnDetails(Mockito.any(), Mockito.any())).thenReturn(columnDetails);
        SiteNavigationPojo categoryLinksPojo = new SiteNavigationPojo();
        List<SiteNavigationPojo> childPageList = new ArrayList<>();
        SiteNavigationPojo childSiteNavigationPojo = new SiteNavigationPojo();
        childPageList.add(childSiteNavigationPojo);
        categoryLinksPojo.setChildPageList(childPageList);
        List<SiteNavigationPojo> parentLinksList = new ArrayList<>();
        parentLinksList.add(childSiteNavigationPojo);
        Mockito.when(navigationUtil.setCategoryDetails(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(categoryLinksPojo);
        Mockito.when(navigationUtil.fetchColumnLinks(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(parentLinksList);

        JSONObject categoryNavJson = categoryNavigationServiceImpl.processNavLinks(DEVICE_TYPE, currentresource, request);
        Assert.assertNotNull(categoryNavJson);
    }

    @Test
    public void testCategoryNavLinksWithRepositoryException() throws RepositoryException {
        Mockito.when(currentresource.getResourceResolver()).thenThrow(RepositoryException.class);
        categoryNavigationServiceImpl.processNavLinks(DEVICE_TYPE, currentresource, request);
    }

    @Test
    public void testCategoryNavLinksWithNullPointerException() throws NullPointerException {
        Mockito.when(currentresource.getResourceResolver()).thenReturn(resourceResolver);
        Mockito.when(currentresource.adaptTo(ValueMap.class)).thenReturn(propertyMap);
        Mockito.when(propertyMap.containsKey(DISPLAY_SHOP_VALUE)).thenReturn(true);
        Mockito.when(propertyMap.get(DISPLAY_SHOP_VALUE, Boolean.class)).thenReturn(true);
        Mockito.when(propertyMap.get("categoryTitle", String.class)).thenReturn("doll");
        Mockito.when(propertyMap.get("linkTargetCategory", String.class)).thenReturn("bitty-baby");
        Mockito.when(propertyMap.get("aeForCategoryTitle", String.class)).thenReturn("aeCategoryTitle");
        Mockito.when(navigationUtil.fetchColumnDetails(Mockito.any(), Mockito.any())).thenReturn(null);
        categoryNavigationServiceImpl.processNavLinks(DEVICE_TYPE, currentresource, request);
    }

}
