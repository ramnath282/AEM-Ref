package com.mattel.ecomm.productdetails.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectReader;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.enums.ResourceMapper;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.CheckGiftCardMessageValidity;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.GiftCard;
import com.mattel.ecomm.coreservices.core.pojos.GiftCardMessageValidity;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = CheckGiftCardMessageValidity.class)
@Designate(ocd = CheckGiftCardMessageValidityImpl.Config.class)
public class CheckGiftCardMessageValidityImpl implements CheckGiftCardMessageValidity {
  private static final Logger LOGGER = LoggerFactory
      .getLogger(CheckGiftCardMessageValidityImpl.class);
  private static final Object CHECK_GIFT_CARD_MSG_SERVICE = "checkGiftCardMsgValidityService";
  /** Reusing GiftCard */
  private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(GiftCard.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(GiftCardMessageValidity.class);
  @Reference
  PropertyReaderService propertyReaderService;
  private String checkGiftCardMsgValidityServiceEndpoint;

  @Override
  public GiftCardMessageValidity execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      final GiftCard checkGiftCardMessageRequet = (GiftCard) baseRequest;
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final Map<String, String> requestHeaders = new HashMap<>();
      final HttpResponse httpResponse;
      final int status;

      requestHeaders.put("Accept", Constant.CONTENT_TYPE_APPLICATION_JSON);
      requestHeaders.put("Content-type", Constant.CONTENT_TYPE_APPLICATION_JSON);
      httpResponse = HttpRequestHandler.post(httpClient,
          dataMap.get(Constant.END_POINT_URL_KEY).toString(), null, requestHeaders,
          checkGiftCardMessageRequet, httpClientContext);
      status = httpResponse.getStatusLine().getStatusCode();

      CheckGiftCardMessageValidityImpl.LOGGER.debug("Response status is {}", status);

      if (!isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          final GiftCardMessageValidity giftCardMessageValidityResp = CheckGiftCardMessageValidityImpl.RESP_READER
              .readValue(inputStream);
          CheckGiftCardMessageValidityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
              Constant.EXECUTE_SERVICE, System.currentTimeMillis() - startTime);
          logServiceErrors(giftCardMessageValidityResp);
          return giftCardMessageValidityResp;
        }
      } else {
        CheckGiftCardMessageValidityImpl.LOGGER.error(
            "Recieved response with error status: {} in {}", status, Constant.EXECUTE_SERVICE);
        CheckGiftCardMessageValidityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
            Constant.EXECUTE_SERVICE, System.currentTimeMillis() - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();

      CheckGiftCardMessageValidityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          Constant.EXECUTE_SERVICE, endTime - startTime);
      CheckGiftCardMessageValidityImpl.LOGGER.error("Encountered error:", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }

    return null;
  }

  @Override
  public GiftCardMessageValidity verify(Map<String, Object> requestMap) throws ServiceException {
    final long startTime = System.currentTimeMillis();

    try {
      final GiftCard giftCard = CheckGiftCardMessageValidityImpl.REQ_READER
          .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
      final String storekey = (String) requestMap.get(Constant.STORE_KEY);
      final String storeId = propertyReaderService.getStoreId(storekey);
      final String endPointUrl = checkGiftCardMsgValidityServiceEndpoint
          .replaceAll(Constant.STORE_ID_PLACEHOLDER, storeId);
      final Map<String, Object> dataMap = new HashMap<>();
      final long endTime;
      dataMap.put(Constant.END_POINT_URL_KEY, endPointUrl);
      endTime = System.currentTimeMillis();

      CheckGiftCardMessageValidityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          CheckGiftCardMessageValidityImpl.CHECK_GIFT_CARD_MSG_SERVICE, endTime - startTime);
      return execute(giftCard, dataMap);
    } catch (final IOException e) {
      final long endTime = System.currentTimeMillis();

      CheckGiftCardMessageValidityImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG,
          CheckGiftCardMessageValidityImpl.CHECK_GIFT_CARD_MSG_SERVICE, endTime - startTime);
      CheckGiftCardMessageValidityImpl.LOGGER.error("Error encountered: ", e);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
    }
  }

  @ObjectClassDefinition(name = "CheckGiftCardMessageValidity Service Configurations")
  public @interface Config {
    @AttributeDefinition(name = "CheckGiftCardMessageValidity Service End Point")
    String endPoint() default "https://mqa.services.commerce.mattel.com/wcs/resources/store/STORE_ID/xchecknames/checkmsg?responseFormat=json&updateCookies=true&storeId=STORE_ID";
  }

  @Activate
  public void activate(Config config) {
    checkGiftCardMsgValidityServiceEndpoint = config.endPoint();
  }
}