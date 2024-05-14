package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.CategoryFilterPojo;
import com.mattel.play.core.pojos.CategoryPojo;
import com.mattel.play.core.services.MultifieldReader;
import com.mattel.play.core.services.TileGalleryAndLandingService;
import com.mattel.play.core.utils.CategoryFilterSlidesUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CategoryFilterSlidesUtils.class, PlayHelper.class})
public class CategoryFilterModelTest {

	CategoryFilterModel categoryFilterModel = new CategoryFilterModel();
	SlingHttpServletRequest request;
	String pagePath = "";
	Node categoryDetail;
	MultifieldReader multifieldReader;
	TileGalleryAndLandingService tileGalleryAndLandingService;
	List<CategoryFilterPojo> catItemsList = new ArrayList<>();
	List<CategoryPojo> categoryList = new ArrayList<>();
	String homePagePath = "";
	ValueMap valueMap;
	HashMap<String, ValueMap> multifieldProperty;
	CategoryFilterPojo categoryItem;
	ResourceResolver resourceResolver;
	String categoryPath;
	Resource currentResource;
	PageManager pageManager;
	Page page;
	Map.Entry<String, ValueMap> entry;
	String[] slideshowValueMapping = new String[1];
	
	@SuppressWarnings("unchecked")
	@Before
    public void setUp() {
	
		multifieldReader = Mockito.mock(MultifieldReader.class);
		categoryFilterModel.setMultifieldReader(multifieldReader);
		categoryDetail = Mockito.mock(Node.class);
		categoryFilterModel.setCategoryDetail(categoryDetail);
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		categoryFilterModel.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		categoryItem = new CategoryFilterPojo();
		categoryPath = "category";
		categoryItem.setCategoryPath(categoryPath);
		valueMap = Mockito.mock(ValueMap.class);
		multifieldProperty = new HashMap<>();
		resourceResolver = Mockito.mock(ResourceResolver.class);
		categoryFilterModel.setCategoryList(categoryList);
		categoryFilterModel.setCatItemsList(catItemsList);
		categoryFilterModel.setHomePagePath(homePagePath);
		request = Mockito.mock(SlingHttpServletRequest.class);
		categoryFilterModel.setRequest(request);
		categoryFilterModel.setPagePath(homePagePath);
		page = Mockito.mock(Page.class);
		catItemsList.add(categoryItem);	
		pageManager = Mockito.mock(PageManager.class);
		currentResource = Mockito.mock(Resource.class);
		entry = Mockito.mock(Map.Entry.class);
		PowerMockito.mockStatic(CategoryFilterSlidesUtils.class);
		PowerMockito.mockStatic(PlayHelper.class);
		catItemsList.add(categoryItem); 
		multifieldProperty.put("", valueMap);
		slideshowValueMapping[0] = "[maxsteel:10]";
		Mockito.when(multifieldReader.propertyReader(categoryDetail)).thenReturn(multifieldProperty);
		Mockito.when(tileGalleryAndLandingService.getCategories(catItemsList)).thenReturn(categoryList);
		Mockito.when(request.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resourceResolver.getResource(pagePath)).thenReturn(currentResource);
		Mockito.when(currentResource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(request.getResource()).thenReturn(currentResource);
		Mockito.when(currentResource.getValueMap()).thenReturn(valueMap);
		Mockito.when(currentResource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getPage(pagePath)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(6)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(6).getPath()).thenReturn(homePagePath); 
		Mockito.when(request.getRequestURI()).thenReturn(homePagePath);
		Mockito.when(request.getRequestURI().split(".html")[0]).thenReturn(homePagePath);
		Mockito.when(entry.getValue()).thenReturn(valueMap);
		Mockito.when(entry.getValue().get("category", String.class)).thenReturn("");
		Mockito.when(CategoryFilterSlidesUtils.getSlideShowValueMapping()).thenReturn(slideshowValueMapping);
		Mockito.when(PlayHelper.getBrandName("")).thenReturn("[maxsteel");
    }

    @Test
    public void init() {

    	categoryFilterModel.init();

    }
    @Test
    public  void getCategoryList() {
    	categoryFilterModel.getCategoryList();
	}
    @Test
	public void getHomePagePath() {
		categoryFilterModel.getHomePagePath();
	}
    @Test
	public void getCategoryFor() {
		categoryFilterModel.getCategoryFor();
	}
    @Test
    public void getCategoryFilterSlidesMappings() {
    	categoryFilterModel.getCategoryFilterSlidesMappings("", "");
    }
    @Test
    public void isDisplayCategory() {
		categoryFilterModel.isDisplayCategory();
	}
    @Test
	public void setDisplayCategory() {
		categoryFilterModel.setDisplayCategory(true);
	}
    @Test
	public void getSlideCount() {
		categoryFilterModel.getSlideCount();
	}
}
