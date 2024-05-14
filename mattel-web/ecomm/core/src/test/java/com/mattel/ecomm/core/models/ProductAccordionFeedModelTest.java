package com.mattel.ecomm.core.models;

import java.util.HashMap;
import java.util.Map;

import javax.jcr.Node;

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
import com.mattel.ecomm.core.services.MultifieldReader;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommHelper.class })
public class ProductAccordionFeedModelTest {
	@InjectMocks
	private ProductAccordionFeedModel productAccordionFeedModel;
	@Mock
	private Resource resource;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private Page currentPage;
	@Mock
	private ResourceResolver resolver;
	@Mock
	private Node productAccordian;
	@Mock
	MultifieldReader multifieldReader;
	String path = "/content/fisher-price/gb/en-gb/home/product/productfinder/product-FXC37.html";
	RequestPathInfo requestPathInfo;
	PageManager pageManager;
	ProductService productService;
	String commerceProductPath;
	ValueMap productProperties;
	Map<String, ValueMap> multifieldProperty;
	Map.Entry<String, ValueMap> entry;
	ValueMap valMap;
	Object cType;
	Object cValue;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws IllegalAccessException {
		productService = Mockito.mock(ProductService.class);
		MemberModifier.field(ProductAccordionFeedModel.class, "resource").set(productAccordionFeedModel, resource);
		MemberModifier.field(ProductAccordionFeedModel.class, "request").set(productAccordionFeedModel, request);
		MemberModifier.field(ProductAccordionFeedModel.class, "multifieldReader").set(productAccordionFeedModel,
				multifieldReader);
		MemberModifier.field(ProductAccordionFeedModel.class, "productAccordian").set(productAccordionFeedModel,
				productAccordian);
		multifieldProperty = new HashMap<>();
		entry = Mockito.mock(Map.Entry.class);
		valMap = Mockito.mock(ValueMap.class);
		multifieldProperty.put("", valMap);
		pageManager = Mockito.mock(PageManager.class);
		commerceProductPath = "/var/commerce/products/fisher-price/en-gb/product_FXC37";
		productProperties = Mockito.mock(ValueMap.class);
		productAccordionFeedModel.setProductService(productService);
		Mockito.when(resource.getPath()).thenReturn(path);
		requestPathInfo = Mockito.mock(RequestPathInfo.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage(path)).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(Mockito.anyInt())).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(2).hasChild("language-masters")).thenReturn(true);
		Mockito.when(currentPage.getAbsoluteParent(2).getName()).thenReturn("fisher-price");
		Mockito.when(productService.productProperties(commerceProductPath, resource)).thenReturn(productProperties);
		Mockito.when(multifieldReader.propertyReader(productAccordian)).thenReturn(multifieldProperty);
		Mockito.when(entry.getValue()).thenReturn(valMap);
		Mockito.when(entry.getValue().get("productAccordTitle", String.class)).thenReturn("productAccordTitle");
	}

	@Test
	public void init() {
		productAccordionFeedModel.init();
	}

	@Test
	public void getProductPropertiesList() {
		Mockito.when(request.getRequestURI()).thenReturn(path);
		productAccordionFeedModel.getProductPropertiesList();
	}

	@Test
	public void getProductPropertiesListLanguageMasters() {
		String lmPath = "/content/fisher-price/language-masters/en/home/product/productfinder/product-FXC37.html";
		Mockito.when(request.getRequestURI()).thenReturn(lmPath);
		Mockito.when(pageManager.getPage(lmPath)).thenReturn(currentPage);
		productAccordionFeedModel.getProductPropertiesList();
	}
}
