package com.mattel.global.master.core.model;

import static org.junit.Assert.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class GridModelTest {

  @InjectMocks
  GridModel gridModel;

  @Mock
  Resource resource;

  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(GlobalUtils.class);
    MemberModifier.field(GridModel.class, "linkURL").set(gridModel, "/content/en/us/home");
    MemberModifier.field(GridModel.class, "backgroundImagePath").set(gridModel,
        "backgroundImagePath");
    MemberModifier.field(GridModel.class, "showMoreText").set(gridModel, "showMoreText");
    MemberModifier.field(GridModel.class, "showLessText").set(gridModel, "showLessText");
    MemberModifier.field(GridModel.class, "linkText").set(gridModel, "linkText");
    Mockito.when(
        GlobalUtils.removeTags("showMoreText", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING))
        .thenReturn("showMoreText");
    Mockito.when(
        GlobalUtils.removeTags("showLessText", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING))
        .thenReturn("showLessText");
    Mockito.when(
        GlobalUtils.removeTags("linkText", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING))
        .thenReturn("linkText");
    
    
  }

  @Test
  public void testToVerifyInternalUrl() throws Exception {
    gridModel.init();
    gridModel.getLinkURL();
  }

  @Test
  public void testGetterSetters() {
    assertEquals("backgroundImagePath", gridModel.getBackgroundImagePath());
    assertEquals("/content/en/us/home", gridModel.getLinkURL());
    assertEquals("showMoreText", gridModel.getShowMoreText());
    assertEquals("showLessText", gridModel.getShowLessText());
    assertEquals("linkText", gridModel.getLinkText());
  }
}
