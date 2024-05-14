
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryAvailabilityResponseTest {

    private InventoryAvailabilityResponse impl = null;
    private ProductInventoryData inventoryavailability = new ProductInventoryData();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new InventoryAvailabilityResponse();
        impl.setInventoryAvailability(inventoryavailability);
        impl.toString();
    }

    @Test
    public void testGetInventoryAvailability()
        throws Exception
    {
        Assert.assertEquals(inventoryavailability, impl.getInventoryAvailability());
    }

}
