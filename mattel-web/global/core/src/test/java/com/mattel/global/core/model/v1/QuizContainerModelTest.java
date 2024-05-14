package com.mattel.global.core.model.v1;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class QuizContainerModelTest {

  @InjectMocks
  private QuizContainerModel quizContainerModel;

  @Mock
  private Resource resource;

  @Before
  public void setup() throws IllegalArgumentException, IllegalAccessException {
    PowerMockito.mockStatic(GlobalUtils.class);
    MemberModifier.field(QuizContainerModel.class, "viewAllLinkURL").set(quizContainerModel,
        "viewAllLinkURL");
    Mockito.when(GlobalUtils.checkLink("viewAllLinkURL", resource)).thenReturn("viewAllLink");
  }

  @Test
  public void testInit() {
    quizContainerModel.init();
    assertEquals("viewAllLink", quizContainerModel.getViewAllLink());
  }
}
