
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateDefaultShippingResponseTest {

    private UpdateDefaultShippingResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdateDefaultShippingResponse();
        impl.setViewTaskName("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetViewTaskName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getViewTaskName());
    }

}
