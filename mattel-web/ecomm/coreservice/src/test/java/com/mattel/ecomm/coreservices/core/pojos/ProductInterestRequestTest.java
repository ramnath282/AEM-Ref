
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductInterestRequestTest {

    private ProductInterestRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ProductInterestRequest();
        impl.toString();
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
