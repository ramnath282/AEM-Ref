
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FacetsTest {

    private Facets impl = null;
    private List<FacetValues> values;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Facets();
        impl.setLabel("dummy_string");
        impl.setLongWrapper(true);
        values = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.FacetValues>();
        impl.setValues(values);
        impl.toString();
    }

    @Test
    public void testGetLabel()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLabel());
    }

    @Test
    public void testGetValues()
        throws Exception
    {
        Assert.assertEquals(values, impl.getValues());
    }

    @Test
    public void testGet_long()
        throws Exception
    {
        Assert.assertEquals(true, impl.getLongWrapper());
    }

}
