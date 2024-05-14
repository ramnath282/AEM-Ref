
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactPreferencesResponseTest {

    private ContactPreferencesResponse impl = null;
    private ContactPrefSEQMap contactprefseqmap = new ContactPrefSEQMap();
    private List<String> contactprefgllist;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new ContactPreferencesResponse();
        impl.setContactPrefSEQMap(contactprefseqmap);
        impl.setPartyId("dummy_string");
        impl.setUserContactPrefCTL("dummy_string");
        contactprefgllist = new java.util.ArrayList<java.lang.String>();
        impl.setContactPrefGLList(contactprefgllist);
        impl.setLoyaltyRequest("dummy_string");
        impl.setUserContactPrefSF("dummy_string");
        impl.setUserExistingContactPref("dummy_string");
        impl.setUserContactPrefFRQ("dummy_string");
        impl.setHash("dummy_string");
        impl.setUserContactPrefRTL("dummy_string");
        impl.setLoyaltyEmail("dummy_string");
        impl.setLoyaltyStatus("dummy_string");
        impl.setUserLocationPrefSL("dummy_string");
        impl.setEmail("dummy_string");
        impl.setUserContactPrefGL("dummy_string");
        impl.setUserContactPrefLOY("dummy_string");
        impl.setUserContactPrefCAT("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPartyId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPartyId());
    }

    @Test
    public void testGetUserContactPrefLOY()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserContactPrefLOY());
    }

    @Test
    public void testGetUserContactPrefCAT()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserContactPrefCAT());
    }

    @Test
    public void testGetHash()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getHash());
    }

    @Test
    public void testGetLoyaltyStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLoyaltyStatus());
    }

    @Test
    public void testGetUserContactPrefSF()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserContactPrefSF());
    }

    @Test
    public void testGetUserContactPrefCTL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserContactPrefCTL());
    }

    @Test
    public void testGetContactPrefGLList()
        throws Exception
    {
        Assert.assertEquals(contactprefgllist, impl.getContactPrefGLList());
    }

    @Test
    public void testGetUserLocationPrefSL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserLocationPrefSL());
    }

    @Test
    public void testGetLoyaltyEmail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLoyaltyEmail());
    }

    @Test
    public void testGetContactPrefSEQMap()
        throws Exception
    {
        Assert.assertEquals(contactprefseqmap, impl.getContactPrefSEQMap());
    }

    @Test
    public void testGetLoyaltyRequest()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLoyaltyRequest());
    }

    @Test
    public void testGetUserContactPrefFRQ()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserContactPrefFRQ());
    }

    @Test
    public void testGetEmail()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmail());
    }

    @Test
    public void testGetUserContactPrefGL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserContactPrefGL());
    }

    @Test
    public void testGetUserExistingContactPref()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserExistingContactPref());
    }

    @Test
    public void testGetUserContactPrefRTL()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserContactPrefRTL());
    }

}
