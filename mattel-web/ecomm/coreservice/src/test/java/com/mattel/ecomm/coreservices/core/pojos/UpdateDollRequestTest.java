
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateDollRequestTest {

    private UpdateDollRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdateDollRequest();
        impl.setOperation("dummy_string");
        impl.setWhatDoll("dummy_string");
        impl.setWhenDoll("dummy_string");
        impl.setWhereDoll("dummy_string");
        impl.setWhoDoll("dummy_string");
        impl.setWhyDoll("dummy_string");
        impl.setProductID("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetWhatDoll()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWhatDoll());
    }

    @Test
    public void testGetWhoDoll()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWhoDoll());
    }

    @Test
    public void testGetWhereDoll()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWhereDoll());
    }

    @Test
    public void testGetOperation()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOperation());
    }

    @Test
    public void testGetWhyDoll()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWhyDoll());
    }

    @Test
    public void testGetWhenDoll()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWhenDoll());
    }

    @Test
    public void testGetProductID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductID());
    }

}
