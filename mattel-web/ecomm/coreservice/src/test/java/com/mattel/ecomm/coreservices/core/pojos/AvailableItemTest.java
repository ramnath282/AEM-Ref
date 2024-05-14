
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AvailableItemTest {

    private AvailableItem impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new AvailableItem();
        impl.setQuantityAvailable(1);
        impl.setItemName("dummy_string");
        impl.setStoreLocation("dummy_string");
        impl.setItemCode("dummy_string");
        impl.setAvailabilityStatus("dummy_string");
        impl.setCatEntryId(1);
        impl.toString();
    }

    @Test
    public void testGetAvailabilityStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAvailabilityStatus());
    }

    @Test
    public void testGetItemName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getItemName());
    }

    @Test
    public void testGetStoreLocation()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreLocation());
    }

    @Test
    public void testGetItemCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getItemCode());
    }

    @Test
    public void testGetCatEntryId()
        throws Exception
    {
        Assert.assertEquals(1, impl.getCatEntryId());
    }

    @Test
    public void testGetQuantityAvailable()
        throws Exception
    {
        Assert.assertEquals(1, impl.getQuantityAvailable());
    }

}
