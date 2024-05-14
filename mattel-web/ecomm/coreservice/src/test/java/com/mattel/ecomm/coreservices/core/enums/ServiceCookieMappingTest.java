package com.mattel.ecomm.coreservices.core.enums;

import org.junit.Assert;
import org.junit.Test;

public class ServiceCookieMappingTest {
    @Test
    public void testGetCookieNames() throws Exception {
        Assert.assertArrayEquals(
                new String[] { "JSESSIONID", "WC_ACTIVEPOINTER", "WC_PERSISTENT", "WC_SESSION_ESTABLISHED",
                        "WC_USERACTIVITY", "WC_AUTHENTICATION", "MATTEL_WELCOME_MSG", "WC_SHOW_USER_ACTIVATION", 
                        "WC_GENERIC_ACTIVITYDATA",
                        "MATTEL_REMEMBER_ME", 
                        "MATTEL_CUSTOMER_SEGMENT", "EmployeeSegment","CMID"},
                ServiceCookieMapping.DEFAULT.getCookieNames());
    }
}
