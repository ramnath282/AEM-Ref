package com.mattel.ecomm.loyaltyrewards.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
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
import com.mattel.ecomm.coreservices.core.interfaces.CustomerService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.CustomerRequest;
import com.mattel.ecomm.coreservices.core.pojos.CustomerResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@Component(service = CustomerService.class)
@Designate(ocd = CustomerServiceImpl.Config.class)
public class CustomerServiceImpl implements CustomerService {

  private static final String END_POINT_URL_KEY = "endPointUrl";
  private static final String REQUEST_HEADERS = "requestHeaders";
  private static final String EXECUTE_SERVICE = "execute";

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
  private static final ObjectReader RESP_READER = ResourceMapper
      .getReaderInstance(CustomerResponse.class);
  private static final ObjectReader REQ_READER = ResourceMapper
	      .getReaderInstance(CustomerRequest.class);
  
  @Reference
  PropertyReaderService propertyReaderService;
  private String getCustomerDataEndpoint;
  private String apiKey;

  @SuppressWarnings("unchecked")
  @Override
  public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap)
      throws ServiceException {
    CustomerServiceImpl.LOGGER.info("{} - start", CustomerServiceImpl.EXECUTE_SERVICE);
    final long startTime = System.currentTimeMillis();
    final Map<String, String> requestHeaders = (Map<String, String>) dataMap.get(CustomerServiceImpl.REQUEST_HEADERS);
    final Map<String, String> responseHeaders = new HashMap<>();

    try (CloseableHttpClient httpClient = createCustomClient(dataMap)) {
      final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
      final CustomerRequest customerRequest = (CustomerRequest) baseRequest;
      String endpoint = dataMap.get(CustomerServiceImpl.END_POINT_URL_KEY).toString();
      HttpResponse httpResponse;
      //temporary code . remove content/dam check code later
      if(endpoint.contains("/content/dam/")){
    	  LOGGER.info("endpoint contains dam path : {}",endpoint);
    	  httpResponse = HttpRequestHandler.get(httpClient,
    	          dataMap.get(CustomerServiceImpl.END_POINT_URL_KEY).toString(), null, requestHeaders,
    	          httpClientContext);
      } else {
    	  LOGGER.info("its is actual endpoint : {}",endpoint);
    	  httpResponse = HttpRequestHandler.post(httpClient,
    	          dataMap.get(CustomerServiceImpl.END_POINT_URL_KEY).toString(), null, requestHeaders,
    	          customerRequest,httpClientContext);
      }
      final int status = httpResponse.getStatusLine().getStatusCode();
      CustomerServiceImpl.LOGGER.debug("Response status is {}", status);
      if (null != httpResponse.getEntity() && !isError(status)) {
        try (final InputStream inputStream = httpResponse.getEntity().getContent()) {
          CustomerResponse customerResponse;
          customerResponse = CustomerServiceImpl.RESP_READER.readValue(inputStream);
          
          final Header statusHeader = httpResponse.getFirstHeader("x-token-status");
          if(Objects.nonNull(statusHeader)){
        	  responseHeaders.put("x-token-status", statusHeader.getValue());
              customerResponse.setResponseHeaders(responseHeaders);
          }
          
          CustomerServiceImpl.LOGGER.debug("Customer service response: {}",customerResponse);
          CustomerServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, CustomerServiceImpl.EXECUTE_SERVICE,
              (System.currentTimeMillis() - startTime));
          return customerResponse;
        }
      } else {
        final long endTime = System.currentTimeMillis();
        CustomerServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, CustomerServiceImpl.EXECUTE_SERVICE,
            endTime - startTime);
        generalExceptionHandling(status, httpResponse.getEntity());
      }
    } catch (final IOException io) {
      final long endTime = System.currentTimeMillis();
      CustomerServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, CustomerServiceImpl.EXECUTE_SERVICE,
          endTime - startTime);
      CustomerServiceImpl.LOGGER.error("Encountered error:", io);
      throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
    }
    final long endTime = System.currentTimeMillis();
    CustomerServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, CustomerServiceImpl.EXECUTE_SERVICE,
        endTime - startTime);
    return null;
  }

  @Override
  public CustomerResponse fetch(Map<String, Object> requestMap) throws ServiceException {
    CustomerServiceImpl.LOGGER.info("fetch - start");
    final long startTime = System.currentTimeMillis();
    final String storekey = (String) requestMap.get(Constant.STORE_KEY);
    final Map<String, Object> dataMap = new HashMap<>();
    final Map<String, String> requestHeaders = new HashMap<>();
    try {
		final CustomerRequest customerRequest = CustomerServiceImpl.REQ_READER
		        .readValue(requestMap.get(Constant.REQUEST_BODY).toString());
		    requestHeaders.put("api_key", apiKey);

		    final long endTime;
		    dataMap.put(CustomerServiceImpl.END_POINT_URL_KEY, getCustomerDataEndpoint);
		    dataMap.put(CustomerServiceImpl.REQUEST_HEADERS,requestHeaders);
		    dataMap.put(Constant.DEF_CONNECT_TIMEOUT, getDefaultConnectTimeout(propertyReaderService, storekey));
		    endTime = System.currentTimeMillis();
		    CustomerServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "CustomerServiceImpl", endTime - startTime);
		    return (CustomerResponse) execute(customerRequest, dataMap);
	} catch (IOException e) {
		final long endTime = System.currentTimeMillis();
		CustomerServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "fetch", endTime - startTime);
		CustomerServiceImpl.LOGGER.error("IO Exception occured {}", e);
		throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
	}
  }

  @ObjectClassDefinition(name = "EAI Customer Service Configurations", 
      description = "Customer Service Configuration for EAI Vendor")
  public @interface Config {
    @AttributeDefinition(name = "Customer Service End Point", 
        description = "Please Enter the Customer Service End Point")
    String endPoint() default "https://api.dev.mattel.com/qa/consumer/fetchloyaltydetails";
    
    @AttributeDefinition(name = "Customer Service API Key", 
            description = "Please Enter the Customer Service API Key in the format")
    String apiKey() default "gjztytm49er5ccpbx4v3nc4f";
  }

  @Activate
  public void activate(final Config config) {
	  getCustomerDataEndpoint = config.endPoint();
	  apiKey = config.apiKey();
  }

}
