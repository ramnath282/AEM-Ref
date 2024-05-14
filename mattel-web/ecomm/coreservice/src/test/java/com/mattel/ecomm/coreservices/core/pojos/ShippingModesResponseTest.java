
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShippingModesResponseTest {

    private ShippingModesResponse impl = null;
    private UsableShippingModePojo[] usableshippingmode = new UsableShippingModePojo[] {};

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ShippingModesResponse();
        impl.setUsableShippingMode(usableshippingmode);
        impl.toString();
    }

    @Test
    public void testGetUsableShippingMode()
        throws Exception
    {
        Assert.assertArrayEquals(usableshippingmode, impl.getUsableShippingMode());
    }

}
