package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
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
import com.mattel.ecomm.core.interfaces.GetProductTypeService;
import com.mattel.ecomm.core.utils.EcomUtil;
import com.mattel.ecomm.coreservices.core.constants.Constant;
import com.mattel.ecomm.coreservices.core.utilities.PropertyReaderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcomUtil.class })
public class ProductDetailBreadcrumbModelTest {

  @InjectMocks
  private ProductDetailBreadcrumbModel productDetailBreadcrumbModel;
  @Mock
  private SlingHttpServletRequest request;
  @Mock
  private Resource resource;
  @Mock
  private PropertyReaderService propertyReaderService;
  @Mock
  private GetProductTypeService getProductType;
  @Mock
  private RequestPathInfo pathInfo;
  @Mock
  private ResourceResolver resolver;
  @Mock
  private PageManager pageManager;
  @Mock
  private Page currentPage;

  private String selectors[] = { "ag_en", "AG", "FRL53" };

  @Before
  public void setUp() throws Exception {
    MemberModifier.field(ProductDetailBreadcrumbModel.class, "request")
            .set(productDetailBreadcrumbModel, request);
    MemberModifier.field(ProductDetailBreadcrumbModel.class, "resource")
            .set(productDetailBreadcrumbModel, resource);
    MemberModifier.field(ProductDetailBreadcrumbModel.class, "propertyReaderService")
            .set(productDetailBreadcrumbModel, propertyReaderService);
    MemberModifier.field(ProductDetailBreadcrumbModel.class, "getProductType")
            .set(productDetailBreadcrumbModel, getProductType);
    PowerMockito.mockStatic(EcomUtil.class);
    Mockito.when(EcomUtil.checkLink(Mockito.any(), Mockito.any()))
            .thenReturn("/content/ag/en/shop/home");
    Mockito.when(EcomUtil.getInheritedProperty(Mockito.any(Resource.class), Mockito.anyString())).thenReturn("inheritedProperty");
    Mockito.when(request.getRequestPathInfo()).thenReturn(pathInfo);
    Mockito.when(currentPage.getPageManager()).thenReturn(pageManager);
    Mockito.when(pathInfo.getSelectors()).thenReturn(selectors);

  }

  @Test
  public void testInit() {
    Map<String, String> properties = new HashMap<String, String>();
    properties.put(Constant.BREADCRUMB_CANONICAL_TAG, " seo_ canonincal");
    properties.put(Constant.PRODUCT_NAME_TAG, "addy-mini-doll-book-frl53");
    Mockito.when(getProductType.getProductType(Mockito.any(), Mockito.any()))
            .thenReturn(properties);
    Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
    Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
    Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
    Mockito.when(currentPage.getAbsoluteParent(3)).thenReturn(currentPage);
    Mockito.when(currentPage.getTitle()).thenReturn("Home");
    Mockito.when(propertyReaderService.getProductCategoryPath(Mockito.any())).thenReturn("ag_en");
    productDetailBreadcrumbModel.init();

    Assert.assertNotNull(productDetailBreadcrumbModel.getBreadcrumbLinks());

  }

}
