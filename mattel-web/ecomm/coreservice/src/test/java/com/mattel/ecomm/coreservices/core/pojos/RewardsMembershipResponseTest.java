
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RewardsMembershipResponseTest {

    private RewardsMembershipResponse impl = null;
    private String[] membershipId = new String[] {};
    private String[] emailid = new String[] {};

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RewardsMembershipResponse();
        impl.setMembershipId(membershipId);
        impl.setEmailId(emailid);
        impl.setFirstName("dummy_string");
        impl.setLastName("dummy_string");
        impl.setMembershipAccountFound(true);
        impl.setPhone("dummy_string");
        impl.setUserType("dummy_string");
        impl.setUsersId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetPhone()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPhone());
    }

    @Test
    public void testGetUserType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserType());
    }

    @Test
    public void testGetUsersId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUsersId());
    }

    @Test
    public void testIsMembershipAccountFound()
        throws Exception
    {
        Assert.assertEquals(true, impl.isMembershipAccountFound());
    }

    @Test
    public void testGetEmailId()
        throws Exception
    {
        Assert.assertArrayEquals(emailid, impl.getEmailId());
    }

    @Test
    public void testGetMembership_id()
        throws Exception
    {
        Assert.assertArrayEquals(membershipId, impl.getMembershipId());
    }

    @Test
    public void testGetFirstName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFirstName());
    }

    @Test
    public void testGetLastName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastName());
    }

}
