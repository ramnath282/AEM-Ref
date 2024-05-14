package com.mattel.ecomm.core.services;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.day.cq.wcm.api.NameConstants;
import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.services.StoreInterestImpl.Config;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
@RunWith(MockitoJUnitRunner.class)
public class StoreInterestImplTest {

  @InjectMocks
  StoreInterestImpl storeInterestImpl;
  @Mock
  private ResourceResolverFactory resourceResolverFactory;
  @Mock
  private ResourceResolver resourceResolver;
  @Mock
  private Resource resource;
  @Mock
  private Page page;
  @Mock
  private Iterator<Page> pages;
  @Mock
  private ValueMap valueMap;
  @Mock
  private Config config;
  String storePageRootPath= "/content/ag/en/retail";
  @Before
  public void setUp() throws Exception {
    Mockito.when(resourceResolverFactory.getServiceResourceResolver(Mockito.any())).thenReturn(resourceResolver);
    Mockito.when(resourceResolver.getResource(Mockito.anyString())).thenReturn(resource);
    Mockito.when(resource.adaptTo(Page.class)).thenReturn(page);
    Mockito.when(page.listChildren()).thenReturn(pages);
    Mockito.when(pages.hasNext()).thenReturn(true,false);
    Mockito.when(pages.next()).thenReturn(page);
    Mockito.when(page.getProperties()).thenReturn(valueMap);
    Mockito.when(valueMap.get(NameConstants.NN_TEMPLATE,String.class)).thenReturn(Constant.RETAIL_STORE_PAGE_TEMPLATE_PATH);
    Mockito.when(valueMap.get("storeKey", String.class)).thenReturn("AG");
    Mockito.when(valueMap.containsKey("storeKey")).thenReturn(true);
    Mockito.when(valueMap.get("storeLocation", String.class)).thenReturn("Atlanda");
    Mockito.when(page.getTitle()).thenReturn("testTitle");
    Mockito.when(config.storePageRootPath()).thenReturn(storePageRootPath);
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetStoreInterest() throws ServiceException, JSONException {
    storeInterestImpl.activate(config);
    JSONArray storeInterest = storeInterestImpl.getStoreInterest();
    Assert.assertNotNull(storeInterest);
  }
}
