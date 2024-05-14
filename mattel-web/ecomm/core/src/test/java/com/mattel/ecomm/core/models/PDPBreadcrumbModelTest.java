package com.mattel.ecomm.core.models;

import static org.junit.Assert.assertEquals;

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
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({EcommHelper.class})
public class PDPBreadcrumbModelTest {

    @InjectMocks
    private PDPBreadcrumbModel pdpBreadcrumbModel;
    @Mock
    private SlingHttpServletRequest request;
    @Mock
    private Resource resource;
    @Mock
    ResourceResolver resourceResolver;
    @Mock
    private PageManager pageManager;
    @Mock
    private Page page;
    @Mock
    private Page currentPage;
    @Mock
    EcommHelper ecomHelper;
    @Mock
    private ProductService productService;
    String pagePath = "/content/fisher-price/en";
    String productSKUId = "drf13";
    String canonicalUrl = "brands > baby toys";
    String pageLocale = "en-us";
    String commerceProductPath = "/var/commerce/products/fisher-price/en-us/product_drf13";
    String productTitle = "Test Product";
    String brandName = "fisher-price";
    @Mock
    ResourceResolver res;
    @Mock
    ValueMap productProperties;
    @Mock
    Page parentProductPage;

    @Before
    public void setUp() throws Exception {
        MemberModifier.field(PDPBreadcrumbModel.class, "request").set(pdpBreadcrumbModel, request);
        MemberModifier.field(PDPBreadcrumbModel.class, "resource").set(pdpBreadcrumbModel, resource);
        PowerMockito.mockStatic(EcommHelper.class);
        PowerMockito.when(EcommHelper.convertSpecialCharacters(productTitle)).thenReturn(productTitle);
        PowerMockito.when(EcommHelper.toCamelCase(productTitle)).thenReturn(productTitle);
        Mockito.when(EcommHelper.fetchProductSKUId(request)).thenReturn(productSKUId);
        Mockito.when(resource.getPath()).thenReturn(pagePath);
        Mockito.when(resource.getResourceResolver()).thenReturn(res);
        Mockito.when(res.adaptTo(PageManager.class)).thenReturn(pageManager);
        Mockito.when(productService.getCurrentPagePath(pageManager, resource, request)).thenReturn(page);
        Mockito.when(page.getAbsoluteParent(5)).thenReturn(parentProductPage);
        Mockito.when(parentProductPage.getPath()).thenReturn("/content/fisher-price/us/en-us/home/product/productfinder/product-gbm54");
        Mockito.when(EcommHelper.getPageLocale(parentProductPage.getPath())).thenReturn(pageLocale);
        Mockito.when(EcommHelper.getBrandName(resource)).thenReturn("fisher-price");
        Mockito.when(productService.productProperties(commerceProductPath, resource)).thenReturn(productProperties);
        Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "canonicalCategory")).thenReturn(canonicalUrl);
        Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "jcr:title")).thenReturn(productTitle);
        Mockito.when(productService.fetchProductProperties(parentProductPage, resource, productSKUId)).thenReturn(productProperties);
    }

    @Test
    public void testGetBreadcrumbLinks() {
        pdpBreadcrumbModel.init();
        assertEquals(productTitle,pdpBreadcrumbModel.getList().get(1).getTitle());
    }
}