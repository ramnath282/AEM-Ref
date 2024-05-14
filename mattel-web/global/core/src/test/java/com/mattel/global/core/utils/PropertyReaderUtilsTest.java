package com.mattel.global.core.utils;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.PropertyReaderUtils.Config;

@RunWith(PowerMockRunner.class)
public class PropertyReaderUtilsTest {

  @InjectMocks
  PropertyReaderUtils propertyReaderUtils;

  @Mock
  Config config;
  
  
  private Map<String, String> appNameToNodePaths;
  @Mock
  private Map<String, String> appNameToRootPagePaths;
  @Mock
  private Map<String, String> siteKeyToSnPIndexUrls;
  
  
  
  @Before
  public void setup() throws IllegalArgumentException, IllegalAccessException{
      appNameToNodePaths = Mockito.mock(Map.class);
    String[] siteDomains = {"ag_en:https://mqa.americangirl.com"};
    MemberModifier.field(PropertyReaderUtils.class, "siteDomains").set(propertyReaderUtils, siteDomains);
  }

  @Test
  public void testGetterSetter() {
    propertyReaderUtils.setApiBaseUrl("apiBaseUrl");
    String[] brandMappings = { "brand1", "brand2" };
    propertyReaderUtils.setBrandMappings(brandMappings);
    propertyReaderUtils.setRootPageForFonts("rootPageForFonts");
    propertyReaderUtils.setScriptUrl("scriptUrl");
    propertyReaderUtils.setSubscriptionId("subscriptionId");

    Assert.assertNotNull(propertyReaderUtils.getApiBaseUrl());
    Assert.assertNotNull(propertyReaderUtils.getBrandMappings());
    Assert.assertNotNull(propertyReaderUtils.getRootPageForFonts());
    Assert.assertNotNull(propertyReaderUtils.getScriptUrl());
    Assert.assertNotNull(propertyReaderUtils.getSubscriptionId());
    Assert.assertNull(propertyReaderUtils.getShortneningConfigs());
  }
  
  @Test
  public void testGetSiteDomain(){
    propertyReaderUtils.getSiteDomain("fp");
  }
  
  @Test
  public void testGetSiteDomainMapping(){
    propertyReaderUtils.getSiteDomainMapping();
  }
  
  @Test
  public void testActivate(){
    Mockito.when(config.scriptUrl()).thenReturn("//assets.adobedtm.com/launch-ENf50179c631924d18b348fc46b3eca369-staging.min.js");
    Mockito.when(config.apiBaseUrl()).thenReturn("//user.mattel.com/");
    Mockito.when(config.subscriptionId()).thenReturn("351");
    Mockito.when(config.rootPageForFonts()).thenReturn("/content/crm/language-masters/brand-specific-fonts/jcr:content/root/brandspecificfonts");
    Mockito.when(config.brandMappings()).thenReturn(new String[] {"fp:/content/fisher-price/"});
    Mockito.when(config.siteDomains()).thenReturn(new String[] {"ag_en:https://mqa.americangirl.com"});
    Mockito.when(config.urlShortenConfig()).thenReturn(new String[] {"ag_en:https://mqa.americangirl.com"});
    propertyReaderUtils.activate(config);
  }
}
