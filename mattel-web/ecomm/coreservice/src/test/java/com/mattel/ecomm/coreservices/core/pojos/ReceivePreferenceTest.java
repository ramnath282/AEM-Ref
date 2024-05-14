
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReceivePreferenceTest {

    private ReceivePreference impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ReceivePreference();
        impl.setStoreID("dummy_string");
        impl.setValue("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetValue()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValue());
    }

    @Test
    public void testGetStoreID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreID());
    }

}
