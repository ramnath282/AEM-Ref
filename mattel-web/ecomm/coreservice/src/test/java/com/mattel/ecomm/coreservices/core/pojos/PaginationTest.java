
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PaginationTest {

    private Pagination impl = null;
    private List<Pagination.Page> pages;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new Pagination();
        impl.setName("dummy_string");
        impl.setPrevious("dummy_string");
        impl.setNext("dummy_string");
        impl.setLast("dummy_string");
        impl.setViewall("dummy_string");
        pages = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Pagination.Page>();
        impl.setPages(pages);
        impl.toString();
    }

    @Test
    public void testGetPrevious()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPrevious());
    }

    @Test
    public void testGetName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getName());
    }

    @Test
    public void testGetViewall()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getViewall());
    }

    @Test
    public void testGetNext()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getNext());
    }

    @Test
    public void testGetPages()
        throws Exception
    {
        Assert.assertEquals(pages, impl.getPages());
    }

    @Test
    public void testGetLast()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLast());
    }

}
