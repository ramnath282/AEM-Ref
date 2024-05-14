package com.mattel.ecomm.pdp.core.servicesimpl;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.AvailableItem;
import com.mattel.ecomm.coreservices.core.pojos.StoreAvailabilityResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class StoreAvailabilityImplTest {

	private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/mattelInventoryAvailability/getStoreAvailability?updateCookies=true";

	private MockWebServer mockWebServer;

	private StoreAvailabilityResponse storeAvailabilityResponse;

	@Mock
	private PropertyReaderService propertyReaderService;

	@InjectMocks
	private StoreAvailabilityImpl storeAvailabilityImpl;

	@Test
	public void testGetStoreAvailability() throws IOException, ServiceException {
		try (InputStream is1 = getClass().getResourceAsStream("storeavailability_request.json");
   			 InputStream is2 = getClass().getResourceAsStream("get_storeAvailability_response.json")) {
			final StoreAvailabilityImpl.Config config = Mockito.mock(StoreAvailabilityImpl.Config.class);
			final MockResponse mockResponse = new MockResponse();
			final Map<String, Object> requestMap = new HashMap<>();
			mockWebServer = new MockWebServer();
			mockResponse.setResponseCode(HttpStatus.SC_OK);
			mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
			mockWebServer.enqueue(mockResponse);
			mockWebServer.start();
			requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
			requestMap.put(Constant.STORE_KEY, "AG");
			requestMap.put(Constant.DOMAIN_KEY, "AG");
			Mockito.when(config.endPoint())
					.thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
			Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
			storeAvailabilityImpl.activate(config);
			storeAvailabilityResponse=storeAvailabilityImpl.getStoreAvailability(requestMap);
			Assert.assertNotNull(storeAvailabilityResponse);
			List<AvailableItem> availabilityDetailsList = storeAvailabilityResponse.getItemAvailabilityDetailsList();
			AvailableItem availableItem = availabilityDetailsList.get(0);
			Assert.assertEquals("FDW89",availableItem.getItemCode());
			Assert.assertEquals("110",availableItem.getStoreLocation());
		} finally {
			if (mockWebServer != null)
				mockWebServer.shutdown();
		}
	}
	
	@Test(expected = ServiceException.class)
	public void testGetStoreAvailability_withWCSError() throws IOException, ServiceException {
		try (InputStream is1 = getClass().getResourceAsStream("storeavailability_request.json")) {
			final StoreAvailabilityImpl.Config config = Mockito.mock(StoreAvailabilityImpl.Config.class);
			final MockResponse mockResponse = new MockResponse();
			final Map<String, Object> requestMap = new HashMap<>();
			mockWebServer = new MockWebServer();
			mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			mockWebServer.enqueue(mockResponse);
			mockWebServer.start();
			requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
			requestMap.put(Constant.STORE_KEY, "AG");
			requestMap.put(Constant.DOMAIN_KEY, "AG");
			Mockito.when(config.endPoint())
					.thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
			Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
			storeAvailabilityImpl.activate(config);
			storeAvailabilityImpl.getStoreAvailability(requestMap);
		}catch (final ServiceException serviceException) {
			Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), serviceException.getErrorKey());
			Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
			throw serviceException;
		} finally {
			if (mockWebServer != null)
				mockWebServer.shutdown();
		}
	}

	@Test(expected = ServiceException.class)
	public void testGetStoreAvailability_withWCSDown() throws IOException, ServiceException {
		try (InputStream is1 = getClass().getResourceAsStream("storeavailability_request.json")) {
			final StoreAvailabilityImpl.Config config = Mockito.mock(StoreAvailabilityImpl.Config.class);
			final MockResponse mockResponse = new MockResponse();
			final Map<String, Object> requestMap = new HashMap<>();
			mockWebServer = new MockWebServer();
			mockResponse.setResponseCode(HttpStatus.SC_SERVICE_UNAVAILABLE);
			mockWebServer.enqueue(mockResponse);
			mockWebServer.start();
			requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
			requestMap.put(Constant.STORE_KEY, "AG");
			requestMap.put(Constant.DOMAIN_KEY, "AG");
			Mockito.when(config.endPoint())
					.thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
			Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
			storeAvailabilityImpl.activate(config);
			storeAvailabilityImpl.getStoreAvailability(requestMap);
		}catch (final ServiceException serviceException) {
			Assert.assertEquals("503", serviceException.getErrorKey());
			System.out.println(serviceException.getErrorMessage());
			Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
			throw serviceException;
		} finally {
			if (mockWebServer != null)
				mockWebServer.shutdown();
		}
	}
	@Test(expected = ServiceException.class)
	public void testGetStoreAvailability_withEmptyRequestPayload() throws IOException, ServiceException {
		try {
			final StoreAvailabilityImpl.Config config = Mockito.mock(StoreAvailabilityImpl.Config.class);
			final MockResponse mockResponse = new MockResponse();
			final Map<String, Object> requestMap = new HashMap<>();
			mockWebServer = new MockWebServer();
			mockWebServer.enqueue(mockResponse);
			mockWebServer.start();
			requestMap.put(Constant.REQUEST_BODY,"");
			requestMap.put(Constant.STORE_KEY, "AG");
			requestMap.put(Constant.DOMAIN_KEY, "AG");
			Mockito.when(config.endPoint())
					.thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));			
			storeAvailabilityImpl.activate(config);
			storeAvailabilityResponse=storeAvailabilityImpl.getStoreAvailability(requestMap);
		}catch (final ServiceException serviceException) {
			Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
			Assert.assertEquals("IO Exception thrown from message body", serviceException.getErrorMessage());
			throw serviceException;
		} finally {
			if (mockWebServer != null)
				mockWebServer.shutdown();
		}
	}
}
