
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UnitPriceTest {

    private UnitPrice impl = null;
    private Map<String, String> quantity;
    private Map<String, String> price;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UnitPrice();
        quantity = new java.util.HashMap<java.lang.String, java.lang.String>();
        impl.setQuantity(quantity);
        price = new java.util.HashMap<java.lang.String, java.lang.String>();
        impl.setPrice(price);
        impl.toString();
    }

    @Test
    public void testGetQuantity()
        throws Exception
    {
        Assert.assertEquals(quantity, impl.getQuantity());
    }

    @Test
    public void testGetPrice()
        throws Exception
    {
        Assert.assertEquals(price, impl.getPrice());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
