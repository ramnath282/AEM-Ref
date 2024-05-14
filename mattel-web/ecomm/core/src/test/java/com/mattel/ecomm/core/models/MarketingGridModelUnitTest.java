
package com.mattel.ecomm.core.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MarketingGridModelUnitTest {

    private MarketingGridModel impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new MarketingGridModel();
        impl.toString();
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
