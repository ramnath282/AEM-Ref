
package com.mattel.ecomm.coreservices.core.pojos;

import javax.servlet.http.Cookie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShippingModesRequestTest {

    private ShippingModesRequest impl = null;
    private Cookie[] cookies = new Cookie[] {};

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ShippingModesRequest();
        impl.setStoreKey("dummy_string");
        impl.setCookies(cookies);
        impl.toString();
    }

    @Test
    public void testGetStoreKey()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreKey());
    }

    @Test
    public void testGetCookies()
        throws Exception
    {
        Assert.assertArrayEquals(cookies, impl.getCookies());
    }

}
