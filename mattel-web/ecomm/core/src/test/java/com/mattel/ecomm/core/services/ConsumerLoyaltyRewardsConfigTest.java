package com.mattel.ecomm.core.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.mattel.ecomm.core.services.ConsumerLoyaltyRewardsConfig.Config;

//@RunWith(PowerMockRunner.class)

@RunWith(MockitoJUnitRunner.class)
public class ConsumerLoyaltyRewardsConfigTest {

  @Mock
  Config config;

  @InjectMocks
  ConsumerLoyaltyRewardsConfig consumerLRC;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void test_Activate_Method() {
    Mockito.when(config.apiFailureCallCount()).thenReturn("3");
    Mockito.when(config.localStorageExpiry()).thenReturn("24-01-2010");
    consumerLRC.activate(config);
    Assert.assertEquals("3", consumerLRC.getApiFailureCallCount());
    Assert.assertEquals("24-01-2010", consumerLRC.getLocalStorageExpiry());
  }
}