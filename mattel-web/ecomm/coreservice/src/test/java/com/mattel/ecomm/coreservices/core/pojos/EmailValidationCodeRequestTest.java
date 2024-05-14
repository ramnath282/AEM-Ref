
package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmailValidationCodeRequestTest {

    private EmailValidationCodeRequest impl = null;

    @Before
    public void setUp()
        throws Exception
    {
        impl = new EmailValidationCodeRequest();
        impl.setChallengeAnswer("dummy_string");
        impl.setStoreId("dummy_string");
        impl.setCatalogId("dummy_string");
        impl.setLangId("dummy_string");
        impl.setUserNameDisplay("dummy_string");
        impl.setUserName("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetLangId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getLangId());
    }

    @Test
    public void testGetUserNameDisplay()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserNameDisplay());
    }

    @Test
    public void testGetUserName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getUserName());
    }

    @Test
    public void testGetStoreId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStoreId());
    }

    @Test
    public void testGetCatalogId()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCatalogId());
    }

    @Test
    public void testGetChallengeAnswer()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getChallengeAnswer());
    }

}
