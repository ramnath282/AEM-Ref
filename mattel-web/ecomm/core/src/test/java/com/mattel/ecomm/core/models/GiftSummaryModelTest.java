package com.mattel.ecomm.core.models;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.core.services.GTCompositeImageService;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;

@RunWith(PowerMockRunner.class)
public class GiftSummaryModelTest {

  @InjectMocks
  private GiftSummaryModel giftSummaryModel;
  @Mock
  private SlingHttpServletRequest request;

  @Mock
  GTCompositeImageService gtCompositeImageService;

  @Mock
  ProductAvailability productAvailability;

  private String baseImageUrl = "https://mattel.scene7.com/is/image/AmericanGirlBrands/GiftTrunkTest?";
  private String[] layer1Url = {
      "&layer=1&src=AmericanGirlBrands/GT-LARGE-T&size=2975;1917&pos=1843;1195&hide=0&wid=600&scl=5",
      "&layer=1&src=AmericanGirlBrands/GT-LARGE-T&size=2975;1917&pos=1843;1195&hide=0&wid=600&scl=5" };
  private String layer2Url = "&layer=2&src=AmericanGirlBrands/GT-SS1-GMY73&size=328,467&pos=2584,1412&hide=0";
  private String layer3Url = "&layer=3&src=AmericanGirlBrands/GT-SS2-GMY74&size=296,420&pos=2594,663&hide=0";
  private String layer4Url = "&layer=4&src=AmericanGirlBrands/GT-GBL30&size=3840,2398&pos=1920,1199&hide=0";
  private String layerCompParams = "&layer=comp&fmt=jpeg&qlt=85,0&resMode=sharp2&op_usm=0.9,1.0,8,0";

  @Mock
  private Resource resource;
  @Mock
  private Iterator<Resource> rootChildren;

  public ProductServiceResponse getPDPInstance() throws Exception {
    try (InputStream is = getClass().getResourceAsStream("trunkJsonResponse.json")) {
      final ObjectReader reader = ResourceMapper.getReaderInstance(ProductServiceResponse.class);
      final ProductServiceResponse response = reader.readValue(is);
      return response;
    }
  }

  @Before
  public void setup() throws Exception {
    MemberModifier.field(GiftSummaryModel.class, "request").set(giftSummaryModel, request);
    MemberModifier.field(GiftSummaryModel.class, "baseImageUrl").set(giftSummaryModel,
        baseImageUrl);
    MemberModifier.field(GiftSummaryModel.class, "layer1Url").set(giftSummaryModel, layer1Url);
    MemberModifier.field(GiftSummaryModel.class, "layer2Url").set(giftSummaryModel, layer2Url);
    MemberModifier.field(GiftSummaryModel.class, "layer3Url").set(giftSummaryModel, layer3Url);
    MemberModifier.field(GiftSummaryModel.class, "layer4Url").set(giftSummaryModel, layer4Url);
    MemberModifier.field(GiftSummaryModel.class, "layerCompParams").set(giftSummaryModel,
        layerCompParams);
    Mockito.when(productAvailability.fetch(Mockito.any())).thenReturn(this.getPDPInstance());
  }

  @Test
  public void testInit() throws ServiceException, JSONException {

    giftSummaryModel.init();
  }

  @Test
  public void testGetSmallTrunkDetails() throws ServiceException, JSONException {
    giftSummaryModel.init();
    giftSummaryModel.setSmallTrunkResponseString("smallTrunkResponseString");
    Assert.assertNotNull(giftSummaryModel.getSmallTrunkDetails());
    Assert.assertNotNull(giftSummaryModel.getSmallTrunkResponseString());
  }

  @Test
  public void testGetLargeTrunkDetails() throws ServiceException, JSONException {
    giftSummaryModel.init();
    giftSummaryModel.setLargeTrunkResponseString("largeTrunkResponseString");
    Assert.assertNotNull(giftSummaryModel.getLargeTrunkDetails());
    Assert.assertNotNull(giftSummaryModel.getLargeTrunkResponseString());
  }

  @Test
  public void testImageService() {
    Assert.assertEquals(baseImageUrl, giftSummaryModel.getBaseImageUrl());
    Assert.assertEquals(layer1Url.length, giftSummaryModel.getLayer1Url().length);
    Assert.assertEquals(layer2Url, giftSummaryModel.getLayer2Url());
    Assert.assertEquals(layer3Url, giftSummaryModel.getLayer3Url());
    Assert.assertEquals(layer4Url, giftSummaryModel.getLayer4Url());
    Assert.assertEquals(layerCompParams, giftSummaryModel.getLayerCompParams());
  }

  @Test
  public void testGetLetterPage() throws IllegalArgumentException, IllegalAccessException {
    Page letterPage = Mockito.mock(Page.class);
    MemberModifier.field(GiftSummaryModel.class, "letterPage").set(giftSummaryModel, letterPage);
    Mockito.when(letterPage.getContentResource()).thenReturn(resource);
    Mockito.when(resource.getChild(Mockito.anyString())).thenReturn(resource);
    Mockito.when(resource.listChildren()).thenReturn(rootChildren);
    Mockito.when(rootChildren.hasNext()).thenReturn(true, false);
    Mockito.when(rootChildren.next()).thenReturn(resource);
    Mockito.when(resource.isResourceType(Mockito.anyString())).thenReturn(true);
    Mockito.when(resource.getPath()).thenReturn("/content/letterPage");
    Assert.assertEquals("/content/letterPage", giftSummaryModel.getLetterPage());
  }
}
