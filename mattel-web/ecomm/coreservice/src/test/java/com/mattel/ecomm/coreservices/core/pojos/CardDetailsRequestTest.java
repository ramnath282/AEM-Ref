package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CardDetailsRequestTest {
    private CardDetailsRequest impl;

    @Before
    public void setUp() throws Exception {
        impl = new CardDetailsRequest();
    }

    @Test
    public void test() {
        Assert.assertNotNull(impl);
    }
}
