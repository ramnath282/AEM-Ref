package com.mattel.global.core.model;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ GlobalUtils.class, Spliterators.class, StreamSupport.class })
public class AssetListingModelTest {

  @InjectMocks
  private AssetListingModel assetListingModel;

  @Mock
  private Resource resource;

  @Mock
  TagManager tagManager;

  ResourceResolver resourceResolver;

  @Before
  public void setup() throws IllegalArgumentException, IllegalAccessException {
    PowerMockito.mockStatic(GlobalUtils.class);
    PowerMockito.mockStatic(Spliterators.class);
    MemberModifier.field(AssetListingModel.class, "linkURL").set(assetListingModel, "linkurl");
    MemberModifier.field(AssetListingModel.class, "image").set(assetListingModel, "image");

    Mockito.when(GlobalUtils.checkLink("linkurl", resource)).thenReturn("linkURL");
    resourceResolver = Mockito.mock(ResourceResolver.class);
    Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
    Mockito.when(GlobalUtils.setBackgroundPath(resourceResolver, "image"))
        .thenReturn("backgroundImagePath");
    String[] filters = { "namespace:filter1" };
    MemberModifier.field(AssetListingModel.class, "filters").set(assetListingModel, filters);
    String[] radioFilters = { "namespace:radiofilter1" };
    MemberModifier.field(AssetListingModel.class, "radiofilters").set(assetListingModel, radioFilters);
    Boolean isAlphabeticallyGroupSort = true;
    MemberModifier.field(AssetListingModel.class, "isAlphabeticallyGroupSort").set(assetListingModel, isAlphabeticallyGroupSort);
    Boolean isAlphabeticallyFacetSort = true;
    MemberModifier.field(AssetListingModel.class, "isAlphabeticallyFacetSort").set(assetListingModel, isAlphabeticallyFacetSort);
    
    Mockito.when(resourceResolver.adaptTo(TagManager.class)).thenReturn(tagManager);
    Tag tag = Mockito.mock(Tag.class);
    Mockito.when(tagManager.resolve(Mockito.anyString())).thenReturn(tag);
    Mockito.when(tag.getTitle()).thenReturn("tagtitle");

    MemberModifier.field(AssetListingModel.class, "backgroundImagePath").set(assetListingModel,
        "backgroundImagePath");
    MemberModifier.field(AssetListingModel.class, "clearAllLabel").set(assetListingModel,
        "clearAllLabel");
    MemberModifier.field(AssetListingModel.class, "enableClearAll").set(assetListingModel,
        "enableClearAll");
    MemberModifier.field(AssetListingModel.class, "enableFilter").set(assetListingModel,
        "enableFilter");
    MemberModifier.field(AssetListingModel.class, "filterSectionLabel").set(assetListingModel,
        "filterSectionLabel");
    MemberModifier.field(AssetListingModel.class, "largeColumns").set(assetListingModel,
        "largeColumns");
    MemberModifier.field(AssetListingModel.class, "linkURL").set(assetListingModel, "linkURL");
    MemberModifier.field(AssetListingModel.class, "showMoreText").set(assetListingModel,
        "<p>showMoreText</p>");
    MemberModifier.field(AssetListingModel.class, "showLessText").set(assetListingModel,
        "<p>showLessText</p>");
    MemberModifier.field(AssetListingModel.class, "linkText").set(assetListingModel,
        "<p>linkText</p>");
    MemberModifier.field(AssetListingModel.class, "filters").set(assetListingModel, filters);
    Mockito.when(GlobalUtils.removeTags("<p>showMoreText</p>", Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING)).thenReturn("showMoreText");
    Mockito.when(GlobalUtils.removeTags("<p>showLessText</p>", Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING)).thenReturn("showLessText");
    Mockito.when(GlobalUtils.removeTags("<p>linkText</p>", Constants.REMOVE_TAGS,
        Constants.EMPTY_ARRAY_STRING)).thenReturn("linkText");

    @SuppressWarnings("unchecked")
    Iterator<Tag> tagItr = Mockito.mock(Iterator.class);
    Mockito.when(tag.listChildren()).thenReturn(tagItr);
    Mockito.when(tagItr.hasNext()).thenReturn(true, false);
    Mockito.when(tagItr.next()).thenReturn(tag);

    @SuppressWarnings("unchecked")
    Iterator<Tag> childTagItr = Mockito.mock(Iterator.class);
    Mockito.when(tag.listChildren()).thenReturn(childTagItr);
    @SuppressWarnings("unchecked")
    Spliterator<Tag> spliterator = Mockito.mock(Spliterator.class);
    Mockito.when(Spliterators.spliteratorUnknownSize(childTagItr, 0)).thenReturn(spliterator);
  }

  @Test
  public void testInit() {
    assetListingModel.init();
  }

  @Test
  public void testGetters() {
    Assert.assertNotNull(assetListingModel.getBackgroundImagePath());
    Assert.assertNotNull(assetListingModel.getClearAllLabel());
    Assert.assertNotNull(assetListingModel.getEnableClearAll());
    Assert.assertNotNull(assetListingModel.getEnableFilter());
    Assert.assertNotNull(assetListingModel.getFilterSectionLabel());
    Assert.assertNotNull(assetListingModel.getLargeColumns());
    Assert.assertNotNull(assetListingModel.getLinkURL());
    Assert.assertNotNull(assetListingModel.getFilters());
    Assert.assertNotNull(assetListingModel.getShowMoreText());
    Assert.assertNotNull(assetListingModel.getShowLessText());
    Assert.assertNotNull(assetListingModel.getLinkText());
  }

}