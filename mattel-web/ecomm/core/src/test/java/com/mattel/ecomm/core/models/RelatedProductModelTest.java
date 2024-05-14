package com.mattel.ecomm.core.models;

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

import com.mattel.ecomm.core.utils.EcomUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcomUtil.class })
public class RelatedProductModelTest {

  @InjectMocks
  private RelatedProductModel relatedProductModel;

  @Mock
  private Resource resource;
 

  @Before
  public void setup() throws Exception {
    PowerMockito.mockStatic(EcomUtil.class);
    MemberModifier.field(RelatedProductModel.class, "resource").set(relatedProductModel, resource);
    MemberModifier.field(RelatedProductModel.class, "popularRecordsTitle").set(relatedProductModel, "popularRecordsTitle");
    MemberModifier.field(RelatedProductModel.class, "landingPageUrl").set(relatedProductModel, "landingPageUrl");
    MemberModifier.field(RelatedProductModel.class, "relatedArticleHeading").set(relatedProductModel, "relatedArticleHeading");
    MemberModifier.field(RelatedProductModel.class, "relatedProductHeading").set(relatedProductModel, "relatedProductHeading");
    MemberModifier.field(RelatedProductModel.class, "articlePageUrl").set(relatedProductModel, "articlePageUrl");
    
  }

  @Test
  public void testInit() throws Exception {
      Mockito.when(EcomUtil.checkLink(Mockito.anyString(), Mockito.any(Resource.class))).thenReturn("Any String");
      relatedProductModel.init();
  }
  
  @Test
  public void testgettersAndSetters() throws Exception {
      relatedProductModel.getArticlePageUrl();
      relatedProductModel.getLandingPageUrl();
      relatedProductModel.getPopularRecordsTitle();
      relatedProductModel.getRelatedArticleHeading();
      relatedProductModel.getRelatedProductHeading();
  }



}
