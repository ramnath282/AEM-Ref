package com.mattel.ecomm.coreservices.core.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsumerLoyaltyRewardsResponseTest {
  private ConsumerLoyaltyRewardsResponse consumerLoyaltyRewardsResponse = null;
  private ConsumerLoyalty consumerLoyalty = null;
  private ConsumerPromotions consumerPromotions = null;
  private Status status = null;

  @Before
  public void setUp() throws Exception {
    consumerLoyaltyRewardsResponse = new ConsumerLoyaltyRewardsResponse();
    consumerLoyaltyRewardsResponse.setBrandID("brandID");
    consumerLoyaltyRewardsResponse.setConsumerID("consumerID");
    
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
    consumerLoyaltyRewardsResponse.setConsumerLoyalty(consumerLoyalty);
    
    consumerPromotions = new ConsumerPromotions();
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
    List<ConsumerPromotions> consumerPromotionsList = new ArrayList<>();
    consumerPromotionsList.add(consumerPromotions);
    consumerLoyaltyRewardsResponse.setConsumerPromotions(consumerPromotionsList);
    
    consumerLoyaltyRewardsResponse.setRequestedBy("requestedBy");
    
    status = new Status();
    status.setMessage("Successfull");
    status.setStatusCode("201");
    consumerLoyaltyRewardsResponse.setStatus(status);
  }
  
  @Test
  public void testRun() {
    status.toString();
    consumerPromotions.toString();
    consumerLoyalty.toString();
    consumerLoyaltyRewardsResponse.toString();
  }
  
  @Test
  public void testGetters(){
    Assert.assertEquals("brandID", consumerLoyaltyRewardsResponse.getBrandID());
    Assert.assertEquals("loyaltyID", consumerLoyalty.getLoyaltyID());
    Assert.assertEquals("promotionID", consumerPromotions.getPromotionID());
    Assert.assertEquals("Successfull", status.getMessage());
  }
}
