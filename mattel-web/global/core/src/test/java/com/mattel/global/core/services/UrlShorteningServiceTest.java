package com.mattel.global.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.PropertyReaderUtils;
import com.mattel.global.master.core.model.UrlShorteningConfig;

@RunWith(PowerMockRunner.class)
public class UrlShorteningServiceTest {
  @InjectMocks
  private UrlShorteningService urlShorteningService;

  @Mock
  private PropertyReaderUtils propertyReaderUtils;

  UrlShorteningConfig urlShorteningConfig;

  @Test
  public void testTrasform1() {
    UrlShorteningConfig urlShorteningConfig = new UrlShorteningConfig();
    urlShorteningConfig.setFrom("CTS");
    urlShorteningConfig.setTo("client");
    urlShorteningConfig.setRemoveHtmlSuffix(true);
    Assert.assertNotNull(urlShorteningConfig.getFrom());
    Assert.assertNotNull(urlShorteningConfig.getTo());
    Assert.assertNotNull(urlShorteningConfig.isRemoveHtmlSuffix());
    urlShorteningConfig.toString();
    Mockito.when(propertyReaderUtils.getShortneningConfig("fp")).thenReturn(urlShorteningConfig);
    urlShorteningService.transform("fp", "testurl/CTS");
  }
  
  @Test
  public void testTrasform2() {
    List<String> urls = new ArrayList<String>();
    urls.add("url1");
    UrlShorteningConfig urlShorteningConfig = new UrlShorteningConfig();
    urlShorteningConfig.setFrom("CTS");
    urlShorteningConfig.setTo("client");
    urlShorteningConfig.setRemoveHtmlSuffix(true);
    Mockito.when(propertyReaderUtils.getShortneningConfig("fp")).thenReturn(urlShorteningConfig);
    urlShorteningService.transform("fp", urls);
  }
  
  @Test
  public void testTrasform3() {
    Map<String, String> siteKeyToUrl = new HashMap<>();
    siteKeyToUrl.put("one", "client");
    UrlShorteningConfig urlShorteningConfig = new UrlShorteningConfig();
    urlShorteningConfig.setFrom("CTS");
    urlShorteningConfig.setTo("client");
    urlShorteningConfig.setRemoveHtmlSuffix(true);
    urlShorteningService.transform(siteKeyToUrl);
  }
}
