
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PDPCollectiveAlternativeCurrencyPriceTest {

    private PDPCollectiveAlternativeCurrencyPrice impl = null;
    private Map<String, String> price;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PDPCollectiveAlternativeCurrencyPrice();
        price = new java.util.HashMap<java.lang.String, java.lang.String>();
        impl.setPrice(price);
        impl.toString();
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
