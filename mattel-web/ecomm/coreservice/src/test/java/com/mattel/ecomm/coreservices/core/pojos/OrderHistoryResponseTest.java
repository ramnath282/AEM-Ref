
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderHistoryResponseTest {

    private OrderHistoryResponse impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new OrderHistoryResponse();
        impl.toString();
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
