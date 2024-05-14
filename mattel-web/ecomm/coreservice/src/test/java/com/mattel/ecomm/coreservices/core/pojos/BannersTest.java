
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BannersTest {

    private Banners impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Banners();
        impl.setTop("dummy_string");
        impl.setBottom("dummy_string");
        impl.setLeft("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetBottom()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBottom());
    }

    @Test
    public void testGetTop()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTop());
    }

    @Test
    public void testGetLeft()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLeft());
    }

}
