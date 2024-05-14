package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ContactTest {
    private final String addressId = "1234";

    private final List<String> addressLine = Arrays.asList();

    private final String addressType = "Shipping & Billing";

    private final List<ContactAttribute> attributes = Arrays.asList();

    private final String city = "San Jose";

    private final String country = "US";

    private final String email1 = "test@domain.com";

    private final String fax2 = "13113143144265";

    private final String firstName = "John";

    private final String lastName = "Doe";

    private final String nickName = "John";

    private final String phone1 = "1234567890";

    private final String primary = "California";

    private final String state = "California";

    private final String zipCode = "22023";
    private Contact contact;

    @Before
    public void createContact() throws Exception {
        contact = new Contact();
        contact.setAddressId(addressId);
        contact.setAddressLine(addressLine);
        contact.setAddressType(addressType);
        contact.setAttributes(attributes);
        contact.setCity(city);
        contact.setCountry(country);
        contact.setEmail1(email1);
        contact.setFax2(fax2);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setNickName(nickName);
        contact.setPhone1(phone1);
        contact.setPrimary(primary);
        contact.setState(state);
        contact.setZipCode(zipCode);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetLastName() throws Exception {
        Assert.assertEquals(lastName, contact.getLastName());
    }

    @Test
    public void testGetZipCode() throws Exception {
        Assert.assertEquals(zipCode, contact.getZipCode());
    }

    @Test
    public void testGetCountry() throws Exception {
        Assert.assertEquals(country, contact.getCountry());
    }

    @Test
    public void testGetCity() throws Exception {
        Assert.assertEquals(city, contact.getCity());
    }

    @Test
    public void testGetNickName() throws Exception {
        Assert.assertEquals(nickName, contact.getNickName());
    }

    @Test
    public void testGetAddressType() throws Exception {
        Assert.assertEquals(addressType, contact.getAddressType());
    }

    @Test
    public void testGetAttributes() throws Exception {
        Assert.assertEquals(attributes, contact.getAttributes());
    }

    @Test
    public void testGetAddressLine() throws Exception {
        Assert.assertEquals(addressLine, contact.getAddressLine());
    }

    @Test
    public void testGetAddressId() throws Exception {
        Assert.assertEquals(addressId, contact.getAddressId());
    }

    @Test
    public void testGetPhone1() throws Exception {
        Assert.assertEquals(phone1, contact.getPhone1());
    }

    @Test
    public void testGetFirstName() throws Exception {
        Assert.assertEquals(firstName, contact.getFirstName());
    }

    @Test
    public void testGetEmail1() throws Exception {
        Assert.assertEquals(email1, contact.getEmail1());
    }

    @Test
    public void testGetFax2() throws Exception {
        Assert.assertEquals(fax2, contact.getFax2());
    }

    @Test
    public void testGetState() throws Exception {
        Assert.assertEquals(state, contact.getState());
    }

    @Test
    public void testGetPrimary() throws Exception {
        Assert.assertEquals(primary, contact.getPrimary());
    }
}
