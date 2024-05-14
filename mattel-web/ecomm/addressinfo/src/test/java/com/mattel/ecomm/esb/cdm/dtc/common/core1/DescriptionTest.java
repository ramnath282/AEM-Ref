
package com.mattel.ecomm.esb.cdm.dtc.common.core1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DescriptionTest {

    private Description impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Description();
        impl.setValue("dummy_string");
        impl.setType("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetValue()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValue());
    }

    @Test
    public void testGetType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getType());
    }

}
