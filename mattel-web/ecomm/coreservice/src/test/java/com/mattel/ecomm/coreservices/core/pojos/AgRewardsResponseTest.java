package com.mattel.ecomm.coreservices.core.pojos;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AgRewardsResponseTest {
    private final LoyaltyDetails loyaltyDetails = Mockito.mock(LoyaltyDetails.class);
    private final Map<String, Object> rewardSegmentsMap = new HashMap<>();
    private AgRewardsResponse agRewardsResponse;

    @Before
    public void createAgRewardsResponse() throws Exception {
        agRewardsResponse = new AgRewardsResponse();
        agRewardsResponse.setLoyaltyDetails(loyaltyDetails);
        agRewardsResponse.setRewardSegmentsMap(rewardSegmentsMap);
        agRewardsResponse.toString();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetLoyaltyDetails() throws Exception {
        Assert.assertEquals(loyaltyDetails, agRewardsResponse.getLoyaltyDetails());
    }

    @Test
    public void testGetRewardSegmentsMap() throws Exception {
        Assert.assertEquals(rewardSegmentsMap, agRewardsResponse.getRewardSegmentsMap());
    }
}
