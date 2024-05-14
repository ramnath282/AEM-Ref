package com.mattel.ecomm.core.models;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.ecomm.core.helper.EcommHelper;
import com.mattel.ecomm.core.interfaces.ProductService;
import com.mattel.ecomm.core.pojos.ProductFeaturePojo;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommHelper.class })
public class PDPSeoModelTest {
	@InjectMocks
	private PDPSeoModel pdpSeoModel;
	@Mock
	private Resource resource;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	Page currentPage;
	@Mock
	private ResourceResolver resolver;
	ProductService productService;
	RequestPathInfo requestPathInfo;
	String path = "/content/fisher-price/gb/en-gb/home/product/productfinder/product-FXC37.html";
	PageManager pageManager;
	String commerceProductPath;
	ValueMap productProperties;
	List<ProductFeaturePojo> productDescription;
	String metaDescription = StringUtils.EMPTY;

	@Before
	public void setup() throws IllegalAccessException {
		MemberModifier.field(PDPSeoModel.class, "resource").set(pdpSeoModel, resource);
		MemberModifier.field(PDPSeoModel.class, "request").set(pdpSeoModel, request);
		commerceProductPath = "/var/commerce/products/fisher-price/en-gb/product_FXC37";
		productService = Mockito.mock(ProductService.class);
		productProperties = Mockito.mock(ValueMap.class);
		pdpSeoModel.setProductService(productService);
		pageManager = Mockito.mock(PageManager.class);
		Mockito.when(resource.getPath()).thenReturn(path);
		requestPathInfo = Mockito.mock(RequestPathInfo.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(request.getRequestURI()).thenReturn(path);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage(path)).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(2)).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(2).hasChild("language-masters")).thenReturn(true);
		Mockito.when(currentPage.getAbsoluteParent(2).getName()).thenReturn("fisher-price");
		Mockito.when(productService.productProperties(commerceProductPath, resource)).thenReturn(productProperties);
		Mockito.when(productProperties.containsKey("seoMetaDescription")).thenReturn(true);
		Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "seoMetaDescription")).thenReturn(metaDescription);
		Mockito.when(productProperties.containsKey("jcr:description")).thenReturn(true);
		productDescription = new LinkedList<>();
		Mockito.when( productService.getProductFeatures(productProperties, productDescription,
				"jcr:description")).thenReturn(productDescription);
	}

	@Test
	public void init() {
		pdpSeoModel.init();
	}

	@Test
	public void getTitle() {
		pdpSeoModel.getTitle();
	}

	@Test
	public void getMetaDescription() {
		pdpSeoModel.getMetaDescription();
	}

	@Test
	public void getCanonicalUrl() {
		pdpSeoModel.getCanonicalUrl();
	}

	@Test
	public void getSeoKeywords() {
		pdpSeoModel.getSeoKeywords();
	}

	@Test
	public void setTitle() {
		pdpSeoModel.setTitle("title");
	}

	@Test
	public void setMetaDescription() {
		pdpSeoModel.setMetaDescription("setMetaDescription");
	}

	@Test
	public void setCanonicalUrl() {
		pdpSeoModel.setCanonicalUrl("setCanonicalUrl");
	}

	@Test
	public void setSeoKeywords() {
		pdpSeoModel.setSeoKeywords("setSeoKeywords");
	}

}
