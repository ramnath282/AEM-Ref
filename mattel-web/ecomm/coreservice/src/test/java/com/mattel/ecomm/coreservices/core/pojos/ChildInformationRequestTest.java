package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChildInformationRequestTest {
    private final String addressId = "11111";

    private final String addressType = "Shipping & Billing";

    private final String dateOfBirth = "20 May 1998";

    private final String firstName = "John";

    private final String gender = "M";

    private final String relationship = "Single";
    private ChildInformationRequest childInformationRequest;

    @Before
    public void createChildInformationRequest() throws Exception {
        childInformationRequest = new ChildInformationRequest();
        childInformationRequest.setAddressId(addressId);
        childInformationRequest.setAddressType(addressType);
        childInformationRequest.setDateOfBirth(dateOfBirth);
        childInformationRequest.setFirstName(firstName);
        childInformationRequest.setGender(gender);
        childInformationRequest.setRelationship(relationship);
        childInformationRequest.toString();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetAddressId() throws Exception {
        Assert.assertEquals(addressId, childInformationRequest.getAddressId());
    }

    @Test
    public void testGetAddressType() throws Exception {
        Assert.assertEquals(addressType, childInformationRequest.getAddressType());
    }

    @Test
    public void testGetDateOfBirth() throws Exception {
        Assert.assertEquals(dateOfBirth, childInformationRequest.getDateOfBirth());
    }

    @Test
    public void testGetFirstName() throws Exception {
        Assert.assertEquals(firstName, childInformationRequest.getFirstName());
    }

    @Test
    public void testGetGender() throws Exception {
        Assert.assertEquals(gender, childInformationRequest.getGender());
    }

    @Test
    public void testGetRelationship() throws Exception {
        Assert.assertEquals(relationship, childInformationRequest.getRelationship());
    }
}
