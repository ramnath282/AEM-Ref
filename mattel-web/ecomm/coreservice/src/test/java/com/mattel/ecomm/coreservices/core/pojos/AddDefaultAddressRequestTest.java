package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddDefaultAddressRequestTest {
    private AddDefaultAddressRequest addDefaultAddressRequest;

    @Before
    public void createAddDefaultAddressRequest() throws Exception {
        addDefaultAddressRequest = new AddDefaultAddressRequest();
        addDefaultAddressRequest.setAddressField2("Richmond Street");
        addDefaultAddressRequest.setAddressLine(new String[] { "Block 1" });
        addDefaultAddressRequest.setAddressType("Shipping & Billing");
        addDefaultAddressRequest.setCity("San Jose");
        addDefaultAddressRequest.setCountry("US");
        addDefaultAddressRequest.setFirstName("John");
        addDefaultAddressRequest.setLastName("Doe");
        addDefaultAddressRequest.setOrganizationUnitName("Mattel");
        addDefaultAddressRequest.setPhone1("1234567890");
        addDefaultAddressRequest.setPrimary("true");
        addDefaultAddressRequest.setState("California");
        addDefaultAddressRequest.setZipCode("22023");
        addDefaultAddressRequest.toString();
    }

    @Test
    public void testGetFirstName() throws Exception {
        Assert.assertEquals("John", addDefaultAddressRequest.getFirstName());
    }

    @Test
    public void testGetLastName() throws Exception {
        Assert.assertEquals("Doe", addDefaultAddressRequest.getLastName());
    }

    @Test
    public void testGetAddressType() throws Exception {
        Assert.assertEquals("Shipping & Billing", addDefaultAddressRequest.getAddressType());
    }

    @Test
    public void testGetZipCode() throws Exception {
        Assert.assertEquals("22023", addDefaultAddressRequest.getZipCode());
    }

    @Test
    public void testGetAddressLine() throws Exception {
        Assert.assertArrayEquals(new String[] { "Block 1" }, addDefaultAddressRequest.getAddressLine());
    }

    @Test
    public void testGetCountry() throws Exception {
        Assert.assertEquals("US", addDefaultAddressRequest.getCountry());
    }

    @Test
    public void testGetPhone1() throws Exception {
        Assert.assertEquals("1234567890", addDefaultAddressRequest.getPhone1());
    }

    @Test
    public void testGetPrimary() throws Exception {
        Assert.assertEquals("true", addDefaultAddressRequest.getPrimary());
    }

    @Test
    public void testGetCity() throws Exception {
        Assert.assertEquals("San Jose", addDefaultAddressRequest.getCity());
    }

    @Test
    public void testGetState() throws Exception {
        Assert.assertEquals("California", addDefaultAddressRequest.getState());
    }

    @Test
    public void testGetAddressField2() throws Exception {
        Assert.assertEquals("Richmond Street", addDefaultAddressRequest.getAddressField2());
    }

    @Test
    public void testGetOrganizationUnitName() throws Exception {
        Assert.assertEquals("Mattel", addDefaultAddressRequest.getOrganizationUnitName());
    }

}
