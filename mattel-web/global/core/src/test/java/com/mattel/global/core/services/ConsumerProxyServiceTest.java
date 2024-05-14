package com.mattel.global.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.interfaces.ConsumerPreferenceService;
import com.mattel.global.core.pojo.ConsumerPreferenceResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerProxyServiceTest {
    private final ObjectMapper mapper = new ObjectMapper();

    //Get and Post services
    @Mock
    private ConsumerPreferenceService consumerPreferenceService;

    @InjectMocks
    private ConsumerProxyService impl;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMakeServiceCallsForGetConsumerPreferences() throws ServiceException, IOException, JSONException {
        final String selectorString = Constant.CONSUMER_PREFERENCE_SERVICE;
        final Map<String, Object> requestMap = new HashMap<>();
        final ConsumerPreferenceResponse consumerPreferenceResponse = new ConsumerPreferenceResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(consumerPreferenceResponse));
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_GET);
        Mockito.when(consumerPreferenceService.fetchEmailPreference(requestMap)).thenReturn(consumerPreferenceResponse);
        Mockito.when(consumerPreferenceService.getResponseValueAsJson(consumerPreferenceResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selectorString);

        Assert.assertNotNull(responseMap);
    }

    @Test
    public void testMakeServiceCallsForUpdateConsumerPreferences() throws ServiceException, IOException, JSONException {
        final String selectorString = Constant.CONSUMER_PREFERENCE_SERVICE;
        final Map<String, Object> requestMap = new HashMap<>();
        final ConsumerPreferenceResponse consumerPreferenceResponse = new ConsumerPreferenceResponse();
        final JSONObject value = new JSONObject(mapper.writeValueAsString(consumerPreferenceResponse));
        Map<String, Object> responseMap;

        requestMap.put(Constant.METHOD_TYPE, HttpConstants.METHOD_POST);
        Mockito.when(consumerPreferenceService.updateEmailPreference(requestMap)).thenReturn(consumerPreferenceResponse);
        Mockito.when(consumerPreferenceService.getResponseValueAsJson(consumerPreferenceResponse)).thenReturn(value);
        responseMap = impl.makeServiceCalls(requestMap, selectorString);

        Assert.assertNotNull(responseMap);
    }

    @Test
    public void testMakeServiceCallsForDefault() {
        final String selectorString = "default";
        final Map<String, Object> requestMap = new HashMap<>();
        Map<String, Object> responseMap = impl.makeServiceCalls(requestMap, selectorString);
        Assert.assertNotNull(responseMap);
    }
}