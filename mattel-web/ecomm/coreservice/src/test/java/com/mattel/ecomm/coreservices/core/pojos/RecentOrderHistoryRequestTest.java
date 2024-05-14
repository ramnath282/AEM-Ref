
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RecentOrderHistoryRequestTest {

    private RecentOrderHistoryRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RecentOrderHistoryRequest();
        impl.setWcToken("dummy_string");
        impl.setWcTrustedToken("dummy_string");
        impl.setStoreKey("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetWcTrustedToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWcTrustedToken());
    }

    @Test
    public void testGetStoreKey()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreKey());
    }

    @Test
    public void testGetWcToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getWcToken());
    }

}
