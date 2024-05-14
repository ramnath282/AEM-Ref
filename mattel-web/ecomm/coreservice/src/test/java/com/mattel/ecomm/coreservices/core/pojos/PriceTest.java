
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PriceTest {

    private Price impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Price();
        impl.setUsage("dummy_string");
        impl.setDescription("dummy_string");
        impl.setCurrency("dummy_string");
        impl.setValue("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCurrency());
    }

    @Test
    public void testGetValue()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValue());
    }

    @Test
    public void testGetDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDescription());
    }

    @Test
    public void testGetUsage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUsage());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
