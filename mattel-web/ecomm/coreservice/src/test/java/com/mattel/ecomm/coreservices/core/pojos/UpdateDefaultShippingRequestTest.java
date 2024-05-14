
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateDefaultShippingRequestTest {

    private UpdateDefaultShippingRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdateDefaultShippingRequest();
        impl.setShippingModeId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetShippingModeId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShippingModeId());
    }

}
