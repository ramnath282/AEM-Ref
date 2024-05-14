package com.mattel.global.core.pojo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PreferenceListTest {

    private PreferenceList impl = null;

    @Before
    public void setUp() throws Exception {
        impl = new PreferenceList();
        impl.setPreferenceID("dummy_string");
        impl.setPreferenceName("dummy_string");
        impl.setPreferenceType("dummy_string");
        impl.setPreferenceUPDTTM("dummy_string");
        impl.setPreferenceOptIn("dummy_string");
        impl.setDoubleOptInStatusCD("dummy_string");
        impl.setPrefernceCRDTTM("dummy_string");
        impl.toString();
    }

    @Test
    public void testGetters() {
        Assert.assertEquals("dummy_string", impl.getPreferenceID());
        Assert.assertEquals("dummy_string", impl.getPreferenceName());
        Assert.assertEquals("dummy_string", impl.getPreferenceType());
        Assert.assertEquals("dummy_string", impl.getPreferenceUPDTTM());
        Assert.assertEquals("dummy_string", impl.getDoubleOptInStatusCD());
        Assert.assertEquals("dummy_string", impl.getDoubleOptInStatusCD());
        Assert.assertEquals("dummy_string", impl.getPrefernceCRDTTM());

    }

    @Test
    public void testObj() throws Exception{
        Assert.assertNotNull(impl);
    }

}