
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BreadCrumbsTest {

    private BreadCrumbs impl = null;
    private List<Object> values;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new BreadCrumbs();
        impl.setName("dummy_string");
        values = new java.util.ArrayList<java.lang.Object>();
        impl.setValues(values);
        impl.toString();
    }

    @Test
    public void testGetName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getName());
    }

    @Test
    public void testGetValues()
        throws Exception
    {
        Assert.assertEquals(values, impl.getValues());
    }

}
