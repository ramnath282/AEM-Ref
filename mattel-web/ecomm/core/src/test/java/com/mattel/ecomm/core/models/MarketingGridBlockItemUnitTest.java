
package com.mattel.ecomm.core.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MarketingGridBlockItemUnitTest {

    private MarketingGridBlockItem impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new MarketingGridBlockItem();
        impl.toString();
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
