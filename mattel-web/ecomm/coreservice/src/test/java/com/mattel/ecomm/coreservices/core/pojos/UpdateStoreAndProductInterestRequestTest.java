
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateStoreAndProductInterestRequestTest {

    private UpdateStoreAndProductInterestRequest impl = null;
    private String[] productinterest = new String[] {};
    private String[] locationinterest = new String[] {};

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdateStoreAndProductInterestRequest();
        impl.setProductInterest(productinterest);
        impl.setLocationInterest(locationinterest);
        impl.setSelectCountry("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetLocationInterest()
        throws Exception
    {
        Assert.assertArrayEquals(locationinterest, impl.getLocationInterest());
    }

    @Test
    public void testGetProductInterest()
        throws Exception
    {
        Assert.assertArrayEquals(productinterest, impl.getProductInterest());
    }

    @Test
    public void testGetSelectCountry()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSelectCountry());
    }

}
