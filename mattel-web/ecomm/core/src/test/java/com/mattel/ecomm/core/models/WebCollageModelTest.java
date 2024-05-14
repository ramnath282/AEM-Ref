package com.mattel.ecomm.core.models;

import static org.junit.Assert.assertEquals;

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

@RunWith(PowerMockRunner.class)
@PrepareForTest({ EcommHelper.class })
public class WebCollageModelTest {
	@InjectMocks
	private WebCollageModel webCollageModel;
	@Mock
	private Resource resource;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private ResourceResolver resolver;
	PageManager pageManager;
	RequestPathInfo requestPathInfo;
	Page page;
	Page parentProductPage;
	ValueMap pageProperties;

	@Before
	public void setup() throws IllegalAccessException {
		MemberModifier.field(WebCollageModel.class, "resource").set(webCollageModel, resource);
		MemberModifier.field(WebCollageModel.class, "request").set(webCollageModel, request);
		pageManager = Mockito.mock(PageManager.class);
		requestPathInfo = Mockito.mock(RequestPathInfo.class);
		page = Mockito.mock(Page.class);
		parentProductPage = page;
		pageProperties = Mockito.mock(ValueMap.class);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(resource.getPath())
				.thenReturn("/content/fisher-price/gb/en-gb/home/product/productfinder/product-FXC37");
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(request.getRequestPathInfo().getSelectorString()).thenReturn("product-FXC37");
		Mockito.when(request.getRequestURI()).thenReturn("/content/fisher-price/gb/en-gb/home/product/productfinder/product-FXC37.html");
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(parentProductPage);
		Mockito.when(parentProductPage.getProperties()).thenReturn(pageProperties);
		Mockito.when(parentProductPage.getProperties().get("webCollageSiteId", String.class))
				.thenReturn("webCollageSiteId");		
		
		Mockito.when(page.getProperties().get("webCollageProductLevelConfig", String.class)).thenReturn("true");
	}

	@Test
	public void init() {
		//webCollageModel.init();
	}

	@Test
	public void getProductSKUId() {
		webCollageModel.getProductSKUId();
	}

	@Test
	public void setProductSKUId() {
		webCollageModel.setProductSKUId("productSKUId");
	}

	@Test
	public void getSiteId() {
		webCollageModel.getSiteId();
	}

	@Test
	public void setSiteId() {
		webCollageModel.setSiteId("siteId");
	}

	@Test
	public void setEnableWebCollage() {
		webCollageModel.setEnableWebCollage(true);
	}

	@Test
	public void setDisableWCMobile() {
		webCollageModel.setDisableWCMobile(false);
	}
	
	@Test
	public void getDisableWebCollageProductLevel() {
		webCollageModel.init();
		assertEquals("true", webCollageModel.getDisableWebCollageProductLevel());
	}

}
