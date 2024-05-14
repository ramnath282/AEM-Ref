
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryDataTest {

    private InventoryData impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new InventoryData();
        impl.setLowInvThreshold(1);
        impl.setAvailableQuantity("dummy_string");
        impl.setProductId(1);
        impl.setUnitOfMeasure("dummy_string");
        impl.setInventoryStatus("dummy_string");
        impl.setPartNumber("dummy_string");
        impl.setBackOrderDate("dummy_string");
        impl.setProductType("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetBackOrderDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBackOrderDate());
    }

    @Test
    public void testGetInventoryStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getInventoryStatus());
    }

    @Test
    public void testGetAvailableQuantity()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAvailableQuantity());
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetLowInvThreshold()
        throws Exception
    {
        Assert.assertEquals(1, (int)impl.getLowInvThreshold());
    }

    @Test
    public void testGetUnitOfMeasure()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUnitOfMeasure());
    }

    @Test
    public void testGetProductType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductType());
    }

    @Test
    public void testGetProductId()
        throws Exception
    {
        Assert.assertEquals(1, (int)impl.getProductId());
    }

}
