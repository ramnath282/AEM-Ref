package com.mattel.global.core.servlets;

import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.services.ConsumerProxyService;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestPathInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.servlet.http.Cookie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ IOUtils.class })
public class ConsumerInfoHandlerTest {
    @InjectMocks
    ConsumerInfoHandler consumerInfoHandler;
    @Mock
    private SlingHttpServletResponse response;
    @Mock
    private SlingHttpServletRequest request;
    @Mock
    private RequestPathInfo pathInfo;

    @Mock ConsumerProxyService consumerProxyService;
    @Mock
    private PrintWriter writer;
    @Mock BufferedReader reader;
    @Mock IOUtils ioUtils;

    @Before
    public void setUp() throws Exception {

        Vector<String> elements = new Vector<String>();
        elements.add(Constant.EMAIL_ADDRESS);
        String selectorString = Constant.CONSUMER_PREFERENCE_SERVICE;
        Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
        Mockito.when(pathInfo.getSelectorString()).thenReturn(selectorString);
        Mockito.when(request.getHeader(Constant.EMAIL_ADDRESS))
            .thenReturn("{bdc642bfb3750251ca3cc39fb2af3f517078618f99f72cf9145533f6a29ea9090b9f42bb67023b51840bf8d67a2202e4}");
        Mockito.when(request.getReader()).thenReturn(reader);
        PowerMockito.mockStatic(IOUtils.class);
        Mockito.when(IOUtils.toString(reader)).thenReturn("testRequestBody");

        MemberModifier.field(ConsumerInfoHandler.class, "consumerProxyService").set(consumerInfoHandler,
            consumerProxyService);
        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put(Constant.RESPONSE_BODY, "testResponse");
        Mockito.when(consumerProxyService.makeServiceCalls(Mockito.any(), Mockito.any()))
            .thenReturn(responseMap);
        Mockito.when(response.getWriter()).thenReturn(writer);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDoGetSlingHttpServletRequestSlingHttpServletResponse() throws IOException {
        consumerInfoHandler.doGet(request, response);
    }

    @Test
    public void testDoPostSlingHttpServletRequestSlingHttpServletResponse() throws IOException {
        consumerInfoHandler.doPost(request, response);
    }

}