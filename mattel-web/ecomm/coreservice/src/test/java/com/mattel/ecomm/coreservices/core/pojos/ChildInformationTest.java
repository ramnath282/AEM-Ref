package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChildInformationTest {
    private final String addressId = "11111";

    private final String addressType = "Shipping & Billing";

    private final String dateOfBirth = "20 May 1998";

    private final String firstName = "John";

    private final String gender = "M";

    private final String lastName = "Doe";

    private final String nickName = "John";

    private final String relationship = "Single";
    private ChildInformation childInformation;

    @Before
    public void createChildInformation() throws Exception {
        childInformation = new ChildInformation();
        childInformation.setAddressId(addressId);
        childInformation.setAddressType(addressType);
        childInformation.setDateOfBirth(dateOfBirth);
        childInformation.setFirstName(firstName);
        childInformation.setGender(gender);
        childInformation.setLastName(lastName);
        childInformation.setNickName(nickName);
        childInformation.setRelationship(relationship);
        childInformation.toString();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetAddressId() throws Exception {
        Assert.assertEquals(addressId, childInformation.getAddressId());
    }

    @Test
    public void testGetAddressType() throws Exception {
        Assert.assertEquals(addressType, childInformation.getAddressType());
    }

    @Test
    public void testGetDateOfBirth() throws Exception {
        Assert.assertEquals(dateOfBirth, childInformation.getDateOfBirth());
    }

    @Test
    public void testGetFirstName() throws Exception {
        Assert.assertEquals(firstName, childInformation.getFirstName());
    }

    @Test
    public void testGetGender() throws Exception {
        Assert.assertEquals(gender, childInformation.getGender());
    }

    @Test
    public void testGetLastName() throws Exception {
        Assert.assertEquals(lastName, childInformation.getLastName());
    }

    @Test
    public void testGetNickName() throws Exception {
        Assert.assertEquals(nickName, childInformation.getNickName());
    }

    @Test
    public void testGetRelationship() throws Exception {
        Assert.assertEquals(relationship, childInformation.getRelationship());
    }
}
