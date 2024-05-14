
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BaseChildProductTest {

    private BaseChildProduct impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new BaseChildProduct();
        impl.setBuyable("dummy_string");
        impl.setImageLink("dummy_string");
        impl.setThumbnail("dummy_string");
        impl.setProductType("dummy_string");
        impl.setFullimage("dummy_string");
        impl.setPartNumber("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetFullimage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFullimage());
    }

    @Test
    public void testGetPartNumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartNumber());
    }

    @Test
    public void testGetBuyable()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBuyable());
    }

    @Test
    public void testGetImageLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getImageLink());
    }

    @Test
    public void testGetThumbnail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getThumbnail());
    }

    @Test
    public void testGetProductType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductType());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
