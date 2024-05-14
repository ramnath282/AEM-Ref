package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressInfoRequestTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
       Assert.assertNotNull(new AddressInfoRequest()); 
    }
}
