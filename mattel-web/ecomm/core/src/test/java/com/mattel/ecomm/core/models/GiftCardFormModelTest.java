package com.mattel.ecomm.core.models;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.core.pojos.GiftCardResponse;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.PDPProduct;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;

@RunWith(PowerMockRunner.class)
public class GiftCardFormModelTest {

  @Mock
  private SlingHttpServletRequest request;
  @InjectMocks
  GiftCardFormModel giftCardFormModel;
  @Mock
  private MultifieldReader multifieldReader;
  @Mock
  PDPProduct pdpProduct;
  @Mock
  private RequestPathInfo pathInfo;
  @Mock
  private Node giftMessage;
  GiftCardResponse giftCardResponse;
  String[] selectors = { "ag_en", "GBM03" };
  PDPResponse pdpResponse;
  ValueMap valueMap;
  Map.Entry<String, ValueMap> entry;

  private static final ObjectReader RESP_PDP_READER = ResourceMapper
      .getReaderInstance(PDPResponse.class);
  private static final String IMAGE_LINK = "https://images.mattel.com/scene7/FLY25_Viewer";

  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws Exception {
    MemberModifier.field(GiftCardFormModel.class, "request").set(giftCardFormModel, request);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    valueMap = Mockito.mock(ValueMap.class);
    entry = Mockito.mock(Entry.class);
    Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
    giftCardFormModel.setMultifieldReader(multifieldReader);
  }

  @Test
  public void testGiftCardFormModel() throws ServiceException, IOException {
    Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
    createPDPResponse();
    Mockito.when(pdpProduct.fetch(Mockito.any())).thenReturn(pdpResponse);
    final Map<String, ValueMap> multifieldProperty = new HashMap<>();
    multifieldProperty.put("document", valueMap);
    multifieldProperty.put("document1", valueMap);
    Mockito.when(multifieldReader.propertyReader(Mockito.any())).thenReturn(multifieldProperty);
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    Mockito.when(valueMap.get("messageTitle", String.class)).thenReturn("GiftCardTitle");
    Mockito.when(valueMap.get("message1", String.class)).thenReturn("Gift card message1");
    Mockito.when(valueMap.get("message2", String.class)).thenReturn("Gift card message2");
    Mockito.when(valueMap.get("message3", String.class)).thenReturn("Gift card message3");

    giftCardFormModel.init();

    Assert.assertNotNull(giftCardFormModel.getGiftCardResponse());
    Assert.assertNotNull(giftCardFormModel.getGiftCardResponse().getAvailability());
    Assert.assertEquals(IMAGE_LINK, giftCardFormModel.getGiftCardResponse().getImageLink());
  }

  @Test
  public void testGiftCardFormModelWithError() throws ServiceException, IOException {
    createPDPResponse();
    String[] selector = { "ag_en" };
    Mockito.when(pathInfo.getSelectors()).thenReturn(selector);
    Mockito.when(pdpProduct.fetch(Mockito.any())).thenReturn(pdpResponse);
    giftCardFormModel.init();
    
    Assert.assertNotNull(giftCardFormModel.getGiftCardResponse());
  }

  public void createPDPResponse() throws IOException {
    try (InputStream is = getClass().getResourceAsStream("get_pdp_product_response.json")) {
      pdpResponse = GiftCardFormModelTest.RESP_PDP_READER.readValue(is);
    }
  }
}
