
package com.mattel.ecomm.esb.cdm.dtc.common.core1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NameValuePairTypeTest {

    private NameValuePairType impl = null;
    private Description description = new Description();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new NameValuePairType();
        impl.setName("dummy_string");
        impl.setValue("dummy_string");
        impl.setDescription(description);
        impl.toString();
    }

    @Test
    public void testGetValue()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValue());
    }

    @Test
    public void testGetName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getName());
    }

    @Test
    public void testGetDescription()
        throws Exception
    {
        Assert.assertEquals(description, impl.getDescription());
    }

}
