
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchAndPromoteRequestTest {

    private SearchAndPromoteRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new SearchAndPromoteRequest();
        impl.setSearchString("dummy_string");
        impl.setCategory("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetSearchString()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSearchString());
    }

    @Test
    public void testGetCategory()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCategory());
    }

}
