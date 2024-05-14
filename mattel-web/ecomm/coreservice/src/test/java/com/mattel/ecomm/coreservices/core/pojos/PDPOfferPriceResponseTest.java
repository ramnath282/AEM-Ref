
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PDPOfferPriceResponseTest {

    private PDPOfferPriceResponse impl = null;
    private List<PDPEntitledPrice> entitledprice;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PDPOfferPriceResponse();
        impl.setResourceId("dummy_string");
        entitledprice = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.PDPEntitledPrice>();
        impl.setEntitledPrice(entitledprice);
        impl.setResourceName("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetEntitledPrice()
        throws Exception
    {
        Assert.assertEquals(entitledprice, impl.getEntitledPrice());
    }

    @Test
    public void testGetResourceId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceId());
    }

    @Test
    public void testGetResourceName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceName());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
