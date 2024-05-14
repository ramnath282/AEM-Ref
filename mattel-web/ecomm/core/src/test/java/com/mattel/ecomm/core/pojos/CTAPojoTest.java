
package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CTAPojoTest {

    private CTAPojo impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new CTAPojo();
        impl.setCtaLabel("dummy_string");
        impl.setCtaLink("dummy_string");
        impl.setCtaType("dummy_string");
        impl.setCtaStyle("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetCtaType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCtaType());
    }

    @Test
    public void testGetCtaLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCtaLink());
    }

    @Test
    public void testGetCtaStyle()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCtaStyle());
    }

    @Test
    public void testGetCtaLabel()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCtaLabel());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
