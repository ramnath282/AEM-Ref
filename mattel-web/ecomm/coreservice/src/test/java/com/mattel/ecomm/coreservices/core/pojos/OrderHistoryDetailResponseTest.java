
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderHistoryDetailResponseTest {

    private OrderHistoryDetailResponse impl = null;
    private OrderDetails orderdetails = new OrderDetails();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new OrderHistoryDetailResponse();
        impl.setOrderDetails(orderdetails);
        impl.toString();
    }

    @Test
    public void testGetOrderDetails()
        throws Exception
    {
        Assert.assertEquals(orderdetails, impl.getOrderDetails());
    }

}
