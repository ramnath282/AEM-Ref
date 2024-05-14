package com.mattel.ecomm.login.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.pojos.EmailValidationCodeResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class EmailValidationCodeImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xResetPassword/xSendValidationCode?responseFormat=json&updateCookies=true";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private EmailValidationCodeImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testEmailValidationCode() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("email_validation_request.json")) {
            final EmailValidationCodeImpl.Config config = Mockito.mock(EmailValidationCodeImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            EmailValidationCodeResponse emailValidationCodeResponse;
            mockWebServer = new MockWebServer();

            mockResponse.setResponseCode(HttpStatus.SC_OK);
            mockResponse.setBody("{" + "\"userId\"" + ":" + "\"123456\"" + "}");
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            cookie.setComment("HttpOnly");
            cookie.setDomain("pattern");
            cookie.setMaxAge(2);
            cookie.setPath("uri");
            cookie.setSecure(true);
            cookie.setValue("newValue");
            cookie.setVersion(1);
            cookieObj[0] = cookie;
            requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            emailValidationCodeResponse = impl.emailValidationCode(requestHeader);

            Assert.assertNotNull(emailValidationCodeResponse);
            Assert.assertTrue(emailValidationCodeResponse.isEmailValidationProcessStatus());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test
    public void testEmailValidationCodeForMissingUserName() throws IOException, ServiceException {
        try (InputStream is1 = getClass().getResourceAsStream("email_validation_request_missing_username.json");
                InputStream is2 = getClass().getResourceAsStream("email_validation_response_missing_username.json")) {
            final EmailValidationCodeImpl.Config config = Mockito.mock(EmailValidationCodeImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            EmailValidationCodeResponse emailValidationCodeResponse;
            mockWebServer = new MockWebServer();

            mockResponse.setResponseCode(HttpStatus.SC_OK);
            mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            cookie.setComment("HttpOnly");
            cookie.setDomain("pattern");
            cookie.setMaxAge(2);
            cookie.setPath("uri");
            cookie.setSecure(true);
            cookie.setValue("newValue");
            cookie.setVersion(1);
            cookieObj[0] = cookie;
            requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is1, StandardCharsets.UTF_8));
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            emailValidationCodeResponse = impl.emailValidationCode(requestHeader);

            Assert.assertNotNull(emailValidationCodeResponse);
            Assert.assertTrue(emailValidationCodeResponse.isEmailValidationProcessStatus());
            Assert.assertNotNull(emailValidationCodeResponse.getErrors());
            Assert.assertEquals(1, emailValidationCodeResponse.getErrors().size());
            Assert.assertEquals("_ERR_USER_NOT_FOUND", emailValidationCodeResponse.getErrors().get(0).getErrorKey());
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected=ServiceException.class)
    public void testEmailValidationCodeForWCSError() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("email_validation_request.json")) {
            final EmailValidationCodeImpl.Config config = Mockito.mock(EmailValidationCodeImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestHeader = new HashMap<>();
            final Cookie cookie = new Cookie("JSESSIONID", "213123132");
            final Cookie[] cookieObj = new Cookie[1];
            mockWebServer = new MockWebServer();

            mockResponse.setResponseCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            mockWebServer.enqueue(mockResponse);
            mockWebServer.start();
            cookie.setComment("HttpOnly");
            cookie.setDomain("pattern");
            cookie.setMaxAge(2);
            cookie.setPath("uri");
            cookie.setSecure(true);
            cookie.setValue("newValue");
            cookie.setVersion(1);
            cookieObj[0] = cookie;
            requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(mockWebServer.getPort())));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.emailValidationCode(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR), serviceException.getErrorKey());
            Assert.assertEquals("Generic Error Ocuured", serviceException.getErrorMessage());
            throw serviceException;
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }

    @Test(expected=ServiceException.class)
    public void testEmailValidationCodeForWCSDown() throws IOException, ServiceException {
        try (InputStream is = getClass().getResourceAsStream("email_validation_request.json")) {
            final EmailValidationCodeImpl.Config config = Mockito.mock(EmailValidationCodeImpl.Config.class);
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
            requestHeader.put(Constant.REQUEST_BODY, IOUtils.toString(is, StandardCharsets.UTF_8));
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(9433)));
            Mockito.when(propertyReaderService.getStoreId("AG")).thenReturn("10651");
            Mockito.when(propertyReaderService.getCookieDomain("AG")).thenReturn("localhost");
            impl.activate(config);
            impl.emailValidationCode(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception Occured", serviceException.getErrorMessage());
            throw serviceException;
        }
    }

    @Test(expected=ServiceException.class)
    public void testEmailValidationCodeForInvalidRequest() throws IOException, ServiceException {
        try {
            final EmailValidationCodeImpl.Config config = Mockito.mock(EmailValidationCodeImpl.Config.class);
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
            requestHeader.put(Constant.REQUEST_BODY, "");
            requestHeader.put(Constant.STORE_KEY, "AG");
            requestHeader.put(Constant.DOMAIN_KEY, "AG");
            requestHeader.put(Constant.REQUEST_COOKIES_KEY, cookieObj);
            Mockito.when(config.endPoint())
                    .thenReturn(endPointUrl.replace("PORT_NUMBER", String.valueOf(9433)));
            impl.activate(config);
            impl.emailValidationCode(requestHeader);
        } catch (final ServiceException serviceException) {
            Assert.assertEquals(Constant.IO_ERROR_KEY, serviceException.getErrorKey());
            Assert.assertEquals("IO Exception thrown from message body", serviceException.getErrorMessage());
            throw serviceException;
        }
    }
}
