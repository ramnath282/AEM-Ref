
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PDPListPriceResponseTest {

    private PDPListPriceResponse impl = null;
    private List<PDPPriceResultList> resultlist;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PDPListPriceResponse();
        impl.setResourceId("dummy_string");
        impl.setResourceName("dummy_string");
        resultlist = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.PDPPriceResultList>();
        impl.setResultList(resultlist);
        impl.toString();
    }

    @Test
    public void testGetResourceId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceId());
    }

    @Test
    public void testGetResultList()
        throws Exception
    {
        Assert.assertEquals(resultlist, impl.getResultList());
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
