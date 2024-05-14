
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FacetValuesTest {

    private FacetValues impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new FacetValues();
        impl.setValue("dummy_string");
        impl.setSelected("dummy_string");
        impl.setCount("dummy_string");
        impl.setLink("dummy_string");
        impl.setUndolink("dummy_string");
        impl.setThreshold(true);
        impl.toString();
    }

    @Test
    public void testGetUndolink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUndolink());
    }

    @Test
    public void testGetValue()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getValue());
    }

    @Test
    public void testGetSelected()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSelected());
    }

    @Test
    public void testGetLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLink());
    }

    @Test
    public void testGetThreshold()
        throws Exception
    {
        Assert.assertEquals(true, impl.getThreshold());
    }

    @Test
    public void testGetCount()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCount());
    }

}
