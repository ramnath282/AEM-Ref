package com.mattel.ecomm.core.models;

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
import com.mattel.ecomm.core.interfaces.CurrencyMappingService;
import com.mattel.ecomm.core.interfaces.ProductService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommHelper.class })
public class CurrencyMappingTest {
	@InjectMocks
	private CurrencyMapping currencyMapping;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private Resource resource;
	@Mock
	private Page currentPage;
	@Mock
	private ResourceResolver resolver;
	RequestPathInfo requestPathInfo;
	ProductService productService;
	CurrencyMappingService currencyMappingService;
	PageManager pageManager;
	String commerceProductPath;
	ValueMap productProperties;
	String path = "/content/fisher-price/gb/en-gb/home/product/productfinder/product-FXC37.html";
	String currencyType;

	@Before
	public void setup() throws IllegalAccessException {
		productService = Mockito.mock(ProductService.class);
		currencyMappingService = Mockito.mock(CurrencyMappingService.class);
		pageManager = Mockito.mock(PageManager.class);
		currencyMapping.setCurrencyMappingService(currencyMappingService);
		currencyMapping.setProductService(productService);
		commerceProductPath = "/var/commerce/products/fisher-price/en-gb/product_FXC37";
		productProperties = Mockito.mock(ValueMap.class);
		currencyType = "USD";
		MemberModifier.field(CurrencyMapping.class, "resource").set(currencyMapping, resource);
		MemberModifier.field(CurrencyMapping.class, "request").set(currencyMapping, request);
		requestPathInfo = Mockito.mock(RequestPathInfo.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(resource.getPath()).thenReturn(path);
		Mockito.when(request.getRequestURI()).thenReturn(path);
		Mockito.when(request.getRequestPathInfo().getSelectorString()).thenReturn("product-FXC37");
		Mockito.when(currentPage.getContentResource()).thenReturn(resource);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage(path)).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(Mockito.anyInt())).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(2).hasChild("language-masters")).thenReturn(true);
		Mockito.when(currentPage.getAbsoluteParent(2).getName()).thenReturn("fisher-price");
		Mockito.when(productProperties.containsKey("listPriceCurrency")).thenReturn(true);
		Mockito.when(productProperties.get("listPriceCurrency", String.class)).thenReturn(currencyType);
		Mockito.when(EcommHelper.getValueMapNodeValue(productProperties, "listPriceCurrency")).thenReturn(currencyType);
		Mockito.when(productService.productProperties(commerceProductPath, resource)).thenReturn(productProperties);
		Mockito.when(currencyMappingService.getCurrencyDetails(currencyType, resource)).thenReturn("&#36;");

	}

	@Test
	public void init() {
		currencyMapping.init();
	}

	@Test
	public void getCurrencyType() {
		currencyMapping.getCurrencyType();
	}

	@Test
	public void setCurrencyType() {
		currencyMapping.setCurrencyType(commerceProductPath);
	}

	@Test
	public void getCurrencyCode() {
		currencyMapping.getCurrencyCode();
	}

	@Test
	public void setCurrencyCode() {
		currencyMapping.setCurrencyCode("currencyCode");
	}
}
