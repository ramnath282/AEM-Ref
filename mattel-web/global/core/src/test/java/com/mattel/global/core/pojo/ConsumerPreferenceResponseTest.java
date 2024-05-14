package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConsumerPreferenceResponseTest {

    private ConsumerPreferenceResponse consumerPreferenceResponse;
    private Status status;

    @Before
    public void setUp() throws Exception {
        consumerPreferenceResponse = new ConsumerPreferenceResponse();
        ConsumerPreferences consumerPreferences = new ConsumerPreferences();
        List<PreferenceList> preferenceLists = new ArrayList<>();
        consumerPreferences.setPreferenceList(preferenceLists);
        consumerPreferenceResponse.setConsumerPreferences(consumerPreferences);

        status = new Status();
        status.setStatusCode("200");
        status.setMessage("Operation Successful");
    }

    @Test public void testToString() {
        status.toString();
    }

    @Test
    public void testGetters() {
        Assert.assertEquals("Operation Successful", status.getMessage());
    }

}