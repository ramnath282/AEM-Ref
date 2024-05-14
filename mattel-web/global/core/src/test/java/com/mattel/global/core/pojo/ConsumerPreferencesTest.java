package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ConsumerPreferencesTest {
    private final List<PreferenceList> preferenceList = Arrays.asList();
    private ConsumerPreferences consumerPreferences;

    @Before
    public void createBaseResponse() throws Exception {
        consumerPreferences = new ConsumerPreferences();
        consumerPreferences.setPreferenceList(preferenceList);
        consumerPreferences.toString();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetCookieList() throws Exception {
        Assert.assertEquals(preferenceList, consumerPreferences.getPreferenceList());
    }

}