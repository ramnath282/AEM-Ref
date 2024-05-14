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
public class MixedMediaViewerModelTest {

  @InjectMocks
  private MixedMediaViewerModel mixedMediaViewerModel;

  private static final String SCENESEVEN_SERVER_URL = "https://s7d9.scene7.com/is/image";
  private static final String SCENESEVEN_CONTENT_URL = "https://s7d9.scene7.com/is/image";
  private static final String SCENESEVEN_VIDEO_URL = "https://s7d9.scene7.com/is/image";

  @Test
  public void testInit() {
    PowerMockito.mockStatic(EcommConfigurationUtils.class);
    Mockito.when(EcommConfigurationUtils.getScenesevenServerUrl()).thenReturn(SCENESEVEN_SERVER_URL);
    Mockito.when(EcommConfigurationUtils.getScenesevenContentUrl()).thenReturn(SCENESEVEN_CONTENT_URL);
    Mockito.when(EcommConfigurationUtils.getScenesevenVideoserverUrl()).thenReturn(SCENESEVEN_VIDEO_URL);

    mixedMediaViewerModel.init();
    Assert.assertEquals(SCENESEVEN_SERVER_URL, mixedMediaViewerModel.getScenesevenServerurl());
    Assert.assertEquals(SCENESEVEN_CONTENT_URL, mixedMediaViewerModel.getScenesevenContenturl());
    Assert.assertEquals(SCENESEVEN_VIDEO_URL, mixedMediaViewerModel.getScenesevenVideoserverurl());

  }

  @Test
  public void testInitWithNull() {
    PowerMockito.mockStatic(EcommConfigurationUtils.class);
    
    mixedMediaViewerModel.init();
    Assert.assertNull(mixedMediaViewerModel.getScenesevenServerurl());
    Assert.assertNull(mixedMediaViewerModel.getScenesevenContenturl());
    Assert.assertNull(mixedMediaViewerModel.getScenesevenVideoserverurl());
  }

}
