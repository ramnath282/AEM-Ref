
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RewardsMembershipRequestTest {

    private RewardsMembershipRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RewardsMembershipRequest();
        impl.setEmailId("dummy_string");
        impl.setMembershipId("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetEmailId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmailId());
    }

    @Test
    public void testGetMembershipId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMembershipId());
    }

}
