
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FedExResponseTest {

    private FedExResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new FedExResponse();
        impl.setImage("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetImage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getImage());
    }

}
