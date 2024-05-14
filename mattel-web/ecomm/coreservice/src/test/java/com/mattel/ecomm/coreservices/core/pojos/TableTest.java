
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TableTest {

    private Table impl = null;
    private List<Object> results;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Table();
        impl.setName("dummy_string");
        results = new java.util.ArrayList<java.lang.Object>();
        impl.setResults(results);
        impl.toString();
    }

    @Test
    public void testGetName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getName());
    }

    @Test
    public void testGetResults()
        throws Exception
    {
        Assert.assertEquals(results, impl.getResults());
    }

}
