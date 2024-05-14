
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChildProductTest {

    private ChildProduct impl = null;
    private Map<String, Object> definingattributes;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ChildProduct();
        impl.setParentPartnumber("dummy_string");
        impl.setLanguage("dummy_string");
        impl.setRegion("dummy_string");
        definingattributes = new java.util.HashMap<java.lang.String, java.lang.Object>();
        impl.setDefiningAttributes(definingattributes);
        impl.setName("dummy_string");
        impl.setSequence(1d);
        impl.toString();
    }

    @Test
    public void testGetLanguage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLanguage());
    }

    @Test
    public void testGetName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getName());
    }

    @Test
    public void testGetRegion()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRegion());
    }

    @Test
    public void testGetParentPartnumber()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getParentPartnumber());
    }

    @Test
    public void testGetDefiningAttributes()
        throws Exception
    {
        Assert.assertEquals(definingattributes, impl.getDefiningAttributes());
    }

    @Test
    public void testGetSequence()
        throws Exception
    {
        Assert.assertEquals(1.0D, impl.getSequence(), 0.0D);
    }

}
