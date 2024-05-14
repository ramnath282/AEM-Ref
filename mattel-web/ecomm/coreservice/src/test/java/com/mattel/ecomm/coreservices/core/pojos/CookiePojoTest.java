
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CookiePojoTest {

    private CookiePojo impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new CookiePojo();
        impl.setCookieName("dummy_string");
        impl.setCookieValue("dummy_string");
        impl.setCookiePath("dummy_string");
        impl.setDomain("dummy_string");
        impl.setHttpOnly(true);
        impl.setMaxAge(1);
        impl.toString();
    }

    @Test
    public void testGetDomain()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDomain());
    }

    @Test
    public void testGetCookieName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCookieName());
    }

    @Test
    public void testGetMaxAge()
        throws Exception
    {
        Assert.assertEquals(1, impl.getMaxAge());
    }

    @Test
    public void testIsHttpOnly()
        throws Exception
    {
        Assert.assertEquals(true, impl.isHttpOnly());
    }

    @Test
    public void testGetCookieValue()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCookieValue());
    }

    @Test
    public void testGetCookiePath()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCookiePath());
    }

}
