package com.mattel.ecomm.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Assert;
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

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.utils.EcommConfigurationUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommConfigurationUtils.class })
public class PLPLayoutModelTest {

  @InjectMocks
  PLPLayoutModel plpLayoutModel;
  @Mock
  private Resource resource;
  @Mock
  ResourceResolver resolver;
  @Mock
  PageManager pgMgr;
  @Mock
  Page currentpage;
  private String pagePath = "/content/ag/en/retail";
  private String rootPath = "/content/ag";

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(PLPLayoutModel.class, "resource").set(plpLayoutModel, resource);
    MemberModifier.field(PLPLayoutModel.class, "categoryId").set(plpLayoutModel, "testID");
    Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
    Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pgMgr);
    Mockito.when(pgMgr.getContainingPage(resource)).thenReturn(currentpage);
    Mockito.when(currentpage.getPath()).thenReturn(pagePath);
    Mockito.when(currentpage.getAbsoluteParent(4)).thenReturn(currentpage);
    PowerMockito.mockStatic(EcommConfigurationUtils.class);
    Mockito.when(EcommConfigurationUtils.getRootCatgoryPagePath()).thenReturn(rootPath);
  }

  @Test
  public void testPLPLayoutModel() {
    plpLayoutModel.init();
    String cateGoryPath = plpLayoutModel.getRelCategoryPath();
    String categoryId = plpLayoutModel.getCategoryId();
    Assert.assertEquals("/content/ag/en/retail", cateGoryPath);
    plpLayoutModel.setRelCategoryPath(rootPath);
    Assert.assertEquals(rootPath, plpLayoutModel.getRelCategoryPath());
    Assert.assertEquals("testID", categoryId);
  }
}
