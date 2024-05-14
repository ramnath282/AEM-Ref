package com.mattel.play.core.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagManager;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.mattel.play.core.helper.PlayHelper;
import com.mattel.play.core.pojos.ProductTilePojo;
import com.mattel.play.core.pojos.TilePojo;
import com.mattel.play.core.services.ProductGalleryAndLandingService;
import com.mattel.play.core.services.TileGalleryAndLandingService;
import com.mattel.play.core.utils.PropertyReaderUtils;


@RunWith(PowerMockRunner.class)
@PrepareForTest({PlayHelper.class, PropertyReaderUtils.class})
public class GalleryModelTest {

	GalleryModel galleryModel;
	List<TilePojo> characterList = new ArrayList<>();
	Resource resource;
	Node node;
	Page page = null;
	PageManager pageManager;
	ResourceResolver resourceResolver;
	Boolean linkNavigationCheck = true;
	TileGalleryAndLandingService tileGalleryAndLandingService;
	String[] galleryCategory;
	TagManager tagManager;
	Tag galleryTag;
	List<ProductTilePojo> categoryProductsList = new LinkedList<>();
	List<ProductTilePojo> allProducts;
	ProductGalleryAndLandingService productGalleryAndLandingService;
	List<TilePojo> charList;
	TilePojo t = new TilePojo();
	String galleryFor = "";
	ValueMap nodeValues;
	private String landinggridPath = "";
	private List<ProductTilePojo> landingProductList = new ArrayList<>();
	String[] pages;
	
	@Before
	public void setUp() throws RepositoryException {
		
		galleryModel = new GalleryModel();
		resource = Mockito.mock(Resource.class);
		resourceResolver = Mockito.mock(ResourceResolver.class);
		pageManager = Mockito.mock(PageManager.class);
		page = Mockito.mock(Page.class);
		tileGalleryAndLandingService = Mockito.mock(TileGalleryAndLandingService.class);
		productGalleryAndLandingService = Mockito.mock(ProductGalleryAndLandingService.class);
		node = Mockito.mock(Node.class);
		tagManager = Mockito.mock(TagManager.class);
		galleryTag = Mockito.mock(Tag.class);
		categoryProductsList = new ArrayList<>();
		allProducts = new ArrayList <>();
		charList = new LinkedList<>();
		nodeValues = Mockito.mock(ValueMap.class);
		PowerMockito.mockStatic(PropertyReaderUtils.class);
		galleryModel.setResource(resource);
		galleryModel.setLinkNavigationCheck(linkNavigationCheck);
		galleryModel.setTileGalleryAndLandingService(tileGalleryAndLandingService);
		galleryModel.setProductGalleryAndLandingService(productGalleryAndLandingService);
		galleryModel.setGalleryFor(galleryFor);
		galleryCategory = new String[5]; 
		galleryCategory[0] = "";
		pages = new String[5];
		pages[0] = "";
		galleryModel.setPages(pages);
		galleryModel.setGalleryCategory(galleryCategory);
		galleryModel.setNode(node);
		galleryModel.setResolver(resourceResolver);
		charList.add(t);
		galleryModel.setLandinggridPath(landinggridPath);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resource.getResourceResolver().adaptTo(PageManager.class)).thenReturn(pageManager);
		Mockito.when(pageManager.getContainingPage(resource)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5)).thenReturn(page);
		Mockito.when(page.getAbsoluteParent(5).getPath()).thenReturn("");
		Mockito.when(tileGalleryAndLandingService.getAllTiles("", "","landing", true)).thenReturn(characterList);
		Mockito.when(resource.getResourceResolver()).thenReturn(resourceResolver);
		Mockito.when(resource.getResourceResolver().adaptTo(TagManager.class)).thenReturn(tagManager);
		Mockito.when(tagManager.resolve(galleryCategory[0])).thenReturn(galleryTag);
		Mockito.when("" + PropertyReaderUtils.getProductPath()).thenReturn("");
		Mockito.when(productGalleryAndLandingService.getTilesByDate("", linkNavigationCheck)).thenReturn(allProducts);
		Mockito.when(node.getPath()).thenReturn("");
		Mockito.when(resourceResolver.getResource("")).thenReturn(resource);
		Mockito.when(resource.adaptTo(ValueMap.class)).thenReturn(nodeValues);
		
		
	}
	
	@Test
	public void init(){
		galleryModel.init();
	}
	@Test
	public void initWhenGalleryForProducts() throws IllegalArgumentException, IllegalAccessException
	{
		galleryModel.setGalleryFor("characters");
		galleryModel.init();
		galleryModel.setGalleryFor("games");
		galleryModel.init();
		galleryModel.setGalleryFor("products");
		galleryModel.init();
		
		PowerMockito.mockStatic(PlayHelper.class);
		Resource mockedResource = Mockito.mock(Resource.class);
		MemberModifier.field(GalleryModel.class, "resource").set(galleryModel, mockedResource);
		Mockito.when(mockedResource.getPath()).thenReturn("/content/play/gallery");
		ResourceResolver mockedResourceResolver = Mockito.mock(ResourceResolver.class);
		Mockito.when(mockedResource.getResourceResolver()).thenReturn(mockedResourceResolver);
		Mockito.when(PlayHelper.getHomePagePath(mockedResource)).thenReturn("/content/play");
		Mockito.when(mockedResourceResolver.getResource("/content/play/jcr:content/root/")).thenReturn(mockedResource);
		galleryModel.init();
	}
	@Test
	public void getCategoryCharacterList() {
		galleryModel.getCategoryCharacterList();	
	}
	@Test
	public void getCharactesListByDate() {
		galleryModel.getCharactesListByDate();
	}
	@Test
	public void getManualAuthorCharList() {
		galleryModel.getManualAuthorCharList();
	}
	@Test
	public void getByDateProductsList() {
		galleryModel.getByDateProductsList();
	}
	@Test
	public void getLandingProductList() {
		galleryModel.getLandingProductList();
	}
	@Test
	public void getManualProductList() {
		galleryModel.getManualProductList();
	}
	@Test
	public void setManualProductList() {
		galleryModel.setManualProductList(landingProductList);
	}
	@Test
	public void setLandingProductList() {
		galleryModel.setLandingProductList(allProducts);
	}
	@Test
	public void setByDateProductsList() {
		galleryModel.setByDateProductsList(categoryProductsList);
	}
	@Test
	public void getCtaButtonLink() {
		galleryModel.getCtaButtonLink();
	}
	@Test
	public void getLinkNavOption() {
		galleryModel.getLinkNavOption();
	}
	@Test
	public void setLinkNavOption() {
		galleryModel.setLinkNavOption("");
	}
	@Test
	public void setCategoryCharacterList() {
		galleryModel.setCategoryCharacterList(characterList);
	}
	@Test
	public void setCharactesListByDate() {
		galleryModel.setCharactesListByDate(characterList);
	}
	@Test
	public void setCategoryProductsList() {
		galleryModel.setCategoryProductsList(categoryProductsList);
	}
	@Test
	public void getGalleryCategory() {
		galleryModel.getGalleryCategory();
	}
	@Test
	public void getCategoryProductsList() {
		galleryModel.getCategoryProductsList();
	}
	@Test
	public void setCharacterList() {
		galleryModel.setCharacterList(charList);
	}
	@Test
	public void setHomePagePath() {
		galleryModel.setHomePagePath(galleryFor);
	}
}
