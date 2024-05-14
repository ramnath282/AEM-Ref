
package com.mattel.ecomm.core.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PromoModelUnitTest {

    private PromoModel impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PromoModel();
        impl.toString();
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
