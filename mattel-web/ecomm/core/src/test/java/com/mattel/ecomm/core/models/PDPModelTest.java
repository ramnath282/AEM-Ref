package com.mattel.ecomm.core.models;

import java.util.Iterator;
import java.util.List;

import javax.jcr.Node;

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

import com.day.cq.wcm.api.Page;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.services.MultifieldReader;
import com.mattel.global.core.utils.NavigationUtil;
import com.mattel.ecomm.coreservices.core.constants.Constant;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ NavigationUtil.class, EcommHelper.class })
public class PDPModelTest {

  @InjectMocks
  private PDPModel pdpModel;

  @Mock
  private SlingHttpServletRequest request;

  @Mock
  private ProductService productService;

  @Mock
  Resource resource, rootResource, childResource;

  @Mock
  Page currentPage, productPage;

  @Mock
  private MultifieldReader multifieldReader;

  @Mock
  private ResourceResolver resolver;

  @Mock
  ValueMap productProperties;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(EcommHelper.class);
    Page parentProductPage = Mockito.mock(Page.class);
    Page parentPage = Mockito.mock(Page.class);
    Iterator<Resource> childItr = Mockito.mock(Iterator.class);
    Iterator<Resource> rootChildItr = Mockito.mock(Iterator.class);
    Resource rootChildResource = Mockito.mock(Resource.class);
    @SuppressWarnings("rawtypes")
    List prodDecription = Mockito.mock(List.class);
    Node node = Mockito.mock(Node.class);
    
    MemberModifier.field(PDPModel.class, "resource").set(pdpModel, resource);
    Mockito.when(resource.getPath()).thenReturn(
        "/content/fisher-price/us/en-us/home/product/productfinder/on-the-go-baby-dome-drf13");
    MemberModifier.field(PDPModel.class, "currentPage").set(pdpModel, currentPage);
    Mockito.when(currentPage.getAbsoluteParent(5)).thenReturn(parentProductPage);
    Mockito.when(EcommHelper.fetchProductSKUId(request)).thenReturn("drf13");
    Mockito.when(productService.fetchProductProperties(parentProductPage, resource, "drf13"))
        .thenReturn(productProperties);
    Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "jcr:title"))
        .thenReturn("Page-Title");
    Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "price")).thenReturn("100");
    Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "badge")).thenReturn("badge");
    Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "ratingAvg"))
        .thenReturn("4.5");
    Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "ageGrade"))
        .thenReturn("ageGrade");
    Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "productReviewCount"))
        .thenReturn("111191");
    Mockito.when(productProperties.containsKey("jcr:description")).thenReturn(true);
    Mockito.when(productService.getProductFeatures(productProperties, null, "jcr:description"))
        .thenReturn(prodDecription);
    Mockito.when(request.getResourceResolver()).thenReturn(resolver);
    Mockito.when(currentPage.getParent()).thenReturn(parentPage);
    Mockito.when(parentPage.getPath()).thenReturn("/testpath");
    Mockito.when(resolver.getResource(Mockito.anyString())).thenReturn(rootResource);
    Mockito.when(rootResource.listChildren()).thenReturn(childItr);
    Mockito.when(childItr.hasNext()).thenReturn(true, false);
    Mockito.when(childItr.next()).thenReturn(childResource);
    Mockito.when(childResource.isResourceType(Constant.AGE_GRADE_MAPPING_RESOURCE_PATH))
        .thenReturn(true);
    Mockito.when(childResource.getPath()).thenReturn("/childresourcepath");
    Mockito.when(rootResource.listChildren()).thenReturn(rootChildItr);
    Mockito.when(rootChildItr.hasNext()).thenReturn(false,false);
    Mockito.when(rootChildItr.next()).thenReturn(rootChildResource);
    Mockito.when(resource.adaptTo(Node.class)).thenReturn(node);
  }

  @Test
  public void initWithProductRecallYes() {
    Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "productRecall"))
        .thenReturn("Yes");
    pdpModel.init();
  }

  @Test
  public void initWithProductRecallNo() {
    Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "productRecall"))
        .thenReturn("No");
    pdpModel.init();
  }

  @Test
  public void getReadMore() {
    pdpModel.getReadMore();
  }

  @Test
  public void getSocialIcons() {
    pdpModel.getSocialIcons();
  }

  @Test
  public void getIsCompatible() {
    pdpModel.getIsCompatible();
  }

  @Test
  public void getPriceSpiderConfigID() {
    pdpModel.getPriceSpiderConfigID();
  }

  @Test
  public void getCompatibleURL() {
    pdpModel.getCompatibleURL();
  }

  @Test
  public void getCompatibleUrlTarget() {
    pdpModel.getCompatibleUrlTarget();
  }

  @Test
  public void getProductSKUId() {
    pdpModel.getProductSKUId();
  }

  @Test
  public void getToolTipDescription() {
    pdpModel.getToolTipDescription();
  }

  @Test
  public void getPriceSpiderMoreOptionConfigID() {
    pdpModel.getPriceSpiderMoreOptionConfigID();
  }

  @Test
  public void getPriceSpiderGetItOnSaleConfigID() {
    pdpModel.getPriceSpiderGetItOnSaleConfigID();
  }

  @Test
  public void getAgeGradeText() {
    pdpModel.getAgeGradeText();
  }

  @Test
  public void getWarningTitle() {
    pdpModel.getWarningTitle();
  }

  @Test
  public void getProductReviewCount() {
    pdpModel.getProductReviewCount();
  }

  @Test
  public void getProductTitle() {
    pdpModel.getProductTitle();
  }

  @Test
  public void getWarningMessage() {
    pdpModel.getWarningMessage();
  }

  @Test
  public void getProductAgeGrade() {
    pdpModel.getProductAgeGrade();
  }

  @Test
  public void getRatingAvg() {
    pdpModel.getRatingAvg();
  }

  @Test
  public void getAnalyticsRatingAvg() {
    pdpModel.getAnalyticsRatingAvg();
  }

  @Test
  public void getEnableCustomerReview() {
    pdpModel.getEnableCustomerReview();
  }

  @Test
  public void getProductDescription() {
    pdpModel.getProductDescription();
  }

  @Test
  public void getCategory() {
    pdpModel.getCategory();
  }

  @Test
  public void getBadge() {
    pdpModel.getBadge();
  }

  @Test
  public void getGrs() {
    pdpModel.getGrs();
  }

  @Test
  public void getRegion() {
    pdpModel.getRegion();
  }

  @Test
  public void getSubCategory() {
    pdpModel.getSubCategory();
  }

  @Test
  public void getLanguage() {
    pdpModel.getLanguage();
  }

  @Test
  public void getListPriceCurrency() {
    pdpModel.getListPriceCurrency();
  }

  @Test
  public void getProductURL() {
    pdpModel.getProductURL();
  }

  @Test
  public void getAnalyticsBrand() {
    pdpModel.getAnalyticsBrand();
  }

  @Test
  public void getEnableAgeGrade() {
    pdpModel.getEnableAgeGrade();
  }

  @Test
  public void getEnableProductPrice() {
    pdpModel.getEnableProductPrice();
  }

  @Test
  public void getProductPrice() {
    pdpModel.getProductPrice();
  }

  @Test
  public void getProductRecall() {
    pdpModel.getProductRecall();
  }

  @Test
  public void getProductRecallDateTime() {
    pdpModel.getProductRecallDateTime();
  }

  @Test
  public void getProductRecallMessage() {
    pdpModel.getProductRecallMessage();
  }
}
