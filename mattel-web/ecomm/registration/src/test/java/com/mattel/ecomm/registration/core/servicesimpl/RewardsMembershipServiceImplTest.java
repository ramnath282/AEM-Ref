package com.mattel.ecomm.registration.core.servicesimpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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
import com.mattel.ecomm.coreservices.core.pojos.RewardsMembershipResponse;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(MockitoJUnitRunner.class)
public class RewardsMembershipServiceImplTest {
    private final String endPointUrl = "http://localhost:PORT_NUMBER/wcs/resources/store/STORE_ID/xuser/findRewardsAccount";
    private MockWebServer mockWebServer;
    @Mock
    private PropertyReaderService propertyReaderService;
    @InjectMocks
    private RewardsMembershipServiceImpl impl;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFindForSuccess() throws IOException, ServiceException {
        try (InputStream is1 = getClass().getResourceAsStream("rewards_membership_request.json");
                InputStream is2 = getClass().getResourceAsStream("rewards_membership_wcs_loyalty.json")) {
            final RewardsMembershipServiceImpl.Config config = Mockito.mock(RewardsMembershipServiceImpl.Config.class);
            final MockResponse mockResponse = new MockResponse();
            final Map<String, Object> requestMap = new HashMap<>();
            RewardsMembershipResponse rewardsMembershipResponse;

            mockWebServer = new MockWebServer();
            mockResponse.setBody(IOUtils.toString(is2, StandardCharsets.UTF_8));
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
            rewardsMembershipResponse = impl.find(requestMap);

            Assert.assertNotNull(rewardsMembershipResponse);
            Assert.assertEquals("fname", rewardsMembershipResponse.getFirstName());
            Assert.assertEquals("lname", rewardsMembershipResponse.getLastName());
            Assert.assertTrue(rewardsMembershipResponse.isMembershipAccountFound());
            Assert.assertEquals("G", rewardsMembershipResponse.getUserType());
            Assert.assertEquals("1231123", rewardsMembershipResponse.getPhone());
            Assert.assertTrue(Arrays.asList(rewardsMembershipResponse.getMembershipId()).contains("132456"));
        } finally {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        }
    }
}
