package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.constants.Constants;
import com.mattel.play.core.pojos.ProductTilePojo;
import com.mattel.play.core.services.ProductGalleryAndLandingService;

public class ProductDetailTest {

	ProductDetail productDetail;
	Resource resource;
	Page page ;
	PageManager pageManager;
	ProductGalleryAndLandingService productGalleryAndLandingService;
	ProductTilePojo productDetails = new ProductTilePojo();
	String productPath = "";
	String pageURL = "";
	ResourceResolver resourceResolver;
	List<String> productTags = new ArrayList<>();
	@Before
	public void setUp() {
		productDetail = new ProductDetail();
		resource = Mockito.mock(Resource.class);
		pageManager=Mockito.mock(PageManager.class);
		page =Mockito.mock(Page.class);
		productGalleryAndLandingService = Mockito.mock(ProductGalleryAndLandingService.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		productDetail.setResource(resource);
		productDetail.setProductGalleryAndLandingService(productGalleryAndLandingService);
		productDetail.setProductPath(productPath);
		productDetails.setage("");
		productDetail.setProductDetails(productDetails);
		productTags.add("");		
		productDetails.setProductTags(productTags);
		productGalleryAndLandingService.setTileAltTextProp("");
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(resource.getPath()).thenReturn(productPath);	
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getPath()).thenReturn(pageURL);
		Mockito.when(resource.getPath().replace(Constants.JCR_PRODUCT_NODE, "")).thenReturn("");
		Mockito.when(productGalleryAndLandingService.getProductTileDetails(productPath,
					resource.getResourceResolver(), resource.getPath().replace(Constants.JCR_PRODUCT_NODE, ""), true,productDetails, true)).thenReturn(productDetails);
	}
	@Test
	public void init() {
		productDetail.init();
	}
	@Test
	public void getAnalyticsCategories() {
		productDetail.getAnalyticsCategories();
	}
	@Test
	public void setAnalyticsCategories() {
		productDetail.setAnalyticsCategories("");
	}
	@Test
	public void getProductDetails() {
		productDetail.getProductDetails();
	}
	@Test
	public void isEnglishLocale() {
		productDetail.isEnglishLocale();
	}
	@Test
	public void setEnglishLocale() {
		productDetail.setEnglishLocale(true);
	}
}
