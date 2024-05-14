package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerResponseTest {
    private String[] tags = {"Group: Employee","Loyalty: Silver","Segment: AG_GIRL_OF_THE_YEAR"};
    private CustomerResponse customerResponse;
    private ConsumerLoyalty consumerLoyalty = null;
    private List<ConsumerPromotions> consumerPromotionsList = null;
    private ConsumerSegments consumerSegments = null;

    @Before
    public void createChildInformationResponse() throws Exception {
    	customerResponse = new CustomerResponse();
    	customerResponse.setConsumerID("consumerid");
    	customerResponse.setFirstName("firstname");
    	customerResponse.setId("id");
    	customerResponse.setTags(tags);
    	
    	
    	consumerLoyalty = new ConsumerLoyalty();
        consumerLoyalty.setLoyaltyAcctSrc("loyaltyAcctSrc");
        consumerLoyalty.setLoyaltyEndDate("loyaltyEndDate");
        consumerLoyalty.setLoyaltyID("loyaltyID");
        consumerLoyalty.setLoyaltyNumber("loyaltyNumber");
        consumerLoyalty.setLoyaltyPointCount("loyaltyPointCount");
        consumerLoyalty.setLoyaltyStartDate("loyaltyStartDate");
        consumerLoyalty.setLoyaltyTierCode("loyaltyTierCode");
        consumerLoyalty.setNextRewardPoint("nextRewardPoint");
        consumerLoyalty.setNextTierAmount("nextTierAmount");
        consumerLoyalty.setTierExpirationDate("tierExpirationDate");
        consumerLoyalty.setTierStartDate("tierStartDate");
        customerResponse.setConsumerLoyalty(consumerLoyalty);
        
        ConsumerPromotions consumerPromotions = new ConsumerPromotions();
        consumerPromotions.setActiveFlag("activeFlag");
        consumerPromotions.setAwardAmount("awardAmount");
        consumerPromotions.setModifiedDate("modifiedDate");
        consumerPromotions.setPromotionBrand("promotionBrand");
        consumerPromotions.setPromotionCreatedDate("promotionCreatedDate");
        consumerPromotions.setPromotionExpiryDate("promotionExpiryDate");
        consumerPromotions.setPromotionID("promotionID");
        consumerPromotions.setPromotionStartDate("promotionStartDate");
        consumerPromotions.setPromotionType("promotionType");
        consumerPromotions.setRedeemedSource("redeemedSource");
        consumerPromotions.setRedeemedStatus("redeemedStatus");
        consumerPromotions.setRewardsPromoCode("rewardsPromoCode");
        consumerPromotionsList = new ArrayList<>();
        consumerPromotionsList.add(consumerPromotions);
        customerResponse.setConsumerPromotions(consumerPromotionsList);
        
        consumerSegments = new ConsumerSegments();
        Segment segment = new Segment();
        segment.setEndDate("2019-09-30");
        segment.setSegmentCode("AG_GIRL_OF_THE_YEAR");
        segment.setSegmentID("120");
        segment.setSegmentName("AGCA_AMERICAN_GIRL_ATLANTA");
        segment.setSegmentOptIn("Y");
        segment.setSegmentType("PROMOTIONAL");
        segment.setStartDate("2019-09-30");
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(segment);
        consumerSegments.setSegmentList(segmentList);
        customerResponse.setConsumerSegments(consumerSegments);
        
    }

    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void testGetConsumerID() throws Exception {
        Assert.assertEquals("consumerid", customerResponse.getConsumerID());
    }
    
    @Test
    public void testGetFirstName() throws Exception {
        Assert.assertEquals("firstname", customerResponse.getFirstName());
    }
    
    @Test
    public void testGetId() throws Exception {
        Assert.assertEquals("id", customerResponse.getId());
    }
    
    @Test
    public void testGetTags() throws Exception {
        Assert.assertEquals(tags, customerResponse.getTags());
    }

    @Test
    public void testGetConsumerPromotions() throws Exception {
        Assert.assertEquals(consumerPromotionsList, customerResponse.getConsumerPromotions());
    }
    
    @Test
    public void testGetConsumerSegments() throws Exception {
        Assert.assertEquals(consumerSegments, customerResponse.getConsumerSegments());
    }
    
    @Test
    public void testGetConsumerLoyalty() throws Exception {
        Assert.assertEquals(consumerLoyalty, customerResponse.getConsumerLoyalty());
    }
}
