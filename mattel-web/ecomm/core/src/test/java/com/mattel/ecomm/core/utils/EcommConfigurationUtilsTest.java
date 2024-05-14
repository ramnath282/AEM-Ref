package com.mattel.ecomm.core.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class EcommConfigurationUtilsTest {
  private EcommConfigurationUtils ecommConfigurationUtils;
  private String placeholder = "dummy_string";
  
  @Before
  public void setUp() throws Exception {
    ecommConfigurationUtils = new EcommConfigurationUtils();
    EcommConfigurationUtils.Config config = Mockito.mock(EcommConfigurationUtils.Config.class);
    
    Mockito.when(config.atPropertyTarget()).thenReturn(placeholder);
    Mockito.when(config.formAPIKey()).thenReturn(placeholder);
    Mockito.when(config.formAPIUrl()).thenReturn(placeholder);
    Mockito.when(config.prefAPIKey()).thenReturn(placeholder);
    Mockito.when(config.prefAPIUrl()).thenReturn(placeholder);
    Mockito.when(config.rootCatgoryPagePath()).thenReturn(placeholder);
    Mockito.when(config.rootDomainSizeChart()).thenReturn(placeholder);
    Mockito.when(config.scenesevenContentUrl()).thenReturn(placeholder);
    Mockito.when(config.scenesevenServerUrl()).thenReturn(placeholder);
    Mockito.when(config.scenesevenVideoserverUrl()).thenReturn(placeholder);
    Mockito.when(config.targetUrl()).thenReturn(placeholder);
    Mockito.when(config.tealiumUrl()).thenReturn(placeholder);
    Mockito.when(config.usableNetScriptPath()).thenReturn(placeholder);
    Mockito.when(config.accessibilitySwitch()).thenReturn(true);
    ecommConfigurationUtils.activate(config);
  }

  @Test
  public void testGetTealiumUrl() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getTealiumUrl());
  }

  @Test
  public void testGetUsableNetScriptPath() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getUsableNetScriptPath());
  }

  @Test
  public void testGetAccessibilitySwitch() throws Exception {
    Assert.assertTrue(EcommConfigurationUtils.getAccessibilitySwitch());
  }

  @Test
  public void testGetTargetUrl() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getTargetUrl());
  }

  @Test
  public void testGetRootCatgoryPagePath() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getRootCatgoryPagePath());
  }

  @Test
  public void testGetRootDomainSizeChart() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getRootDomainSizeChart());
  }

  @Test
  public void testSetRootDomainSizeChart() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getRootDomainSizeChart());
  }

  @Test
  public void testGetScenesevenServerUrl() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getScenesevenServerUrl());
  }

  @Test
  public void testGetScenesevenContentUrl() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getScenesevenContentUrl());
  }

  @Test
  public void testGetScenesevenVideoserverUrl() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getScenesevenVideoserverUrl());
  }

  @Test
  public void testGetFormAPIUrl() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getFormAPIUrl());
  }

  @Test
  public void testGetFormAPIKey() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getFormAPIKey());
  }

  @Test
  public void testGetPrefAPIKey() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getPrefAPIKey());
  }

  @Test
  public void testGetPrefAPIUrl() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getPrefAPIUrl());
  }

  @Test
  public void testGetAtPropertyTarget() throws Exception {
    Assert.assertEquals(placeholder, EcommConfigurationUtils.getAtPropertyTarget());
  }
}
