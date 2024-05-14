
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductInventoryDataTest {

    private ProductInventoryData impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ProductInventoryData();
        impl.setAvailableQuantity("dummy_string");
        impl.setUnitOfMeasure("dummy_string");
        impl.setProductId("dummy_string");
        impl.setInventoryStatus("dummy_string");
        impl.toString();
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
    public void testGetUnitOfMeasure()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUnitOfMeasure());
    }

    @Test
    public void testGetProductId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductId());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
