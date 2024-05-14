
package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChildPartnumberListTest {

    private ChildPartnumberList impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ChildPartnumberList();
        impl.setFGM98("dummy_string");
        impl.setDFN61("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetFGM98()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFGM98());
    }

    @Test
    public void testGetDFN61()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDFN61());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
