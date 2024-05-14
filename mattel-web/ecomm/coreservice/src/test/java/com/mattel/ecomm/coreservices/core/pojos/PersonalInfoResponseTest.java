
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersonalInfoResponseTest {

    private PersonalInfoResponse impl = null;
    private List<Contact> contact;
    private List<ReceivePreference> receiveemailpreference;
    private List<Attribute> attributes;
    private List<ReceivePreference> receivesmspreference;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PersonalInfoResponse();
        impl.setLastName("dummy_string");
        impl.setRegistrationStatus("dummy_string");
        impl.setResourceId("dummy_string");
        impl.setPreferredCurrency("dummy_string");
        impl.setDistinguishedName("dummy_string");
        impl.setOrgizationId("dummy_string");
        impl.setAddressId("dummy_string");
        impl.setPhone1("dummy_string");
        impl.setAccountStatus("dummy_string");
        impl.setEmail1("dummy_string");
        impl.setProfileType("dummy_string");
        contact = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Contact>();
        impl.setContact(contact);
        impl.setChallengeQuestion("dummy_string");
        impl.setNickName("dummy_string");
        impl.setAddressType("dummy_string");
        impl.setResourceName("dummy_string");
        impl.setUserId("dummy_string");
        impl.setRegistrationDateTime("dummy_string");
        impl.setOrganizationDistinguishedName("dummy_string");
        impl.setFirstName("dummy_string");
        impl.setLogonId("dummy_string");
        receiveemailpreference = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.ReceivePreference>();
        impl.setReceiveEmailPreference(receiveemailpreference);
        impl.setLastUpdate("dummy_string");
        impl.setRegistrationApprovalStatus("dummy_string");
        attributes = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.Attribute>();
        impl.setAttributes(attributes);
        impl.setPasswordExpired("dummy_string");
        impl.setPrimary("dummy_string");
        impl.setDefaultShipMethod("dummy_string");
        receivesmspreference = new java.util.ArrayList<com.mattel.ecomm.coreservices.core.pojos.ReceivePreference>();
        impl.setReceiveSMSPreference(receivesmspreference);
        impl.toString();
    }

    @Test
    public void testGetNickName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getNickName());
    }

    @Test
    public void testGetPrimary()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPrimary());
    }

    @Test
    public void testGetAccountStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAccountStatus());
    }

    @Test
    public void testGetOrganizationDistinguishedName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrganizationDistinguishedName());
    }

    @Test
    public void testGetDefaultShipMethod()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDefaultShipMethod());
    }

    @Test
    public void testGetPasswordExpired()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPasswordExpired());
    }

    @Test
    public void testGetPreferredCurrency()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPreferredCurrency());
    }

    @Test
    public void testGetEmail1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmail1());
    }

    @Test
    public void testGetChallengeQuestion()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getChallengeQuestion());
    }

    @Test
    public void testGetOrgizationId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getOrgizationId());
    }

    @Test
    public void testGetPhone1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPhone1());
    }

    @Test
    public void testGetLogonId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonId());
    }

    @Test
    public void testGetProfileType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getProfileType());
    }

    @Test
    public void testGetDistinguishedName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDistinguishedName());
    }

    @Test
    public void testGetRegistrationApprovalStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRegistrationApprovalStatus());
    }

    @Test
    public void testGetAttributes()
        throws Exception
    {
        Assert.assertEquals(attributes, impl.getAttributes());
    }

    @Test
    public void testGetRegistrationStatus()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRegistrationStatus());
    }

    @Test
    public void testGetAddressType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressType());
    }

    @Test
    public void testGetResourceName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceName());
    }

    @Test
    public void testGetLastName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastName());
    }

    @Test
    public void testGetContact()
        throws Exception
    {
        Assert.assertEquals(contact, impl.getContact());
    }

    @Test
    public void testGetResourceId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getResourceId());
    }

    @Test
    public void testGetAddressId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressId());
    }

    @Test
    public void testGetReceiveEmailPreference()
        throws Exception
    {
        Assert.assertEquals(receiveemailpreference, impl.getReceiveEmailPreference());
    }

    @Test
    public void testGetRegistrationDateTime()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getRegistrationDateTime());
    }

    @Test
    public void testGetLastUpdate()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastUpdate());
    }

    @Test
    public void testGetFirstName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFirstName());
    }

    @Test
    public void testGetReceiveSMSPreference()
        throws Exception
    {
        Assert.assertEquals(receivesmspreference, impl.getReceiveSMSPreference());
    }

    @Test
    public void testGetUserId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserId());
    }

}
