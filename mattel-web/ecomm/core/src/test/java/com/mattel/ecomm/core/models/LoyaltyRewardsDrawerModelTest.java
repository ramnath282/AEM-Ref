package com.mattel.ecomm.core.models;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.services.ConsumerLoyaltyRewardsConfig;
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
public class LoyaltyRewardsDrawerModelTest {

  @InjectMocks
  LoyaltyRewardsDrawerModel loyaltyRewardsDrawerModel;

  @Mock
  private Resource resource;

  String navRootPath = "/content/ag/en";
  String externalStoreRootPath = "/content/ag/en/retail";

  @Mock
  private Node navigationLinks;

  @Mock
  private Node externalStores;

  @Mock
  private Node externalLinks;

  @Mock
  private MultifieldReader multifieldReader;
  
  @Mock
  private ConsumerLoyaltyRewardsConfig consumerLoyaltyRewardsConfig;

  ResourceResolver resourceResolver;
  PageManager pageManager;

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(LoyaltyRewardsDrawerModel.class, "resource").set(loyaltyRewardsDrawerModel, resource);
    MemberModifier.field(LoyaltyRewardsDrawerModel.class, "consumerLoyaltyRewardsConfig").set(loyaltyRewardsDrawerModel,
    		consumerLoyaltyRewardsConfig);
    MemberModifier.field(LoyaltyRewardsDrawerModel.class, "apiFailureCallCount").set(loyaltyRewardsDrawerModel, "apiFailureCallCount");
    MemberModifier.field(LoyaltyRewardsDrawerModel.class, "localStorageExpiry").set(loyaltyRewardsDrawerModel, "localStorageExpiry");
  }

  @Test
  public void testGetters() throws IllegalArgumentException, IllegalAccessException {
    Assert.assertEquals(consumerLoyaltyRewardsConfig, loyaltyRewardsDrawerModel.getConsumerLoyaltyRewardsConfig());
    Assert.assertEquals("apiFailureCallCount", loyaltyRewardsDrawerModel.getApiFailureCallCount());
    Assert.assertEquals("localStorageExpiry", loyaltyRewardsDrawerModel.getLocalStorageExpiry());
  }
}
