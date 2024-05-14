package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BaseRequestTest {

    private BaseRequest baseRequest;

    @Before
    public void setUp() throws Exception {
        baseRequest = new BaseRequest();
    }

    @Test
    public void test() {
        Assert.assertNotNull(baseRequest);
    }

}