
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PersonalInfoRequestTest {

    private PersonalInfoRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new PersonalInfoRequest();
        impl.setFirstName("dummy_string");
        impl.setLastName("dummy_string");
        impl.setLogonId("dummy_string");
        impl.setLogonPassword("dummy_string");
        impl.setLogonPasswordVerify("dummy_string");
        impl.setEmail1("dummy_string");
        impl.setPhone1("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetEmail1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getEmail1());
    }

    @Test
    public void testGetLogonPasswordVerify()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPasswordVerify());
    }

    @Test
    public void testGetPhone1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPhone1());
    }

    @Test
    public void testGetLogonPassword()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonPassword());
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

    @Test
    public void testGetLogonId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLogonId());
    }

}
