
package com.mattel.ecomm.core.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LinkComponentUnitTest {

    private LinkComponent impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new LinkComponent();
        impl.toString();
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
