package com.mattel.ecomm.coreservices.core.pojos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ApplyRewardsResponseTest {
    private ApplyRewardsResponse applyRewardsResponse;

    @Before
    public void setUp() throws Exception {
        applyRewardsResponse = new ApplyRewardsResponse();
    }

    @Test
    public void test() {
        Assert.assertNotNull(applyRewardsResponse);
        applyRewardsResponse.toString();
    }
}
