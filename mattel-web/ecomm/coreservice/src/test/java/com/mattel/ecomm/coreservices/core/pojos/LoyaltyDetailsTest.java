
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoyaltyDetailsTest {

    private LoyaltyDetails impl;
    private XMLGregorianCalendar enddate;
    private Date tierexpirationdate = new Date();
    private XMLGregorianCalendar startdate;

    @Before
    public void setUp()
        throws Exception
    {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        enddate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        startdate =  DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        impl = new LoyaltyDetails();
        impl.setLoyaltySegment("dummy_string");
        impl.setEndDate(enddate);
        impl.setPdsErrorResponse("dummy_string");
        impl.setLoyaltyPoints(1l);
        impl.setLoyaltySourceChannel(1);
        impl.setTierExpirationDate(tierexpirationdate);
        impl.setSourceAttributeID("dummy_string");
        impl.setEmailAddress("dummy_string");
        impl.setLoyaltyPointsToNextTier(1l);
        impl.setComment("dummy_string");
        impl.setLoyaltyStatus("dummy_string");
        impl.setStartDate(startdate);
        impl.setSyncStatus("dummy_string");
        impl.setLoyaltyNumber(1);
        impl.toString();
    }

    @Test
    public void testGetStartDate()
        throws Exception
    {
        Assert.assertEquals(startdate, impl.getStartDate());
    }

    @Test
    public void testGetLoyaltySegment()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLoyaltySegment());
    }

    @Test
    public void testGetLoyaltySourceChannel()
        throws Exception
    {
        Assert.assertEquals(1, impl.getLoyaltySourceChannel());
    }

    @Test
    public void testGetSourceAttributeID()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSourceAttributeID());
    }

    @Test
    public void testGetPdsErrorResponse()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPdsErrorResponse());
    }

    @Test
    public void testGetLoyaltyStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLoyaltyStatus());
    }

    @Test
    public void testGetLoyaltyPointsToNextTier()
        throws Exception
    {
        Assert.assertEquals(1L, impl.getLoyaltyPointsToNextTier());
    }

    @Test
    public void testGetLoyaltyNumber()
        throws Exception
    {
        Assert.assertEquals(1, impl.getLoyaltyNumber());
    }

    @Test
    public void testGetEndDate()
        throws Exception
    {
        Assert.assertEquals(enddate, impl.getEndDate());
    }

    @Test
    public void testGetLoyaltyPoints()
        throws Exception
    {
        Assert.assertEquals(1L, impl.getLoyaltyPoints());
    }

    @Test
    public void testGetSyncStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getSyncStatus());
    }

    @Test
    public void testGetEmailAddress()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmailAddress());
    }

    @Test
    public void testGetComment()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getComment());
    }

    @Test
    public void testGetTierExpirationDate()
        throws Exception
    {
        Assert.assertEquals(tierexpirationdate, impl.getTierExpirationDate());
    }

}
