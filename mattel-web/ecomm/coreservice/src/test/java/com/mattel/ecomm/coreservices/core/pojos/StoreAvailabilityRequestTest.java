
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StoreAvailabilityRequestTest {

    private StoreAvailabilityRequest impl = null;
    private List<String> productslist;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new StoreAvailabilityRequest();
        impl.setCatalogId("dummy_string");
        impl.setStoreSelected("dummy_string");
        impl.setPartNumber("dummy_string");
        productslist = new java.util.ArrayList<java.lang.String>();
        impl.setProductsList(productslist);
        impl.toString();
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetCatalogId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCatalogId());
    }

    @Test
    public void testGetProductsList()
        throws Exception
    {
        Assert.assertEquals(productslist, impl.getProductsList());
    }

    @Test
    public void testGetStoreSelected()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreSelected());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
