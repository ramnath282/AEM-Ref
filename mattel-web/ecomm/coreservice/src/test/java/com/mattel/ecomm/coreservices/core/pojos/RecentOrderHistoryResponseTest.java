
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecentOrderHistoryResponseTest {

    private RecentOrderHistoryResponse impl = null;
    private RecentOrderHistoryResponse.OrderHistoryList orderhistorylist;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RecentOrderHistoryResponse();
        impl.setOrderHistoryList(orderhistorylist);
        impl.toString();
    }

    @Test
    public void testGetOrderHistoryList()
        throws Exception
    {
        Assert.assertEquals(orderhistorylist, impl.getOrderHistoryList());
    }

}
