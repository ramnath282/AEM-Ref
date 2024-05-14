package com.mattel.global.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.GlobalPropertyReaderUtils;

@RunWith(PowerMockRunner.class)
public class ListingModelTest {
  @InjectMocks
  private ListingModel listingModel;
  @Mock
  GlobalPropertyReaderUtils globalPropertyReaderUtils;

  @Test
  public void testToVerifyValidSnpUrlReturnFromModel()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListingModel.class, "globalPropertyReaderUtils").set(listingModel,
        globalPropertyReaderUtils);
    MemberModifier.field(ListingModel.class, "siteName").set(listingModel, "corp");
    Mockito.when(globalPropertyReaderUtils.getSnpUrl("corp"))
        .thenReturn("//stage-sp10056c97.guided.ss-omtrdc.net/");
    listingModel.init();
    assertEquals("//stage-sp10056c97.guided.ss-omtrdc.net/", listingModel.getSnpUrl());
  }

  @Test
  public void testToVerifyIfGlobalPropertyReaderUtilIsNull()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListingModel.class, "globalPropertyReaderUtils").set(listingModel, null);
    MemberModifier.field(ListingModel.class, "siteName").set(listingModel, "corp");
    Mockito.when(globalPropertyReaderUtils.getSnpUrl("corp"))
        .thenReturn("//stage-sp10056c97.guided.ss-omtrdc.net/");
    listingModel.init();
    assertNotEquals("//stage-sp10056c97.guided.ss-omtrdc.net/", listingModel.getSnpUrl());
  }

  @Test
  public void testToVerifyAllPropertiesModel()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListingModel.class, "seeMoreLabel").set(listingModel, "See More");
    MemberModifier.field(ListingModel.class, "defaultImage").set(listingModel, "defaultImage");
    MemberModifier.field(ListingModel.class, "searchType").set(listingModel, "Corporate");
    MemberModifier.field(ListingModel.class, "initialLoadCount").set(listingModel, 12);
    MemberModifier.field(ListingModel.class, "productLimit").set(listingModel, 20);
    MemberModifier.field(ListingModel.class, "viewAllLabel").set(listingModel, "viewAllLabel");
    MemberModifier.field(ListingModel.class, "siteName").set(listingModel, "corp");

    listingModel.init();
    assertEquals("defaultImage", listingModel.getDefaultImage());
    assertEquals("See More", listingModel.getSeeMoreLabel());
    assertEquals("Corporate", listingModel.getSearchType());
    assertNotNull(listingModel.getInitialLoadCount());
    assertNotNull(listingModel.getProductLimit());
    assertEquals("viewAllLabel", listingModel.getViewAllLabel());
    assertEquals("corp", listingModel.getSiteName());
  }

}
