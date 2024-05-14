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

import com.mattel.global.core.model.v1.CtaItemModel;
import com.mattel.global.core.utils.GlobalUtils;
import com.mattel.global.master.core.constants.Constants;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalUtils.class)
public class TextOverlayModelTest {

    @InjectMocks
    private TextOverlayModel textOverlayModel;
    
    @Mock
    private Resource resource;

    @Before
    public void setup() throws Exception {
      PowerMockito.mockStatic(GlobalUtils.class);
      MemberModifier.field(TextOverlayModel.class, "backgroundColor").set(textOverlayModel, "backgroundColor");
      MemberModifier.field(TextOverlayModel.class, "backgroundColorMob").set(textOverlayModel, "backgroundColorMob");
      MemberModifier.field(TextOverlayModel.class, "title").set(textOverlayModel, "title");
      MemberModifier.field(TextOverlayModel.class, "description").set(textOverlayModel, "description");
      MemberModifier.field(TextOverlayModel.class, "subTitle").set(textOverlayModel, "subTitle");
      MemberModifier.field(TextOverlayModel.class, "entrCompClickable").set(textOverlayModel, true);
      MemberModifier.field(TextOverlayModel.class, "ctaurl").set(textOverlayModel, "ctaurl");
      MemberModifier.field(TextOverlayModel.class, "linkOption").set(textOverlayModel, "newwindow");
	  
      PowerMockito.mockStatic(GlobalUtils.class);
      Resource ctaResource = Mockito.mock(Resource.class);
      Mockito.when(GlobalUtils.getCtaURL(resource)).thenReturn(ctaResource);
      CtaItemModel ctaModel = Mockito.mock(CtaItemModel.class);
      Mockito.when(ctaResource.adaptTo(CtaItemModel.class)).thenReturn(ctaModel );
      Mockito.when(ctaModel.getUrl()).thenReturn("ctaUrl");
      Mockito.when(ctaModel.getLinkOptions()).thenReturn("linkOption");
      
      Mockito.when(GlobalUtils.removeTags("title", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING)).thenReturn("title");
      Mockito.when(GlobalUtils.removeTags("description", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING)).thenReturn("description");
      Mockito.when(GlobalUtils.removeTags("subTitle", Constants.REMOVE_TAGS, Constants.EMPTY_ARRAY_STRING)).thenReturn("subTitle");
    }

    @Test
    public void testToVerifyTextOverlayModel() {
      assertEquals("backgroundColor", textOverlayModel.getBackgroundColor());
      assertEquals("backgroundColorMob", textOverlayModel.getBackgroundColorMob());
      assertEquals("title", textOverlayModel.getTitle());
      assertEquals("subTitle", textOverlayModel.getSubTitle());
      assertEquals("description", textOverlayModel.getDescription());
      assertEquals(true, textOverlayModel.getEntrCompClickable());
      assertEquals("ctaurl", textOverlayModel.getCtaurl());
      assertEquals("newwindow", textOverlayModel.getLinkOption());
    }
    
    @Test
    public void testInit() throws IllegalArgumentException, IllegalAccessException{
      Resource ctaResource = Mockito.mock(Resource.class); 
      Mockito.when(GlobalUtils.getCtaURL(resource)).thenReturn(ctaResource);
      CtaItemModel model = Mockito.mock(CtaItemModel.class); 
      Mockito.when(ctaResource.adaptTo(CtaItemModel.class)).thenReturn(model);
      Mockito.when(model.getUrl()).thenReturn("ctaurl");
      Mockito.when(model.getLinkOptions()).thenReturn("newwindow");
      textOverlayModel.init();
    }

}
