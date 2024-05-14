package com.mattel.ecomm.core.services;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.commons.RangeIterator;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
public class MarketingContentProviderServiceImplTest {
  @InjectMocks
  private MarketingContentProviderServiceImpl marketingContentProviderServiceImpl;

  @Mock
  private GetResourceResolver getResourceResolver;

  @Mock
  private PropertyReaderService propertyReaderService;

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(MarketingContentProviderServiceImpl.class, "getResourceResolver")
        .set(marketingContentProviderServiceImpl, getResourceResolver);
    MemberModifier.field(MarketingContentProviderServiceImpl.class, "propertyReaderService")
        .set(marketingContentProviderServiceImpl, propertyReaderService);
    ResourceResolver resourceResolver = Mockito.mock(ResourceResolver.class);
    TagManager tagManager = Mockito.mock(TagManager.class);
    Tag tag = Mockito.mock(Tag.class);
    @SuppressWarnings("unchecked")
    RangeIterator<Resource> tagItr = Mockito.mock(RangeIterator.class);
    Resource taggedResource = Mockito.mock(Resource.class);
    ValueMap valueMap = Mockito.mock(ValueMap.class);
    String[] test = {"position"};

    Mockito.when(getResourceResolver.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(propertyReaderService.getExperienceFragmentRootPath("ag_en"))
        .thenReturn("/content/experience-fragments/ag/shop/marketing-contents");
    Mockito.when(propertyReaderService.getTagRootPaths("ag_en"))
        .thenReturn("/content/cq:tags/ag/Shop");
    Mockito.when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
    Mockito.when(tagManager.resolve(Mockito.anyString())).thenReturn(tag);
    Mockito.when(tag.getTagID()).thenReturn("testtag");
    Mockito.when(tagManager.find("testtag")).thenReturn(tagItr);
    Mockito.when(tagItr.hasNext()).thenReturn(true, false);
    Mockito.when(tagItr.next()).thenReturn(taggedResource);
    Mockito.when(taggedResource.getPath())
        .thenReturn("/content/experience-fragments/ag/shop/marketing-contents");
    Mockito.when(taggedResource.getValueMap()).thenReturn(valueMap);
    Mockito.when(valueMap.get("cq:tags", String[].class)).thenReturn(test);
  }

  @Test
  public void testGetContentFromTags() {
    marketingContentProviderServiceImpl.getContentFromTags("ag_en", "glg73", "default");
  }

  @Test
  public void testGetContentFromTagsWithNonDefaultPosition() {
    marketingContentProviderServiceImpl.getContentFromTags("ag_en", "glg73", "position1");
  }

  @Test
  public void testGetContentFromTagsWithNullPosition() {
    marketingContentProviderServiceImpl.getContentFromTags("ag_en", "glg73", null);
  }
}
