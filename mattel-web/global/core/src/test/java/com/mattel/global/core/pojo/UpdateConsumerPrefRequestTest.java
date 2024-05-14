package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UpdateConsumerPrefRequestTest {

    private UpdateConsumerPrefRequest consumerPrefRequest;
    private Status status;

    @Before
    public void setUp() throws Exception {
        consumerPrefRequest = new UpdateConsumerPrefRequest();
        consumerPrefRequest.setEmailAddress("emailAddress");
        consumerPrefRequest.setRequestedBy("AEM");

        ConsumerPreferences consumerPreferences = new ConsumerPreferences();
        List<PreferenceList> preferenceLists = new ArrayList<>();
        consumerPreferences.setPreferenceList(preferenceLists);

        status = new Status();
        status.setStatusCode("200");
        status.setMessage("Operation Successful");
    }

    @Test
    public void testRun() {
        status.toString();
    }

    @Test
    public void testGetters() {
        Assert.assertEquals("Operation Successful", status.getMessage());
        Assert.assertEquals("AEM", consumerPrefRequest.getRequestedBy());
    }

}