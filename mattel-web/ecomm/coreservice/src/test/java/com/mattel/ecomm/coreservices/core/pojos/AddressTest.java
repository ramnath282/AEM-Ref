package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressTest {
    private Address address;

    @Before
    public void createAddress() throws Exception {
        address = new Address();
        address.setAddressField2("Richmond Street");
        address.setAddressId("1234");
        address.setAddressLine(new String[] { "Block 1" });
        address.setAddressType("Shipping & Billing");
        address.setBillingCodeType("SB");
        address.setBusinessTitle("Director");
        address.setCity("San Jose");
        address.setCountry("US");
        address.setDefaultAddress(true);
        address.setFirstName("John");
        address.setLastName("Doe");
        address.setMiddleName("Senoir");
        address.setNickName("John");
        address.setOfficeAddress("NA");
        address.setOrganizationUnitName("Mattel");
        address.setPersonTitle("Mr.");
        address.setState("California");
        address.setZipCode("22023");
        address.toString();
    }

    @Test
    public void testGetMiddleName() throws Exception {
        Assert.assertEquals("Senoir", address.getMiddleName());
    }

    @Test
    public void testGetLastName() throws Exception {
        Assert.assertEquals("Doe", address.getLastName());
    }

    @Test
    public void testGetBusinessTitle() throws Exception {
        Assert.assertEquals("Director", address.getBusinessTitle());
    }

    @Test
    public void testGetCountry() throws Exception {
        Assert.assertEquals("US", address.getCountry());
    }

    @Test
    public void testGetFirstName() throws Exception {
        Assert.assertEquals("John", address.getFirstName());
    }

    @Test
    public void testGetOfficeAddress() throws Exception {
        Assert.assertEquals("NA", address.getOfficeAddress());
    }

    @Test
    public void testGetZipCode() throws Exception {
        Assert.assertEquals("22023", address.getZipCode());
    }

    @Test
    public void testGetBillingCodeType() throws Exception {
        Assert.assertEquals("SB", address.getBillingCodeType());
    }

    @Test
    public void testGetPersonTitle() throws Exception {
        Assert.assertEquals("Mr.", address.getPersonTitle());
    }

    @Test
    public void testGetAddressLine() throws Exception {
        Assert.assertArrayEquals(new String[] { "Block 1" }, address.getAddressLine());
    }

    @Test
    public void testGetOrganizationUnitName() throws Exception {
        Assert.assertEquals("Mattel", address.getOrganizationUnitName());
    }

    @Test
    public void testGetCity() throws Exception {
        Assert.assertEquals("San Jose", address.getCity());
    }

    @Test
    public void testGetAddressField2() throws Exception {
        Assert.assertEquals("Richmond Street", address.getAddressField2());
    }

    @Test
    public void testGetNickName() throws Exception {
        Assert.assertEquals("John", address.getNickName());
    }

    @Test
    public void testGetAddressType() throws Exception {
        Assert.assertEquals("Shipping & Billing", address.getAddressType());
    }

    @Test
    public void testGetAddressId() throws Exception {
        Assert.assertEquals("1234", address.getAddressId());
    }

    @Test
    public void testGetState() throws Exception {
        Assert.assertEquals("California", address.getState());
    }

    @Test
    public void testIsDefaultAddress() throws Exception {
        Assert.assertTrue(address.isDefaultAddress());
    }
}
