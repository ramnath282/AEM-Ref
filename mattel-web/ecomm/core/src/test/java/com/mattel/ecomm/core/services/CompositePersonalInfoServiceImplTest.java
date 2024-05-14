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
import com.mattel.ecomm.coreservices.core.interfaces.PersonalInfo;
import com.mattel.ecomm.coreservices.core.pojos.ChildInformation;
import com.mattel.ecomm.coreservices.core.pojos.ChildInformationResponse;
import com.mattel.ecomm.coreservices.core.pojos.PersonalInfoResponse;

@RunWith(MockitoJUnitRunner.class)
public class CompositePersonalInfoServiceImplTest {
    @Spy
    private com.mattel.ecomm.coreservices.core.interfaces.ChildInformation childInformationService;
    @Spy
    private PersonalInfo personalInfo;
    @InjectMocks
    private CompositePersonalInfoServiceImpl impl;

    @Test
    public void testGetCompositePersonalInfoServiceResponse() throws Exception {
        final Map<String, Object> requestMap = new HashMap<>();
        final Map<String, Object> responseMap = new HashMap<>();
        final PersonalInfoResponse  personalInfoResponse  = new PersonalInfoResponse ();
        final ChildInformation childInformation = new ChildInformation();
        final ChildInformationResponse childInformationResponse = new ChildInformationResponse();
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
        personalInfoResponse.setFirstName("Jane");
        personalInfoResponse.setLastName("Doe");
        personalInfoResponse.setCookieList(cookies);
        Mockito.when(personalInfo.getPersonalInfo(requestMap)).thenReturn(personalInfoResponse);
        cookies.get(5).setValue("test2");
        childInformation.setFirstName("Jane");
        childInformation.setLastName("Doe");
        childInformationResponse.setChildInformation(Arrays.asList(childInformation));
        childInformationResponse.setCookieList(new ArrayList<>(cookies));
        Mockito.when(childInformationService.getChildrenInfo(requestMap)).thenReturn(childInformationResponse);
        impl.getCompositePersonalInfoServiceResponse(requestMap, responseMap);
    }

    @Test
    public void testGetCompositePersonalInfoServiceResponseForServiceException() throws Exception {
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
        Mockito.when(personalInfo.getPersonalInfo(requestMap)).thenThrow(new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured"));
        Mockito.when(childInformationService.getChildrenInfo(requestMap)).thenThrow(new ServiceException(Constant.IO_ERROR_KEY, "IO Exception Occured"));
        impl.getCompositePersonalInfoServiceResponse(requestMap, responseMap);
    }
}
