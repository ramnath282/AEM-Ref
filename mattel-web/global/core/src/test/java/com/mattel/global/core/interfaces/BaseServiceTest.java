package com.mattel.global.core.interfaces;

import com.mattel.global.core.constants.Constant;
import com.mattel.global.core.exceptions.ServiceException;
import com.mattel.global.core.pojo.BaseResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BaseServiceTest {
    BaseService impl;

    @Before
    public void setUp() throws Exception {
        impl = (baseRequest, dataMap) -> new BaseResponse();
    }

    @Test(expected = ServiceException.class)
    public void testGetResponseValueAsJsonForError1() throws Exception {
        try {
            impl.getResponseValueAsJson(null);
        } catch (final ServiceException se) {
            Assert.assertEquals("1004", se.getErrorKey());
            throw se;
        }
    }

    @Test(expected = ServiceException.class)
    public void testGetResponseValueAsJsonForError2() throws Exception {
        try {
            impl.getResponseValueAsJson(new Object());
        } catch (final ServiceException se) {
            Assert.assertEquals("1003", se.getErrorKey());
            throw se;
        }
    }

    @Test
    public void testGetResponseValueAsJson() throws Exception {
        Assert.assertNotNull(impl.getResponseValueAsJson(new BaseResponse()));
    }

    @Test
    public void testIsErrorForSuccess1() throws Exception {
        Assert.assertFalse(impl.isError(java.net.HttpURLConnection.HTTP_OK));
    }

    @Test
    public void testIsErrorForSuccess2() throws Exception {
        Assert.assertFalse(impl.isError(java.net.HttpURLConnection.HTTP_CREATED));
    }

    @Test
    public void testIsErrorForSuccess3() throws Exception {
        Assert.assertFalse(impl.isError(java.net.HttpURLConnection.HTTP_NO_CONTENT));
    }

    @Test
    public void testIsErrorForFailure3() throws Exception {
        Assert.assertTrue(impl.isError(java.net.HttpURLConnection.HTTP_BAD_REQUEST));
    }

    @Test
    public void testIsErrorForFailure2() throws Exception {
        Assert.assertTrue(impl.isError(java.net.HttpURLConnection.HTTP_UNAUTHORIZED));
    }

    @Test
    public void testIsErrorForFailure() throws Exception {
        Assert.assertTrue(impl.isError(java.net.HttpURLConnection.HTTP_INTERNAL_ERROR));
    }

    @Test
    public void testCreateCustomClient() throws Exception {
        final Map<String, Object> dataMap = new HashMap<>();

        dataMap.put(Constant.DEF_CONNECT_TIMEOUT, new Integer(-1));
        Assert.assertNotNull(impl.createCustomClient(dataMap));
    }


    @Test(expected = ServiceException.class)
    public void testGeneralExceptionHandlingInt() throws Exception {
        try {
            impl.generalExceptionHandling(java.net.HttpURLConnection.HTTP_INTERNAL_ERROR);
        } catch (final ServiceException se) {
            Assert.assertEquals(String.valueOf(java.net.HttpURLConnection.HTTP_INTERNAL_ERROR),
                se.getErrorKey());
            throw se;
        }
    }

    @Test(expected = ServiceException.class)
    public void testGeneralExceptionHandlingIntBaseResponse() throws Exception {
        try {
            impl.generalExceptionHandling(java.net.HttpURLConnection.HTTP_BAD_GATEWAY,
                new BaseResponse());
        } catch (final ServiceException se) {
            Assert.assertEquals(String.valueOf(java.net.HttpURLConnection.HTTP_BAD_GATEWAY),
                se.getErrorKey());
            throw se;
        }
    }
}