
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UpdateContactPreferencesRequestTest {

    private UpdateContactPreferencesRequest impl = null;
    private List<String> contprefcat;
    private List<String> contprefloy;
    private List<String> contprefctl;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new UpdateContactPreferencesRequest();
        impl.setStoreId("dummy_string");
        impl.setCatalogId("dummy_string");
        impl.setPartyId("dummy_string");
        impl.setEmail("dummy_string");
        impl.setHash("dummy_string");
        impl.setUrl("dummy_string");
        impl.setSourceName("dummy_string");
        impl.setLoyaltyEmail("dummy_string");
        impl.setLoyaltyRequest("dummy_string");
        impl.setLangId("dummy_string");
        impl.setContPrefGL("dummy_string");
        contprefcat = new java.util.ArrayList<java.lang.String>();
        impl.setContPrefCAT(contprefcat);
        contprefloy = new java.util.ArrayList<java.lang.String>();
        impl.setContPrefLOY(contprefloy);
        contprefctl = new java.util.ArrayList<java.lang.String>();
        impl.setContPrefCTL(contprefctl);
        impl.toString();
    }

    @Test
    public void testGetPartyId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartyId());
    }

    @Test
    public void testGetSourceName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSourceName());
    }

    @Test
    public void testGetContPrefCAT()
        throws Exception
    {
        Assert.assertEquals(contprefcat, impl.getContPrefCAT());
    }

    @Test
    public void testGetContPrefLOY()
        throws Exception
    {
        Assert.assertEquals(contprefloy, impl.getContPrefLOY());
    }

    @Test
    public void testGetHash()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getHash());
    }

    @Test
    public void testGetCatalogId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCatalogId());
    }

    @Test
    public void testGetUrl()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUrl());
    }

    @Test
    public void testGetLoyaltyEmail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLoyaltyEmail());
    }

    @Test
    public void testGetContPrefGL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getContPrefGL());
    }

    @Test
    public void testGetLangId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLangId());
    }

    @Test
    public void testGetLoyaltyRequest()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLoyaltyRequest());
    }

    @Test
    public void testGetEmail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmail());
    }

    @Test
    public void testGetContPrefCTL()
        throws Exception
    {
        Assert.assertEquals(contprefctl, impl.getContPrefCTL());
    }

    @Test
    public void testGetStoreId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreId());
    }

}
