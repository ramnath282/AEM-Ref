
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductInterestResponseTest {

    private ProductInterestResponse impl = null;
    private String[] eventinterestlist = new String[] {};
    private String[] productinterestlist = new String[] {};
    private String[] locationinterestlist = new String[] {};

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ProductInterestResponse();
        impl.setEventInterestList(eventinterestlist);
        impl.setProductInterestList(productinterestlist);
        impl.setUserExistingProductInterest("dummy_string");
        impl.setPartyId("dummy_string");
        impl.setLocationInterestList(locationinterestlist);
        impl.setUserPreviousProductInterest("dummy_string");
        impl.setEmail("dummy_string");
        impl.setHash("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPartyId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartyId());
    }

    @Test
    public void testGetProductInterestList()
        throws Exception
    {
        Assert.assertArrayEquals(productinterestlist, impl.getProductInterestList());
    }

    @Test
    public void testGetHash()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getHash());
    }

    @Test
    public void testGetUserExistingProductInterest()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserExistingProductInterest());
    }

    @Test
    public void testGetLocationInterestList()
        throws Exception
    {
        Assert.assertArrayEquals(locationinterestlist, impl.getLocationInterestList());
    }

    @Test
    public void testGetEventInterestList()
        throws Exception
    {
        Assert.assertArrayEquals(eventinterestlist, impl.getEventInterestList());
    }

    @Test
    public void testGetEmail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmail());
    }

    @Test
    public void testGetUserPreviousProductInterest()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserPreviousProductInterest());
    }

}
