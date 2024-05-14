package com.mattel.global.core.model;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.mattel.global.core.services.ListingDetailService;
import com.mattel.global.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class SocialLinkModelTest {
  @InjectMocks
  private SocialLinkModel socialLinkModel;

  @Mock
  SlingHttpServletRequest request;

  @Mock
  ListingDetailService listingDetailService;

  @Mock
  RequestPathInfo requestPathInfo;

  @Mock
  Resource resource;

  @Mock
  private Node categoryConfiguration;

  @Mock
  private MultifieldReader multifieldReader;

  @Mock
  Page currentPage;

  private final String[] selectors = {
      "articledetailspage.american-girl-brings-new-in-store-experiences-to-its-flagship-stores-in-new-york-and-chicago-in-time-for-the-holidays" };
  Object[] objArry = { "corp:categories/AmericanGirlNews", "corp:categories/CorporateNews" };

  @Before
  public void setup() throws Exception {
    MemberModifier.field(SocialLinkModel.class, "request").set(socialLinkModel, request);
    MemberModifier.field(SocialLinkModel.class, "currentPage").set(socialLinkModel, currentPage);
    MemberModifier.field(SocialLinkModel.class, "listingDetailService").set(socialLinkModel,
        listingDetailService);
    MemberModifier.field(SocialLinkModel.class, "socialLinkLabel").set(socialLinkModel,
        "socialLinkLabel");
    MemberModifier.field(SocialLinkModel.class, "siteName").set(socialLinkModel, "siteName");
    MemberModifier.field(SocialLinkModel.class, "categoryConfiguration").set(socialLinkModel, categoryConfiguration);

    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors()).thenReturn(selectors);
    Mockito.when(currentPage.getPath()).thenReturn("/content/mattel/mattel-corporate/us/en-us/home/news/news-details");

    @SuppressWarnings("unchecked")
    Map<String, ValueMap> multifieldProperty = Mockito.mock(Map.class);
    Mockito.when(multifieldReader.propertyReader(categoryConfiguration)).thenReturn(multifieldProperty);

    @SuppressWarnings("unchecked")
    Set<Entry<String, ValueMap>> entrySet= Mockito.mock(Set.class);
    Mockito.when(multifieldProperty.entrySet()).thenReturn(entrySet);

    @SuppressWarnings("unchecked")
    Iterator<Entry<String, ValueMap>> setItr = Mockito.mock(Iterator.class);
    Mockito.when(entrySet.iterator()).thenReturn(setItr);
    Mockito.when(setItr.hasNext()).thenReturn(true, false);

    @SuppressWarnings("unchecked")
    Entry<String, ValueMap> entry = Mockito.mock(Entry.class);
    Mockito.when(setItr.next()).thenReturn(entry );

    ValueMap value = Mockito.mock(ValueMap.class);
    Mockito.when(entry.getValue()).thenReturn(value);
    Mockito.when(value.get("categoryType", "")).thenReturn("corp:categories/AmericanGirlNews");
    Mockito.when(value.get("nodePath", "")).thenReturn("nodepath");

    Mockito.when(categoryConfiguration.getPath()).thenReturn("configurationNodepath");
    Mockito.when(categoryConfiguration.hasNode("nodepath/socialConfiguration/item0")).thenReturn(true);
  }

  @Test
  public void testToVerifyValidCategoryTagsWithValidSelectors() {
    Mockito.when(listingDetailService.getArticleTagList(ArgumentMatchers.any(String[].class),
        ArgumentMatchers.anyString())).thenReturn(objArry);
    socialLinkModel.init();
    Assert.assertNotNull(socialLinkModel.getTagList());
    Assert.assertEquals("socialLinkLabel", socialLinkModel.getSocialLinkLabel());
  }

  @Test
  public void testToVerifyForNullResponse() {
    Mockito.when(listingDetailService.getArticleTagList(ArgumentMatchers.any(String[].class),
        ArgumentMatchers.anyString())).thenReturn(null);
    socialLinkModel.init();
    Assert.assertNull(socialLinkModel.getTagList());
  }

  @Test
  public void testToVerifyForEmptyArrayResponse() {
    Object[] object = {};
    Mockito.when(listingDetailService.getArticleTagList(ArgumentMatchers.any(String[].class),
        ArgumentMatchers.anyString())).thenReturn(object);
    socialLinkModel.init();
    Assert.assertNull(socialLinkModel.getTagList());
  }

  @Test
  public void testGetterSetters(){
    Assert.assertNotNull(socialLinkModel.isSocailLinkAuthored());
    socialLinkModel.setSocailLinkAuthored(true);
  }

}
