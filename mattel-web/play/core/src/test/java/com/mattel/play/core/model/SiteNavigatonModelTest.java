package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.day.cq.wcm.api.Page;
import com.mattel.play.core.pojos.SiteNavigationPojo;
import com.mattel.play.core.services.TileGalleryAndLandingService;

public class SiteNavigatonModelTest {

	SiteNavigationModel siteNavigationModel = new SiteNavigationModel();
	TileGalleryAndLandingService tileGalleryAndLandingService;
	SlingHttpServletRequest request = null;
	String pageUrl = "pageurl";
	Boolean collectAllPages;
	Boolean collectRootPage;
	String brandNavUrl = "";
	Resource resource;
	List<SiteNavigationPojo> navItemsList;
	Page rootPage = null;
	ResourceResolver resourceResolver;
	String currentPath = "";
	
	
	@Before 
    public void setUp() {
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		request = Mockito.mock(SlingHttpServletRequest.class);
		resource = Mockito.mock(Resource.class);
		navItemsList = new ArrayList<>();
		rootPage = Mockito.mock(Page.class);
		siteNavigationModel.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		siteNavigationModel.setRequest(request);
		siteNavigationModel.setResource(resource);
		siteNavigationModel.setCollectAllPages(collectAllPages);
		siteNavigationModel.setCollectAllPages(collectRootPage);
		siteNavigationModel.setPageUrl(pageUrl);
		siteNavigationModel.setBrandNavUrl(brandNavUrl);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(request.getResourceResolver().getResource(pageUrl)).thenReturn(resource);
		Mockito.when(resource.adaptTo(Page.class)).thenReturn(rootPage);
		Mockito.when(request.getPathInfo()).thenReturn(currentPath);
		Mockito.when(tileGalleryAndLandingService.getSiteNavigationDetails(rootPage, collectAllPages,currentPath, collectRootPage)).thenReturn(navItemsList);
		
		
                    

    }

    @Test
    public void init() {

    	siteNavigationModel.init();

    }
    @Test
	public void getNavItemsList() {
    	siteNavigationModel.getNavItemsList();
	}
    @Test
    public void getBrandNavUrl() {
    	siteNavigationModel.getBrandNavUrl();
    }
}
