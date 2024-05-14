
package com.mattel.ecomm.coreservices.core.pojos.productdetailspojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParentPartnumberListTest {

    private ParentPartnumberList impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ParentPartnumberList();
        impl.setDFN61DS("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetDFN61DS()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDFN61DS());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
