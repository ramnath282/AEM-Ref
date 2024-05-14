package com.mattel.global.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
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

import com.mattel.global.core.pojo.AttachmentDetailsPojo;
import com.mattel.global.core.pojo.ItemListPojo;
import com.mattel.global.core.services.ListingDetailService;

@RunWith(PowerMockRunner.class)
public class ListingDetailModelTest {

  @InjectMocks
  private ListingDetailModel listingDetailModel;

  @Mock
  SlingHttpServletRequest request;

  @Mock
  ListingDetailService listingDetailService;

  @Mock
  RequestPathInfo requestPathInfo;
  @Mock
  Resource resource;

  ItemListPojo itemListPojo;
  AttachmentDetailsPojo attachmentDetails;
  private final String[] selectors = {
      "articledetailspage.american-girl-brings-new-in-store-experiences-to-its-flagship-stores-in-new-york-and-chicago-in-time-for-the-holidays" };

  @Before
  public void setup() throws Exception {
    attachmentDetails = new AttachmentDetailsPojo();
    attachmentDetails.setAttachmentTitle("heading");
    attachmentDetails.setAttachmentDescription("description");
    attachmentDetails.setAttachmentUrl("url");
    List<AttachmentDetailsPojo> enclosureDetailList = new ArrayList<AttachmentDetailsPojo>();
    enclosureDetailList.add(attachmentDetails);
    itemListPojo = new ItemListPojo();
    itemListPojo.setTitle("title");
    itemListPojo.setContent("htmlContent");
    itemListPojo.setDescription("Description");
    MemberModifier.field(ListingDetailModel.class, "request").set(listingDetailModel, request);
    MemberModifier.field(ListingDetailModel.class, "listingDetailService").set(listingDetailModel,
        listingDetailService);
    MemberModifier.field(ListingDetailModel.class, "downloadMedia").set(listingDetailModel,
        "downloadMedia");
    MemberModifier.field(ListingDetailModel.class, "viewLabel").set(listingDetailModel,
        "viewLabel");
    MemberModifier.field(ListingDetailModel.class, "downloadPdfLabel").set(listingDetailModel,
        "downloadPdfLabel");
    MemberModifier.field(ListingDetailModel.class, "downloadAllFilesLabel").set(listingDetailModel,
        "downloadAllFilesLabel");
    MemberModifier.field(ListingDetailModel.class, "siteName").set(listingDetailModel, "siteName");

    Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
    Mockito.when(requestPathInfo.getSelectors()).thenReturn(selectors);

  }

  @Test
  public void testToVerifyResponseWithValidSelectors() {
    Mockito.when(listingDetailService.getFragmentDetails(ArgumentMatchers.any(String[].class),
        ArgumentMatchers.anyString())).thenReturn(itemListPojo);
    listingDetailModel.init();
    Assert.assertNotNull(listingDetailModel.getItemListPojo());
    Assert.assertEquals("downloadMedia", listingDetailModel.getDownloadMedia());
    Assert.assertEquals("viewLabel", listingDetailModel.getViewLabel());
    Assert.assertEquals("downloadPdfLabel", listingDetailModel.getDownloadPdfLabel());
    Assert.assertEquals("downloadAllFilesLabel", listingDetailModel.getDownloadAllFilesLabel());
    Assert.assertEquals("siteName", listingDetailModel.getSiteName());
  }

  @Test
  public void testToVerifyIfListingDetailServiceIsNull()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListingDetailModel.class, "listingDetailService").set(listingDetailModel,
        listingDetailService);
    listingDetailModel.init();
    Assert.assertNull(listingDetailModel.getItemListPojo());
  }

  @Test
  public void testToVerifyForInvalidServiceInjection()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListingDetailModel.class, "listingDetailService").set(listingDetailModel,
        null);

    listingDetailModel.init();
    Assert.assertNull(listingDetailModel.getItemListPojo());
  }

  @Test
  public void testToVerifyForInvalidInjection()
      throws IllegalArgumentException, IllegalAccessException {
    MemberModifier.field(ListingDetailModel.class, "listingDetailService").set(listingDetailModel,
        null);
    Mockito.when(requestPathInfo.getSelectors()).thenReturn(null);
    listingDetailModel.init();
    Assert.assertNull(listingDetailModel.getItemListPojo());
  }

  @Test
  public void testToVerifyForInvalidSelector()
      throws IllegalArgumentException, IllegalAccessException {
    Mockito.when(requestPathInfo.getSelectors()).thenReturn(null);

    listingDetailModel.init();
    Assert.assertNull(listingDetailModel.getItemListPojo());
  }

}
