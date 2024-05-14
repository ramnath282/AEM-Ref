package com.mattel.fisherprice.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.fisherprice.core.constants.Constants;
import com.mattel.fisherprice.core.services.MultifieldReader;
import com.mattel.fisherprice.core.utils.FisherPriceConfigurationUtils;
import com.mattel.fisherprice.core.utils.FisherPriceUtils;
import com.mattel.fisherprice.core.utils.PropertyReaderUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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

import javax.jcr.Node;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(PowerMockRunner.class) @PrepareForTest({ FisherPriceConfigurationUtils.class,
    PropertyReaderUtils.class,
    FisherPriceUtils.class }) public class TargetRecommmendationModelTest {

  @InjectMocks TargetRecommendationModel targetRecommendationModel;

  @Mock SlingHttpServletRequest request;

  @Mock Node entityParameters;

  @Mock Resource resource;

  @Mock ResourceResolver res;

  @Mock PageManager pagemanager;

  @Mock Page page;

  @Mock ValueMap pageProperties;

  @Mock MultifieldReader multifieldReader;

  Set<Map.Entry<String, ValueMap>> entryset = new HashSet<>();

  @Mock Map<String, ValueMap> multifieldProperty;

  @Before public void setUp() throws Exception {
    MemberModifier.field(TargetRecommendationModel.class, "mboxValue")
        .set(targetRecommendationModel, "mboxValue");
    MemberModifier.field(TargetRecommendationModel.class, "request")
        .set(targetRecommendationModel, request);
    MemberModifier.field(TargetRecommendationModel.class, "isItProductMbox")
        .set(targetRecommendationModel, "false");
    PowerMockito.mockStatic(FisherPriceConfigurationUtils.class);
    PowerMockito.mockStatic(PropertyReaderUtils.class);
    PowerMockito.mockStatic(FisherPriceUtils.class);
    Mockito.when(request.getAttribute(Constants.COMPONENT_ID)).thenReturn("232343");
    Mockito.when(resource.getResourceResolver()).thenReturn(res);
    Mockito.when(res.adaptTo(PageManager.class)).thenReturn(pagemanager);
    Mockito.when(pagemanager.getContainingPage(resource)).thenReturn(page);
    Mockito.when(FisherPriceUtils.getInheritedProperty(page, "atProperty"))
        .thenReturn("1ac37e38-4f08-5480-d275-1c63faf46400");
    Mockito.when(page.getProperties()).thenReturn(pageProperties);
    Mockito.when(pageProperties.get("articleId", String.class)).thenReturn("article-1");
  }

  @Test public void testInit() {
    targetRecommendationModel.init();
  }

  @Test public void getMboxValue() {
    targetRecommendationModel.getMboxValue();
  }

  @Test public void getAtProperty() {
    targetRecommendationModel.getAtProperty();
  }

  @Test public void getEntityParameters() {
    targetRecommendationModel.getEntityParameters();
  }

  @Test public void getParamsString() {
    targetRecommendationModel.getParamsString();
  }

  @Test public void getComponentId() {
    targetRecommendationModel.getComponentId();
  }

  @Test public void getEntityParametersList() {
    targetRecommendationModel.getEntityParametersList();
  }

}
