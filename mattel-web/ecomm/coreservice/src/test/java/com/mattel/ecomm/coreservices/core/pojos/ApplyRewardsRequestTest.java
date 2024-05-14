package com.mattel.ecomm.coreservices.core.pojos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ApplyRewardsRequestTest {
    private ApplyRewardsRequest applyRewardsRequest;

    @Before
    public void setUp() throws Exception {
        applyRewardsRequest = new ApplyRewardsRequest();
        applyRewardsRequest.setLangId("langId");
    }

    @Test
    public void test() {
        assertEquals("langId", applyRewardsRequest.getLangId());
        applyRewardsRequest.toString();
    }
}
