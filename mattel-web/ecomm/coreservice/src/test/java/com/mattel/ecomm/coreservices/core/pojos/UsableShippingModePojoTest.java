
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UsableShippingModePojoTest {

    private UsableShippingModePojo impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UsableShippingModePojo();
        impl.setField1("dummy_string");
        impl.setShipModeCode("dummy_string");
        impl.setShipModeDescription("dummy_string");
        impl.setShipModeId("dummy_string");
        impl.setField2("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetField1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getField1());
    }

    @Test
    public void testGetShipModeId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShipModeId());
    }

    @Test
    public void testGetField2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getField2());
    }

    @Test
    public void testGetShipModeCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShipModeCode());
    }

    @Test
    public void testGetShipModeDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getShipModeDescription());
    }

}
