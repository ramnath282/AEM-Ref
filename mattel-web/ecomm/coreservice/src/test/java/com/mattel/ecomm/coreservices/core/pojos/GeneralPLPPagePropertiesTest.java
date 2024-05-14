
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeneralPLPPagePropertiesTest {

    private GeneralPLPPageProperties impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new GeneralPLPPageProperties();
        impl.setQuery("dummy_string");
        impl.setTotal("dummy_string");
        impl.setPageLower("dummy_string");
        impl.setPageUpper("dummy_string");
        impl.setPageTotal("dummy_string");
        impl.setRedirect("dummy_string");
        impl.setSeoSearchTitle("dummy_string");
        impl.setSeoSearchDescription("dummy_string");
        impl.setSeoSearchKeywords("dummy_string");
        impl.setSeoBrowseTitle("dummy_string");
        impl.setSeoBrowseDescription("dummy_string");
        impl.setSeoBrowseKeywords("dummy_string");
        impl.setSeoItemTitle("dummy_string");
        impl.setSeoItemDescription("dummy_string");
        impl.setSeoItemKeywords("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPageTotal()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPageTotal());
    }

    @Test
    public void testGetRedirect()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRedirect());
    }

    @Test
    public void testGetSeoSearchKeywords()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoSearchKeywords());
    }

    @Test
    public void testGetSeoBrowseTitle()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoBrowseTitle());
    }

    @Test
    public void testGetSeoBrowseKeywords()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoBrowseKeywords());
    }

    @Test
    public void testGetSeoBrowseDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoBrowseDescription());
    }

    @Test
    public void testGetSeoItemDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoItemDescription());
    }

    @Test
    public void testGetPageUpper()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPageUpper());
    }

    @Test
    public void testGetPageLower()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPageLower());
    }

    @Test
    public void testGetSeoItemKeywords()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoItemKeywords());
    }

    @Test
    public void testGetSeoSearchDescription()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoSearchDescription());
    }

    @Test
    public void testGetTotal()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTotal());
    }

    @Test
    public void testGetSeoItemTitle()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoItemTitle());
    }

    @Test
    public void testGetQuery()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getQuery());
    }

    @Test
    public void testGetSeoSearchTitle()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSeoSearchTitle());
    }

}
