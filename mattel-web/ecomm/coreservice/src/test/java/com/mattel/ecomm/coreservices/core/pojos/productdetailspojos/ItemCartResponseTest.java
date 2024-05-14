
package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import java.util.List;
import com.mattel.ecomm.coreservices.core.pojos.CartOrderItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemCartResponseTest {

    private ItemCartResponse impl = null;
    private List<CartOrderItem> orderitem;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ItemCartResponse();
        impl.setOrderId("dummy_string");
        orderitem = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.CartOrderItem>();
        impl.setOrderItem(orderitem);
        impl.toString();
    }

    @Test
    public void testGetOrderId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrderId());
    }

    @Test
    public void testGetOrderItem()
        throws Exception
    {
        Assert.assertEquals(orderitem, impl.getOrderItem());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
