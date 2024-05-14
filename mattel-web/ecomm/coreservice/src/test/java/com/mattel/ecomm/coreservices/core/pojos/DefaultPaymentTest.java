
package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultPaymentTest {

    private DefaultPayment impl = null;
    private Date createdate = new Date();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new DefaultPayment();
        impl.setMaskAccount("dummy_string");
        impl.setLastUser("dummy_string");
        impl.setUsersId("dummy_string");
        impl.setExpMonth("dummy_string");
        impl.setExpYear("dummy_string");
        impl.setCreditCardId("dummy_string");
        impl.setCardType("dummy_string");
        impl.setCreateDate(createdate);
        impl.setDefaultFlag("dummy_string");
        impl.setAddressId("dummy_string");
        impl.setField1("dummy_string");
        impl.setField2("dummy_string");
        impl.setToken("dummy_string");
        impl.setField3("dummy_string");
        impl.setCardName("dummy_string");
        impl.setHashCode("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetUsersId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUsersId());
    }

    @Test
    public void testGetCardName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCardName());
    }

    @Test
    public void testGetLastUser()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLastUser());
    }

    @Test
    public void testGetField1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getField1());
    }

    @Test
    public void testGetField2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getField2());
    }

    @Test
    public void testGetDefaultFlag()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getDefaultFlag());
    }

    @Test
    public void testGetField3()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getField3());
    }

    @Test
    public void testGetAddressId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressId());
    }

    @Test
    public void testGetExpMonth()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getExpMonth());
    }

    @Test
    public void testGetCreateDate()
        throws Exception
    {
        Assert.assertEquals(createdate, impl.getCreateDate());
    }

    @Test
    public void testGetHashCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getHashCode());
    }

    @Test
    public void testGetToken()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getToken());
    }

    @Test
    public void testGetExpYear()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getExpYear());
    }

    @Test
    public void testGetMaskAccount()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getMaskAccount());
    }

    @Test
    public void testGetCreditCardId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCreditCardId());
    }

    @Test
    public void testGetCardType()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCardType());
    }

}
