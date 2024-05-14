package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class QuickViewModelTest {

  @Mock
  private Resource resource;

  @InjectMocks
  QuickViewModel quickViewModel;

  ValueMap properties;

  @Before
  public void setup() throws Exception {
    quickViewModel = new QuickViewModel();
    properties = Mockito.mock(ValueMap.class);
    Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(properties);
  }

  @Test
  public void testInit() throws Exception {
    quickViewModel.init();
  }

}
