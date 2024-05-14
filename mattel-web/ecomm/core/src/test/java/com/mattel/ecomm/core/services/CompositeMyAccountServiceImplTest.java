package com.mattel.ecomm.core.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;
import com.mattel.ecomm.coreservices.core.interfaces.AddressInfo;
import com.mattel.ecomm.coreservices.core.interfaces.AgRewards;
import com.mattel.ecomm.coreservices.core.interfaces.PaymentInfo;
import com.mattel.ecomm.coreservices.core.pojos.Address;
import com.mattel.ecomm.coreservices.core.pojos.AddressInfoResponse;
import com.mattel.ecomm.coreservices.core.pojos.AgRewardsResponse;
import com.mattel.ecomm.coreservices.core.pojos.Card;
import com.mattel.ecomm.coreservices.core.pojos.LoyaltyDetails;
import com.mattel.ecomm.coreservices.core.pojos.PaymentInfoResponse;

@RunWith(MockitoJUnitRunner.class)
public class CompositeMyAccountServiceImplTest {
    @Spy
    private AddressInfo addressInfo;
    @Spy
    private AgRewards agRewards;
    @Spy
    private PaymentInfo paymentInfo;
    @InjectMocks
    private CompositeMyAccountServiceImpl impl;

    @Test
    public void testGetCompositeMyAccountServiceResponse() throws Exception {
        final Map<String, Object> requestMap = new HashMap<>();
        final Map<String, Object> responseMap = new HashMap<>();
        final Address address = new Address();
        final LoyaltyDetails loyaltyDetails = new LoyaltyDetails();
        final Card card  = new Card();
        final AddressInfoResponse addressInfoResponse = new AddressInfoResponse();
        final AgRewardsResponse agRewardsResponse = new AgRewardsResponse();
        final PaymentInfoResponse paymentInfoResponse = new PaymentInfoResponse();

        final Cookie cookie1 = new Cookie("JSESSIONID",
                "0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq");
        final Cookie cookie2 = new Cookie("WC_SESSION_ESTABLISHED", "true");
        final Cookie cookie3 = new Cookie("WC_PERSISTENT",
                "kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651");
        final Cookie cookie4 = new Cookie("WC_AUTHENTICATION_11450726",
                "11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D");
        final Cookie cookie5 = new Cookie("WC_ACTIVEPOINTER", "-1%2C10651");
        final Cookie cookie6 = new Cookie("WC_USERACTIVITY_11450726", "test");
        final Cookie cookie7 = new Cookie("MATTEL_TEST", "test");
        final List<Cookie> cookies = Arrays.asList(cookie1,
                cookie2, cookie3, cookie4, cookie5, cookie6, cookie7);

        cookie1.setPath("/wcs/resources");
        cookie1.setMaxAge((int)TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
        cookie1.setComment("; HttpOnly;");
        cookie1.setSecure(true);
        cookie3.setMaxAge((int)TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
        cookie4.setSecure(true);

        requestMap.put(Constant.REQUEST_COOKIES_KEY, cookies.toArray());
        address.setAddressId("1234");
        addressInfoResponse.setAddress(Arrays.asList(address));
        addressInfoResponse.setDefaultShipMethod("Standard Shipping");
        Mockito.when(addressInfo.getAddressInfo(requestMap)).thenReturn(addressInfoResponse);
        cookies.get(2).setValue("zAP%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651");
        loyaltyDetails.setLoyaltyNumber(201005892);
        agRewardsResponse.setLoyaltyDetails(loyaltyDetails);
        agRewardsResponse.setCookieList(new ArrayList<>(cookies));
        Mockito.when(agRewards.getAgReward(requestMap)).thenReturn(agRewardsResponse);
        cookies.get(5).setValue("test2");
        card.setCreditCardId("42424242");
        paymentInfoResponse.setCards(Arrays.asList(card));
        paymentInfoResponse.setCookieList(new ArrayList<>(cookies));
        Mockito.when(paymentInfo.getPaymentInformation(requestMap)).thenReturn(paymentInfoResponse);
        impl.getCompositeMyAccountServiceResponse(requestMap, responseMap);
    }

    @Test
    public void testGetCompositeMyAccountServiceResponseForServiceException() throws Exception {
        final Map<String, Object> requestMap = new HashMap<>();
        final Map<String, Object> responseMap = new HashMap<>();
        final Cookie cookie1 = new Cookie("JSESSIONID",
                "0000Dch-UevJAP6hq7Juda6be7F:1b7o43dnq");
        final Cookie cookie2 = new Cookie("WC_SESSION_ESTABLISHED", "true");
        final Cookie cookie3 = new Cookie("WC_PERSISTENT",
                "kA6%2FJwFUGfMUwswLwcortDevqEU%3D%0A%3B2019-03-28+01%3A59%3A38.031_1552563415165-1_10651");
        final Cookie cookie4 = new Cookie("WC_AUTHENTICATION_11450726",
                "11450726%2Ciq3xEXbdEdFZIDrGRgy0MgFhKq0%3D");
        final Cookie cookie5 = new Cookie("WC_ACTIVEPOINTER", "-1%2C10651");
        final Cookie cookie6 = new Cookie("WC_USERACTIVITY_11450726", "test");
        final Cookie cookie7 = new Cookie("MATTEL_TEST", "test");
        final List<Cookie> cookies = Arrays.asList(cookie1,
                cookie2, cookie3, cookie4, cookie5, cookie6, cookie7);

        cookie1.setPath("/wcs/resources");
        cookie1.setMaxAge((int)TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
        cookie1.setComment("; HttpOnly;");
        cookie1.setSecure(true);
        cookie3.setMaxAge((int)TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
        cookie4.setSecure(true);

        requestMap.put(Constant.REQUEST_COOKIES_KEY, cookies.toArray());
        Mockito.when(addressInfo.getAddressInfo(requestMap)).thenThrow(new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured"));
        Mockito.when(agRewards.getAgReward(requestMap)).thenThrow(new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured"));
        Mockito.when(paymentInfo.getPaymentInformation(requestMap)).thenThrow(new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured"));
        impl.getCompositeMyAccountServiceResponse(requestMap, responseMap);
    }

    @Test
    public void testGetRequestCookieListFromResponse() throws Exception {
    }
}
