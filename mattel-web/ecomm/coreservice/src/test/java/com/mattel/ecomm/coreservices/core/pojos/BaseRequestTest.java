package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BaseRequestTest {
    private BaseRequest baseRequest;

    @Before
    public void setUp() throws Exception {
        baseRequest = new BaseRequest();
        baseRequest.setLangId("-1");
    }

    @Test
    public void test() {
        Assert.assertNotNull(baseRequest);
    }

    @Test
    public void testLangId() {
        Assert.assertNotNull("-1", baseRequest.getLangId());
    }
}
