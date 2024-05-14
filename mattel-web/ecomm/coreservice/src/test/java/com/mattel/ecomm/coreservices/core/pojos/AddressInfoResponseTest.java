package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddressInfoResponseTest {
    private Address address;
    private List<Address> addresses;

    private final String defaultShipMethod = "SB";
    private AddressInfoResponse addressInfoResponse;

    @Before
    public void createAddressInfoResponse() throws Exception {
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
        addresses = Arrays.asList(address);
        addressInfoResponse = new AddressInfoResponse();
        addressInfoResponse.setAddress(addresses);
        addressInfoResponse.setDefaultShipMethod(defaultShipMethod);
    }

    @Test
    public void testGetAddress() throws Exception {
        Assert.assertEquals(addresses, addressInfoResponse.getAddress());
    }

    @Test
    public void testGetDefaultShipMethod() throws Exception {
        Assert.assertEquals("SB", addressInfoResponse.getDefaultShipMethod());
    }
}
