package com.mattel.ecomm.fedex.core.servicesimpl;

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
import com.mattel.ecomm.coreservices.core.interfaces.FedExService;
import com.mattel.ecomm.coreservices.core.pojos.BaseRequest;
import com.mattel.ecomm.coreservices.core.pojos.BaseResponse;
import com.mattel.ecomm.coreservices.core.pojos.FedExRequest;
import com.mattel.ecomm.coreservices.core.pojos.FedExResponse;
import com.mattel.ecomm.coreservices.core.utilities.HttpRequestHandler;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

/**
 * @author CTS
 *
 */
@Component(service = FedExService.class)
@Designate(ocd = FedExServiceImpl.Config.class)
public class FedExServiceImpl implements FedExService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FedExServiceImpl.class);

	private static final ObjectReader REQ_READER = ResourceMapper.getReaderInstance(FedExRequest.class);

	private static final ObjectReader RESP_READER = ResourceMapper.getReaderInstance(FedExResponse.class);

	@Reference
	PropertyReaderService propertyReaderService;

	private String fedExEndPoint;
	private static final String STORE_ID_PLACEHOLDER = "STORE_ID";
	private static final String END_POINT_URL_KEY = "endPointUrl";
	private static final String EXECUTE_SERVICE = "Execute";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mattel.ecomm.coreservices.core.interfaces.BaseService#execute(com.
	 * mattel.ecomm.coreservices.core.pojos.BaseRequest, java.util.Map)
	 */
	@Override
	public BaseResponse execute(BaseRequest baseRequest, Map<String, Object> dataMap) throws ServiceException {
		FedExServiceImpl.LOGGER.info("Start of FedEx execute service");
		final long startTime = System.currentTimeMillis();
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			final FedExRequest fedExRequest = (FedExRequest) baseRequest;
			final Map<String, String> requestHeaders = new HashMap<>();
			final HttpClientContext httpClientContext = HttpRequestHandler.buildHttpContext();
			final HttpResponse httpResponse;
			final int status;
			requestHeaders.put("Accept", "application/json");
			requestHeaders.put("Content-type", "application/json");
			httpResponse = HttpRequestHandler.post(client, dataMap.get(FedExServiceImpl.END_POINT_URL_KEY).toString(),
					null, requestHeaders, fedExRequest, httpClientContext);
			status = httpResponse.getStatusLine().getStatusCode();
			FedExServiceImpl.LOGGER.debug("Status is {}", status);
			if (null != httpResponse.getEntity() && !isError(status)) {
				try (InputStream inputStream = httpResponse.getEntity().getContent()) {
					final FedExResponse fedExResponse = FedExServiceImpl.RESP_READER.readValue(inputStream);
					FedExServiceImpl.LOGGER.debug("FedEx service Response {}", fedExResponse);
					final long endTime = System.currentTimeMillis();
					FedExServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, FedExServiceImpl.EXECUTE_SERVICE,
							endTime - startTime);
					return fedExResponse;
				}
			} else {
				final long endTime = System.currentTimeMillis();
				FedExServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, FedExServiceImpl.EXECUTE_SERVICE,
						endTime - startTime);
				generalExceptionHandling(status);
			}
		} catch (final IOException e) {
			FedExServiceImpl.LOGGER.error("Error Encountered:", e);
			final long endTime = System.currentTimeMillis();
			FedExServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "Execure Exception", endTime - startTime);
			throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured");
		}
		final long endTime = System.currentTimeMillis();
		FedExServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, FedExServiceImpl.EXECUTE_SERVICE,
				endTime - startTime);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mattel.ecomm.coreservices.core.interfaces.FedExService#fetch(java.
	 * util.Map)
	 */
	@Override
	public FedExResponse fetch(Map<String, Object> requestMap) throws ServiceException {
		FedExServiceImpl.LOGGER.info("Start of FedEx fetch service");
		final long startTime = System.currentTimeMillis();
		try {
			final FedExRequest fedExRequest = FedExServiceImpl.REQ_READER
					.readValue(requestMap.get(Constant.REQUEST_BODY).toString());
			FedExServiceImpl.LOGGER.debug("FedExRequest Request is {}", fedExRequest);
			final String storekey = (String) requestMap.get(Constant.STORE_KEY);
			final String storeId = propertyReaderService.getStoreId(storekey);
			final String endPointUrl = fedExEndPoint.replaceAll(FedExServiceImpl.STORE_ID_PLACEHOLDER, storeId);
			final Map<String, Object> dataMap = new HashMap<>();
			dataMap.put(FedExServiceImpl.END_POINT_URL_KEY, endPointUrl);
			final long endTime = System.currentTimeMillis();
			FedExServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "FedEx", endTime - startTime);
			return (FedExResponse) execute(fedExRequest, dataMap);
		} catch (final IOException e) {
			final long endTime = System.currentTimeMillis();
			FedExServiceImpl.LOGGER.debug(Constant.EXECUTION_TIME_LOG, "FedEx Exception", endTime - startTime);
			FedExServiceImpl.LOGGER.error("Error Encountered {}", e);
			throw new ServiceException(Constant.IO_ERROR_KEY, "IO Exception thrown from message body");
		}
	}

	@ObjectClassDefinition(name = "WCS FedEx Service Configurations", description = "WCS FedEx Service Endpoint Configurations")
	public @interface Config {
		@AttributeDefinition(name = "FedEx Service End Point", description = "Please Enter the FedEx Service End Point in the format"
				+ "http://domain/restendpoint/wcs/resources/store/${store_id}/fedex/generateReturnLabel?responseFormat=json&updateCookies=true")
		String endPoint() default "https://api.dev.mattel.com/QA/store/STORE_ID/fedex/generateReturnLabel?responseFormat=json&updateCookies=true&api_key=muzt944eh78z8aqmqc9xkqfv";
	}

	@Activate
	public void activate(Config config) {
		fedExEndPoint = config.endPoint();
	}

}
