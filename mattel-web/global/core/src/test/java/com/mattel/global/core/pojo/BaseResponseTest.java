package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BaseResponseTest {


    private final Map<String,String> responseHeaders = new HashMap<>();
    private Status status = new Status();
    private BaseResponse baseResponse;

    @Before
    public void createBaseResponse() throws Exception {
        baseResponse = new BaseResponse();
        baseResponse.setResponseHeaders(responseHeaders);
        baseResponse.setStatus(status);
        baseResponse.toString();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetStatus() throws Exception {
        Assert.assertEquals(status, baseResponse.getStatus());
    }

    @Test
    public void testGetResponseHeaders() throws Exception {
        Assert.assertEquals(responseHeaders, baseResponse.getResponseHeaders());
    }
}