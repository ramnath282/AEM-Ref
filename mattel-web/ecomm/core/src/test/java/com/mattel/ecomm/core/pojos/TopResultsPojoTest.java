
package com.mattel.ecomm.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TopResultsPojoTest {

    private TopResultsPojo impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new TopResultsPojo();
        impl.setLabel("dummy_string");
        impl.setCategoryId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetLabel()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLabel());
    }

    @Test
    public void testGetCategoryId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCategoryId());
    }

    @Test
    public void testObj()
        throws Exception
    {
        Assert.assertNotNull(impl);
    }

}
