
package com.mattel.ecomm.esb.cdm.dtc.validateaddress.components1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RowRequestTypeTest {

    private RowRequestType impl = null;
    private UserFieldsType userfields = new UserFieldsType();

    @Before
    public void setUp()
        throws Exception
    {
        impl = new RowRequestType();
        impl.setAddressLine1("dummy_string");
        impl.setAddressLine2("dummy_string");
        impl.setAddressLine3("dummy_string");
        impl.setAddressLine4("dummy_string");
        impl.setCity("dummy_string");
        impl.setStateProvince("dummy_string");
        impl.setPostalCode("dummy_string");
        impl.setCountry("dummy_string");
        impl.setFirmName("dummy_string");
        impl.setCanLanguage("dummy_string");
        impl.setUserFields(userfields);
        impl.setUSUrbanName("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetAddressLine4()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine4());
    }

    @Test
    public void testGetAddressLine2()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine2());
    }

    @Test
    public void testGetFirmName()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getFirmName());
    }

    @Test
    public void testGetAddressLine3()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine3());
    }

    @Test
    public void testGetCountry()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCountry());
    }

    @Test
    public void testGetStateProvince()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getStateProvince());
    }

    @Test
    public void testGetUserFields()
        throws Exception
    {
        Assert.assertEquals(userfields, impl.getUserFields());
    }

    @Test
    public void testGetAddressLine1()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getAddressLine1());
    }

    @Test
    public void testGetCanLanguage()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCanLanguage());
    }

    @Test
    public void testGetPostalCode()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getPostalCode());
    }

    @Test
    public void testGetCity()
        throws Exception
    {
        Assert.assertEquals("dummy_string", impl.getCity());
    }
    @Test
    public void getUSUrbanName() {
      Assert.assertEquals("dummy_string", impl.getUSUrbanName());
  }


}
