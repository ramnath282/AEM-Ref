package com.mattel.global.core.sitemap;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DynamicSiteMapPageConfigTest {
  
  @Test
  public void testGetterSesster() {
    DynamicSiteMapPageConfig dynamicSiteMapPageConfig = new DynamicSiteMapPageConfig();
    DataSourceType dataSourceType = null;
    dynamicSiteMapPageConfig.setDataSourceType(dataSourceType);
    dynamicSiteMapPageConfig.setRepoPath("repoPath");
    dynamicSiteMapPageConfig.setFilePath("filePath");
    dynamicSiteMapPageConfig.setNodePrimaryId("1");
    List<String> excludeNodes = new ArrayList<>();
    dynamicSiteMapPageConfig.setExcludeNodes(excludeNodes );
    dynamicSiteMapPageConfig.setLastModifiedProperty("lastModifiedProperty");
    dynamicSiteMapPageConfig.setExcludeProperty("excludeProperty");
    dynamicSiteMapPageConfig.setIsActiveProperty("true");
    
    Assert.assertEquals("repoPath", dynamicSiteMapPageConfig.getRepoPath());
    Assert.assertEquals("filePath", dynamicSiteMapPageConfig.getFilePath());
    Assert.assertEquals("1", dynamicSiteMapPageConfig.getNodePrimaryId());
    Assert.assertEquals("lastModifiedProperty", dynamicSiteMapPageConfig.getLastModifiedProperty());
    Assert.assertEquals("excludeProperty", dynamicSiteMapPageConfig.getExcludeProperty());
    Assert.assertEquals("true", dynamicSiteMapPageConfig.getIsActiveProperty());
    Assert.assertEquals(excludeNodes, dynamicSiteMapPageConfig.getExcludeNodes());
    Assert.assertEquals(dataSourceType, dynamicSiteMapPageConfig.getDataSourceType());
    
    dynamicSiteMapPageConfig.toString();
  }
}
