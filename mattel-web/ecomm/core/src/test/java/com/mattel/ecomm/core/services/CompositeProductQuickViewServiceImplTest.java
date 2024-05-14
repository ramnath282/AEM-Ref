package com.mattel.ecomm.core.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.PDPCollectiveOfferPrice;
import com.mattel.ecomm.coreservices.core.interfaces.PDPListPrice;
import com.mattel.ecomm.coreservices.core.interfaces.PDPOfferPrice;
import com.mattel.ecomm.coreservices.core.interfaces.PDPProduct;
import com.mattel.ecomm.coreservices.core.interfaces.ProductInventoryCheckService;
import com.mattel.ecomm.coreservices.core.pojos.InventoryCheckResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPCollectiveOfferPriceResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPOfferPriceResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;

@RunWith(MockitoJUnitRunner.class)
public class CompositeProductQuickViewServiceImplTest {

  @InjectMocks
  CompositeProductQuickViewServiceImpl compositeProductQuickViewServiceImpl;
  private static final ObjectReader RESP_PDP_READER = ResourceMapper
      .getReaderInstance(PDPResponse.class);
  private static final ObjectReader RESP_PDP_OFFER_READER = ResourceMapper
      .getReaderInstance(PDPOfferPriceResponse.class);
  private static final ObjectReader RESP_PDP_INV_READER = ResourceMapper
      .getReaderInstance(InventoryCheckResponse.class);
  @Spy
  private PDPProduct pdpProduct;
  @Spy
  PDPListPrice pdpListPrice;
  @Spy
  private PDPOfferPrice pdpOfferPrice;
  @Spy
  private PDPCollectiveOfferPrice pdpCollectiveOfferPrice;
  @Spy
  private ProductInventoryCheckService productInventoryCheckService;
  @Spy
  MarketingContentProviderService marketingContentProviderService;

  List<Cookie> cookieList = new ArrayList<Cookie>();
  private PDPResponse pdpResponse;
  private PDPOfferPriceResponse pdpOfferResponse;
  private PDPCollectiveOfferPriceResponse pdpOfferPriceResponse;
  private InventoryCheckResponse inventoryCheckResponse;

  @Before
  public void setUp() throws Exception {
    try (InputStream is1 = getClass().getResourceAsStream("get_pdp_product_response.json");
        InputStream is2 = getClass().getResourceAsStream("get_pdp_offer_price_response.json");
        InputStream is3 = getClass().getResourceAsStream("inventory_check_response.json")) {
      this.addCookies();
      pdpResponse = CompositeProductQuickViewServiceImplTest.RESP_PDP_READER.readValue(is1);
      pdpResponse.setCookieList(cookieList);
      pdpOfferResponse = CompositeProductQuickViewServiceImplTest.RESP_PDP_OFFER_READER
          .readValue(is2);
      pdpOfferResponse.setCookieList(cookieList);
      inventoryCheckResponse = CompositeProductQuickViewServiceImplTest.RESP_PDP_INV_READER
          .readValue(is3);
      inventoryCheckResponse.setCookieList(cookieList);
    }
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetQuickViewProductData() throws ServiceException {

    final Map<String, Object> requestHeader = new HashMap<>();
    final Cookie cookie = new Cookie("JSESSIONID", "213123132");
    final Cookie[] cookieObj = new Cookie[1];
    cookie.setComment("HttpOnly");
    cookie.setDomain("pattern");
    cookie.setMaxAge(2);
    cookie.setPath("uri");
    cookie.setSecure(true);
    cookie.setValue("newValue");
    cookie.setVersion(1);
    cookieObj[0] = cookie;
    requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
    Map<String, Object> responseMap = new HashMap<String, Object>();
    Mockito.when(pdpProduct.fetch(Mockito.any())).thenReturn(pdpResponse);
    Mockito.when(pdpCollectiveOfferPrice.getOfferPrice(Mockito.any()))
        .thenReturn(pdpOfferPriceResponse);
    Mockito.when(productInventoryCheckService.fetch(Mockito.any()))
        .thenReturn(inventoryCheckResponse);
    List<String> experienceFragmentPath = new ArrayList<>();
    experienceFragmentPath.add("exprienceFrangmentPath");
    Mockito.when( marketingContentProviderService.getContentFromTags(Mockito.any(),Mockito.any(),Mockito.any()))
    .thenReturn(experienceFragmentPath);
    Map<String, Object> compositeProductQuickViewServiceImplResponse = compositeProductQuickViewServiceImpl
        .getQuickViewProductData(requestHeader, responseMap);
    Assert.assertNotNull(compositeProductQuickViewServiceImplResponse);
  }

  @Test
  public void testGetRequestCookieListFromResponse() {

  }

  private void addCookies() {
    final Cookie cookie1 = new Cookie("JSESSIONID", "213123132");
    final Cookie cookie2 = new Cookie("WC_SESSION_ESTABLISHED", "true");
    this.cookieList.add(cookie1);
    this.cookieList.add(cookie2);
  }

}
