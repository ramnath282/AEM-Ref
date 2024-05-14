package com.mattel.ecomm.core.models;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommConfigurationUtils.class })
public class TealiumModelTest {

  @InjectMocks
  private TealiumModel tealiumModel;

  @Test
  public void testTealiumUrl() {
    PowerMockito.mockStatic(EcommConfigurationUtils.class);
    Mockito.when(EcommConfigurationUtils.getTealiumUrl()).thenReturn("TealiumUrl");
    tealiumModel.init();
    Assert.assertEquals("TealiumUrl", tealiumModel.getTealiumUrl());
  }

  @Test
  public void testTealiumUrlWithNull() {
    PowerMockito.mockStatic(EcommConfigurationUtils.class);
    tealiumModel.setTealiumUrl(null);
    tealiumModel.init();
    Assert.assertNull(tealiumModel.getTealiumUrl());
  }

}
