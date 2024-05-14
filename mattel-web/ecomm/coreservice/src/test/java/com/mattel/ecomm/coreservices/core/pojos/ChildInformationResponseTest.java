package com.mattel.ecomm.coreservices.core.pojos;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChildInformationResponseTest {
    private List<ChildInformation> childInformation = Arrays.asList();
    private ChildInformationResponse childInformationResponse;

    @Before
    public void createChildInformationResponse() throws Exception {
        childInformationResponse = new ChildInformationResponse();
        childInformationResponse.setChildInformation(childInformation);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetChildInformation() throws Exception {
        Assert.assertEquals(childInformation, childInformationResponse.getChildInformation());
    }
}
