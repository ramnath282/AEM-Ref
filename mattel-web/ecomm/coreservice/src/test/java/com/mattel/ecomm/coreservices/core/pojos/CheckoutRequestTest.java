package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CheckoutRequestTest {
    private CheckoutRequest checkoutRequest;

    @Before
    public void setUp() throws Exception {
        checkoutRequest = new CheckoutRequest();
    }

    @Test
    public void test() {
        Assert.assertNotNull(checkoutRequest);
    }
}
