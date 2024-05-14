
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchResultsTest {

    private SearchResults impl = null;
    private List<Table> tables;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new SearchResults();
        impl.setProdId("dummy_string");
        impl.setTitle("dummy_string");
        impl.setDesc("dummy_string");
        impl.setUrl("dummy_string");
        impl.setAuxDesc1("dummy_string");
        impl.setAvailability("dummy_string");
        impl.setBackorderDate("dummy_string");
        impl.setBuyable("dummy_string");
        impl.setCategory("dummy_string");
        impl.setCustSegExcl("dummy_string");
        impl.setFullimage("dummy_string");
        impl.setImageLink("dummy_string");
        impl.setLanguage("dummy_string");
        impl.setPdpLink("dummy_string");
        impl.setProductStatus("dummy_string");
        impl.setPromoMsg("dummy_string");
        impl.setPublished("dummy_string");
        impl.setRatingAvg("dummy_string");
        impl.setThumbnail("dummy_string");
        tables = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Table>();
        impl.setTables(tables);
        impl.toString();
    }

    @Test
    public void testGetCustSegExcl()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCustSegExcl());
    }

    @Test
    public void testGetLanguage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLanguage());
    }

    @Test
    public void testGetBackorderDate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBackorderDate());
    }

    @Test
    public void testGetProductStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProductStatus());
    }

    @Test
    public void testGetBuyable()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getBuyable());
    }

    @Test
    public void testGetCategory()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCategory());
    }

    @Test
    public void testGetPromoMsg()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPromoMsg());
    }

    @Test
    public void testGetRatingAvg()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRatingAvg());
    }

    @Test
    public void testGetAuxDesc1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAuxDesc1());
    }

    @Test
    public void testGetPublished()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPublished());
    }

    @Test
    public void testGetImageLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getImageLink());
    }

    @Test
    public void testGetThumbnail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getThumbnail());
    }

    @Test
    public void testGetUrl()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUrl());
    }

    @Test
    public void testGetTitle()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getTitle());
    }

    @Test
    public void testGetAvailability()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAvailability());
    }

    @Test
    public void testGetFullimage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFullimage());
    }

    @Test
    public void testGetTables()
        throws Exception
    {
        Assert.assertEquals(tables, impl.getTables());
    }

    @Test
    public void testGetProdId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProdId());
    }

    @Test
    public void testGetDesc()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDesc());
    }

    @Test
    public void testGetPdpLink()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPdpLink());
    }

}
