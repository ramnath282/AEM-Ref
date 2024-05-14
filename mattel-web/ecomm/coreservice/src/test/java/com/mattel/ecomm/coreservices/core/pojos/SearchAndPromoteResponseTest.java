
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SearchAndPromoteResponseTest {

    private SearchAndPromoteResponse impl = null;
    private List<Facets> facets;
    private List<SearchAndPromoteResponse.ResultsSets> resultssets;
    private GeneralPLPPageProperties generalplppageproperties = new GeneralPLPPageProperties();
    private List<Banners> banners;
    private List<Menus> menus;
    private List<BreadCrumbs> breadcrumbs;
    private List<Pagination> pagination;
    private List<SearchAndPromoteResponse.ResultCounts> resultcount;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new SearchAndPromoteResponse();
        facets = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Facets>();
        impl.setFacets(facets);
        resultssets = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.SearchAndPromoteResponse.ResultsSets>();
        impl.setResultsSets(resultssets);
        impl.setGeneralPLPPageProperties(generalplppageproperties);
        banners = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Banners>();
        impl.setBanners(banners);
        menus = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Menus>();
        impl.setMenus(menus);
        breadcrumbs = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.BreadCrumbs>();
        impl.setBreadCrumbs(breadcrumbs);
        pagination = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Pagination>();
        impl.setPagination(pagination);
        resultcount = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.SearchAndPromoteResponse.ResultCounts>();
        impl.setResultcount(resultcount);
        impl.toString();
    }

    @Test
    public void testGetBreadCrumbs()
        throws Exception
    {
        Assert.assertEquals(breadcrumbs, impl.getBreadCrumbs());
    }

    @Test
    public void testGetGeneralPLPPageProperties()
        throws Exception
    {
        Assert.assertEquals(generalplppageproperties, impl.getGeneralPLPPageProperties());
    }

    @Test
    public void testGetMenus()
        throws Exception
    {
        Assert.assertEquals(menus, impl.getMenus());
    }

    @Test
    public void testGetResultcount()
        throws Exception
    {
        Assert.assertEquals(resultcount, impl.getResultcount());
    }

    @Test
    public void testGetBanners()
        throws Exception
    {
        Assert.assertEquals(banners, impl.getBanners());
    }

    @Test
    public void testGetFacets()
        throws Exception
    {
        Assert.assertEquals(facets, impl.getFacets());
    }

    @Test
    public void testGetPagination()
        throws Exception
    {
        Assert.assertEquals(pagination, impl.getPagination());
    }

    @Test
    public void testGetResultsSets()
        throws Exception
    {
        Assert.assertEquals(resultssets, impl.getResultsSets());
    }

}
