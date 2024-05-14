package com.mattel.global.core.sitemap;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import junitx.framework.Assert;

public class SiteConfigTest {
    SiteConfig siteConfig;
    List<String> list = new ArrayList<String>();
    
    List<String> damAssetTypes = new ArrayList<String>();
    List<String> excludePagesOfTemplateType = new ArrayList<String>();
    
    @Mock
    List<DynamicSiteMapPageConfig> dynamicPageConfigs;
    
    @Mock
    List<RewriteVanityRule> rewriteVanityRules;
    
    @Mock
    List<String> priorityProperties;
    
    @Mock 
    List<BasePredicate> predicates;
    
    
    
    @Before
    public void setup() throws Exception {
        siteConfig = new SiteConfig();
        list.add("item 1");
        list.add("item 2");
        list.add("item 3");
        damAssetTypes.add("damAssetType 1");
        excludePagesOfTemplateType.add("excludePagesOfTemplateType");
    }
    @Test
    public void testSiteConfig(){
        siteConfig.toString();
    }
    
    @Test
    public void testgettersAndSetters(){
        siteConfig.setPredicates(predicates);
        siteConfig.setSiteName("siteName");
        siteConfig.setLocale("locale");
        siteConfig.setSiteKey("siteKey");
        siteConfig.setPriorityProperties(priorityProperties);
        siteConfig.setIncludeInheritance(true);
        siteConfig.setDynamicPageConfigs(dynamicPageConfigs);
        siteConfig.setRewriteVanityRules(rewriteVanityRules);
        siteConfig.setAdditionalSkipProperty("additionalSkipProperty");
        siteConfig.setChangeFreqProperties(list);
        siteConfig.setDamAssetProperty("damAssetProperty");
        siteConfig.setDamAssetTypes(damAssetTypes);
        siteConfig.setDefaultFrequency("defaultFrequency");
        siteConfig.setDefaultPriority("defaultPriority");
        siteConfig.setEnableHideInSiteMap(true);
        siteConfig.setEndsWith("endsWith");
        siteConfig.setExcludePagesOfTemplateType(excludePagesOfTemplateType);
        siteConfig.setExtensionlessUrls(true);
        siteConfig.setExternalizerDomain("externalizerDomain");
        siteConfig.setIncludeHtmlPrefix(true);
        siteConfig.setRootPath("rootPath");
        siteConfig.setUseTreeAlgorithm(true);
        siteConfig.setPageExcludeProperty("pageExcludeProperty");
        siteConfig.setPageSkipProperty("pageSkipProperty");
        siteConfig.setPageStopProperty("pageStopProperty");
        Assert.assertEquals("pageStopProperty", siteConfig.getPageStopProperty());
    }
    
    @Test
    public void testgettersAndSetters1(){
        siteConfig = new SiteConfig(siteConfig);
    }
    
    
}
