package com.mattel.global.core.services;

import com.adobe.granite.crypto.CryptoSupport;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.pojo.ConsumerPreferenceResponse;
import com.mattel.global.core.utils.CryptoSupportUtils;
import com.mattel.global.core.utils.HttpRequestHandler;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.osgi.service.component.annotations.Reference;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerPreferenceServiceImplTest1 {

    private final String endPointUrl = "http://localhost:PORT_NUMBER/preprod/consumerpreferences/filteredresponse";
    private final String apiKey = "4csmwmp8ff55ur2aanfa568x";
    private MockWebServer mockWebServer;
    private ConsumerPreferenceResponse consumerPreferenceResponse;

    @InjectMocks
    private ConsumerPreferenceServiceImpl impl;

    @Test
    public void testFetchEmailPreferenceWithError() throws Exception {
        try {
            final ConsumerPreferenceServiceImpl.Config config = Mockito.mock(ConsumerPreferenceServiceImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();

            mockWebServer = new MockWebServer();
            mockResponse.setResponseCode(HttpStatus.SC_BAD_REQUEST);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();

            requestMap.put(Constant.EMAIL_ADDRESS, "");
            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(config.apiKey()).thenReturn(apiKey);
            impl.activate(config);
            consumerPreferenceResponse = impl.fetchEmailPreference(requestMap);

            Assert.assertNotNull(consumerPreferenceResponse);
            Assert.assertEquals(String.valueOf(HttpStatus.SC_BAD_REQUEST), consumerPreferenceResponse.getStatus().getStatusCode());
        } catch (final ServiceException serviceException){
            Assert.assertEquals(String.valueOf(HttpStatus.SC_BAD_REQUEST), serviceException.getErrorKey());
            Assert.assertEquals("Generic Error Occurred", serviceException.getErrorMessage());
        } finally {
            if (mockWebServer != null) {
                mockWebServer.shutdown();
            }
        }
    }

    @Test
    public void testFetchEmailPreferenceForAPIDown() throws Exception {
        try {
            final ConsumerPreferenceServiceImpl.Config config = Mockito.mock(ConsumerPreferenceServiceImpl.Config.class);
            final Map<String, Object> requestMap = new HashMap<>();

            requestMap.put(Constant.EMAIL_ADDRESS, "");
            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf("8000")));
            Mockito.when(config.apiKey()).thenReturn(apiKey);
            impl.activate(config);
            consumerPreferenceResponse = impl.fetchEmailPreference(requestMap);

            Assert.assertNull(consumerPreferenceResponse);
        } catch (final ServiceException serviceException){
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception", serviceException.getErrorMessage());
        }

    }

    @Test
    public void testUpdateEmailPreferenceWithError() throws IOException, ServiceException {
        try (InputStream inputStream = getClass().getResourceAsStream("update_consumer_preferences_request.json")) {
            final ConsumerPreferenceServiceImpl.Config config = Mockito.mock(ConsumerPreferenceServiceImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();

            mockWebServer = new MockWebServer();
            mockResponse.setResponseCode(HttpStatus.SC_BAD_REQUEST);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(inputStream, StandardCharsets.UTF_8));

            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(config.apiKey()).thenReturn(apiKey);
            impl.activate(config);
            consumerPreferenceResponse = impl.updateEmailPreference(requestMap);
            Assert.assertNull(consumerPreferenceResponse);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(String.valueOf(HttpStatus.SC_BAD_REQUEST), serviceException.getErrorKey());
            Assert.assertEquals("Generic Error Occurred", serviceException.getErrorMessage());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test
    public void testUpdateEmailPreferenceForAPIDown() throws IOException, ServiceException {
        try (InputStream inputStream = getClass().getResourceAsStream("update_consumer_preferences_request.json")) {
            final ConsumerPreferenceServiceImpl.Config config = Mockito.mock(ConsumerPreferenceServiceImpl.Config.class);

            final Map<String, Object> requestMap = new HashMap<>();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(inputStream, StandardCharsets.UTF_8));

            Mockito.when(config.endPoint()).thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf("8090")));
            Mockito.when(config.apiKey()).thenReturn(apiKey);
            impl.activate(config);
            consumerPreferenceResponse = impl.updateEmailPreference(requestMap);
            Assert.assertNull(consumerPreferenceResponse);

        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occurred", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

}