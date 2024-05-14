package com.mattel.fisherprice.core.services;

import java.util.Locale;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;

@RunWith(PowerMockRunner.class)
public class RelatedArticleServiceTest {

  @InjectMocks
  private RelatedArticleService relatedArticleService;

  @Mock
  private ResourceResolverFactory resolverFactory;

  @Before
  public void setup() throws Exception {
    ResourceResolver resolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resolverFactory.getServiceResourceResolver(Mockito.anyMap())).thenReturn(resolver);
    TagManager tagManager = Mockito.mock(TagManager.class);
    Mockito.when(resolver.adaptTo(TagManager.class)).thenReturn(tagManager);
    Tag tag = Mockito.mock(Tag.class);
    Mockito.when(tagManager.resolve("tag1")).thenReturn(tag);
    Mockito.when(tag.getTitle()).thenReturn("tagTitle");
    Mockito.when(tag.getLocalTagID()).thenReturn("tagID");
    Mockito.when(tag.getName()).thenReturn("tagName");
    Mockito.when(tag.getTitle(Locale.US)).thenReturn("us_tagTitle");
    Mockito.when(resolver.isLive()).thenReturn(true);
  }

  @Test
  public void testGetTagRelatedData() {
    String[] primaryTags = { "tag1" };
    Locale locale = Locale.US;
    relatedArticleService.getTagRelatedData(primaryTags, locale);
  }

}
