package com.mattel.global.core.services;

import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.pojo.ConsumerPreferenceResponse;
import com.mattel.global.core.pojo.UpdateConsumerPrefRequest;
import com.mattel.global.core.utils.CryptoSupportUtils;
import com.mattel.global.core.utils.HttpRequestHandler;
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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ CryptoSupportUtils.class, HttpRequestHandler.class })
@PowerMockIgnore({ "javax.net.ssl.*", "javax.security.*" })
public class ConsumerPreferenceServiceImplTest2 {

    private final String endPointUrl = "http://localhost:PORT_NUMBER/preprod/consumerpreferences/filteredresponse";
    private final String apiKey = "4csmwmp8ff55ur2aanfa568x";
    private MockWebServer mockWebServer;
    private ConsumerPreferenceResponse consumerPreferenceResponse;

    @InjectMocks
    private ConsumerPreferenceServiceImpl impl;
    @InjectMocks
    private UpdateConsumerPrefRequest updateConsumerPrefRequest;

    @Test
    public void testFetchEmailPreference() throws IOException, ServiceException,
        URISyntaxException {
        try(InputStream inputStream = getClass().getResourceAsStream("get_consumer_preferences.json")) {
            final ConsumerPreferenceServiceImpl.Config config = Mockito.mock(ConsumerPreferenceServiceImpl.Config.class);

            final Map<String, Object> requestMap = new HashMap<>();
            PowerMockito.mockStatic(CryptoSupportUtils.class);
            PowerMockito.mockStatic(HttpRequestHandler.class);

            if(Objects.nonNull(inputStream)) {
                HttpResponse  httpResponse =Mockito.mock(HttpResponse.class);
                Mockito.when(HttpRequestHandler.get(Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any(),
                    Mockito.any())).thenReturn(httpResponse);
                StatusLine  statusLine= Mockito.mock(StatusLine.class);
                HttpEntity httpEntity = Mockito.mock(HttpEntity.class);
                Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
                Mockito.when(statusLine.getStatusCode()).thenReturn(200);
                Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
                Mockito.when(httpEntity.getContent()).thenReturn(inputStream);
            }

            requestMap.put(Constant.EMAIL_ADDRESS, "email_address");
            Mockito.when(CryptoSupportUtils.decryptString((String)requestMap.get(Constant.EMAIL_ADDRESS))).thenReturn("test@mattel.com");
            Mockito.when(config.endPoint()).thenReturn(endPointUrl);
            Mockito.when(config.apiKey()).thenReturn(apiKey);
            impl.activate(config);
            consumerPreferenceResponse = impl.fetchEmailPreference(requestMap);

            Assert.assertNotNull(consumerPreferenceResponse);
            Assert.assertEquals(String.valueOf(HttpStatus.SC_OK), consumerPreferenceResponse.getStatus().getStatusCode());

        }
    }

    @Test
    public void testUpdateEmailPreference() throws IOException, ServiceException {
        try (InputStream inputStream1 = getClass().getResourceAsStream("update_consumer_preferences_request.json");
            InputStream inputStream2 = getClass().getResourceAsStream("get_consumer_preferences.json")) {
            final ConsumerPreferenceServiceImpl.Config config = Mockito.mock(ConsumerPreferenceServiceImpl.Config.class);
            final Map<String, Object> requestMap = new HashMap<>();

            PowerMockito.mockStatic(CryptoSupportUtils.class);
            PowerMockito.mockStatic(HttpRequestHandler.class);

            HttpResponse httpResponse = Mockito.mock(HttpResponse.class);
            Mockito.when(HttpRequestHandler.post(Mockito.any(), Mockito.anyString(), Mockito.any(), Mockito.any(UpdateConsumerPrefRequest.class), Mockito.any()
            )).thenReturn(httpResponse);
            StatusLine statusLine= Mockito.mock(StatusLine.class);
            HttpEntity httpEntity = Mockito.mock(HttpEntity.class);
            Mockito.when(httpResponse.getStatusLine()).thenReturn(statusLine);
            Mockito.when(statusLine.getStatusCode()).thenReturn(200);
            Mockito.when(httpResponse.getEntity()).thenReturn(httpEntity);
            Mockito.when(httpEntity.getContent()).thenReturn(inputStream2);
            requestMap.put(Constant.REQUEST_BODY, IOUtils.toString(inputStream1, StandardCharsets.UTF_8));
            Mockito.when(config.endPoint()).thenReturn(endPointUrl);
            Mockito.when(config.apiKey()).thenReturn(apiKey);
            impl.activate(config);
            consumerPreferenceResponse = impl.updateEmailPreference(requestMap);
            Assert.assertNotNull(consumerPreferenceResponse);

        }
    }

}