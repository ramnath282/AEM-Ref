package com.mattel.ecomm.core.models;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.json.JSONArray;
import org.json.JSONException;
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

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;
import com.mattel.ecomm.core.interfaces.StoreInterest;
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.PDPProduct;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommConfigurationUtils.class,EcomUtil.class })
public class PDPProductModelTest {

  @InjectMocks
  private PDPProductModel pDPProductModel;
  @Mock
  private SlingHttpServletRequest request;
  @Mock
  private RequestPathInfo pathInfo;
  @Mock
  PDPProduct pdpProduct;
  @Mock
  StoreInterest storeInterest;
  @Mock
  MarketingContentProviderService marketingContentProviderService;
  @Mock
  PropertyReaderService propertyReaderService;
  @Mock
  GetProductTypeService getProductTypeService;
  
  private static final ObjectReader RESP_PDP_READER = ResourceMapper
      .getReaderInstance(PDPResponse.class);

  private String selectors[] = { "1456", "AG" };

  private PDPResponse pdpResponse;

  private List<String> experienceFragmentPath;

  private JSONArray retailStores;

  @Before
  public void setUp() throws Exception {
    createPDPResponse();
    createStoreInterestResponse();
    experienceFragmentPath = new ArrayList<String>();
    MemberModifier.field(PDPProductModel.class, "request").set(pDPProductModel, request);
    MemberModifier.field(PDPProductModel.class, "storeInterest").set(pDPProductModel,
        storeInterest);
    MemberModifier.field(PDPProductModel.class, "marketingContentProviderService")
        .set(pDPProductModel, marketingContentProviderService);
    MemberModifier.field(PDPProductModel.class, "propertyReaderService").set(pDPProductModel,
        propertyReaderService);
    MemberModifier.field(PDPProductModel.class, "getProductTypeService").set(pDPProductModel,
        getProductTypeService);
    Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
    Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);
    Mockito.when(storeInterest.getStoreInterest()).thenReturn(retailStores);
    Mockito.when(pdpProduct.fetch(Mockito.any())).thenReturn(pdpResponse);
    Mockito.when(marketingContentProviderService.getContentFromTags(Mockito.any(), Mockito.any(),Mockito.any()))
        .thenReturn(experienceFragmentPath);
    Mockito.when(propertyReaderService.getBvPassKey(Mockito.any())).thenReturn("");
    PowerMockito.mockStatic(EcommConfigurationUtils.class);
    PowerMockito.mockStatic(EcomUtil.class);

  }

  @Test
  public void testGetPdpProductUIResponse() throws ServiceException, JSONException {
    Mockito.when(storeInterest.getStoreInterest()).thenReturn(retailStores);
    pDPProductModel.init();
    PDPProductUIResponse pdpUIResponse = pDPProductModel.getPdpProductUIResponse();
    Assert.assertNotNull(pdpUIResponse);
    Assert.assertEquals("FLY25", pdpUIResponse.getPartNumber());
    Assert.assertNotNull(pDPProductModel.getBazarVoicePassKey());
  }

  public void createPDPResponse() throws IOException {
    try (InputStream is = getClass().getResourceAsStream("get_pdp_product_response.json")) {
      pdpResponse = PDPProductModelTest.RESP_PDP_READER.readValue(is);
    }
  }

  public void createStoreInterestResponse() throws IOException, JSONException {
    retailStores = new JSONArray();
    retailStores.put(0, "ATLANTA RETAIL:North Point Mall:US");
    retailStores.put(1, "BOSTON RETAIL:Natick Mall:US");
  }

}
