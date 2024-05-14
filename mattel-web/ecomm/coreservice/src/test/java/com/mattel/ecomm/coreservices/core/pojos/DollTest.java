
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DollTest {

    private Doll impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Doll();
        impl.setBoughtFor("dummy_string");
        impl.setCharacter("dummy_string");
        impl.setProductID("dummy_string");
        impl.setPurchasedDate("dummy_string");
        impl.setReason("dummy_string");
        impl.setStore("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPurchasedDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPurchasedDate());
    }

    @Test
    public void testGetStore()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStore());
    }

    @Test
    public void testGetBoughtFor()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBoughtFor());
    }

    @Test
    public void testGetReason()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getReason());
    }

    @Test
    public void testGetCharacter()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCharacter());
    }

    @Test
    public void testGetProductID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductID());
    }

}
