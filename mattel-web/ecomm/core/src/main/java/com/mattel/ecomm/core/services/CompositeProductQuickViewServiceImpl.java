package com.mattel.ecomm.core.services;

import com.mattel.ecomm.core.interfaces.MarketingContentProviderService;
import com.mattel.ecomm.core.pojos.PDPProductUIResponse;
import com.mattel.ecomm.core.utils.ProductUIAdapter;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ProductAssociationMapping;
import com.mattel.ecomm.coreservices.core.enums.ServiceCookieMapping;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.CompositeProductQuickViewService;
import com.mattel.ecomm.coreservices.core.interfaces.PDPCollectiveOfferPrice;
import com.mattel.ecomm.coreservices.core.interfaces.PDPProduct;
import com.mattel.ecomm.coreservices.core.interfaces.ProductInventoryCheckService;
import com.mattel.ecomm.coreservices.core.pojos.InventoryCheckResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPCollectiveOfferPriceResponse;
import com.mattel.ecomm.coreservices.core.pojos.PDPCollectiveProductPriceList;
import com.mattel.ecomm.coreservices.core.pojos.PDPResponse;
import com.mattel.ecomm.coreservices.core.pojos.Product;
import com.mattel.ecomm.coreservices.core.pojos.ProductAssociation;
import com.mattel.ecomm.coreservices.core.pojos.UnitPrice;
import com.mattel.ecomm.coreservices.core.utilities.CookieUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.util.HttpURLConnection;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = CompositeProductQuickViewService.class)
public class CompositeProductQuickViewServiceImpl implements CompositeProductQuickViewService {
  private static final String OFFER_PRICE_DETAILS = "offerPriceDetails";
  private static final String PRODUCT_DETAILS = "productDetails";
  private static final String INVENTORY_DETAILS = "inventoryDetail";
  private static final String MARKETING_CONTENT_POSITIONTYPE="default";
  private static final Logger LOGGER = LoggerFactory
      .getLogger(CompositeProductQuickViewServiceImpl.class);
  @Reference
  private PDPProduct pdpProduct;
  @Reference
  private PDPCollectiveOfferPrice pdpCollectiveOfferPrice;
  @Reference
  private ProductInventoryCheckService productInventoryCheckService;
  @Reference
  private MarketingContentProviderService marketingContentProviderService;

  @Override
  public Map<String, Object> getQuickViewProductData(Map<String, Object> requestMap,
      Map<String, Object> responseMap) throws ServiceException {
    final long startTime = System.currentTimeMillis();
    final String[] cookieNames = ServiceCookieMapping.DEFAULT.getCookieNames();
    final JSONObject response = new JSONObject();
    final List<String> associationPartNumbers = new ArrayList<>();
    JSONObject pdpProductJson = new JSONObject();
    JSONObject offerPriceJson = new JSONObject();
    JSONObject productInventoryJson = new JSONObject();
    final long endTime;

    try {
      response.put(CompositeProductQuickViewServiceImpl.PRODUCT_DETAILS, pdpProductJson);
      response.put(CompositeProductQuickViewServiceImpl.OFFER_PRICE_DETAILS, offerPriceJson);
      response.put(CompositeProductQuickViewServiceImpl.INVENTORY_DETAILS, productInventoryJson);

      pdpProductJson = invokePDPProductService(requestMap, cookieNames, response,
          associationPartNumbers);

      if (Objects.nonNull(pdpProductJson)) {
        offerPriceJson = callPDPOfferPriceService(requestMap, cookieNames, associationPartNumbers);
        productInventoryJson = invokePDPProductInventoryService(requestMap);
      }

      response.put(CompositeProductQuickViewServiceImpl.OFFER_PRICE_DETAILS, offerPriceJson);
      response.put(CompositeProductQuickViewServiceImpl.INVENTORY_DETAILS, productInventoryJson);
    } catch (final JSONException e) {
      endTime = System.currentTimeMillis();
      CompositeProductQuickViewServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          "getCombinedAccountServiceResponse", endTime - startTime);
      CompositeProductQuickViewServiceImpl.LOGGER
          .error("JSONException occured in getCombinedAccountServiceResponse response {}", e);
      throw new ServiceException(String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR), "JSONException thrown from message body", true);
    }

    responseMap.put(Constant.RESPONSE_BODY, response.toString());
    endTime = System.currentTimeMillis();
    CompositeProductQuickViewServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        "getCombinedAccountServiceResponse", endTime - startTime);
    return responseMap;
  }

  private JSONObject invokePDPProductInventoryService(Map<String, Object> requestMap) throws ServiceException {
    final String partnumber = (String) requestMap.get(Constant.PART_NUMBER);
    if (StringUtils.isNotBlank(partnumber)) {
      final JSONObject requestJson = new JSONObject();
      final List<String> partNumberArr = new ArrayList<>();
      partNumberArr.add(partnumber);
      try {
        requestJson.append("partNumbers", partnumber);
      } catch (final JSONException e) {
        CompositeProductQuickViewServiceImpl.LOGGER
            .error("JSONException occured in callPDPProductInventoryService mrthod {}", e);
      }
      requestMap.put(Constant.REQUEST_BODY, requestJson.toString());

    }
    return callPDPproductInventoryService(requestMap);
  }

  /**
   * This method accepts the requestMap and returns the JSON response of
   * productInventoryCheckService
   *
   * @param requestMap
   *          request map containing the partNumbers as part of request body
   * @return productInventoryJson Product Inventory JSON
   * @throws ServiceException 
   */
  private JSONObject callPDPproductInventoryService(Map<String, Object> requestMap) throws ServiceException {
    InventoryCheckResponse inventoryCheckResponse;
    JSONObject productInventoryJson = new JSONObject();

      inventoryCheckResponse = productInventoryCheckService.fetch(requestMap);
      if (Objects.nonNull(inventoryCheckResponse)) {
        productInventoryJson = productInventoryCheckService
            .getResponseValueAsJson(inventoryCheckResponse);
      }

    return productInventoryJson;
  }

  private JSONObject invokePDPProductService(Map<String, Object> requestMap,
      final String[] cookieNames, final JSONObject response, List<String> associationPartNumbers)
      throws JSONException, ServiceException {
    AtomicBoolean errorFlag = new AtomicBoolean();
    JSONObject pdpProductJson = callPDPProductService(requestMap, cookieNames, associationPartNumbers, errorFlag);
      
    response.put(CompositeProductQuickViewServiceImpl.PRODUCT_DETAILS, pdpProductJson);
      
    if (errorFlag.get()) {
      pdpProductJson = null;
    }

    return pdpProductJson;
  }

  private JSONObject callPDPOfferPriceService(Map<String, Object> requestMap, String[] cookieNames,
      List<String> associationPartNumbers) throws JSONException, ServiceException {
    JSONObject offerPriceJson = new JSONObject();
    final JSONObject requestJson = new JSONObject();
    final Set<String> partNumbers = new HashSet<>();
    final String partnumber = (String) requestMap.get(Constant.PART_NUMBER);

    partNumbers.add(partnumber);
    partNumbers.addAll(associationPartNumbers);
    requestJson.put("partNumbers", String.join(",",
        partNumbers.stream().map(pn -> "" + pn + "").collect(Collectors.toList())));
    requestMap.put(Constant.REQUEST_BODY, requestJson.toString());

    final PDPCollectiveOfferPriceResponse pdpOfferPriceResponse = pdpCollectiveOfferPrice
          .getOfferPrice(requestMap);
  
    if (null != pdpOfferPriceResponse) {
      updateRequestMap(requestMap, pdpOfferPriceResponse.getCookieList(), cookieNames);
      pdpOfferPriceResponse.setCookieList(null);
      
      if (!pdpCollectiveOfferPrice.isServiceError(pdpOfferPriceResponse)) {
        offerPriceJson = transformPdpOfferPriceResponse(pdpOfferPriceResponse, partNumbers);
      } else {
        CompositeProductQuickViewServiceImpl.LOGGER.error(
            "Unable to fetch offer price details, requestMap: {}, errorresponse : {}", requestMap,
            pdpOfferPriceResponse);
        offerPriceJson = pdpCollectiveOfferPrice.getResponseValueAsJson(pdpOfferPriceResponse);
      }
    }

    return offerPriceJson;
  }

  private JSONObject transformPdpOfferPriceResponse(
      PDPCollectiveOfferPriceResponse pdpOfferPriceResponse, Set<String> partNumbers)
      throws JSONException {
    final List<Map<String, Object>> prices = new ArrayList<>();
    final List<PDPCollectiveProductPriceList> entitledPrice = pdpOfferPriceResponse
        .getPriceDetails().getProductPriceList();
    final JSONObject pricesJson = new JSONObject();
    Optional.ofNullable(entitledPrice)
        .ifPresent(entitledPriceCons -> entitledPriceCons.forEach(priceResultList -> {
          if (partNumbers.contains(priceResultList.getPartNumber())) {
            final Map<String, Object> finalPrice = new HashMap<>();
            final List<UnitPrice> unitPrice = priceResultList.getUnitPrice();
            final List<UnitPrice> listPrices = priceResultList.getListPrice();

            finalPrice.put("partNumber", priceResultList.getPartNumber());
            Optional.ofNullable(unitPrice).ifPresent(unitPriceCons -> unitPriceCons
                .forEach(price -> finalPrice.put(Constant.PRICE, price.getPrice())));
            Optional.ofNullable(listPrices).ifPresent(listPriceCons -> listPriceCons
                .forEach(listPrice -> finalPrice.put(Constant.LIST_PRICE, listPrice.getPrice())));
            prices.add(finalPrice);
          }
        }));

    pricesJson.put("prices", prices);
    return pricesJson;
  }

  private JSONObject callPDPProductService(Map<String, Object> requestMap, String[] cookieNames,
      List<String> associationPartNumbers, AtomicBoolean errorFlag) throws ServiceException {
    final PDPResponse pdpResponse = pdpProduct.fetch(requestMap);
    JSONObject pdpProductJson = null;
    PDPProductUIResponse pdpProductUIResponse = null;
    final List<ProductAssociation> productAssociations;

    if (null != pdpResponse) {
      updateRequestMap(requestMap, pdpResponse.getCookieList(), cookieNames);
      
      if (Objects.isNull(pdpResponse.getCatalogEntryView())) {
        CompositeProductQuickViewServiceImpl.LOGGER.error(
            "Unable to fetch product details, requestmap: {}, errorresponse: {}", requestMap,
            pdpResponse);
        errorFlag.set(true);
        return pdpProduct.getResponseValueAsJson(pdpResponse);
      }
      
      pdpProductUIResponse = formatProduct(pdpResponse,requestMap);
      pdpResponse.setCookieList(null);

      if (Objects.nonNull(pdpProductUIResponse)) {
        productAssociations = pdpProductUIResponse.getAssociationList();
        if(Objects.nonNull(productAssociations) && !productAssociations.isEmpty()) {
          productAssociations.stream()
                  .filter(
                          assoc -> ProductAssociationMapping.QUICK_TYPE.equals(assoc.getAssociationType()))
                  .map(ProductAssociation::getPartNumber).forEach(associationPartNumbers::add);
        }
      }

      pdpProductJson = pdpProduct.getResponseValueAsJson(pdpProductUIResponse);
    }
    return pdpProductJson;
  }

  private void updateRequestMap(Map<String, Object> requestMap, List<Cookie> responseCookieList,
      String[] cookieNames) {
    if (null != responseCookieList && !responseCookieList.isEmpty()) {
      final Cookie[] reqCookies = getRequestCookieListFromResponse(requestMap, responseCookieList,
          cookieNames);
      requestMap.replace(Constant.REQUEST_COOKIES_KEY, reqCookies);
    }
  }

  /**
   * This methods accepts List of Cookies received from response and converts that into List of
   * string representation of those cookie objects
   *
   * @param responseCookieList
   *          List of Cookies received from response
   * @return reqCookies List of string representation of Cookie object
   */
  public Cookie[] getRequestCookieListFromResponse(Map<String, Object> requestMap,
      List<Cookie> responseCookieList, String[] cookieNames) {
    CompositeProductQuickViewServiceImpl.LOGGER
        .info("Start of getRequestCookieListFromResponse method");
    final long startTime = System.currentTimeMillis();
    final List<Cookie> updateCookieList = getUpdatedResponseCookieList(requestMap,
        responseCookieList);
    final List<Cookie> reqCookies = new ArrayList<>();
    final AtomicInteger atomicInteger = new AtomicInteger(0);
    updateCookieList.forEach(cookie -> {
      final boolean isValidServiceCookie = CookieUtils.checkValidServiceCookie(cookie.getName(),
          cookieNames);
      if (isValidServiceCookie) {
        reqCookies.add(cookie);
        atomicInteger.getAndIncrement();
      }
    });
    final Cookie[] cookies = reqCookies.toArray(new Cookie[atomicInteger.get()]);
    final long endTime = System.currentTimeMillis();
    CompositeProductQuickViewServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        "getRequestCookieListFromResponse", endTime - startTime);
    CompositeProductQuickViewServiceImpl.LOGGER
        .info("End of getRequestCookieListFromResponse method");
    return cookies;
  }

  /**
   * This method will replace the Cookies in request map with the cookies received in response map
   *
   * @param requestMap
   *          request map of the service
   * @param responseCookieList
   *          List of cookies received from response
   * @return the updated cookie list which needs to be sent the next Service of Composite Service
   */
  private List<Cookie> getUpdatedResponseCookieList(Map<String, Object> requestMap,
      List<Cookie> responseCookieList) {
    CompositeProductQuickViewServiceImpl.LOGGER
        .info("Start of getUpdatedResponseCookieList method");
    final long startTime = System.currentTimeMillis();
    final Cookie[] requestCookies = (Cookie[]) requestMap.get(Constant.REQUEST_COOKIES_KEY);
    final List<Cookie> requestCookieList = Objects.nonNull(requestCookies)
        ? Arrays.asList(requestCookies) : Arrays.asList();
    final List<Cookie> updatedResponseCookieList = new ArrayList<>(requestCookieList);
    requestCookieList.forEach(reqCookie -> responseCookieList.forEach(cookie -> {
      if (StringUtils.equals(cookie.getName(), reqCookie.getName())) {
        CookieUtils.replaceCookieFromList(reqCookie, cookie, updatedResponseCookieList);
      }
    }));
    final long endTime = System.currentTimeMillis();
    CompositeProductQuickViewServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
        "getUpdatedResponseCookieList", endTime - startTime);
    CompositeProductQuickViewServiceImpl.LOGGER.info("End of getUpdatedResponseCookieList method");
    return updatedResponseCookieList;
  }

  private PDPProductUIResponse formatProduct(PDPResponse pdpResponse,Map<String, Object> requestMap) {
    CompositeProductQuickViewServiceImpl.LOGGER
        .info("CompositeProductQuickViewService formatProduct Start");
    PDPProductUIResponse pdpProductUIResponse = null;
    final Map<String, Product> catalogView = pdpResponse.getCatalogEntryView().get(0);
    final Product product = catalogView.get(Constant.PRODUCT);
    if (Objects.nonNull(product)) {
      final String partnumber = (String) requestMap.get(Constant.PART_NUMBER);
      final String siteKey = (String) requestMap.get(Constant.STORE_KEY);
      final List<String> experienceFragmentPath = marketingContentProviderService.getContentFromTags(siteKey, partnumber,MARKETING_CONTENT_POSITIONTYPE);
      pdpProductUIResponse = ProductUIAdapter.transformProductToSignleSKU(product,experienceFragmentPath);

    }
    CompositeProductQuickViewServiceImpl.LOGGER
        .info("CompositeProductQuickViewService formatProduct End");
    return pdpProductUIResponse;
  }
}
