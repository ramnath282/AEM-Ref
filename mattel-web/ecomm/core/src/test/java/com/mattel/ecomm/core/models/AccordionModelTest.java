package com.mattel.ecomm.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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

import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.ecomm.coreservices.core.interfaces.ProductAvailability;
import com.mattel.ecomm.coreservices.core.pojos.shopify.ProductServiceResponse;
import com.mattel.ecomm.coreservices.core.pojos.shopify.Product;

@RunWith(PowerMockRunner.class)
public class AccordionModelTest {

  @InjectMocks
  private AccordionModel accordionModel;
  @Mock
  private SlingHttpServletRequest request;
  @Mock
  private Resource resource;
  @Mock
  private ResourceResolver resolver;
  @Mock
  private RequestPathInfo pathInfo;
  @Mock
  ProductAvailability productAvailability;

  MultifieldReader multifieldReader;
  Node keyMultifield;
  Map<String, ValueMap> multifieldProperty;
  ValueMap valueMap;
  Map.Entry<String, ValueMap> entry;

  String[] selectors = { "ag_en", "GBM03" };

  private ProductServiceResponse productServiceResponse;

  @SuppressWarnings("unchecked")
  @Before
  public void setup() throws Exception {
    MemberModifier.field(AccordionModel.class, "request").set(accordionModel, request);
    entry = Mockito.mock(Entry.class);
    valueMap = Mockito.mock(ValueMap.class);
    multifieldReader = Mockito.mock(MultifieldReader.class);
    keyMultifield = Mockito.mock(Node.class);
    multifieldProperty = new HashMap<>();
    multifieldProperty.put("document", valueMap);
    multifieldProperty.put("document", valueMap);
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    accordionModel.setMultifieldReader(multifieldReader);
    accordionModel.setKeyMultifield(keyMultifield);
  }

  @Test
  public void testInit() throws Exception {
    createProductServiceResponse();
    Mockito.when(entry.getValue()).thenReturn(valueMap);
    Mockito.when(multifieldReader.propertyReader(keyMultifield)).thenReturn(multifieldProperty);
    Mockito.when(entry.getValue().get("key", String.class))
        .thenReturn("MarketingDescription,LegalAge,MarketingAge");
    Mockito.when(entry.getValue().get("accordianTitle", String.class)).thenReturn("Specification");
    Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
    Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
    Mockito.when(productAvailability.fetch(Mockito.any())).thenReturn(productServiceResponse);

    accordionModel.init();
    Assert.assertNotNull(productServiceResponse.getProduct());
    Assert.assertNotNull(accordionModel.getAttrs());
    Assert.assertNotNull(accordionModel.getKeyList());
    Assert.assertNotNull(accordionModel.getKeyList().get(0).getAccordionKeyList());
    Assert.assertNotNull(accordionModel.getKeyMultifield());
    Assert.assertEquals("Specification", accordionModel.getKeyList().get(0).getAccordianTitle());
    Assert.assertTrue(accordionModel.getKeyList().get(0).isFlag());
  }

  public void createProductServiceResponse() throws IOException {
    productServiceResponse = new ProductServiceResponse();
    Product shopify = new Product();
    Map<String, Object> attributes = new HashMap<>();
    List<String> keyList1 = new ArrayList<>();
    keyList1
        .add("Offer valid through 1/31/19  or until limited promotional quantities are exhausted.");
    attributes.put("MarketingDescription", keyList1);
    List<String> keyList2 = new ArrayList<>();
    keyList2.add("8+");
    attributes.put("LegalAge", keyList2);
    String key3 = "3+";
    attributes.put("MarketingAge", key3);
    shopify.setAttributes(attributes);
    productServiceResponse.setProduct(shopify);
  }
}