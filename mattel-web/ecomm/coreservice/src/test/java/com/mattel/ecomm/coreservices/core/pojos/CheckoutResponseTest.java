package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CheckoutResponseTest {
    private CheckoutResponse checkoutResponse;

    @Before
    public void setUp() throws Exception {
        checkoutResponse = new CheckoutResponse();
    }

    @Test
    public void test() {
        Assert.assertNotNull(checkoutResponse);
    }
}
