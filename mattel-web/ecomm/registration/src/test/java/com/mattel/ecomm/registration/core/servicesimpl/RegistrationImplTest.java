package com.mattel.ecomm.registration.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.RegistrationResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xuser/register?storeId=STORE_ID&responseFormat=json";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private RegistrationImpl impl;

    @Test
    public void testMakeRegistrationSuccess() throws IOException, ServiceException {
        try (InputStream is1 = getClass().getResourceAsStream("user_registration_request.json");
                InputStream is2 = getClass().getResourceAsStream("user_registration_response.json")) {
            final RegistrationImpl.Config config = Mockito.mock(RegistrationImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            RegistrationResponse registrationResponse;

            mockWebServer = new MockWebServer();
            mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
            addCookies(mockResponse);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            registrationResponse = impl.makeRegistration(requestMap);

            Assert.assertNotNull(registrationResponse);
            Assert.assertEquals("RedirectView", registrationResponse.getViewTaskName());
            Assert.assertEquals("11191724", registrationResponse.getUserId());
            Assert.assertEquals("25543529", registrationResponse.getAddressId());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test
    public void testMakeRegistrationErrorAlreadyRegistered() throws IOException, ServiceException {
        try (InputStream is1 = getClass().getResourceAsStream("user_registration_request.json");
                InputStream is2 = getClass().getResourceAsStream("user_registration_response_error1.json")) {
            final RegistrationImpl.Config config = Mockito.mock(RegistrationImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            RegistrationResponse registrationResponse;

            mockWebServer = new MockWebServer();
            mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
            addCookies(mockResponse);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            registrationResponse = impl.makeRegistration(requestMap);

            Assert.assertNotNull(registrationResponse);
            Assert.assertNotNull(registrationResponse.getErrors());
            Assert.assertEquals(1, registrationResponse.getErrors().size());
            Assert.assertEquals("_ERR_LOGONID_ALREDY_EXIST", registrationResponse.getErrors().get(0).getErrorKey());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testMakeRegistrationForWCSError() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("user_registration_request.json")) {
            final RegistrationImpl.Config config = Mockito.mock(RegistrationImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();

            mockWebServer = new MockWebServer();
            mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.makeRegistration(requestMap);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), serviceException.getErrorKey());
            Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testMakeRegistrationForInvalidResponse() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("user_registration_request.json")) {
            final RegistrationImpl.Config config = Mockito.mock(RegistrationImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();

            mockWebServer = new MockWebServer();
            mockResponse.setBody("");
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.makeRegistration(requestMap);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected = ServiceException.class)
    public void testMakeRegistrationForWCSDown() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("user_registration_request.json")) {
            final RegistrationImpl.Config config = Mockito.mock(RegistrationImpl.Config.class);
            final Map<String, Object> requestMap = new HashMap<>();

            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.makeRegistration(requestMap);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        }
    }

    @Test(expected = ServiceException.class)
    public void testMakeRegistrationForInvalidRequest() throws IOException, ServiceException {
        try {
            final RegistrationImpl.Config config = Mockito.mock(RegistrationImpl.Config.class);
            final Map<String, Object> requestMap = new HashMap<>();

            requestMap.put(Constant.REQUEST_BODY, "");
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(8080)));
            impl.activate(config);
            impl.makeRegistration(requestMap);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception thrown from message body", serviceException.getErrorMessage());
            throw serviceException;
        }
    }

    @Test
    public void testMakeRegistrationErrorLogonMismatch() throws IOException, ServiceException {
        try (InputStream is1 = getClass().getResourceAsStream("user_registration_request_logon_mismatch.json");
                InputStream is2 = getClass().getResourceAsStream("user_registration_response_error2.json")) {
            final RegistrationImpl.Config config = Mockito.mock(RegistrationImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            RegistrationResponse registrationResponse;

            mockWebServer = new MockWebServer();
            mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
            addCookies(mockResponse);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestMap.put(Constant.STORE_KEY, "AG");
            requestMap.put(Constant.DOMAIN_KEY, "AG");
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            registrationResponse = impl.makeRegistration(requestMap);

            Assert.assertNotNull(registrationResponse);
            Assert.assertNotNull(registrationResponse.getErrors());
            Assert.assertEquals(1, registrationResponse.getErrors().size());
            Assert.assertEquals("_ERR_LOGONID_DO_NOT_MATCH", registrationResponse.getErrors().get(0).getErrorKey());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    private void addCookies(final MockResponse mockResponse) {
        mockResponse.addHeader("Set-Cookie",
                "JSESSIONID=0000Ai-NPQF6j4iYMwCVf_brFbR:1b7o43dnq; path=/; domain=localhost; HttpOnly; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
        mockResponse.addHeader("Set-Cookie",
                "WC_SESSION_ESTABLISHED=true; path=/; domain=localhost; Expires=Tue, 19 Jan 2038 03:14:07 GMT;");
    }
}
