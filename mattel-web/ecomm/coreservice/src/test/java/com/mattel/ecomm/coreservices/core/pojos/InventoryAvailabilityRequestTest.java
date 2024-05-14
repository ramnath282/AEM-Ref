
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryAvailabilityRequestTest {

    private InventoryAvailabilityRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new InventoryAvailabilityRequest();
        impl.setPartNumber("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

}
