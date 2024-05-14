package com.mattel.ecomm.core.models;

import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

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
public class ProductFeaturesModelTest {
	@InjectMocks
	private ProductFeaturesModel productFeaturesModel;
	@Mock
	private SlingHttpServletRequest request;
	@Mock
	private Resource resource;
	@Mock
	private Page currentPage;
	@Mock
	private ResourceResolver resolver;
	@Mock
	private Node varProductNode;
	RequestPathInfo requestPathInfo;
	ProductService productService;
	PageManager pageManager;
	String commerceProductPath;
	ValueMap productProperties;
	String path = "/content/fisher-price/gb/en-gb/home/product/productfinder/product-FXC37.html";
	List<ProductFeaturePojo> productDescription;
	@Mock
	Resource commerceResource;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() throws IllegalAccessException, RepositoryException {
		productService = Mockito.mock(ProductService.class);
		pageManager = Mockito.mock(PageManager.class);
		productFeaturesModel.setProductService(productService);
		commerceProductPath = "/var/commerce/products/fisher-price/en-gb/product_FXC37";
		productProperties = Mockito.mock(ValueMap.class);
		productDescription = Mockito.mock(List.class);		
		MemberModifier.field(ProductFeaturesModel.class, "resource").set(productFeaturesModel, resource);
		MemberModifier.field(ProductFeaturesModel.class, "request").set(productFeaturesModel, request);
		Mockito.when(resource.getPath()).thenReturn(path);
		Mockito.when(resource.getResourceResolver()).thenReturn(resolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(currentPage);
		Mockito.when(pageManager.getPage(path)).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(Mockito.anyInt())).thenReturn(currentPage);
		Mockito.when(currentPage.getAbsoluteParent(2).hasChild("language-masters")).thenReturn(true);
		Mockito.when(currentPage.getAbsoluteParent(2).getName()).thenReturn("fisher-price");
		requestPathInfo = Mockito.mock(RequestPathInfo.class);
		Mockito.when(request.getRequestURI()).thenReturn(path);
		Mockito.when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		Mockito.when(request.getRequestPathInfo().getSelectorString()).thenReturn("product-FXC37");
		Mockito.when(productService.productProperties(commerceProductPath, resource)).thenReturn(productProperties);
		Mockito.when(productProperties.containsKey("jcr:description")).thenReturn(true);
		Mockito.when(productService.getProductFeatures(productProperties, productDescription, "jcr:description"))
				.thenReturn(productDescription);
		Mockito.when(currentPage.getProperties()).thenReturn(productProperties);
		Mockito.when(currentPage.getProperties().get("productFeaturesTitle", String.class)).thenReturn("title");
		Mockito.when(resolver.getResource(commerceProductPath)).thenReturn(commerceResource);
		Mockito.when(commerceResource.adaptTo(Node.class)).thenReturn(varProductNode);
		Mockito.when(varProductNode.hasNode("assets")).thenReturn(true);
		Mockito.when(varProductNode.getNode("assets")).thenReturn(varProductNode);
		Mockito.when(varProductNode.hasNode("asset0")).thenReturn(true);
		Mockito.when(varProductNode.getNode("asset0")).thenReturn(varProductNode);
		
	}

	@Test
	public void init() {
		productFeaturesModel.init();
	}

	@Test
	public void getProductSKUId() {
		productFeaturesModel.getProductSKUId();
	}

	@Test
	public void getProductFeaturesTitle() {
		productFeaturesModel.getProductFeaturesTitle();
	}

	@Test
	public void getProductDescription() {
		productFeaturesModel.getProductDescription();
	}

}
