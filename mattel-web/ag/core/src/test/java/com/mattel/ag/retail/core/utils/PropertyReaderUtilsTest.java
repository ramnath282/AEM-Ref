package com.mattel.ag.retail.core.utils;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mattel.ag.retail.core.utils.PropertyReaderUtils.Config;

public class PropertyReaderUtilsTest {
  PropertyReaderUtils propertyReaderUtils;

  Config config;

  @Before
  public void setUp() {
    propertyReaderUtils = new PropertyReaderUtils();
    config = Mockito.mock(PropertyReaderUtils.Config.class);
    propertyReaderUtils.setRetailQuestionairreBaseURL("ScriptUrl");
    propertyReaderUtils.setPersonalShopperBaseURL("subscriptionId");
    propertyReaderUtils.setBazaarVoiceBaseURL("testUrl");
    propertyReaderUtils.setContentDamPath("subscriptionId");
    propertyReaderUtils.setTealiumUrl("tealiumUrl");
    propertyReaderUtils.setEventsPath("eventPath");
    propertyReaderUtils.setAgStoreTagsPath("agStoreTagsPath");
    propertyReaderUtils.setTargetUrl("targetUrl");
    propertyReaderUtils.setTargetSnpUrl("/targetSnpUrl");
    propertyReaderUtils.setSnpParams("snpParams");
    propertyReaderUtils.setTypeAheadAccountURLs("typeAheadAccountURLs");
    propertyReaderUtils.setSnpAccountURLs("/snpAccountURLs");
  }
  
  @Test
  public void testConfigForGetTargetUrl() {
    when(config.targetUrl())
        .thenReturn(propertyReaderUtils.getTargetUrl());
    propertyReaderUtils.activate(config);
    assertSame("targetUrl", propertyReaderUtils.getTargetUrl());
  }
  
  @Test
  public void testConfigForGetTypeAheadAccountURLs() {
    when(config.typeAheadAccountURLs())
        .thenReturn(propertyReaderUtils.getTypeAheadAccountURLs());
    propertyReaderUtils.activate(config);
    assertSame("typeAheadAccountURLs", propertyReaderUtils.getTypeAheadAccountURLs());
  }
  
  @Test
  public void testConfigForGetSnpAccountURLs() {
    when(config.snpAccountURLs())
        .thenReturn(propertyReaderUtils.getSnpAccountURLs());
    propertyReaderUtils.activate(config);
    assertSame("/snpAccountURLs", propertyReaderUtils.getSnpAccountURLs());
  }
  
  @Test
  public void testConfigForGetSnpParams() {
    when(config.snpParams())
        .thenReturn(propertyReaderUtils.getSnpParams());
    propertyReaderUtils.activate(config);
    assertSame("snpParams", propertyReaderUtils.getSnpParams());
  }
  
  @Test
  public void testConfigForGetTargetSnpUrl() {
    when(config.targetSnpUrl())
        .thenReturn(propertyReaderUtils.getTargetSnpUrl());
    propertyReaderUtils.activate(config);
    assertSame("/targetSnpUrl", propertyReaderUtils.getTargetSnpUrl());

  }
  
  @Test
  public void testConfigForGetRetailQuestionairreBaseURL() {
    when(config.retailQuestionairreBaseURL())
        .thenReturn(propertyReaderUtils.getRetailQuestionairreBaseURL());
    propertyReaderUtils.activate(config);
    assertSame("ScriptUrl", propertyReaderUtils.getRetailQuestionairreBaseURL());

  }
  
  @Test
  public void testConfigForGetPersonalShopperBaseURL() {
    when(config.personalShopperBaseURL())
        .thenReturn(propertyReaderUtils.getPersonalShopperBaseURL());
    propertyReaderUtils.activate(config);
    assertSame("subscriptionId", propertyReaderUtils.getPersonalShopperBaseURL());

  }

  @Test
  public void testConfigForGetBazaarVoiceBaseURL() {
    when(config.bazaarVoiceBaseURL()).thenReturn(propertyReaderUtils.getBazaarVoiceBaseURL());
    propertyReaderUtils.activate(config);
    assertSame("testUrl", propertyReaderUtils.getBazaarVoiceBaseURL());

  }

  @Test
  public void testConfigForGetContentDamPath() {
    when(config.contentDamPath()).thenReturn(propertyReaderUtils.getContentDamPath());
    propertyReaderUtils.activate(config);
    assertSame("subscriptionId", propertyReaderUtils.getContentDamPath());

  }

  @Test
  public void testgetTealiumUrl() {
    when(config.tealiumUrl()).thenReturn(propertyReaderUtils.getTealiumUrl());
    propertyReaderUtils.activate(config);
    assertSame("tealiumUrl", propertyReaderUtils.getTealiumUrl());
  }

  @Test
  public void testgetEventsPath() {
    when(config.eventsPath()).thenReturn(propertyReaderUtils.getEventsPath());
    propertyReaderUtils.activate(config);
    assertSame("eventPath", propertyReaderUtils.getEventsPath());
  }

  @Test
  public void testAgStoreTagsPath() {
    when(config.agStoreTagPath()).thenReturn(propertyReaderUtils.getAgStoreTagsPath());
    propertyReaderUtils.activate(config);
    assertSame("agStoreTagsPath", propertyReaderUtils.getAgStoreTagsPath());
  }
}
