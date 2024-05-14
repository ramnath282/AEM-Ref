package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BaseResponseTest {
    private final List<Cookie> cookieList = Arrays.asList();
    private final Map<String,String> responseHeaders = new HashMap<>();

    private final List<ErrorResponse> errors = Arrays.asList();
    private BaseResponse baseResponse;

    @Before
    public void createBaseResponse() throws Exception {
        baseResponse = new BaseResponse();
        baseResponse.setCookieList(cookieList);
        baseResponse.setErrors(errors);
        baseResponse.setResponseHeaders(responseHeaders);
        baseResponse.toString();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetErrors() throws Exception {
        Assert.assertEquals(errors, baseResponse.getErrors());
    }

    @Test
    public void testGetCookieList() throws Exception {
        Assert.assertEquals(cookieList, baseResponse.getCookieList());
    }
    
    @Test
    public void testGetResponseHeaders() throws Exception {
        Assert.assertEquals(responseHeaders, baseResponse.getResponseHeaders());
    }
}
