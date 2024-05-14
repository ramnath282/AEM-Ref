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
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@RunWith(PowerMockRunner.class)
public class ReplacementPartsModelTest {
	@InjectMocks
	private ReplacementPartsModel replacementPartsModel;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private Resource resource;
	@Mock
	private ResourceResolver resolver;
	PageManager pageManager;
	RequestPathInfo requestPathInfo;
	ResourceResolver currentResourceResolver;
	String path = "/content/fisher-price/gb/en-gb/home/product/productfinder/product-FXC37.html";
	Page page;
	String property = "reltitle";
	ValueMap pageProperties;

	@Before
	public void setup() throws IllegalAccessException {
		MemberModifier.field(ReplacementPartsModel.class, "resource").set(replacementPartsModel, resource);
		MemberModifier.field(ReplacementPartsModel.class, "request").set(replacementPartsModel, request);
		pageManager = Mockito.mock(PageManager.class);
		currentResourceResolver = resolver;
		pageProperties = Mockito.mock(ValueMap.class);
		page = Mockito.mock(Page.class);
		pageProperties = Mockito.mock(ValueMap.class);
		Mockito.when(resource.getPath()).thenReturn(path);
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(request.getPathInfo()).thenReturn(path);
		Mockito.when(request.getResourceResolver()).thenReturn(resolver);
		Mockito.when(currentResourceResolver.getResource("/content/fisher-price/gb/en-gb/home/product/productfinder"))
				.thenReturn(resource);
		Mockito.when(pageManager.getContainingPage("/content/fisher-price/gb/en-gb/home/product/productfinder"))
				.thenReturn(page);
		requestPathInfo = Mockito.mock(RequestPathInfo.class);
		requestPathInfo = Mockito.mock(RequestPathInfo.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(request.getRequestURI()).thenReturn(path);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(page.getProperties()).thenReturn(pageProperties);
		Mockito.when(page.getProperties().get("relSearchTitle", String.class)).thenReturn(property);
	}

	@Test
	public void init() {
		replacementPartsModel.init();
	}

	@Test
	public void getProductSKUId() {
		replacementPartsModel.getProductSKUId();
	}

	@Test
	public void getEnableReplacementParts() {
		replacementPartsModel.getEnableReplacementParts();
	}
}
